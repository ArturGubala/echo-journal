package com.example.echo_journal.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.components.IconWithText
import com.example.echo_journal.core.presentation.components.TopicDropdown
import com.example.echo_journal.core.utils.toInt
import com.example.echo_journal.settings.presentation.components.CardHeader
import com.example.echo_journal.settings.presentation.components.SettingsCard
import com.example.echo_journal.settings.presentation.components.TopicTagsWithAddButton
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

                Box {
                    var topicOffset by remember { mutableStateOf(IntOffset.Zero) }
                    val verticalSpace = 16.dp.toInt()

                    SettingsCard(
                        header = {
                            CardHeader(
                                title = stringResource(R.string.default_topic_settings_title),
                                subtitle = stringResource(R.string.default_topic_settings_subtitle),
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        content = {
                            TopicTagsWithAddButton(
                                topicState = state.topicState,
                                onAction = onAction,
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        topicOffset = IntOffset(
                                            coordinates.positionInParent().x.toInt(),
                                            coordinates.positionInParent().y.toInt() + coordinates.size.height + verticalSpace
                                        )
                                    }
                                    .fillMaxWidth(),
                            )
                        },
                        modifier = Modifier
                            .background(Color.White)
                    )

                    TopicDropdown(
                        searchQuery = state.topicState.topicValue,
                        topics = state.topicState.foundTopics,
                        onTopicClick = { onAction(SettingsAction.OnFoundedTopicClick(it)) },
                        onCreateClick = { onAction(SettingsAction.OnCreateTopicClick) },
                        startOffset = topicOffset,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                }
            }
        }
    }
}
