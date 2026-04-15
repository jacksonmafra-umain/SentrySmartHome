package com.umain.sentry.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors

/** Row of glossy colour dots used to choose the lamp's light colour. The
 *  selected swatch gets a thin white ring. */
@Composable
fun ColorSwatchRow(
    colors: List<Color>,
    selected: Color,
    onSelect: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        colors.forEach { c ->
            val isSelected = c == selected
            Box(
                Modifier
                    .size(if (isSelected) 36.dp else 32.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            0f to c,
                            0.7f to c,
                            1f to c.copy(alpha = 0.65f),
                        )
                    )
                    .let { if (isSelected) it.border(2.dp, Color.White, CircleShape) else it }
                    .clickable { onSelect(c) }
            )
        }
    }
}

val DefaultLightColors: List<Color> = listOf(
    SentryColors.SwatchGreen,
    SentryColors.SwatchBlue,
    SentryColors.SwatchWarm,
    SentryColors.SwatchPink,
    SentryColors.SwatchPurple,
)
