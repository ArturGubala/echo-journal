package com.example.echo_journal.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.echo_journal.core.domain.Mood
import java.time.Instant

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val mood: Mood,
    val audioFilePath: String,
    val audioDuration: Int,
    val amplitudeLogFilePath: String,
    val description: String,
    val topics: List<String>,
    val creationTimestamp: Instant
)
