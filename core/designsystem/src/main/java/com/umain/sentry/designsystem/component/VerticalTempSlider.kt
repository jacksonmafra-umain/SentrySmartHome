package com.umain.sentry.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors

/**
 * Vertical temperature slider with a cold→warm gradient and a bubble handle,
 * used on the Bedroom → Thermostat screen. Value is clamped to [range].
 */
@Composable
fun VerticalTempSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    range: ClosedFloatingPointRange<Float> = 15f..30f,
    width: Dp = 64.dp,
    height: Dp = 280.dp,
) {
    val span = range.endInclusive - range.start
    Box(
        modifier
            .size(width = width, height = height)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val normalized = 1f - (change.position.y / size.height).coerceIn(0f, 1f)
                    onValueChange(range.start + normalized * span)
                }
            }
    ) {
        Canvas(Modifier.size(width = width, height = height)) {
            val w = this.size.width
            val h = this.size.height

            val trackX = w / 2f
            drawLine(
                brush = Brush.verticalGradient(
                    0f to SentryColors.AccentOrange,
                    0.5f to SentryColors.AccentWarm,
                    1f to SentryColors.AccentBlue,
                ),
                start = Offset(trackX, 0f),
                end = Offset(trackX, h),
                strokeWidth = 4f,
                cap = androidx.compose.ui.graphics.StrokeCap.Round,
            )

            // Tick marks every 5°
            val steps = 4
            for (i in 0..steps) {
                val ty = h * i / steps
                drawLine(
                    color = Color.White.copy(alpha = 0.25f),
                    start = Offset(trackX - 10f, ty),
                    end = Offset(trackX - 18f, ty),
                    strokeWidth = 1f,
                )
            }

            // Handle
            val ratio = ((value - range.start) / span).coerceIn(0f, 1f)
            val hy = h * (1f - ratio)
            drawCircle(
                color = Color.White,
                radius = 10f,
                center = Offset(trackX, hy),
            )
            drawCircle(
                color = Color.Black.copy(alpha = 0.5f),
                radius = 10f,
                center = Offset(trackX, hy),
                style = Stroke(width = 2f),
            )
        }
    }
}
