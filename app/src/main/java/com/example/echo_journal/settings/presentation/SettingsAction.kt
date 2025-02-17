package com.example.echo_journal.settings.presentation

import com.example.echo_journal.core.domain.MoodUi

interface SettingsAction {
    data class OnMoodClick(val mood: MoodUi) : SettingsAction
    data class OnTopicTextChange(val newTopic: String) : SettingsAction
    data object OnNewTopicClick : SettingsAction
    data object OnEditModeCancel : SettingsAction
}
