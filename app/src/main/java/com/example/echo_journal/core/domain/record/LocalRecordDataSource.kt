package com.example.echo_journal.core.domain.record

import kotlinx.coroutines.flow.Flow

interface LocalRecordDataSource {
    fun getRecords(): Flow<List<Record>>
    suspend fun upsertRecord(record: Record)
    suspend fun deleteRecord(record: Record)
}
