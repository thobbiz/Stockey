package com.example.project_umbrella.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.project_umbrella.R


val Univers = FontFamily(
    Font(R.font.universe_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Univers,
        fontSize = 20.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = Univers,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Univers,
        fontSize = 18.sp,
        fontWeight = FontWeight.Thin
    ),
    displayLarge = TextStyle(
        fontFamily = Univers,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = Univers,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Univers,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Univers,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Univers,
        fontSize = 20.sp
    ),

    labelMedium = TextStyle(
        fontFamily = Univers,
        fontSize = 17.sp,
        fontWeight = FontWeight.Thin
    )
)