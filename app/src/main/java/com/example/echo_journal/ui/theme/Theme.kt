package com.example.echo_journal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = EchoBlue,
    background = EchoLightBlue,
    surface = Color.White,
    surfaceVariant = EchoSofBlue,
    onPrimary = Color.White,
    onSurface = EchoDark,
    onSurfaceVariant = EchoGrayBlue,
    secondary = EchoDarkSteel,
    outline = EchoMutedGray,
    outlineVariant = EchoLightGray,
    errorContainer = EchoSoftPeach,
    onErrorContainer = EchoRed,
    onPrimaryContainer = EchoPaleBlue,
    surfaceTint = EchoDeepBlue,
    secondaryContainer = EchoDustyBlue
)

@Composable
fun EchoJournalTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
