package com.example.echo_journal.core.data.audio.di

import com.example.echo_journal.core.data.audio.AndroidAudioPlayer
import com.example.echo_journal.core.data.audio.AndroidAudioRecorder
import com.example.echo_journal.core.domain.audio.AudioPlayer
import com.example.echo_journal.core.domain.audio.AudioRecorder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val audioModule = module {
    single<AudioPlayer> { AndroidAudioPlayer(androidContext()) }
    single<AudioRecorder> { AndroidAudioRecorder(androidContext()) }
}
