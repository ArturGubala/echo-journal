package com.example.echo_journal.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.echo_journal.core.data.database.entity.RecordEntity

@Database(
    entities = [
        RecordEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class EchoJournalDatabase : RoomDatabase()
