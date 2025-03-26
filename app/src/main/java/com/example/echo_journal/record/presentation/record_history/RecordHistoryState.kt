package com.example.echo_journal.record.presentation.record_history

import androidx.compose.runtime.Stable
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.PlayerState
import com.example.echo_journal.core.domain.record.Record
import com.example.echo_journal.utils.Constants
import java.time.Instant

data class RecordHistoryState(
    val records: Map<Instant, List<RecordHolderState>> = mapOf(),
    val filterState: FilterState = FilterState(),
    val isFilterActive: Boolean = false,
    val recordHistorySheetState: RecordHistorySheetState = RecordHistorySheetState(),
    val isPermissionDialogVisible: Boolean = false
) {

    @Stable
    data class RecordHolderState(
        val record: Record,
        val playerState: PlayerState = PlayerState(
            duration = record.audioDuration,
            amplitudeLogFilePath = record.amplitudeLogFilePath
        )
    )

    data class RecordHistorySheetState(
        val isVisible: Boolean = false,
        val isRecording: Boolean = true,
        val recordingTime: String = Constants.DEFAULT_FORMATTED_TIME
    )

    @Stable
    data class FilterState(
        val isMoodsOpen: Boolean = false,
        val isTopicsOpen: Boolean = false,
        val moodFilterItems: List<FilterItem> = Mood.entries.map { FilterItem(title = it.name) },
        val topicFilterItems: List<FilterItem> = listOf()
    ) {
        data class FilterItem(
            val title: String = "",
            val isChecked: Boolean = false
        )
    }
}