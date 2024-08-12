package com.example.bookshelf.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bookshelf.R

// Set of Material typography styles to start with
val merriweather = FontFamily(
    Font(R.font.merriweather_regular)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = merriweather,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = merriweather,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = merriweather,
        fontSize = 22.sp
    ),
    titleLarge = TextStyle(
        fontFamily = merriweather,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = merriweather,
        fontSize = 18.sp
    )
)