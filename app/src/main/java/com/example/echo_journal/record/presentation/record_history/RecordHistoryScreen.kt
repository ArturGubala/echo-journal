package com.example.echo_journal.record.presentation.record_history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.util.ObserveAsEvents
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
                        text = stringResource(R.string.record_screen_topappbar_title),
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
            FloatingActionButton(
                onClick = { },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .size(64.dp)
                    .offset(y = (-23).dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Floating action button.",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment =   Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_entries),
                contentDescription = "No entries icon"
            )
            Text(
                text = "No Entries",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Start recording your first Echo ",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordHistoryScreenPreview() {
//    RecordHistoryScreen()
}
