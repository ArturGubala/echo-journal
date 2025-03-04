package com.example.echo_journal.record.presentation.record_history

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.util.ObserveAsEvents
import com.example.echo_journal.record.presentation.components.EmptyRecordHistoryScreen
import com.example.echo_journal.record.presentation.components.RecordHistoryFAB
import com.example.echo_journal.settings.navigation.navigateToSettings
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RecordHistoryRoute(
    navController: NavController,
    viewModel: RecordHistoryViewModel = koinViewModel()
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RecordHistoryEvent.NavigateToSettings -> {
                navController.navigateToSettings()
            }
        }
    }

    RecordHistoryScreen(
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordHistoryScreen(
    onAction: (RecordHistoryAction) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.record_screen_top_appbar_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                },
                actions = {
                    IconButton(onClick = { onAction(RecordHistoryAction.OnSettingsClick) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            RecordHistoryFAB(
                onResult = { isGranted, isLongClicked ->
                    if (isGranted) {
                        if (isLongClicked) {

                        } else {

                        }
                    }
                },
                onLongPressRelease = { isEntryCanceled ->

                }
            )
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0),
    ) { padding ->
        EmptyRecordHistoryScreen(
            modifier = Modifier.padding(padding)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordHistoryScreenPreview() {
//    RecordHistoryScreen()
}
