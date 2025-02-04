package com.example.echo_journal.core.presentation.util

import com.example.echo_journal.R
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.MoodUi

fun getMood(mood: Mood): Int {
    return when (mood) {
        Mood.EXCITED -> R.drawable.ic_excited
        Mood.PEACEFUL -> R.drawable.ic_peaceful
        Mood.NEUTRAL -> R.drawable.ic_neutral
        Mood.SAD -> R.drawable.ic_sad
        Mood.STRESSED -> R.drawable.ic_stressed
    }
}

fun getMoodColoured(mood: Mood): Int {
    return when (mood) {
        Mood.EXCITED -> R.drawable.ic_excited_coloured
        Mood.PEACEFUL -> R.drawable.ic_peaceful_coloured
        Mood.NEUTRAL -> R.drawable.ic_neutral_coloured
        Mood.SAD -> R.drawable.ic_sad_coloured
        Mood.STRESSED -> R.drawable.ic_stressed_coloured
    }
}

fun getMoodByName(value: String): Mood? {
    return when (value) {
        "Excited" -> Mood.EXCITED
        "Peaceful" -> Mood.PEACEFUL
        "Neutral" -> Mood.NEUTRAL
        "Sad" -> Mood.SAD
        "Stressed" -> Mood.STRESSED
        else -> null
    }
}

fun getMoodUiByMood(mood: Mood): MoodUi {
    val resId = getMood(mood)
    val name = when (mood) {
        Mood.EXCITED -> "Excited"
        Mood.PEACEFUL -> "Peaceful"
        Mood.NEUTRAL -> "Neutral"
        Mood.SAD -> "Sad"
        Mood.STRESSED -> "Stressed"
    }

    return MoodUi(
        resId = resId,
        name = name,
        isSelected = false
    )
}
