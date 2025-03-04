package com.example.echo_journal.record.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R

@Composable
fun EmptyRecordHistoryScreen(
    title: String = stringResource(R.string.no_entries),
    supportingText: String = stringResource(R.string.start_screen_supporting_text),
    modifier: Modifier = Modifier

) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =   Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_entries),
            contentDescription = "No entries icon"
        )
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = supportingText,
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.widthIn(max = 264.dp)
        )
    }
}
