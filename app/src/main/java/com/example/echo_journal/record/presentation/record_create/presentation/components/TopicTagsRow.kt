@file:OptIn(ExperimentalLayoutApi::class)

package com.example.echo_journal.record.presentation.record_create.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echo_journal.core.domain.topic.Topic
import com.example.echo_journal.core.presentation.components.TopicTag
import com.example.echo_journal.core.presentation.components.TopicTextField

@Composable
fun TopicTagsRow(
    value: String,
    onValueChange: (String) -> Unit,
    topics: List<Topic>,
    onTagClearClick: (Topic) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        topics.forEach { topic ->
            TopicTag(
                topic = topic,
                onClearClick = onTagClearClick
            )
        }

        TopicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f)
        )
    }
}
