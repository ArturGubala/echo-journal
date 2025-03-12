package com.example.echo_journal.core.data.database

import com.example.echo_journal.core.data.database.dao.RecordDao
import com.example.echo_journal.core.data.database.mappers.toRecordEntity
import com.example.echo_journal.core.data.database.mappers.toRecords
import com.example.echo_journal.core.domain.record.LocalRecordDataSource
import com.example.echo_journal.core.domain.record.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRecordDataSource(
    private val recordDao: RecordDao
) : LocalRecordDataSource {
    override fun getRecords(): Flow<List<Record>> {
        return recordDao.getEntries().map { it.toRecords() }
    }

    override suspend fun upsertRecord(record: Record) {
        recordDao.upsertEntry(record.toRecordEntity())
    }

    override suspend fun deleteRecord(record: Record) {
        recordDao.deleteEntry(record.toRecordEntity())
    }
}
