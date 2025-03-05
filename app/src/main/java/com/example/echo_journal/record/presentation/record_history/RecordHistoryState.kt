package com.example.echo_journal.record.presentation.record_history

import androidx.compose.runtime.Stable
import com.example.echo_journal.core.domain.Entry
import com.example.echo_journal.core.domain.PlayerState
import com.example.echo_journal.utils.Constants
import java.time.Instant

data class RecordHistoryState(
    val entries: Map<Instant, List<EntryHolderState>> = mapOf(),
    val recordHistorySheetState: RecordHistorySheetState = RecordHistorySheetState()
) {

    @Stable
    data class EntryHolderState(
        val entry: Entry,
        val playerState: PlayerState = PlayerState(
            duration = entry.audioDuration,
            amplitudeLogFilePath = entry.amplitudeLogFilePath
        )
    )

    data class RecordHistorySheetState(
        val isVisible: Boolean = false,
        val isRecording: Boolean = true,
        val recordingTime: String = Constants.DEFAULT_FORMATTED_TIME
    )
}