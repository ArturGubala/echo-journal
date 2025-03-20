package com.example.echo_journal.record.presentation.record_history

interface RecordHistoryEvent {
    data object NavigateToSettings : RecordHistoryEvent
    data class NavigateToRecordCreateScreen(
        val audioFilePath: String,
        val amplitudeLogFilePath: String
    ) : RecordHistoryEvent
}
