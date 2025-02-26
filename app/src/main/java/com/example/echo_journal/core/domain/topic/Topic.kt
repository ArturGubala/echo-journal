package com.example.echo_journal.core.domain.topic

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val name: String,
    val isDefault: Boolean
)
