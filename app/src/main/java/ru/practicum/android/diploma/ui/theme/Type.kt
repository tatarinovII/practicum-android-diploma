package ru.practicum.android.diploma.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = PracticumFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = FontSizeBig,
        lineHeight = LineHeightBig
    ),
    titleLarge = TextStyle(
        fontFamily = PracticumFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = FontSizeMedium,
        lineHeight = LineHeightMedium
    ),
    titleMedium = TextStyle(
        fontFamily = PracticumFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = FontSizeBase,
        lineHeight = LineHeightSmall
    ),
    titleSmall = TextStyle(
        fontFamily = PracticumFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = FontSizeBase,
        lineHeight = LineHeightSmall
    ),
    bodySmall = TextStyle(
        fontFamily = PracticumFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = FontSizeSmall,
        lineHeight = LineHeightVerySmall
    )
)
