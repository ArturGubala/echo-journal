package com.example.echo_journal.core.data.converter

import androidx.room.TypeConverter
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.presentation.util.getMoodByName
import com.example.echo_journal.core.presentation.util.getMoodStringByMood

class MoodTypeConverter {
    @TypeConverter
    fun fromMood(mood: Mood): String = getMoodStringByMood(mood)

    @TypeConverter
    fun toMood(value: String): Mood = getMoodByName(value) ?: Mood.NEUTRAL
}
