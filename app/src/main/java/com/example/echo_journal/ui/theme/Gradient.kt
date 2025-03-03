package com.example.echo_journal.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Gradient {
    val PrimaryGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF578CFF),
            Color(0xFF1F70F5)
        )
    )
    val DisabledSolidColor = Brush.linearGradient(
        colors = listOf(
            EchoSofBlue,
            EchoSofBlue
        )
    )
    val FABRecordingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3982F6).copy(alpha = 0.2f),
            Color(0xFF0E5FE0).copy(alpha = 0.2f)
        )
    )
    val FABPulsatingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3982F6).copy(alpha = 0.1f),
            Color(0xFF0E5FE0).copy(alpha = 0.1f)
        )
    )
}