package com.example.echo_journal.record.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.echo_journal.record.presentation.record_history.RecordHistoryRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRecordHistory(navOptions: NavOptions? = null) = navigate(RecordHistoryScreen, navOptions)
fun NavController.navigateToCreateRecord(path: String, navOptions: NavOptions? = null) {
    navigate(CreateRecordScreen(path = path), navOptions)
}

fun NavGraphBuilder.recordHistoryScreen(navController: NavController) {
    composable<RecordHistoryScreen> {
        RecordHistoryRoute(navController)
    }
}

fun NavGraphBuilder.createRecordScreen(
        onBackClick: () -> Unit
) {
    composable<CreateRecordScreen> {}
}

@Serializable
object RecordHistoryScreen

@Serializable
data class CreateRecordScreen(
    val path: String
)
