package com.example.echo_journal.record.di

import com.example.echo_journal.record.presentation.record_create.RecordCreateViewModel
import com.example.echo_journal.record.presentation.record_history.RecordHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recordViewModelModule = module {
    viewModelOf(::RecordHistoryViewModel)
    viewModel { RecordCreateViewModel(get(), get(), get(), get(), get()) }
}
