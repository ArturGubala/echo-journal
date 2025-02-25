package com.example.echo_journal.core.data.database.di

import androidx.room.Room
import com.example.echo_journal.core.data.database.EchoJournalDatabase
import com.example.echo_journal.core.data.database.RoomLocalTopicDataSource
import com.example.echo_journal.core.domain.topic.LocalTopicDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            EchoJournalDatabase::class.java,
            "echo_journal_db"
        ).build()
    }
    single { get<EchoJournalDatabase>().topicDao() }
    singleOf(::RoomLocalTopicDataSource).bind<LocalTopicDataSource>()
}
