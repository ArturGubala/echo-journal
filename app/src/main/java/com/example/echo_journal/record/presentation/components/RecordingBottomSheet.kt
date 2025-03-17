@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.echo_journal.record.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R
import com.example.echo_journal.record.presentation.record_history.RecordHistoryAction
import com.example.echo_journal.record.presentation.record_history.RecordHistoryState
import com.example.echo_journal.ui.theme.Gradient

@Composable
fun RecordingBottomSheet(
    recordHistorySheetState: RecordHistoryState.RecordHistorySheetState,
    onAction: (RecordHistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val sheetState = rememberModalBottomSheetState()

    if (recordHistorySheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction(RecordHistoryAction.StopRecording(saveFile = false))
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetHeader(
                    modifier = Modifier.padding(vertical = 8.dp),
                    isRecording = recordHistorySheetState.isRecording,
                    recordingTime = recordHistorySheetState.recordingTime
                )
                RecordButtons(
                    modifier = Modifier.padding(vertical = 42.dp, horizontal = 16.dp),
                    isRecording = recordHistorySheetState.isRecording,
                    onCancelClick = { onAction(RecordHistoryAction.StopRecording(saveFile = false)) },
                    onRecordClick = {
                        if (recordHistorySheetState.isRecording) {
                            onAction(RecordHistoryAction.StopRecording(saveFile = true))
                        } else {
                            onAction(RecordHistoryAction.ResumeRecording)
                        }
                    },
                    onPauseClick = {
                        if (recordHistorySheetState.isRecording) {
                            onAction(RecordHistoryAction.PauseRecording)
                        } else {
                            onAction(RecordHistoryAction.StopRecording(saveFile = true))
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomSheetHeader(
    recordingTime: String,
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Title
        Text(
            text = if (isRecording) {
                stringResource(R.string.recording_your_memories)
            } else {
                stringResource(R.string.recording_paused)
            },
            style = MaterialTheme.typography.titleSmall
        )

        // Timer
        Box(
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            Text(
                text = if (recordingTime.length > 5) recordingTime else "00:$recordingTime",
                style = MaterialTheme.typography.labelMedium
            )

            // Hidden placeholder text to define a fixed width.
            Text(
                text = "00:00:00",
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Transparent)
            )
        }
    }
}

@Composable
private fun RecordButtons(
    isRecording: Boolean,
    onCancelClick: () -> Unit,
    onRecordClick: () -> Unit,
    onPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    recordButtonSize: Dp = 72.dp,
    auxiliaryButtonSize: Dp = 48.dp
) {
    Row(
        modifier = modifier.fillMaxWidth().height(recordButtonSize),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        // Cancel button
        IconButton(
            modifier = Modifier.size(auxiliaryButtonSize),
            onClick = onCancelClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.cancel_recording)
            )
        }

        // Record button
        Box(
            modifier = Modifier.size(recordButtonSize),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(recordButtonSize)
                    .clip(CircleShape)
                    .background(brush = Gradient.PrimaryGradient, shape = CircleShape)
                    .clickable { onRecordClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (isRecording) {
                        painterResource(R.drawable.ic_checkmark)
                    } else {
                        painterResource(R.drawable.ic_recording)
                    },
                    contentDescription = stringResource(R.string.recording_button)
                )
            }

            if (isRecording) {
                ButtonPulsatingCircle()
            }
        }


        // Pause button
        IconButton(
            modifier = Modifier.size(auxiliaryButtonSize),
            onClick = onPauseClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Image(
                painter = if (isRecording) {
                    painterResource(R.drawable.ic_pause)
                } else {
                    painterResource(R.drawable.ic_checkmark_primary)
                },
                contentDescription = stringResource(R.string.pause_button)
            )
        }
    }
}

