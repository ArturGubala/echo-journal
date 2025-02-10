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
        "EXCITED" -> Mood.EXCITED
        "PEACEFUL" -> Mood.PEACEFUL
        "NEUTRAL" -> Mood.NEUTRAL
        "SAD" -> Mood.SAD
        "STRESSED" -> Mood.STRESSED
        else -> null
    }
}

fun getMoodUiByMood(mood: Mood): MoodUi {
    val resId = getMood(mood)
    val name = when (mood) {
        Mood.EXCITED -> "EXCITED"
        Mood.PEACEFUL -> "PEACEFUL"
        Mood.NEUTRAL -> "NEUTRAL"
        Mood.SAD -> "SAD"
        Mood.STRESSED -> "STRESSED"
    }

    return MoodUi(
        resId = resId,
        name = name,
        isSelected = false
    )
}
