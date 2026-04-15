package com.umain.sentry.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** Uses the platform default SansSerif — the designs use a modern humanist sans
 *  which SansSerif (Roboto/Google Sans on most devices) approximates well enough
 *  for a demo. Swap in a bundled Inter/Manrope font if you want higher fidelity. */
private val Sans = FontFamily.SansSerif

val SentryTypography = Typography(
    displayLarge = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Light,    fontSize = 64.sp, lineHeight = 68.sp),
    displayMedium = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Normal,  fontSize = 44.sp, lineHeight = 50.sp),

    headlineLarge  = TextStyle(fontFamily = Sans, fontWeight = FontWeight.SemiBold, fontSize = 28.sp, lineHeight = 34.sp),
    headlineMedium = TextStyle(fontFamily = Sans, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 28.sp),
    headlineSmall  = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Medium,   fontSize = 18.sp, lineHeight = 24.sp),

    titleLarge  = TextStyle(fontFamily = Sans, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 26.sp),
    titleMedium = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Medium,   fontSize = 16.sp, lineHeight = 22.sp),
    titleSmall  = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Medium,   fontSize = 13.sp, lineHeight = 18.sp),

    bodyLarge   = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Normal, fontSize = 15.sp, lineHeight = 22.sp),
    bodyMedium  = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Normal, fontSize = 13.sp, lineHeight = 18.sp),
    bodySmall   = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Normal, fontSize = 11.sp, lineHeight = 14.sp),

    labelLarge = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Medium, fontSize = 13.sp, lineHeight = 16.sp),
    labelMedium = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 14.sp),
    labelSmall = TextStyle(fontFamily = Sans, fontWeight = FontWeight.Medium, fontSize = 10.sp, lineHeight = 12.sp),
)
