package com.example.echo_journal.core.data.database

import com.example.echo_journal.core.data.database.dao.TopicDao
import com.example.echo_journal.core.data.database.mappers.toTopicEntity
import com.example.echo_journal.core.data.database.mappers.toTopicList
import com.example.echo_journal.core.domain.topic.LocalTopicDataSource
import com.example.echo_journal.core.domain.topic.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalTopicDataSource(
    private val topicDao: TopicDao
) : LocalTopicDataSource {
    override fun getTopics(): Flow<List<Topic>> {
        return topicDao.getTopics().map { it.toTopicList() }
    }

    override suspend fun getTopicsByIds(ids: List<Long>): List<Topic> {
        return topicDao.getTopicsByIds(ids).toTopicList()
    }

    override suspend fun searchTopics(query: String): List<Topic> {
        return topicDao.searchTopics(query).toTopicList()
    }

    override suspend fun insertTopic(topic: Topic) {
        topicDao.insertTopic(topic.toTopicEntity())
    }

    override suspend fun deleteTopic(topic: Topic) {
        topicDao.deleteTopic(topic.toTopicEntity())
    }
}
