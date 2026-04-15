package com.umain.sentry.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors

/**
 * Stylised hanging pendant lamp used on the "Main light" and "Device Hub" screens.
 * Entirely drawn with Canvas so the demo stays asset-free. The "glow" parameter
 * controls the visible brightness — pass 0f..1f to dim the lamp (it affects the
 * radial halo + inner shade fill opacity).
 */
@Composable
fun HangingLampIllustration(
    modifier: Modifier = Modifier,
    size: Dp = 180.dp,
    glow: Float = 1f,
    warmColor: Color = SentryColors.AccentWarm,
) {
    Box(modifier.size(size)) {
        Canvas(Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height
            val centerX = w / 2f

            // 1. hanging cord
            drawLine(
                color = Color.White.copy(alpha = 0.25f),
                start = Offset(centerX, 0f),
                end = Offset(centerX, h * 0.28f),
                strokeWidth = 1.2f,
            )

            // 2. halo glow
            drawCircle(
                brush = Brush.radialGradient(
                    0f to warmColor.copy(alpha = 0.55f * glow),
                    0.6f to warmColor.copy(alpha = 0.12f * glow),
                    1f to Color.Transparent,
                    center = Offset(centerX, h * 0.55f),
                    radius = w * 0.55f,
                ),
                radius = w * 0.55f,
                center = Offset(centerX, h * 0.55f),
            )

            // 3. shade (trapezoid from a narrow top to a wide bottom)
            val shade = Path().apply {
                moveTo(centerX - w * 0.12f, h * 0.28f)
                lineTo(centerX + w * 0.12f, h * 0.28f)
                lineTo(centerX + w * 0.28f, h * 0.58f)
                lineTo(centerX - w * 0.28f, h * 0.58f)
                close()
            }
            drawPath(
                path = shade,
                brush = Brush.verticalGradient(
                    0f to Color(0xFF1B1E23),
                    1f to Color(0xFF2B303A),
                    startY = h * 0.28f,
                    endY = h * 0.58f,
                ),
            )
            drawPath(
                path = shade,
                brush = Brush.linearGradient(
                    0f to Color.White.copy(alpha = 0.22f),
                    1f to Color.Transparent,
                    start = Offset(centerX - w * 0.2f, h * 0.3f),
                    end = Offset(centerX, h * 0.58f),
                ),
            )

            // 4. inner warm slice (the visible bulb light under the shade)
            val bulbGlow = Path().apply {
                moveTo(centerX - w * 0.20f, h * 0.58f)
                lineTo(centerX + w * 0.20f, h * 0.58f)
                lineTo(centerX + w * 0.06f, h * 0.82f)
                lineTo(centerX - w * 0.06f, h * 0.82f)
                close()
            }
            drawPath(
                path = bulbGlow,
                brush = Brush.verticalGradient(
                    0f to warmColor.copy(alpha = 0.95f * glow),
                    1f to warmColor.copy(alpha = 0f),
                    startY = h * 0.58f,
                    endY = h * 0.82f,
                ),
            )
        }
    }
}
