package com.example.echo_journal.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.echo_journal.record.navigation.RecordHistoryScreen
import com.example.echo_journal.record.navigation.recordHistoryScreen
import com.example.echo_journal.settings.navigation.settingsScreen

@Composable
fun EchoJournalNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any = RecordHistoryScreen
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        recordHistoryScreen(navController)
        settingsScreen()
    }
}
