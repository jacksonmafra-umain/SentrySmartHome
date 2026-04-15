package com.umain.sentry.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors
import com.umain.sentry.designsystem.tooling.PreviewColumn
import com.umain.sentry.designsystem.tooling.SentryPreview

/**
 * A reusable "frosted glass" container: a translucent white fill layered on top
 * of a soft diagonal highlight, with a hairline border. Drop-in alternative to
 * Material3 Card when you want the Phenomenon look.
 */
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    tint: Color = SentryColors.GlassMid,
    border: BorderStroke? = BorderStroke(1.dp, SentryColors.GlassBorder),
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier
            .clip(shape)
            .background(tint)
            .background(
                Brush.linearGradient(
                    0f   to Color.White.copy(alpha = 0.08f),
                    0.4f to Color.Transparent,
                )
            )
            .let { if (border != null) it.border(border, shape) else it }
    ) {
        content()
    }
}

@SentryPreview
@Composable
private fun GlassSurfacePreview() {
    PreviewColumn {
        GlassSurface(modifier = Modifier.height(80.dp)) {
            Box(Modifier.padding(16.dp)) {
                Text(
                    "Default glass (alpha .12)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SentryColors.TextPrimary,
                )
            }
        }
        GlassSurface(
            modifier = Modifier.height(80.dp),
            tint = SentryColors.GlassLow,
        ) {
            Box(Modifier.padding(16.dp)) {
                Text(
                    "Low glass (chips, dock)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SentryColors.TextPrimary,
                )
            }
        }
        GlassSurface(
            modifier = Modifier.height(80.dp),
            tint = SentryColors.GlassHigh,
            border = null,
        ) {
            Box(Modifier.padding(16.dp)) {
                Text(
                    "High glass (active tab)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SentryColors.TextPrimary,
                )
            }
        }
    }
}
