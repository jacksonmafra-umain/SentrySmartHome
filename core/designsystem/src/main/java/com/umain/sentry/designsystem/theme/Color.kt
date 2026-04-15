package com.umain.sentry.designsystem.theme

import androidx.compose.ui.graphics.Color

/**
 * Sentry Smart Home palette — recreated from the Phenomenon Studio mockup.
 * The look is "dark glass": a near-black background with frosted, slightly
 * tinted translucent cards, soft radial highlights and small accent spots.
 */
object SentryColors {
    val Background       = Color(0xFF0A0A0C)
    val BackgroundRaised = Color(0xFF121214)

    // Glass surfaces — used for cards, tabs, chips. Alpha-laid on the bg.
    val GlassHigh  = Color(0x33FFFFFF) // bright glass (active tab)
    val GlassMid   = Color(0x1FFFFFFF) // normal glass card
    val GlassLow   = Color(0x14FFFFFF) // subtle glass (chips)
    val GlassBorder = Color(0x1FFFFFFF)

    val TextPrimary   = Color(0xFFF5F5F7)
    val TextSecondary = Color(0xFFB5B5BC)
    val TextMuted     = Color(0xFF86868E)

    val AccentOrange = Color(0xFFFF6B4A)   // emergency, hot, warning
    val AccentGreen  = Color(0xFF6BD16B)   // "on" switch, OK status
    val AccentBlue   = Color(0xFF5EC6FF)   // cold, thermostat
    val AccentWarm   = Color(0xFFFFB86B)   // lamp glow
    val AccentRed    = Color(0xFFFF5A5F)   // alerts, notification dot

    // Lamp-color swatches used on the Main light screen
    val SwatchGreen  = Color(0xFF9FE7C1)
    val SwatchBlue   = Color(0xFF78C6FF)
    val SwatchWarm   = Color(0xFFFFD27A)
    val SwatchPink   = Color(0xFFFFA3C7)
    val SwatchPurple = Color(0xFFCDB2FF)
}
