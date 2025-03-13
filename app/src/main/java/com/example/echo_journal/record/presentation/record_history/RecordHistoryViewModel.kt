package com.example.echo_journal.record.presentation.record_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echo_journal.core.data.database.RoomRecordDataSource
import com.example.echo_journal.core.domain.audio.AudioPlayer
import com.example.echo_journal.core.domain.audio.AudioRecorder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecordHistoryViewModel(
    private val recordDataSource: RoomRecordDataSource,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer
): ViewModel() {

    private val _state = MutableStateFlow(RecordHistoryState())
    val state = _state
        .onStart {
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            RecordHistoryState(),
        )

    private val eventChannel = Channel<RecordHistoryEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RecordHistoryAction) {
        when(action) {
            is RecordHistoryAction.OnSettingsClick -> {
                viewModelScope.launch {
                    eventChannel.send(RecordHistoryEvent.NavigateToSettings)
                }
            }
            is RecordHistoryAction.StartRecording -> {
                toggleSheetState()
            }
            is RecordHistoryAction.PauseRecording -> {}
            is RecordHistoryAction.ResumeRecording -> {}
            is RecordHistoryAction.StopRecording -> {
                toggleSheetState()
            }
            is RecordHistoryAction.PermissionDialogOpend -> {}
        }
    }

    private fun toggleSheetState() {
        _state.value = _state.value.copy(
            recordHistorySheetState = _state.value.recordHistorySheetState.copy(
                isVisible = !_state.value.recordHistorySheetState.isVisible,
                isRecording = true
            )
        )
    }
}
