package com.umain.sentry.feature.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.umain.sentry.data.model.ActivityEvent
import com.umain.sentry.data.model.ActivityKind
import com.umain.sentry.designsystem.component.GlassSurface
import com.umain.sentry.designsystem.component.SegmentedTabs
import com.umain.sentry.designsystem.component.WaveformBar
import com.umain.sentry.designsystem.theme.SentryColors
import com.umain.sentry.navigation.SentryRoute
import com.umain.sentry.ui.Spacings
import kotlin.random.Random
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.activitiesScreen(onBack: () -> Unit) {
    composable<SentryRoute.Activities> {
        ActivitiesRoute(onBack)
    }
}

private enum class ActivitiesTab { LiveVideo, Activities, Devices }

private fun ActivitiesTab.label() = when (this) {
    ActivitiesTab.LiveVideo  -> "Live video"
    ActivitiesTab.Activities -> "Activities"
    ActivitiesTab.Devices    -> "Devices"
}

@Composable
private fun ActivitiesRoute(
    onBack: () -> Unit,
    viewModel: ActivitiesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableStateOf(ActivitiesTab.Activities) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = Spacings.screenHorizontal, vertical = Spacings.screenVertical),
        verticalArrangement = Arrangement.spacedBy(Spacings.cardGap),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier.size(40.dp).clip(CircleShape).background(SentryColors.GlassLow)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = SentryColors.TextPrimary,
                    modifier = Modifier.size(18.dp),
                )
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = "11:14:16 AM",
                style = MaterialTheme.typography.labelLarge,
                color = SentryColors.TextSecondary,
            )
        }

        WaveformBar(
            levels = remember { generateWaveform() },
            height = 32.dp,
        )

        SegmentedTabs(
            items = ActivitiesTab.entries,
            selected = selectedTab,
            onSelect = { selectedTab = it },
            label = { it.label() },
            badge = { if (it == ActivitiesTab.Activities) state.events.count { ev -> ev.live }.takeIf { c -> c > 0 } else null },
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.events, key = { it.id }) { event ->
                ActivityCard(event)
            }
        }
    }
}

@Composable
private fun ActivityCard(event: ActivityEvent) {
    GlassSurface(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        cornerRadius = 20.dp,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // thumbnail placeholder
            Box(
                Modifier
                    .size(width = 108.dp, height = 76.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(14.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF6B4A3B), Color(0xFF1E1E23))
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) {
                val playIcon = if (event.live) Icons.Rounded.Pause else Icons.Rounded.PlayArrow
                Box(
                    Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SentryColors.AccentGreen),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = playIcon,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    event.kinds.take(3).forEach { kind ->
                        Box(
                            Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.08f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = kind.icon(),
                                contentDescription = null,
                                tint = SentryColors.TextPrimary,
                                modifier = Modifier.size(12.dp),
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                    }
                    if (event.live) {
                        Spacer(Modifier.width(4.dp))
                        Box(Modifier.size(8.dp).clip(CircleShape).background(SentryColors.AccentRed))
                    }
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SentryColors.TextPrimary,
                )
                Text(
                    text = "${event.startedAt} – ${event.endedAt}",
                    style = MaterialTheme.typography.labelSmall,
                    color = SentryColors.TextSecondary,
                )
            }
        }
    }
}

private fun ActivityKind.icon(): ImageVector = when (this) {
    ActivityKind.Sound   -> Icons.Rounded.RecordVoiceOver
    ActivityKind.Animal  -> Icons.Rounded.Pets
    ActivityKind.Motion  -> Icons.Rounded.Sensors
    ActivityKind.Package -> Icons.Rounded.Inventory2
}

private fun generateWaveform(): List<Float> {
    val r = Random(42)
    return List(64) { r.nextFloat() * 0.9f + 0.1f }
}
