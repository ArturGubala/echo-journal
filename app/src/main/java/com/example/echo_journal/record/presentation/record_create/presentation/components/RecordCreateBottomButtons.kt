package com.example.echo_journal.record.presentation.record_create.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echo_journal.core.presentation.components.PrimaryButton
import com.example.echo_journal.core.presentation.components.SecondaryButton

@Composable
fun RecordCreateBottomButtons(
    primaryButtonText: String,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryButtonEnabled: Boolean = true,
    primaryLeadingIcon: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SecondaryButton(
            text = "Cancel",
            onClick = onCancelClick,
            modifier = Modifier.fillMaxHeight()
        )
        PrimaryButton(
            text = primaryButtonText,
            onClick = onConfirmClick,
            modifier = Modifier.weight(1f),
            enabled = primaryButtonEnabled,
            leadingIcon = primaryLeadingIcon
        )
    }
}
