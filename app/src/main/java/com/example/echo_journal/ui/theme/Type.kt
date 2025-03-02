package com.example.echo_journal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.echo_journal.R

val Inter = FontFamily(
    Font(resId = R.font.inter_medium, weight = FontWeight.Medium, loadingStrategy = FontLoadingStrategy.Async),
    Font(resId = R.font.inter_regular, weight = FontWeight.Normal, loadingStrategy = FontLoadingStrategy.Async),
    Font(resId = R.font.inter_semibold, weight = FontWeight.SemiBold, loadingStrategy = FontLoadingStrategy.Async)
)

val Typography = Typography(
    labelMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = EchoGrayBlue
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = EchoGrayBlue
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        color = EchoGrayBlue
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = EchoGrayBlue
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = EchoDark
    ),
    titleSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        color = EchoDark
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
        color = EchoDark
    ),
)
