package com.example.echo_journal.core.data

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.example.echo_journal.core.domain.audio.AudioPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AndroidAudioPlayer(
    private val context: Context,
) : AudioPlayer {

    private var filePath: String = ""

    private var player: MediaPlayer? = null
    private var onCompletionListener: (() -> Unit)? = null

    private val _currentPositionFlow = MutableStateFlow(0)
    override val currentPositionFlow: StateFlow<Int> = _currentPositionFlow

    private var updateJob: Job? = null
    private var isCurrentlyPlaying: Boolean = false

    override fun initializeFile(filePath: String) {
        _currentPositionFlow.value = 0
        this.filePath = filePath
        createPlayer()
    }

    override fun play() {
        if (player == null) {
            createPlayer()
        }
        player?.start()
        player?.metrics
        startUpdatingCurrentPosition()
    }

    override fun pause() {
        checkPlayerReady()
        player?.pause()
        stopUpdatingCurrentPosition()
    }

    override fun resume() {
        checkPlayerReady()
        player?.start()
        startUpdatingCurrentPosition()
    }

    override fun stop() {
        checkPlayerReady()
        stopUpdatingCurrentPosition()
        _currentPositionFlow.value = 0

        player?.stop()
        player?.release()
        player = null
        isCurrentlyPlaying = false
    }

    override fun setOnCompletionListener(listener: () -> Unit) {
        onCompletionListener = listener
    }

    override fun getDuration(): Int {
        checkPlayerReady()
        return player?.duration ?: 0
    }

    override fun isPlaying(): Boolean {
        return player?.isPlaying == true || isCurrentlyPlaying
    }

    private fun createPlayer() {
        val mediaPlayer = MediaPlayer.create(context, filePath.toUri())
            ?: throw IllegalArgumentException("Invalid file path: $filePath")

        mediaPlayer.apply {
            player = this
            setOnCompletionListener {
                onCompletionListener?.invoke()
            }
        }
    }

    private fun startUpdatingCurrentPosition() {
        checkPlayerReady()
        stopUpdatingCurrentPosition()

        updateJob = CoroutineScope(Dispatchers.IO).launch {
            while (player?.isPlaying == true) {
                _currentPositionFlow.value = player?.currentPosition ?: 0
                delay(10L)
            }
        }
    }

    private fun stopUpdatingCurrentPosition() {
        updateJob?.cancel()
        updateJob = null
    }

    private fun checkPlayerReady() {
        if (player == null) {
            throw IllegalStateException("Player is not initialized")
        }
    }
}
