package com.example.echo_journal.settings.di

import com.example.echo_journal.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsViewModelModule = module {
    viewModelOf(::SettingsViewModel)
}
