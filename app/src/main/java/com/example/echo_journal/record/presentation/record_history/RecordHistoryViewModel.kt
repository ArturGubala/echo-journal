package com.example.echo_journal.record.presentation.record_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RecordHistoryViewModel(): ViewModel() {

    private val eventChannel = Channel<RecordHistoryEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RecordHistoryAction) {
        when(action) {
            is RecordHistoryAction.OnSettingsClick -> {
                viewModelScope.launch {
                    eventChannel.send(RecordHistoryEvent.NavigateToSettings)
                }
            }
        }
    }
}
