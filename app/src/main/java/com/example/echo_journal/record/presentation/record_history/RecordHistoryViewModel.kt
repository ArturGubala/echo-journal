package com.example.echo_journal.record.presentation.record_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echo_journal.core.data.database.RoomRecordDataSource
import com.example.echo_journal.core.domain.PlayerState
import com.example.echo_journal.core.domain.audio.AudioPlayer
import com.example.echo_journal.core.domain.audio.AudioRecorder
import com.example.echo_journal.record.presentation.record_history.RecordHistoryState.RecordHolderState
import com.example.echo_journal.utils.InstantFormatter
import com.example.echo_journal.utils.StopWatch
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecordHistoryViewModel(
    private val recordDataSource: RoomRecordDataSource,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer
): ViewModel() {

    private val _state = MutableStateFlow(RecordHistoryState())
    val state = _state
        .onStart {
            setupAudioPlayerListeners()
            observeAudioPlayerCurrentPosition()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            RecordHistoryState(),
        )

    private val eventChannel = Channel<RecordHistoryEvent>()
    val events = eventChannel.receiveAsFlow()

    private val stopWatch = StopWatch()
    private var stopWatchJob: Job? = null

    private var playingRecordId = MutableStateFlow<Long?>(null)

    fun onAction(action: RecordHistoryAction) {
        when(action) {
            is RecordHistoryAction.OnSettingsClick -> {
                viewModelScope.launch {
                    eventChannel.send(RecordHistoryEvent.NavigateToSettings)
                }
            }
            is RecordHistoryAction.StartRecording -> {
                toggleSheetState()
                startRecording()
            }
            is RecordHistoryAction.PauseRecording -> {
                pauseRecording()
            }
            is RecordHistoryAction.ResumeRecording -> {
                resumeRecording()
            }
            is RecordHistoryAction.StopRecording -> {
                toggleSheetState()
                stopRecording(action.saveFile)
            }
            is RecordHistoryAction.PermissionDialogOpend -> {
                _state.update {
                    it.copy(isPermissionDialogVisible = action.isOpen)
                }
            }

            is RecordHistoryAction.OnStartRecordingClick -> {
                startRecording()
            }
            is RecordHistoryAction.OnStopRecordingClick -> {
                stopRecording(action.saveFile)
            }

            is RecordHistoryAction.RecordPlayClick -> playRecord(action.recordId)
            is RecordHistoryAction.RecordPauseClick -> pauseRecord(action.recordId)
            is RecordHistoryAction.RecordResumeClick -> resumeRecord(action.recordId)
        }
    }

    private fun observeAudioPlayerCurrentPosition() {
        viewModelScope.launch {
            audioPlayer.currentPositionFlow.collect { positionMillis ->
                val currentPositionText =
                    InstantFormatter.formatMillisToTime(positionMillis.toLong())
                playingRecordId.value?.let { recordId ->
                    updatePlayerStateCurrentPosition(
                        recordId = recordId,
                        currentPosition = positionMillis,
                        currentPositionText = currentPositionText
                    )
                }

            }
        }
    }

    private fun setupAudioPlayerListeners() {
        audioPlayer.setOnCompletionListener {
            playingRecordId.value?.let { recordId ->
                updatePlayerStateAction(recordId, PlayerState.Action.Initializing)
                audioPlayer.stop()
            }
        }
    }

    private fun startRecording() {
        audioRecorder.start()
        stopWatch.start()
        stopWatchJob = viewModelScope.launch {
            stopWatch.formattedTime.collect {
                val updatedSheetState = _state.value.recordHistorySheetState.copy(recordingTime = it)
                _state.update {
                    it.copy(recordHistorySheetState = updatedSheetState)
                }
            }
        }
    }

    private fun pauseRecording() {
        audioRecorder.pause()
        stopWatch.pause()
        toggleRecordingState()
    }

    private fun resumeRecording() {
        audioRecorder.resume()
        stopWatch.start()
        toggleRecordingState()
    }

    private fun stopRecording(saveFile: Boolean) {
        val audioFilePath = audioRecorder.stop(saveFile)
        stopWatch.reset()
        stopWatchJob?.cancel()
        if (saveFile) {
            val amplitudeLogFilePath = audioRecorder.getAmplitudeLogFilePath()
            stopRecordsPlaying()
            viewModelScope.launch {
                eventChannel.send(
                    RecordHistoryEvent.NavigateToRecordCreateScreen(
                        audioFilePath, amplitudeLogFilePath
                    )
                )
            }
        }
    }

    private fun playRecord(recordId: Long) {
        if (audioPlayer.isPlaying()) {
            stopRecordsPlaying()
            audioPlayer.stop()
        }

        playingRecordId.value = recordId
        updatePlayerStateAction(recordId, PlayerState.Action.Playing)

        val audioFilePath = getCurrentRecordHolderState(recordId).record.audioFilePath
        audioPlayer.initializeFile(audioFilePath)
        audioPlayer.play()
    }

    private fun pauseRecord(recordId: Long) {
        updatePlayerStateAction(recordId, PlayerState.Action.Paused)
        audioPlayer.pause()
    }

    private fun resumeRecord(recordId: Long) {
        updatePlayerStateAction(recordId, PlayerState.Action.Resumed)
        audioPlayer.resume()
    }

    private fun stopRecordsPlaying() {
        val updatedRecords = _state.value.records.mapValues { (_, recordList) ->
            recordList.map { recordHolderState ->
                if (recordHolderState.playerState.action == PlayerState.Action.Playing ||
                    recordHolderState.playerState.action == PlayerState.Action.Paused
                ) {
                    val updatedPlayerState = recordHolderState.playerState.copy(
                        action = PlayerState.Action.Initializing,
                        currentPosition = 0,
                        currentPositionText = "00:00:00"
                    )
                    recordHolderState.copy(playerState = updatedPlayerState)
                } else {
                    recordHolderState

                }
            }
        }
        _state.update {
            it.copy(records = updatedRecords)
        }
    }

    private fun updatePlayerStateCurrentPosition(
        recordId: Long,
        currentPosition: Int,
        currentPositionText: String
    ) {
        val recordHolderState = getCurrentRecordHolderState(recordId)
        val updatedPlayerState = recordHolderState.playerState.copy(
            currentPosition = currentPosition,
            currentPositionText = currentPositionText
        )
        updatePlayerState(recordId, updatedPlayerState)
    }

    private fun updatePlayerState(recordId: Long, newPlayerState: PlayerState) {
        val updatedRecords = _state.value.records.mapValues { (_ , recordList) ->
            recordList.map { recordHolderState ->
                if (recordHolderState.record.id == recordId) {
                    recordHolderState.copy(playerState = newPlayerState)
                } else {
                    recordHolderState
                }
            }
        }
        _state.update {
            it.copy(records = updatedRecords)
        }
    }

    private fun updatePlayerStateAction(recordId: Long, action: PlayerState.Action) {
        val recordHolderState = getCurrentRecordHolderState(recordId)
        val updatedPlayerState = recordHolderState.playerState.copy(action = action)
        updatePlayerState(recordId, updatedPlayerState)
    }

    private fun getCurrentRecordHolderState(recordId: Long): RecordHolderState {
        return _state.value.records.values
            .flatten()
            .find { it.record.id == recordId }
            ?: throw IllegalArgumentException("Record with ID $recordId not found")
    }

    private fun toggleSheetState() {
        _state.update{
            it.copy(
                recordHistorySheetState = _state.value.recordHistorySheetState.copy(
                    isVisible = !_state.value.recordHistorySheetState.isVisible,
                    isRecording = true
                )
            )
        }
    }

    private fun toggleRecordingState() {
        val updatedSheetState =
            _state.value.recordHistorySheetState.copy(isRecording = !_state.value.recordHistorySheetState.isRecording)
        updateSheetState(updatedSheetState)
    }

    private fun updateSheetState(updatedSheetState: RecordHistoryState.RecordHistorySheetState) {
        _state.update {
            it.copy(recordHistorySheetState = updatedSheetState)
        }
    }
}
