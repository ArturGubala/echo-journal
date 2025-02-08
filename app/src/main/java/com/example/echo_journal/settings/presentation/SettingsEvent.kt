package com.example.echo_journal.settings.presentation

interface SettingsEvent {
    data class Error(val error: String): SettingsEvent
}
