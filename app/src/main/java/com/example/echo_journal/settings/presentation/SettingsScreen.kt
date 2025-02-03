package com.example.echo_journal.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R
import com.example.echo_journal.settings.presentation.components.CardHeader
import com.example.echo_journal.settings.presentation.components.IconWithText
import com.example.echo_journal.settings.presentation.components.SettingsCard

@Composable
internal fun SettingsRoute() {
    SettingsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
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
            SettingsCard(
                header =  {
                    CardHeader(
                        title = "My Topics",
                        subtitle = "Select default topics to apply to all new entries"
                    )
                },
                content = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        AssistChip(
                            label = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text( text = "#", modifier = Modifier.padding(end = 3.dp) )
                                    Text( text = "Topic 1" )
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "Back",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clickable { }
                                    )
                                }
                            },
                            modifier = Modifier,
                            onClick = { },
                            border = null,
                            shape = RoundedCornerShape(20.dp),
                            colors = AssistChipDefaults.assistChipColors(
                                labelColor = MaterialTheme.colorScheme.primary,
                                leadingIconContentColor = MaterialTheme.colorScheme.primary,
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            )
                        )

                    }
                }
            )
        }
    }
}
