package com.example.echo_journal.settings.presentation

import com.example.echo_journal.core.domain.MoodUi

data class SettingsState (
    val isLoading: Boolean = false,
    val moods: List<MoodUi> = emptyList(),
    val topics: List<String> = emptyList(),
    val newTopic: String? = null,
    val isCreatingTopic: Boolean = false
)
