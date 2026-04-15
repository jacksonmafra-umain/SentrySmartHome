package com.umain.sentry.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Circular dimmer ring used on the "Main light" and Device Hub screens.
 * Draws a faint full track, a warm arc from 0..progress, notch tick marks, and
 * a glowing knob handle. Progress is [0f, 1f]; 0 is at the bottom (6 o'clock)
 * and the arc sweeps clockwise, matching the Phenomenon mockup.
 */
@Composable
fun CircularDimmer(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 260.dp,
    trackWidth: Dp = 2.dp,
    glow: Color = SentryColors.AccentWarm,
    content: @Composable () -> Unit = {},
) {
    var boxSize by remember { mutableStateOf(Size.Zero) }

    Box(
        modifier
            .size(size)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val cx = boxSize.width / 2f
                    val cy = boxSize.height / 2f
                    val dx = change.position.x - cx
                    val dy = change.position.y - cy
                    var deg = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
                    deg = (deg + 90f + 360f) % 360f
                    onProgressChange((deg / 360f).coerceIn(0f, 1f))
                }
            }
    ) {
        Canvas(Modifier.size(size)) {
            boxSize = this.size
            val stroke = trackWidth.toPx()
            val r = this.size.minDimension / 2f - stroke * 2
            val center = Offset(this.size.width / 2f, this.size.height / 2f)

            drawCircle(
                color = Color.White.copy(alpha = 0.12f),
                radius = r,
                center = center,
                style = Stroke(width = stroke),
            )

            for (i in 0 until 36) {
                val t = i * 10f
                val rad = Math.toRadians((t - 90).toDouble())
                val outer = Offset(
                    center.x + (r + 6f) * cos(rad).toFloat(),
                    center.y + (r + 6f) * sin(rad).toFloat(),
                )
                val inner = Offset(
                    center.x + (r - 2f) * cos(rad).toFloat(),
                    center.y + (r - 2f) * sin(rad).toFloat(),
                )
                drawLine(Color.White.copy(alpha = 0.18f), inner, outer, 1f)
            }

            val sweep = progress.coerceIn(0f, 1f) * 360f
            rotate(90f, center) {
                drawArc(
                    brush = Brush.sweepGradient(
                        0f to glow.copy(alpha = 0f),
                        0.25f to glow,
                        1f to glow.copy(alpha = 0f),
                    ),
                    startAngle = 0f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(center.x - r, center.y - r),
                    size = Size(r * 2, r * 2),
                    style = Stroke(width = stroke * 2f),
                )
            }

            val knobDeg = (progress * 360f) - 90f
            val knobRad = Math.toRadians(knobDeg.toDouble())
            val knob = Offset(
                center.x + r * cos(knobRad).toFloat(),
                center.y + r * sin(knobRad).toFloat(),
            )
            drawCircle(glow.copy(alpha = 0.55f), radius = 18f, center = knob)
            drawCircle(Color.White, radius = 9f, center = knob)
        }
        content()
    }
}
