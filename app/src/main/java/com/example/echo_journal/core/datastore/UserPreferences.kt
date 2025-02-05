package com.example.echo_journal.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val defaultMoodResId: String = "",
    val defaultTopics: List<String> = emptyList()
)