package com.example.echo_journal.record.presentation.record_history

import androidx.compose.runtime.Stable
import com.example.echo_journal.core.domain.PlayerState
import com.example.echo_journal.core.domain.record.Record
import com.example.echo_journal.utils.Constants
import java.time.Instant

data class RecordHistoryState(
    val records: Map<Instant, List<RecordHolderState>> = mapOf(),
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
}