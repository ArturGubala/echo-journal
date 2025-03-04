package com.example.echo_journal.core.domain

import androidx.compose.runtime.Stable
import com.example.echo_journal.utils.Constants
import java.time.Instant

@Stable
data class Entry(
    val id: Long = Constants.INITIAL_ENTRY_ID,
    val title: String,
    val mood: Mood,
    val audioFilePath: String,
    val audioDuration: Int,
    val amplitudeLogFilePath: String,
    val description: String = "",
    val topics: List<String> = emptyList(),
    val creationTimestamp: Instant = Instant.now()
)