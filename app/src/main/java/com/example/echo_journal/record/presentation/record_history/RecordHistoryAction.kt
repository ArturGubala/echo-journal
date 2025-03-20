package com.example.echo_journal.record.presentation.record_history

interface RecordHistoryAction {
    data object OnSettingsClick : RecordHistoryAction

    data object StartRecording : RecordHistoryAction
    data object PauseRecording : RecordHistoryAction
    data object ResumeRecording : RecordHistoryAction
    data class StopRecording(val saveFile: Boolean) : RecordHistoryAction
    data object OnStartRecordingClick : RecordHistoryAction
    data class OnStopRecordingClick(val saveFile: Boolean) : RecordHistoryAction

    data class RecordPlayClick(val recordId: Long) : RecordHistoryAction
    data class RecordPauseClick(val recordId: Long) : RecordHistoryAction
    data class RecordResumeClick(val recordId: Long) : RecordHistoryAction

    data class PermissionDialogOpend(val isOpen: Boolean) : RecordHistoryAction

    data object MoodsFilterToggled : RecordHistoryAction
    data object TopicsFilterToggled : RecordHistoryAction
    data object MoodsFilterClearClicked : RecordHistoryAction
    data object TopicsFilterClearClicked : RecordHistoryAction

}