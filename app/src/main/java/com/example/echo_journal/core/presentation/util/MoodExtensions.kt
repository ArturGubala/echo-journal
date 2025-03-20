package com.example.echo_journal.core.presentation.util

import androidx.compose.ui.graphics.Color
import com.example.echo_journal.R
import com.example.echo_journal.core.domain.Mood
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.ui.theme.MoodExcited35
import com.example.echo_journal.ui.theme.MoodExcited80
import com.example.echo_journal.ui.theme.MoodExcited95
import com.example.echo_journal.ui.theme.MoodNeutral35
import com.example.echo_journal.ui.theme.MoodNeutral80
import com.example.echo_journal.ui.theme.MoodNeutral95
import com.example.echo_journal.ui.theme.MoodPeaceful35
import com.example.echo_journal.ui.theme.MoodPeaceful80
import com.example.echo_journal.ui.theme.MoodPeaceful95
import com.example.echo_journal.ui.theme.MoodSad35
import com.example.echo_journal.ui.theme.MoodSad80
import com.example.echo_journal.ui.theme.MoodSad95
import com.example.echo_journal.ui.theme.MoodStressed35
import com.example.echo_journal.ui.theme.MoodStressed80
import com.example.echo_journal.ui.theme.MoodStressed95

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
        isSelected = false,
        moodColor = getMoodColorByMoodName(name)
    )
}

fun getMoodStringByMood(mood: Mood): String {
    return when (mood) {
        Mood.EXCITED -> "EXCITED"
        Mood.PEACEFUL -> "PEACEFUL"
        Mood.NEUTRAL -> "NEUTRAL"
        Mood.SAD -> "SAD"
        Mood.STRESSED -> "STRESSED"
    }
}

fun getMoodColorByMoodName(name: String): MoodColor {
    return when (name) {
        "EXCITED" -> MoodColor(
            button = MoodExcited35,
            track = MoodExcited80,
            background = MoodExcited95
        )
        "PEACEFUL" -> MoodColor(
            button = MoodPeaceful35,
            track = MoodPeaceful80,
            background = MoodPeaceful95
        )
        "NEUTRAL" -> MoodColor(
            button = MoodNeutral35,
            track = MoodNeutral80,
            background = MoodNeutral95
        )
        "SAD" -> MoodColor(
            button = MoodSad35,
            track = MoodSad80,
            background = MoodSad95
        )
        "STRESSED" -> MoodColor(
            button = MoodStressed35,
            track = MoodStressed80,
            background = MoodStressed95
        )
        else -> MoodColor(
            button = Color.Transparent,
            track = Color.Transparent,
            background = Color.Transparent
        )
    }
}

data class MoodColor(
    val button: Color = Color.Transparent,
    val track: Color = Color.Transparent,
    val background: Color = Color.Transparent
)
