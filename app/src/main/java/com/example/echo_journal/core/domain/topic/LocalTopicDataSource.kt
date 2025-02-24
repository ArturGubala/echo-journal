package com.example.echo_journal.core.domain.topic

import kotlinx.coroutines.flow.Flow

interface LocalTopicDataSource {
    fun getTopics(): Flow<List<Topic>>
    suspend fun getTopicsByIds(ids: List<Long>): List<Topic>
    suspend fun searchTopics(query: String): List<Topic>
    suspend fun insertTopic(topic: Topic)
    suspend fun deleteTopic(topic: Topic)
}
