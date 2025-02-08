package com.example.echo_journal.settings.presentation

import com.example.echo_journal.core.domain.MoodUi

interface SettingsAction {
    data class OnMoodClick(val mood: MoodUi) : SettingsAction
}
