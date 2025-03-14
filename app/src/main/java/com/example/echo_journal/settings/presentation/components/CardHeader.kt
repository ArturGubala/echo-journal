package com.example.echo_journal.settings.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardHeader(
    title: String,
    subtitle: String,
    arrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = arrangement
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun CardHeaderPreview() {
    CardHeader(
        title = "My mood",
        subtitle = "Select default mood to apply to all new records"
    )
}
