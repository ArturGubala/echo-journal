package com.example.echo_journal.settings.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echo_journal.core.datastore.UserPreferences
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.domain.topic.Topic
import com.example.echo_journal.core.presentation.util.getMood
import com.example.echo_journal.core.presentation.util.getMoodByName
import com.example.echo_journal.core.presentation.util.getMoodColoured
import com.example.echo_journal.core.presentation.util.getMoodUiByMood
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesDataSource: DataStore<UserPreferences>,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            initialiseMoods()
            getUserPreferences()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SettingsState(),
        )

    private val searchQuery = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
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

    init {
        viewModelScope.launch {
            observeTopicSearchResults()
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnMoodClick -> {
                setDeafultMood(getMoodByName(action.mood.name))
            }
            is SettingsAction.OnTopicValueChange -> {
                updateTopicState {
                    it.copy(topicValue = action.newTopic)
                }
                searchQuery.value = action.newTopic
            }
            is SettingsAction.OnCreateTopicClick -> {
                addTopic()
            }
            is SettingsAction.AddButtonVisibleToggled -> {
                toggleAddButtonVisibility()
            }
            is SettingsAction.OnTagClearClick -> {
                deleteTopic(action.topic)
            }
        }
    }

    // TODO: Extract logic responsible for getting / setting
    //  user preferences to separate class. Like data source?

    private fun initialiseMoods() {
        var moods = mutableListOf<MoodUi>()

        Mood.entries.forEach {
            moods.add(
                MoodUi(
                    resId = getMood(it),
                    name = it.name,
                    isSelected = false
                )
            )
        }

        _state.update {
            it.copy(
                moods = moods
            )
        }
    }

    private fun getUserPreferences() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val userPreferences = userPreferencesDataSource.data.first()

            if (userPreferences.defaultMood != null) {
                _state.update {
                    it.copy(
                        moods = state.value.moods.map { mood ->
                            if (mood.name == userPreferences.defaultMood.name) {
                                mood.copy(
                                    resId = getMoodColoured(userPreferences.defaultMood),
                                    isSelected = true
                                )
                            } else {
                                mood.copy(isSelected = false)
                            }
                        }
                    )
                }
            }
            updateTopicState {
                it.copy(
                    topics = userPreferences.topics
                )
            }

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun setDeafultMood(mood: Mood?) {
        if (mood == null) return

        viewModelScope.launch {
            userPreferencesDataSource.updateData {
                it.copy(
                    defaultMood = mood
                )
            }

            _state.update {
                val updatedMoods = it.moods.map { moodUi ->
                    if (moodUi.name == getMoodUiByMood(mood).name) {
                        moodUi.copy(
                            resId = getMoodColoured(mood),
                            isSelected = true
                        )
                    } else {
                        moodUi.copy(
                            resId = getMood(Mood.valueOf(moodUi.name)),
                            isSelected = false
                        )
                    }
                }

                it.copy(
                    moods = updatedMoods
                )
            }
        }
    }

    private fun toggleAddButtonVisibility() {
        updateTopicState {
            it.copy(isAddButtonVisible = it.isAddButtonVisible.not())
        }
    }

    private fun addTopic() {
        val newTopic = Topic(
            name = state.value.topicState.topicValue,
            isDefault = true
        )

        updateTopicState {
            it.copy(
                topics = it.topics + newTopic,
                topicValue = "",
                isAddButtonVisible = true
            )
        }

        viewModelScope.launch {
            userPreferencesDataSource.updateData {
                it.copy(
                    topics = _state.value.topicState.topics
                )
            }
        }
    }

    private fun deleteTopic(topic: Topic) {
        updateTopicState {
            it.copy(
                topics = it.topics.filter { it.name.lowercase() != topic.name.lowercase() }
            )
        }

        viewModelScope.launch {
            userPreferencesDataSource.updateData {
                it.copy(
                    topics = _state.value.topicState.topics
                )
            }
        }
    }

    private suspend fun observeTopicSearchResults() {
        searchResults.collect {
            updateTopicState {
                it.copy(foundTopics = searchResults.value)
            }
        }
    }

    private fun filterTopics(query: String): List<Topic> {
        return _state.value.topicState.topics
            // TODO: Remove comment after implement saving topics
            //  outside of settings screen
//            .filter { it.isDefault == false }
            .filter { it.name.lowercase().startsWith(query.lowercase().lowercase()) }

    }

    private fun updateTopicState(update: (SettingsState.TopicState) -> SettingsState.TopicState) {
        _state.update {
            it.copy(topicState = update(it.topicState))
        }
    }
}
