@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.echo_journal.record.presentation.record_create

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echo_journal.core.data.database.RoomRecordDataSource
import com.example.echo_journal.core.datastore.UserPreferences
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.domain.PlayerState
import com.example.echo_journal.core.domain.audio.AudioPlayer
import com.example.echo_journal.core.domain.record.Record
import com.example.echo_journal.core.domain.topic.Topic
import com.example.echo_journal.core.presentation.util.getMoodByName
import com.example.echo_journal.core.presentation.util.getMoodColorByMoodName
import com.example.echo_journal.core.presentation.util.getMoodColoured
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.BottomSheetClosed
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.BottomSheetOpened
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.CreateTopicClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.DescriptionValueChanged
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.LeaveDialogConfirmClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.LeaveDialogToggled
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.MoodSelected
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.PauseClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.PlayClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.ResumeClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.SaveButtonClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.SheetConfirmClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.TagClearClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.TitleValueChanged
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.TopicClicked
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction.TopicValueChanged
import com.example.echo_journal.utils.InstantFormatter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class RecordCreateViewModel(
    private val audioFilePath: String,
    private val amplitudeLogFilePath: String,
    private val recordDataSource: RoomRecordDataSource,
    private val userPreferencesDataSource: DataStore<UserPreferences>,
    private val audioPlayer: AudioPlayer
) : ViewModel() {

    private val _state = MutableStateFlow(RecordCreateState())
    val state = _state
//        .onStart {
//            initializeAudioPlayer()
//            getUserPreferences()
//            subscribeToTopicSearchResults()
//            setupAudioPlayerListeners()
//            observeAudioPlayerCurrentPosition()
//        }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000L),
//            RecordCreateState(),
//        )

    init {
        initializeAudioPlayer()
        getUserPreferences()
        subscribeToTopicSearchResults()
        setupAudioPlayerListeners()
        observeAudioPlayerCurrentPosition()
    }

    private val eventChannel = Channel<RecordCreateEvent>()
    val events = eventChannel.receiveAsFlow()

    private val searchQuery = MutableStateFlow("")
    private val searchResults: StateFlow<List<Topic>> = searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(emptyList())
            } else {
                flow {
                    val foundTopics = filterTopics(query)
                    emit(foundTopics)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList(),
        )

    fun onAction(action: RecordCreateAction) {
        when (action) {
            BottomSheetClosed -> toggleSheetState()
            is BottomSheetOpened -> toggleSheetState(action.mood)
            is SheetConfirmClicked -> setCurrentMood(action.mood)

            is MoodSelected -> updateActiveMood(action.mood)

            is TitleValueChanged -> {
                _state.update { it.copy(titleValue = action.title) }
            }
            is DescriptionValueChanged -> {
                _state.update { it.copy(descriptionValue = action.description) }
            }

            is TopicValueChanged -> updateTopic(action.topic)
            is TagClearClicked -> {
                _state.update { it.copy(currentTopics = _state.value.currentTopics - action.topic) }
            }

            is TopicClicked -> updateCurrentTopics(action.topic)
            CreateTopicClicked -> addNewTopic()

            PauseClicked -> pauseAudio()
            PlayClicked -> playAudio()
            ResumeClicked -> resumeAudio()

            is SaveButtonClicked -> saveEntry(action.outputDir)

            LeaveDialogToggled -> toggleLeaveDialog()
            LeaveDialogConfirmClicked -> {
                toggleLeaveDialog()
                audioPlayer.stop()
                viewModelScope.launch {
                    eventChannel.send(RecordCreateEvent.NavigateBack)
                }
            }
        }
    }

    private fun initializeAudioPlayer() {
        audioPlayer.initializeFile(audioFilePath)
        _state.update {
            it.copy(
                playerState = _state.value.playerState.copy(
                    duration = audioPlayer.getDuration(),
                    amplitudeLogFilePath = amplitudeLogFilePath
                )
            )
        }
    }

    private fun setupAudioPlayerListeners() {
        audioPlayer.setOnCompletionListener {
            updatePlayerStateAction(PlayerState.Action.Initializing)
        }
    }

    private fun updatePlayerStateAction(action: PlayerState.Action) {
        val updatedPlayerState = _state.value.playerState.copy(action = action)
        _state.update { it.copy(playerState = updatedPlayerState) }
    }

    private fun observeAudioPlayerCurrentPosition() {
        viewModelScope.launch {
            audioPlayer.currentPositionFlow.collect { positionMillis ->
                val currentPositionText =
                    InstantFormatter.formatMillisToTime(positionMillis.toLong())

                _state.update {
                    it.copy(
                        playerState = it.playerState.copy(
                            currentPosition = positionMillis,
                            currentPositionText = currentPositionText
                        )
                    )
                }
            }
        }
    }

    private fun toggleSheetState(activeMood: MoodUi? = null) {
        updateSheetState {
            it.copy(
                isOpen = !it.isOpen,
                activeMood = activeMood
            )
        }
    }

    private fun playAudio() {
        updatePlayerStateAction(PlayerState.Action.Playing)
        audioPlayer.play()
    }

    private fun pauseAudio() {
        updatePlayerStateAction(PlayerState.Action.Paused)
        audioPlayer.pause()
    }

    private fun resumeAudio() {
        updatePlayerStateAction(PlayerState.Action.Resumed)
        audioPlayer.resume()
    }

    private fun saveEntry(outputDir: File) {
        val newAudioFilePath = renameFile(outputDir, audioFilePath, "audio")
        val newAmplitudeLogFilePath = renameFile(outputDir, amplitudeLogFilePath, "amplitude")
        val topics = _state.value.currentTopics.map { it.name }

        val newRecord = Record(
            title = _state.value.titleValue,
            mood = getMoodByName(_state.value.currentMood?.name.toString())!!,
            audioFilePath = newAudioFilePath,
            audioDuration = _state.value.playerState.duration,
            amplitudeLogFilePath = newAmplitudeLogFilePath,
            description = _state.value.descriptionValue,
            topics = topics
        )

        viewModelScope.launch {
            recordDataSource.upsertRecord(newRecord)
            eventChannel.send(RecordCreateEvent.NavigateBack)
        }
    }

    private fun renameFile(outputDir: File, filePath: String, newValue: String): String {
        val file = File(filePath)
        val newFileName = file.name.replace("temp", newValue)
        val newFile = File(outputDir, newFileName)
        val isRenamed = file.renameTo(newFile)

        return if (isRenamed) newFile.absolutePath else throw IllegalStateException("Failed to rename ${file.name}.")
    }

    private fun toggleLeaveDialog() {
        _state.update {
            it.copy(showLeaveDialog = !_state.value.showLeaveDialog)
        }
    }

    private fun updateSheetState(update: (RecordCreateState.RecordSheetState) -> RecordCreateState.RecordSheetState) {
        _state.update {
            it.copy(recordSheetState = update(it.recordSheetState))
        }
    }

    private fun getUserPreferences() {
        viewModelScope.launch {
            val userPreferences = userPreferencesDataSource.data.first()

            if (userPreferences.defaultMood != null) {
                _state.update {
                    it.copy(

                        currentMood  = MoodUi(
                            resId = getMoodColoured(userPreferences.defaultMood),
                            name = userPreferences.defaultMood.name,
                            isSelected = true,
                            moodColor = getMoodColorByMoodName(userPreferences.defaultMood.name)
                        )
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        currentMood = MoodUi(
                            resId = getMoodColoured(Mood.NEUTRAL),
                            name = "NEUTRAL",
                            isSelected = true,
                            moodColor = getMoodColorByMoodName("NEUTRAL")
                        )
                    )
                }
            }

            _state.update {
                it.copy(
                    currentTopics = userPreferences.topics.filter { it.isDefault == true },
                    topics = userPreferences.topics
                )
            }
        }
    }

    private fun filterTopics(query: String): List<Topic> {
        return _state.value.currentTopics
            // TODO: Remove comment after implement saving topics
            //  outside of settings screen
//            .filter { it.isDefault == false }
            .filter { it.name.lowercase().startsWith(query.lowercase().lowercase()) }

    }

    private fun setCurrentMood(mood: MoodUi) {
        _state.update {
            it.copy(
                currentMood = mood
            )
        }
        toggleSheetState()
    }

    private fun updateActiveMood(mood: MoodUi) {
        _state.update {
            it.copy(
                recordSheetState = _state.value.recordSheetState.copy(activeMood = MoodUi(
                    resId = getMoodColoured(getMoodByName(mood.name)!!),
                    name = mood.name,
                    isSelected = true,
                    moodColor = mood.moodColor
                ))
            )
        }
    }

    private fun updateTopic(topic: String) {
        _state.update {
            it.copy(topicValue = topic)
        }
        searchQuery.value = topic
    }

    private fun updateCurrentTopics(newTopic: Topic) {
        _state.update {
            it.copy(
                currentTopics = _state.value.currentTopics + newTopic,
                topics = _state.value.topics + newTopic,
                topicValue = ""
            )
        }
    }

    private fun addNewTopic() {
        val newTopic = Topic(
            name = _state.value.topicValue,
            isDefault = false
        )

        updateCurrentTopics(newTopic)

        viewModelScope.launch {
            userPreferencesDataSource.updateData {
                it.copy(
                    topics = _state.value.topics
                )
            }
        }
    }

    private fun subscribeToTopicSearchResults() {
        viewModelScope.launch {
            searchResults?.collect {
                _state.update {
                    it.copy(
                        foundTopics = searchResults.value
                    )
                }
            }
        }
    }
}
