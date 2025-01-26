package com.example.echo_journal.record.di

import com.example.echo_journal.record.presentation.record_history.RecordHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recordHistoryViewModelModule = module {
    viewModelOf(::RecordHistoryViewModel)
}
