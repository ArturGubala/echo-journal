package com.example.echo_journal.settings.presentation

interface SettingsListEvent {
    data class Error(val error: String): SettingsListEvent
}
