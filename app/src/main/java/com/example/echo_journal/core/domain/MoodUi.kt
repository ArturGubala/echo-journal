package com.example.echo_journal.core.domain

import androidx.annotation.DrawableRes
import com.example.echo_journal.core.presentation.util.MoodColor

data class MoodUi(
    @DrawableRes val resId: Int,
    val name: String,
    val isSelected: Boolean,
    val moodColor: MoodColor
)
