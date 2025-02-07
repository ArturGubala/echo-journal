package com.example.echo_journal.settings.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echo_journal.core.datastore.UserPreferences
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.presentation.util.getMood
import com.example.echo_journal.core.presentation.util.getMoodColoured
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
}