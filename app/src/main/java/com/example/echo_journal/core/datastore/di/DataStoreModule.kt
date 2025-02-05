package com.example.echo_journal.core.datastore.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.echo_journal.core.datastore.UserPreferencesSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {

    single {
        DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO),
        ) {
            androidContext()
                .dataStoreFile("user_settings.json")
        }
    }
}
