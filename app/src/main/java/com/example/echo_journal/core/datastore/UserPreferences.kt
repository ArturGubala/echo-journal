package com.example.echo_journal.core.datastore

import com.example.echo_journal.core.domain.Mood
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val defaultMood: Mood? = null,
    val defaultTopics: List<String> = emptyList()
)