package com.example.echo_journal.record.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.util.getMood
import com.example.echo_journal.core.presentation.util.getMoodByName
import com.example.echo_journal.record.presentation.record_history.RecordHistoryAction
import com.example.echo_journal.record.presentation.record_history.RecordHistoryState.FilterState

@Composable
fun EchoFilter(
    filterState: FilterState,
    onAction: (RecordHistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Moods Chip
            item {
                FilterChip(
                    defaultTitle = stringResource(R.string.all_moods),
                    filterItems = filterState.moodFilterItems,
                    isFilterSelected = filterState.isMoodsOpen,
                    onClick = { onAction(RecordHistoryAction.MoodsFilterToggled) },
                    onClearClick = { onAction(RecordHistoryAction.MoodsFilterClearClicked) },
                    leadingIcon = {
                        if (filterState.moodFilterItems.isNotEmpty()) {
                            SelectedMoodIcons(
                                moodFilterItems = filterState.moodFilterItems
                            )
                        }
                    }
                )
            }

            // Topic Chip
            item {
                FilterChip(
                    defaultTitle = stringResource(R.string.all_topics),
                    filterItems = filterState.topicFilterItems,
                    isFilterSelected = filterState.isTopicsOpen,
                    onClick = { onAction(RecordHistoryAction.TopicsFilterToggled) },
                    onClearClick = { onAction(RecordHistoryAction.TopicsFilterClearClicked) },
                )
            }
        }
    }
}

@Composable
fun SelectedMoodIcons(
    moodFilterItems: List<FilterState.FilterItem>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-4).dp)
    ) {
        moodFilterItems.forEach { filterItem ->
            if (filterItem.isChecked) {
                val mood = getMoodByName(filterItem.title)
                Image(
                    modifier = Modifier.height(22.dp),
                    painter = painterResource(getMood(mood!!)),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    defaultTitle: String,
    filterItems: List<FilterState.FilterItem>,
    isFilterSelected: Boolean,
    onClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    AssistChip(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = when {
                isFilterSelected -> MaterialTheme.colorScheme.surface
                filterItems.isSomeMoodSelected() -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.background
            }
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isFilterSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        ),
        label = {
            Text(
                text = getFormatFilterTitle(defaultTitle, filterItems),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        trailingIcon = {
            if (filterItems.isSomeMoodSelected()) {
                // Clear icon
                IconButton(
                    modifier = Modifier.size(18.dp),
                    onClick = { onClearClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_filter),
                        tint = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon
    )
}

private fun getFormatFilterTitle(
    defaultTitle: String,
    filterItems: List<FilterState.FilterItem>
): String {
    val pickedItems = filterItems
        .filter { it.isChecked }
        .map { it.title }

    return when {
        pickedItems.isEmpty() -> defaultTitle
        pickedItems.size == 1 -> pickedItems.first()
        pickedItems.size == 2 -> pickedItems.joinToString(", ")
        else -> {
            val firstTwo = pickedItems.take(2).joinToString(", ")
            "$firstTwo +${pickedItems.size - 2}"
        }
    }
}

private fun List<FilterState.FilterItem>.isSomeMoodSelected() = this.any { it.isChecked }
