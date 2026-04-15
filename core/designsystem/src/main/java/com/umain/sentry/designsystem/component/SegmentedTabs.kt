package com.umain.sentry.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors
import com.umain.sentry.designsystem.tooling.PreviewColumn
import com.umain.sentry.designsystem.tooling.SentryPreview

/** Pill-shaped segmented control used throughout the product for room tabs,
 *  activity tabs and the inner tabs inside the room detail screen. */
@Composable
fun <T> SegmentedTabs(
    items: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: (T) -> String,
    badge: (T) -> Int? = { null },
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items.forEach { item ->
            val isSelected = item == selected
            val background by animateColorAsState(
                if (isSelected) SentryColors.GlassHigh else Color.Transparent,
                label = "tab-bg"
            )
            Box(
                Modifier
                    .clip(RoundedCornerShape(50))
                    .background(background)
                    .clickable { onSelect(item) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = label(item),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) SentryColors.TextPrimary else SentryColors.TextSecondary,
                    )
                    badge(item)?.let { count ->
                        Box(
                            Modifier
                                .padding(start = 6.dp)
                                .clip(RoundedCornerShape(50))
                                .background(SentryColors.AccentRed)
                                .padding(horizontal = 6.dp, vertical = 1.dp),
                        ) {
                            Text(
                                text = count.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = SentryColors.TextPrimary,
                            )
                        }
                    }
                }
            }
        }
    }
}

@SentryPreview
@Composable
private fun SegmentedTabsPreview() {
    PreviewColumn {
        var rooms by remember { mutableStateOf("Doorbell") }
        SegmentedTabs(
            items = listOf("Doorbell", "Living room", "Kitchen", "Backyard"),
            selected = rooms,
            onSelect = { rooms = it },
            label = { it },
        )

        var activity by remember { mutableStateOf("Activities") }
        SegmentedTabs(
            items = listOf("Live video", "Activities", "Devices"),
            selected = activity,
            onSelect = { activity = it },
            label = { it },
            badge = { tab -> 3.takeIf { tab == "Activities" } },
        )
    }
}
