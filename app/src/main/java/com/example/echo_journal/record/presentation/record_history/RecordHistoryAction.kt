package com.example.echo_journal.record.presentation.record_history

interface RecordHistoryAction {
    data object OnSettingsClick : RecordHistoryAction

    data object StartRecording : RecordHistoryAction
    data object PauseRecording : RecordHistoryAction
    data object ResumeRecording : RecordHistoryAction
    data class StopRecording(val saveFile: Boolean) : RecordHistoryAction

    data class PermissionDialogOpend(val isOpen: Boolean) : RecordHistoryAction
}