package com.example.echo_journal.record.presentation.record_history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.util.ObserveAsEvents
import com.example.echo_journal.record.navigation.navigateToCreateRecord
import com.example.echo_journal.record.presentation.components.EchoFilter
import com.example.echo_journal.record.presentation.components.EmptyRecordHistoryScreen
import com.example.echo_journal.record.presentation.components.JournalEntries
import com.example.echo_journal.record.presentation.components.RecordHistoryFAB
import com.example.echo_journal.record.presentation.components.RecordingBottomSheet
import com.example.echo_journal.settings.navigation.navigateToSettings
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RecordHistoryRoute(
    navController: NavController,
    viewModel: RecordHistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RecordHistoryEvent.NavigateToSettings -> {
                navController.navigateToSettings()
            }

            is RecordHistoryEvent.NavigateToRecordCreateScreen -> {
                navController.navigateToCreateRecord(
                    audioFilePath = event.audioFilePath,
                    amplitudeLogFilePath = event.amplitudeLogFilePath
                )
            }
        }

    }
    RecordHistoryScreen(
        onAction = viewModel::onAction,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordHistoryScreen(
    onAction: (RecordHistoryAction) -> Unit,
    state: RecordHistoryState
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
                        text = stringResource(R.string.your_echojournal),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                },
                actions = {
                    IconButton(onClick = { onAction(RecordHistoryAction.OnSettingsClick) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
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
                            onAction(RecordHistoryAction.StartRecording)
                        }
                    }
                },
                onLongPressRelease = { isEntryCanceled ->

                }
            )
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    ) { padding ->

        if (state.records.isEmpty())
            EmptyRecordHistoryScreen(
                modifier = Modifier.padding(padding)
            )
        else {
            HomeScreen(
                state = state,
                onAction = onAction
            )
        }

        RecordingBottomSheet(
            recordHistorySheetState = state.recordHistorySheetState,
            onAction = onAction
        )
    }
}

@Composable
private fun HomeScreen(
    state: RecordHistoryState,
    onAction: (RecordHistoryAction) -> Unit
) {
    var filterOffset by remember { mutableStateOf(IntOffset.Zero) }

    Column {
        EchoFilter(
            filterState = state.filterState,
            onAction = onAction,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    filterOffset = IntOffset(
                        coordinates.positionInParent().x.toInt(),
                        coordinates.positionInParent().y.toInt() + coordinates.size.height
                    )
                }
        )

        if (state.records.isEmpty() && state.isFilterActive) {
            EmptyRecordHistoryScreen(
                title = stringResource(R.string.no_entries_found),
                supportingText = stringResource(R.string.no_entries_found_supporting_text)
            )
        }

        JournalEntries(
            entryNotes = state.records,
            onAction = onAction
        )
    }

//    if (state.filterState.isMoodsOpen) {
//        FilterList(
//            filterItems = state.filterState.moodFilterItems,
//            onItemClick = { onAction(RecordHistoryAction.MoodFilterItemClicked(it)) },
//            onDismissClicked = { onAction(RecordHistoryAction.MoodsFilterToggled) },
//            startOffset = filterOffset
//        )
//    }
//
//    if (state.filterState.isTopicsOpen) {
//        FilterList(
//            filterItems = state.filterState.topicFilterItems,
//            onItemClick = { onAction(RecordHistoryAction.TopicFilterItemClicked(it)) },
//            onDismissClicked = { onAction(RecordHistoryAction.TopicsFilterToggled) },
//            startOffset = filterOffset
//        )
//    }
}
