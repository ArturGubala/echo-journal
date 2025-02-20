package com.example.echo_journal.settings.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echo_journal.core.datastore.UserPreferences
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.presentation.util.getMood
import com.example.echo_journal.core.presentation.util.getMoodByName
import com.example.echo_journal.core.presentation.util.getMoodColoured
import com.example.echo_journal.core.presentation.util.getMoodUiByMood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
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

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnMoodClick -> {
                setDeafultMood(getMoodByName(action.mood.name))
            }
            is SettingsAction.OnTopicValueChange -> {
                updateTopicState {
                    it.copy(topicValue = action.newTopic)
                }
            }
            is SettingsAction.OnNewTopicClick -> {
                updateTopicState {
                    it.copy(topicValue = "")
                }
            }
            is SettingsAction.AddButtonVisibleToggled -> {
                toggleAddButtonVisibility()
            }
        }
    }

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

    private fun updateTopicState(update: (SettingsState.TopicState) -> SettingsState.TopicState) {
        _state.update {
            it.copy(topicState = update(it.topicState))
        }
    }
}