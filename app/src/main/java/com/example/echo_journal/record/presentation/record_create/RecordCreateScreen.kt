package com.example.echo_journal.record.presentation.record_create

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.echo_journal.R
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.presentation.components.MoodPlayer
import com.example.echo_journal.core.presentation.components.TopicDropdown
import com.example.echo_journal.core.presentation.util.ObserveAsEvents
import com.example.echo_journal.core.presentation.util.getMoodColorByMoodName
import com.example.echo_journal.core.utils.toDp
import com.example.echo_journal.core.utils.toInt
import com.example.echo_journal.record.presentation.record_create.presentation.components.LeaveDialog
import com.example.echo_journal.record.presentation.record_create.presentation.components.MoodChooseButton
import com.example.echo_journal.record.presentation.record_create.presentation.components.RecordCreateBottomButtons
import com.example.echo_journal.record.presentation.record_create.presentation.components.RecordCreateSheet
import com.example.echo_journal.record.presentation.record_create.presentation.components.RecordTextField
import com.example.echo_journal.record.presentation.record_create.presentation.components.TopicTagsRow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun RecordCreateRoute(
    onBackClick: () -> Unit,
    audioFilePath: String,
    amplitudeLogFilePath: String,
    viewModel: RecordCreateViewModel = koinViewModel(
        parameters = { parametersOf(audioFilePath, amplitudeLogFilePath) }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            RecordCreateEvent.NavigateBack -> {
                onBackClick
            }
        }
    }

    RecordCreateScreenRoot(
        state = state,
        onAction = viewModel::onAction
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordCreateScreenRoot(
    state: RecordCreateState,
    onAction: (RecordCreateAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Create record")
                },

            )
        },
        bottomBar = {
            val context = LocalContext.current
            RecordCreateBottomButtons(
                primaryButtonText = stringResource(R.string.save),
                onCancelClick = { onAction(RecordCreateAction.LeaveDialogToggled) },
                onConfirmClick = {
                    val outputDir = context.filesDir
                    onAction(RecordCreateAction.SaveButtonClicked(outputDir!!))
                },
                modifier = Modifier.padding(16.dp),
                primaryButtonEnabled = state.isSaveButtonEnabled
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        RecordCreateScreen(
            state = state,
            onAction = onAction
        )
        RecordCreateSheet(
            recordCreateSheetState = state.recordSheetState,
            onAction = onAction,
        )
        if(state.showLeaveDialog) {
            LeaveDialog(
                headline = stringResource(R.string.cancel_recording),
                onConfirm = { onAction(RecordCreateAction.LeaveDialogConfirmClicked) },
                onDismissRequest = { onAction(RecordCreateAction.LeaveDialogToggled) },
                supportingText = stringResource(R.string.leave_dialog_supporting_text),
                cancelButtonName = stringResource(R.string.cancel),
                confirmButtonName = stringResource(R.string.leave)
            )
        }
    }
}


@Composable
private fun RecordCreateScreen(
    state: RecordCreateState,
    onAction: (RecordCreateAction) -> Unit
) {
    Box {
        var topicOffset by remember { mutableStateOf(IntOffset.Zero) }

        val verticalSpace = 16.dp.toInt()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(verticalSpace.toDp())
        ) {
            // AddTitle text field
            RecordTextField(
                value = state.titleValue,
                onValueChange = { onAction(RecordCreateAction.TitleValueChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                hintText = stringResource(R.string.add_title),
                leadingIcon = {
                    MoodChooseButton(
                        mood = state.currentMood,
                        onClick = { onAction(RecordCreateAction.BottomSheetOpened(state.currentMood!!)) }
                    )
                },
                textStyle = MaterialTheme.typography.titleMedium,
                iconSpacing = 6.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MoodPlayer(
                    moodColor = getMoodColorByMoodName(if (state.currentMood != null) state.currentMood.name else Mood.NEUTRAL.name),
                    playerState = state.playerState,
                    onPlayClick = { onAction(RecordCreateAction.PlayClicked) },
                    onPauseClick = { onAction(RecordCreateAction.PauseClicked) },
                    onResumeClick = {onAction(RecordCreateAction.ResumeClicked) },
                    modifier = Modifier.height(44.dp).weight(1f),
                )
            }


            TopicTagsRow(
                value = state.topicValue,
                onValueChange = { onAction(RecordCreateAction.TopicValueChanged(it)) },
                topics = state.currentTopics,
                onTagClearClick = { onAction(RecordCreateAction.TagClearClicked(it)) },
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        topicOffset = IntOffset(
                            coordinates.positionInParent().x.toInt(),
                            coordinates.positionInParent().y.toInt() + coordinates.size.height + verticalSpace
                        )
                    }
                    .onFocusChanged {
                        onAction(RecordCreateAction.TopicValueChanged(""))
                    }
            )

            RecordTextField(
                value = state.descriptionValue,
                onValueChange = { onAction(RecordCreateAction.DescriptionValueChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                hintText = stringResource(R.string.add_description),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                },
                singleLine = false
            )
        }

        TopicDropdown(
            searchQuery = state.topicValue,
            topics = state.foundTopics,
            onTopicClick = { onAction(RecordCreateAction.TopicClicked(it)) },
            onCreateClick = { onAction(RecordCreateAction.CreateTopicClicked) },
            startOffset = topicOffset
        )
    }
}
