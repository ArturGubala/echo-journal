package com.example.echo_journal.core.domain

import androidx.annotation.DrawableRes

data class MoodUi(
    @DrawableRes val resId: Int,
    val name: String,
    val isSelected: Boolean
)
