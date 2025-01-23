package com.example.echo_journal.record.presentation.record_history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.echo_journal.R

@Composable
internal fun RecordHistoryRoute() {
    RecordHistoryScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordHistoryScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Small Top App Bar")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment =   Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_entries),
                contentDescription = "No entries icon"
            )
            Text(
                text = "No Entries",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Start recording your first Echo ",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordHistoryScreenPreview() {
    RecordHistoryScreen()
}
