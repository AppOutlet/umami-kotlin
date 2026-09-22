package dev.appoutlet.kombu.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

private val KombuFontFamily = FontFamily.SansSerif

private fun kombuTextStyle(
    fontSize: TextUnit,
    lineHeight: TextUnit,
    fontWeight: FontWeight,
    letterSpacing: TextUnit = TextUnit.Unspecified,
) = TextStyle(
    fontFamily = KombuFontFamily,
    fontSize = fontSize,
    lineHeight = lineHeight,
    fontWeight = fontWeight,
    letterSpacing = letterSpacing,
)

val KombuTypography = Typography(
    displayLarge = kombuTextStyle(57.sp, 64.sp, FontWeight.Bold, (-1.14).sp),
    displayMedium = kombuTextStyle(45.sp, 52.sp, FontWeight.Bold, (-0.9).sp),
    displaySmall = kombuTextStyle(36.sp, 44.sp, FontWeight.Bold, (-0.72).sp),
    headlineLarge = kombuTextStyle(32.sp, 40.sp, FontWeight.Bold, (-0.64).sp),
    headlineMedium = kombuTextStyle(28.sp, 36.sp, FontWeight.Bold, (-0.56).sp),
    headlineSmall = kombuTextStyle(24.sp, 32.sp, FontWeight.Bold, (-0.48).sp),
    titleLarge = kombuTextStyle(22.sp, 28.sp, FontWeight.Bold, (-0.44).sp),
    titleMedium = kombuTextStyle(16.sp, 24.sp, FontWeight.Medium),
    titleSmall = kombuTextStyle(14.sp, 20.sp, FontWeight.Medium),
    bodyLarge = kombuTextStyle(16.sp, 24.sp, FontWeight.Normal),
    bodyMedium = kombuTextStyle(14.sp, 20.sp, FontWeight.Normal),
    bodySmall = kombuTextStyle(12.sp, 16.sp, FontWeight.Normal),
    labelLarge = kombuTextStyle(14.sp, 20.sp, FontWeight.Medium),
    labelMedium = kombuTextStyle(12.sp, 16.sp, FontWeight.Medium, 0.96.sp),
    labelSmall = kombuTextStyle(11.sp, 16.sp, FontWeight.Medium, 0.88.sp),
)
