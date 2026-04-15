package com.umain.sentry.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

private val SentryDarkColorScheme = darkColorScheme(
    background     = SentryColors.Background,
    onBackground   = SentryColors.TextPrimary,
    surface        = SentryColors.BackgroundRaised,
    onSurface      = SentryColors.TextPrimary,
    surfaceVariant = SentryColors.GlassMid,
    onSurfaceVariant = SentryColors.TextSecondary,
    primary        = SentryColors.AccentOrange,
    onPrimary      = SentryColors.TextPrimary,
    secondary      = SentryColors.AccentGreen,
    onSecondary    = SentryColors.Background,
    tertiary       = SentryColors.AccentBlue,
    onTertiary     = SentryColors.Background,
    error          = SentryColors.AccentRed,
    onError        = SentryColors.TextPrimary,
)

/** Root theme used by every screen. Always dark — the whole product is dark. */
@Composable
fun SentryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SentryDarkColorScheme,
        typography  = SentryTypography,
        shapes      = SentryShapes,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(SentryBackgroundBrush())
        ) {
            content()
        }
    }
}

/** Very soft radial highlight at the top — mimics the "spotlight over dark room"
 *  vibe the Phenomenon mockups have. */
@Composable
fun SentryBackgroundBrush(): Brush =
    Brush.verticalGradient(
        0f   to androidx.compose.ui.graphics.Color(0xFF14141A),
        0.5f to SentryColors.Background,
        1f   to SentryColors.Background,
    )
