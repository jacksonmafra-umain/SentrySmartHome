package com.umain.sentry.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors

/**
 * Flexible icon-plus-text chip. When [stacked] is true the icon sits above the
 * labels (used by the Timer/Power chips on the Light screen and Open-fully /
 * Half / Closed / Auto chips on the Curtains screen). When false the chip is
 * compact and horizontal.
 */
@Composable
fun ActionChip(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    selected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    stacked: Boolean = false,
) {
    val bg = if (selected) SentryColors.GlassHigh else SentryColors.GlassLow
    val iconTint = if (selected) SentryColors.AccentOrange else SentryColors.TextPrimary

    val shape = RoundedCornerShape(24.dp)
    val inner: @Composable () -> Unit = {
        androidx.compose.foundation.layout.Box(
            Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp),
            )
        }
        Column {
            Text(title, style = MaterialTheme.typography.labelLarge, color = SentryColors.TextPrimary)
            subtitle?.let {
                Text(it, style = MaterialTheme.typography.labelSmall, color = SentryColors.TextSecondary)
            }
        }
    }

    if (stacked) {
        Column(
            modifier
                .clip(shape)
                .background(bg)
                .clickable(onClick = onClick)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) { inner() }
    } else {
        Row(
            modifier
                .clip(shape)
                .background(bg)
                .clickable(onClick = onClick)
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) { inner() }
    }
}
