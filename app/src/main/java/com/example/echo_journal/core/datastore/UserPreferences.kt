package com.example.echo_journal.core.datastore

import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.topic.Topic
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val defaultMood: Mood? = null,
    val topics: List<Topic> = emptyList()
)
