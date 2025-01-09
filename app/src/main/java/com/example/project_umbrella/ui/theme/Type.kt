package com.example.project_umbrella.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.project_umbrella.R


val Geist = FontFamily(
    Font(R.font.circularstd_bold),
    Font(R.font.circularstd_medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Geist,
        fontSize = 20.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = Geist,
        fontSize = 12.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = Geist,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Geist,
        fontSize = 30.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Geist,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Geist,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Geist,
        fontSize = 14.sp
    )
)