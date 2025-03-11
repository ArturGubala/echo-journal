package com.example.echo_journal.core.data.database.mappers

import com.example.echo_journal.core.data.database.entity.RecordEntity
import com.example.echo_journal.core.domain.record.Record

fun Record.toRecordEntity(): RecordEntity = RecordEntity(
    id = id,
    title = title,
    mood = mood,
    audioFilePath = audioFilePath,
    audioDuration = audioDuration,
    amplitudeLogFilePath = amplitudeLogFilePath,
    description = description,
    topics = topics,
    creationTimestamp = creationTimestamp
)

fun RecordEntity.toRecord(): Record = Record(
    id = id,
    title = title,
    mood = mood,
    audioFilePath = audioFilePath,
    audioDuration = audioDuration,
    amplitudeLogFilePath = amplitudeLogFilePath,
    description = description,
    topics = topics,
    creationTimestamp = creationTimestamp
)

fun List<RecordEntity>.toRecords(): List<Record> = map { it.toRecord() }
