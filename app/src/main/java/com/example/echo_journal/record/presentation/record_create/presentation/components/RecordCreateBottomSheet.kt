@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.echo_journal.record.presentation.record_create.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.core.presentation.components.IconWithText
import com.example.echo_journal.core.presentation.util.getMoodColorByMoodName
import com.example.echo_journal.core.presentation.util.getMoodUiByMood
import com.example.echo_journal.record.presentation.record_create.RecordCreateAction
import com.example.echo_journal.record.presentation.record_create.RecordCreateState

@Composable
fun RecordCreateSheet(
    recordCreateSheetState: RecordCreateState.RecordSheetState,
    onAction: (RecordCreateAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val isPrimaryButtonEnabled by remember(recordCreateSheetState.activeMood) {
        derivedStateOf {
            recordCreateSheetState.activeMood != null
        }
    }

    if (recordCreateSheetState.isOpen) {
        ModalBottomSheet(
            onDismissRequest = { onAction(RecordCreateAction.BottomSheetClosed) },
            sheetState = sheetState
        ) {
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {

                Text(
                    text = stringResource(R.string.how_are_you_doing),
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Mood.entries.forEach {
                        val moodUi: MoodUi
                        if (recordCreateSheetState.activeMood != null && recordCreateSheetState.activeMood.name == it.name) {
                            moodUi = MoodUi(
                                resId = recordCreateSheetState.activeMood.resId,
                                name = "NEUTRAL",
                                isSelected = true,
                                moodColor = getMoodColorByMoodName("NEUTRAL")
                            )
                        } else {
                            moodUi = getMoodUiByMood(it)
                        }
                        IconWithText(
                            id = moodUi.resId,
                            text = moodUi.name.lowercase().replaceFirstChar { it.uppercase() },
                            modifier = Modifier
                                .weight(1f)
                                .clickable(
                                    interactionSource = null,
                                    indication = null
                                ) {
                                    onAction(
                                        RecordCreateAction.MoodSelected(moodUi)
                                    )
                                }
                        )
                    }
                }

                RecordCreateBottomButtons(
                    primaryButtonText = stringResource(R.string.confirm),
                    onCancelClick = { onAction(RecordCreateAction.BottomSheetClosed) },
                    onConfirmClick = {
                        onAction(RecordCreateAction.SheetConfirmClicked(recordCreateSheetState.activeMood!!))
                    },
                    primaryButtonEnabled = isPrimaryButtonEnabled,
                    primaryLeadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = if (isPrimaryButtonEnabled) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.outline
                            }
                        )
                    }
                )
            }
        }
    }
}
