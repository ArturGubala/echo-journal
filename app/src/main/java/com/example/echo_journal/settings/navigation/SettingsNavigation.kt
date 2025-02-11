package com.example.echo_journal.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.echo_journal.settings.presentation.SettingsRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSettings(navOptions: NavOptions? = null) = navigate(SettingsScreen, navOptions)

fun NavGraphBuilder.settingsScreen(
    onBackClick: () -> Unit
) {
    composable<SettingsScreen> {
        SettingsRoute(
            onBackClick = onBackClick
        )
    }
}

@Serializable
object SettingsScreen
