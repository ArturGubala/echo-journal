package com.example.echo_journal.record.presentation.record_create

import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.domain.topic.Topic
import java.io.File

sealed interface RecordCreateAction {
    data object BottomSheetClosed : RecordCreateAction
    data class BottomSheetOpened(val mood: MoodUi) : RecordCreateAction
    data class SheetConfirmClicked(val mood: MoodUi) : RecordCreateAction

    data class MoodSelected(val mood: MoodUi) : RecordCreateAction
    data class TitleValueChanged(val title: String) : RecordCreateAction
    data class DescriptionValueChanged(val description: String) : RecordCreateAction

    data class TopicValueChanged(val topic: String) : RecordCreateAction
    data class TagClearClicked(val topic: Topic) : RecordCreateAction

    data class TopicClicked(val topic: Topic) : RecordCreateAction
    data object CreateTopicClicked : RecordCreateAction

    data object PlayClicked : RecordCreateAction
    data object PauseClicked : RecordCreateAction
    data object ResumeClicked : RecordCreateAction

    data class SaveButtonClicked(val outputDir: File) : RecordCreateAction

    data object LeaveDialogToggled : RecordCreateAction
    data object LeaveDialogConfirmClicked : RecordCreateAction
}
