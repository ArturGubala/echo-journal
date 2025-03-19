package com.example.echo_journal.record.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.echo_journal.record.presentation.record_create.RecordCreateRoute
import com.example.echo_journal.record.presentation.record_history.RecordHistoryRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRecordHistory(navOptions: NavOptions? = null) = navigate(RecordHistoryScreen, navOptions)
fun NavController.navigateToCreateRecord(audioFilePath: String, amplitudeLogFilePath: String, navOptions: NavOptions? = null) {
    navigate(
        CreateRecordScreen(audioFilePath = audioFilePath, amplitudeLogFilePath = amplitudeLogFilePath),
        navOptions
    )
}

fun NavGraphBuilder.recordHistoryScreen(navController: NavController) {
    composable<RecordHistoryScreen> {
        RecordHistoryRoute(navController)
    }
}

fun NavGraphBuilder.createRecordScreen(
        onBackClick: () -> Unit
) {
    composable<CreateRecordScreen> {
        val args = it.toRoute<CreateRecordScreen>()
        RecordCreateRoute(
            onBackClick = onBackClick,
            audioFilePath = args.audioFilePath,
            amplitudeLogFilePath = args.amplitudeLogFilePath
        )
    }
}

@Serializable
object RecordHistoryScreen

@Serializable
data class CreateRecordScreen(
    val audioFilePath: String,
    val amplitudeLogFilePath: String
)
