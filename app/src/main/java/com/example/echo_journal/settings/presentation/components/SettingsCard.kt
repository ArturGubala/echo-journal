package com.example.echo_journal.settings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R

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
                subtitle = "Select default mood to apply to all new entries"
            )
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_stressed),
                    contentDescription = "content description",
                )
                Image(
                    painter = painterResource(R.drawable.ic_sad),
                    contentDescription = "content description"
                )
                Image(
                    painter = painterResource(R.drawable.ic_neutral),
                    contentDescription = "content description"
                )
                Image(
                    painter = painterResource(R.drawable.ic_peaceful),
                    contentDescription = "content description"
                )
                Image(
                    painter = painterResource(R.drawable.ic_excited),
                    contentDescription = "content description"
                )
            }
        },
//        modifier = Modifier.fillMaxWidth()
    )
}
