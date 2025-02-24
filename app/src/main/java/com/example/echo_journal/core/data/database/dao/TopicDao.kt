package com.example.echo_journal.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.echo_journal.core.data.database.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    @Query("SELECT * FROM topics")
    fun getTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE id IN (:ids) ORDER BY name ASC")
    suspend fun getTopicsByIds(ids: List<Long>): List<TopicEntity>

    @Query("SELECT * FROM topics WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun searchTopics(query: String): List<TopicEntity>

    @Insert
    suspend fun insertTopic(topic: TopicEntity)

    @Delete
    suspend fun deleteTopic(topic: TopicEntity)
}
