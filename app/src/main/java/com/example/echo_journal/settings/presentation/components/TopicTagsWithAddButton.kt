package com.example.echo_journal.settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R
import com.example.echo_journal.core.presentation.components.TopicTag
import com.example.echo_journal.core.presentation.components.TopicTextField
import com.example.echo_journal.settings.presentation.SettingsAction
import com.example.echo_journal.settings.presentation.SettingsState.TopicState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicTagsWithAddButton(
    topicState: TopicState,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        topicState.topics
            .filter { it.isDefault }
            .forEach { topic ->
            TopicTag(
                topic = topic,
                onClearClick = { onAction(SettingsAction.OnTagClearClick(topic)) }
            )
        }

        if (topicState.isAddButtonVisible) {
            TopicAddButton(
                onClick = { onAction(SettingsAction.AddButtonVisibleToggled) }
            )
        } else {
            val focusRequester = remember { FocusRequester() }

            LaunchedEffect(true) {
                focusRequester.requestFocus()
            }

            TopicTextField(
                value = topicState.topicValue,
                onValueChange = { onAction(SettingsAction.OnTopicValueChange(it)) },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                ,
                hintText = "",
                showLeadingIcon = false
            )
        }
    }
}

@Composable
private fun TopicAddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .size(32.dp)
            .clickable { onClick() },
        shape = CircleShape,
        color = Color(0xFFF2F2F7),
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Icon(
            modifier = Modifier.padding(4.dp),
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_default_topic)
        )
    }
}
