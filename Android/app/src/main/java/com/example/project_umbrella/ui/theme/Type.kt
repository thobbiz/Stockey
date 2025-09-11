package com.example.project_umbrella.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.project_umbrella.R


val Poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 17.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = Poppins,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        fontSize = 16.sp
    ),
    displayLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 32.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Poppins,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Poppins,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 20.sp
    ),

    labelMedium = TextStyle(
        fontFamily = Poppins,
        fontSize = 17.sp,
        fontWeight = FontWeight.Thin
    )
)