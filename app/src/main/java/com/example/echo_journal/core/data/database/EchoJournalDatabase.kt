package com.example.echo_journal.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.echo_journal.core.data.converter.InstantConverter
import com.example.echo_journal.core.data.converter.MoodTypeConverter
import com.example.echo_journal.core.data.converter.TopicsConverter
import com.example.echo_journal.core.data.database.dao.RecordDao
import com.example.echo_journal.core.data.database.entity.RecordEntity

@Database(
    entities = [
        RecordEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    MoodTypeConverter::class,
    InstantConverter::class,
    TopicsConverter::class
)

abstract class EchoJournalDatabase : RoomDatabase() {
    abstract val recordDao: RecordDao
}
