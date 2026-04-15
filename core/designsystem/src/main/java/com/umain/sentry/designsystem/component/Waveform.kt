package com.umain.sentry.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Decorative waveform visualization shown above the Activities screen content.
 * Each value in [levels] is a magnitude 0..1 — we draw a thin vertical line
 * for each, centered on the canvas. Cheap, no audio processing; purely visual.
 */
@Composable
fun WaveformBar(
    levels: List<Float>,
    modifier: Modifier = Modifier,
    height: Dp = 28.dp,
    color: Color = Color.White.copy(alpha = 0.6f),
) {
    Canvas(
        modifier
            .fillMaxWidth()
            .height(height)
    ) {
        if (levels.isEmpty()) return@Canvas
        val slotWidth = size.width / levels.size
        val mid = size.height / 2f
        levels.forEachIndexed { i, level ->
            val bar = size.height * level.coerceIn(0.05f, 1f)
            val x = slotWidth * (i + 0.5f)
            drawLine(
                color = color,
                start = Offset(x, mid - bar / 2f),
                end = Offset(x, mid + bar / 2f),
                strokeWidth = 2f,
                cap = androidx.compose.ui.graphics.StrokeCap.Round,
            )
        }
    }
}
