package com.example.echo_journal.settings.presentation

import androidx.compose.ui.unit.IntOffset
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.data.entity.Topic

data class SettingsState (
    val isLoading: Boolean = false,
    val moods: List<MoodUi> = emptyList(),
    val topicState: TopicState = TopicState()
) {

    data class TopicState(
        val topicValue: String = "",
        val currentTopics: List<Topic> = listOf(),
        val foundTopics: List<Topic> = listOf(),
        val topicDropdownOffset: IntOffset = IntOffset.Zero,
        val isAddButtonVisible: Boolean = true
    )
}
