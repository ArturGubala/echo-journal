package com.example.echo_journal.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.components.IconWithText
import com.example.echo_journal.settings.presentation.components.CardHeader
import com.example.echo_journal.settings.presentation.components.SettingsCard
import com.example.echo_journal.ui.theme.TopicBackground
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingsRoute(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(
        onBackClick = onBackClick,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun SettingsScreen(
    onBackClick: () -> Unit,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.settings_screen_top_appbar_title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SettingsCard(
                    header = {
                        CardHeader(
                            title = stringResource(R.string.default_mood_settings_title),
                            subtitle = stringResource(R.string.default_mood_settings_subtitle),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            state.moods.forEach {
                                IconWithText(
                                    id = it.resId,
                                    text = it.name.lowercase().replaceFirstChar { it.uppercase() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable(
                                            interactionSource = null,
                                            indication = null
                                        ) {
                                            onAction(
                                                SettingsAction.OnMoodClick(it)
                                            )
                                        }
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .background(Color.White)
                )
                var expanded by remember { mutableStateOf(false) }
//                val focusRequester = remember { FocusRequester() }
                SettingsCard(
                    header = {
                        CardHeader(
                            title = stringResource(R.string.default_topic_settings_title),
                            subtitle = stringResource(R.string.default_topic_settings_subtitle),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    content = {

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
                            ) {
                                for (i in 0..3) {
                                    AssistChip(
                                        label = {
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "#",
                                                    modifier = Modifier.padding(end = 3.dp)
                                                        .alpha(0.5f)
                                                )
                                                Text(text = "Topic new en ${i + 1}")
                                                Icon(
                                                    imageVector = Icons.Filled.Clear,
                                                    contentDescription = "Clear",
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                        .alpha(.3f)
                                                        .clickable { }
                                                )
                                            }
                                        },
                                        modifier = Modifier,
                                        onClick = { },
                                        border = null,
                                        shape = RoundedCornerShape(20.dp),
                                        colors = AssistChipDefaults.assistChipColors(
                                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            containerColor = TopicBackground
                                        )
                                    )
                                }
                                val focusRequester = remember { FocusRequester() }

                                if (!expanded) {
                                    IconButton(
                                        onClick = {
//                                    onAction(SettingsAction.OnNewTopicClick)
                                            expanded = !expanded
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
//                                    .offset(x = 3.dp),
                                            .align(alignment = Alignment.CenterVertically),
                                        colors = IconButtonColors(
                                            containerColor = TopicBackground,
                                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            disabledContentColor = MaterialTheme.colorScheme.primary

                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = "Add",
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                    }
                                } else {


                                    BasicTextField(
                                        value = state.newTopic,
                                        onValueChange = {
                                            onAction(
                                                SettingsAction.OnTopicTextChange(
                                                    it
                                                )
                                            )
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .align(Alignment.CenterVertically)
                                            .menuAnchor(MenuAnchorType.PrimaryEditable)
                                            .focusRequester(focusRequester),
                                        decorationBox = { innerTextField ->
                                            Box(
                                                contentAlignment = Alignment.CenterStart,
                                            ) {
                                                innerTextField()
                                                if (state.newTopic.isEmpty()) {
                                                    Text(
                                                        text = stringResource(R.string.default_topic_settings_placeholder),
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        modifier = Modifier.alpha(0.5f)
                                                            .padding(start = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    )

                                    LaunchedEffect(Unit) {
                                            focusRequester.requestFocus()
                                    }

                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Option 1") },
                                            onClick = { expanded = false },
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Option 2") },
                                            onClick = { expanded = false }
                                        )
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .background(Color.White)
                )
            }
        }
    }
}
