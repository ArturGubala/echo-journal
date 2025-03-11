package com.example.echo_journal.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.echo_journal.core.data.database.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM records ORDER BY creationTimestamp DESC")
    fun getEntries(): Flow<List<RecordEntity>>

    @Upsert
    suspend fun upsertEntry(entryDb: RecordEntity)

    @Delete
    suspend fun deleteEntry(entryDb: RecordEntity)
}
