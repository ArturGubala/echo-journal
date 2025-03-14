package com.example.echo_journal.settings.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.components.IconWithText

@Composable
fun SettingsCard(
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        )
    ) {
        Column(
            modifier = modifier
                .padding(start = 14.dp, end = 14.dp, bottom = 12.dp, top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            header()
            content()
        }
    }
}

@Preview
@Composable
fun SettingsCardPreview() {
    SettingsCard(
        header =  {
            CardHeader(
                title = "My mood",
                subtitle = "Select default mood to apply to all new records"
            )
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconWithText(
                    id = R.drawable.ic_stressed,
                    text = "Stressed",
                    modifier = Modifier.weight(1f)
                )
                IconWithText(
                    id = R.drawable.ic_sad,
                    text = "Sad",
                    modifier = Modifier.weight(1f)
                )
                IconWithText(
                    id = R.drawable.ic_neutral,
                    text = "Neutral",
                    modifier = Modifier.weight(1f)
                )
                IconWithText(
                    id = R.drawable.ic_peaceful,
                    text = "Peaceful",
                    modifier = Modifier.weight(1f)
                )
                IconWithText(
                    id = R.drawable.ic_excited,
                    text = "Excited",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    )
}
