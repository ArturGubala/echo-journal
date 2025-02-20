package com.example.echo_journal.data.entity

import com.example.echo_journal.utils.Constants

data class Topic(
    val id: Long = Constants.INITIAL_TOPIC_ID,
    val name: String
)
