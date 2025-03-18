package com.example.echo_journal.record.presentation.record_create

import androidx.compose.runtime.Stable
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.domain.PlayerState
import com.example.echo_journal.core.domain.topic.Topic

data class RecordCreateState(
    val currentMood: MoodUi? = null,
    val titleValue: String = "",
    val topicValue: String = "",
    val descriptionValue: String = "",
    val playerState: PlayerState = PlayerState(),
    val currentTopics: List<Topic> = emptyList(),
    val foundTopics: List<Topic> = emptyList(),
    val topics: List<Topic> = emptyList(),
    val recordSheetState: RecordSheetState = RecordSheetState(),
    val showLeaveDialog: Boolean = false
) {
    val isSaveButtonEnabled: Boolean
        get() = titleValue.isNotBlank()

    @Stable
    data class RecordSheetState(
        val isOpened: Boolean = false,
        val activeMood: MoodUi? = null,
        val moods: List<MoodUi> = emptyList()
    )
}
