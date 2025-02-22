package com.example.echo_journal.settings.presentation

import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.domain.Topic

interface SettingsAction {
    data class OnMoodClick(val mood: MoodUi) : SettingsAction
    data class OnTopicValueChange(val newTopic: String) : SettingsAction
    data object OnNewTopicClick : SettingsAction
    data class OnFoundedTopicClick(val topic: Topic) : SettingsAction
    data object OnCreateTopicClick : SettingsAction
    data class OnTagClearClick(val topic: Topic) : SettingsAction
    data object AddButtonVisibleToggled : SettingsAction
}
