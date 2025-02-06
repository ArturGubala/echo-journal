package com.example.echo_journal.settings.presentation

import com.example.echo_journal.core.domain.MoodUi

interface SettingsListAction {
    data class OnMoodClick(val mood: MoodUi) : SettingsListAction
}
