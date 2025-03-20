package com.example.echo_journal.record.presentation.components

sealed interface RecordListPosition {
    data object First : RecordListPosition
    data object Middle : RecordListPosition
    data object Last : RecordListPosition
    data object Single : RecordListPosition
}
