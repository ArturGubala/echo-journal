package com.example.echo_journal.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.echo_journal.core.data.database.dao.TopicDao
import com.example.echo_journal.core.data.database.entity.TopicEntity

@Database(
    entities = [TopicEntity::class],
    version = 1,
    exportSchema = false
)

abstract class EchoJournalDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
}
