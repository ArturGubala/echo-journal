package com.example.echo_journal.record.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavController.navigateToRecordHistory(navOptions: NavOptions? = null) = navigate(RecordHistoryScreen, navOptions)
fun NavController.navigateToCreateRecord(path: String, navOptions: NavOptions? = null) {
    navigate(CreateRecordScreen(path = path), navOptions)
}

fun NavGraphBuilder.recordHistoryScreen() {
    composable<RecordHistoryScreen> {}
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
