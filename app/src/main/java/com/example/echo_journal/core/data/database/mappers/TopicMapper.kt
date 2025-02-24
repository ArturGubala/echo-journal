package com.example.echo_journal.core.data.database.mappers

import com.example.echo_journal.core.data.database.entity.TopicEntity
import com.example.echo_journal.core.domain.topic.Topic

fun Topic.toTopicEntity(): TopicEntity = TopicEntity(
    id = id,
    name = name
)

fun TopicEntity.toTopic(): Topic = Topic(
    id = id,
    name = name
)

fun List<TopicEntity>.toTopicList(): List<Topic> = map { it.toTopic() }
