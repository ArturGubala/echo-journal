
package com.example.echo_journal.record.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echo_journal.record.presentation.record_history.RecordHistoryAction
import com.example.echo_journal.record.presentation.record_history.RecordHistoryState
import com.example.echo_journal.utils.InstantFormatter
import java.time.Instant

@Composable
fun JournalEntries(
    entryNotes: Map<Instant, List<RecordHistoryState.RecordHolderState>>,
    onAction: (RecordHistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 20.dp),
    ) {
        entryNotes.forEach { (instant, entries) ->
            item {
                // DataHeader
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
                    text = InstantFormatter.formatToRelativeDay(instant),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            items(items = entries, key = { recordState -> recordState.record.id }) { entryHolderState ->
                RecordHolder(
                    recordState = entryHolderState,
                    recordPosition = when {
                        entries.size == 1 -> RecordListPosition.Single
                        entryHolderState == entries.first() -> RecordListPosition.First
                        entryHolderState == entries.last() -> RecordListPosition.Last
                        else -> RecordListPosition.Middle
                    },
                    onAction = onAction
                )
            }
        }
    }
}
