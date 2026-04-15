package com.umain.sentry.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WaterDrop
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.umain.sentry.data.model.Device
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.designsystem.component.EmergencyCallButton
import com.umain.sentry.designsystem.component.GlassSurface
import com.umain.sentry.designsystem.component.SegmentedTabs
import com.umain.sentry.designsystem.component.SentrySwitch
import com.umain.sentry.designsystem.theme.SentryColors
import com.umain.sentry.navigation.SentryRoute
import com.umain.sentry.ui.Spacings
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeScreen(onNavigate: (SentryRoute) -> Unit) {
    composable<SentryRoute.Home> {
        HomeRoute(onNavigate)
    }
}

@Composable
private fun HomeRoute(
    onNavigate: (SentryRoute) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    HomeContent(
        state = state,
        onSelectRoom = { room ->
            viewModel.selectRoom(room.id)
            // Tapping a room tab jumps into the room detail.
            onNavigate(SentryRoute.Room(roomId = room.id))
        },
        onOpenActivities = { onNavigate(SentryRoute.Activities) },
        onOpenDeviceHub = { onNavigate(SentryRoute.DeviceHub) },
        onToggleDevice = viewModel::toggleDevice,
        onEmergency = { /* demo — no-op */ },
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onSelectRoom: (com.umain.sentry.data.model.Room) -> Unit,
    onOpenActivities: () -> Unit,
    onOpenDeviceHub: () -> Unit,
    onToggleDevice: (String) -> Unit,
    onEmergency: () -> Unit,
) {
    var selectedRoomTab by remember { mutableStateOf(state.rooms.firstOrNull()) }
    // Keep local tab state in sync with rooms once they load.
    if (selectedRoomTab == null && state.rooms.isNotEmpty()) {
        selectedRoomTab = state.rooms.first()
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = Spacings.screenHorizontal,
                vertical = Spacings.screenVertical,
            ),
        verticalArrangement = Arrangement.spacedBy(Spacings.cardGap),
    ) {
        HomeHeader(
            homeName = state.homeName,
            unreadCount = state.unreadActivities,
            onOpenActivities = onOpenActivities,
        )

        Spacer(Modifier.height(4.dp))

        SegmentedTabs(
            items = state.rooms,
            selected = selectedRoomTab ?: state.rooms.firstOrNull() ?: return@Column,
            onSelect = { room ->
                selectedRoomTab = room
                onSelectRoom(room)
            },
            label = { it.name },
        )

        DoorbellCard(
            onFullscreen = onOpenDeviceHub,
            onMic = { /* demo */ },
        )

        EmergencyCallButton(onClick = onEmergency)

        // A 2-column grid of device cards. Row 1: lock + thermostat
        val devices = state.devices
        val pairs = devices.windowed(size = 2, step = 2, partialWindows = true)
        pairs.forEach { row ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacings.cardGap),
            ) {
                row.forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { onToggleDevice(device.id) },
                        modifier = Modifier.weight(1f),
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }

        BottomDock(
            onLock = { /* demo */ },
            onEmergency = onEmergency,
            onLightbulb = onOpenDeviceHub,
        )
    }
}

@Composable
private fun HomeHeader(
    homeName: String,
    unreadCount: Int,
    onOpenActivities: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar circle
        Box(
            Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(SentryColors.SwatchWarm, SentryColors.SwatchPink)
                    )
                )
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = homeName,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            color = SentryColors.TextPrimary,
            modifier = Modifier.weight(1f),
        )
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(SentryColors.GlassLow)
                .clickable(onClick = onOpenActivities),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.Notifications,
                contentDescription = "Activities",
                tint = SentryColors.TextPrimary,
                modifier = Modifier.size(20.dp),
            )
            if (unreadCount > 0) {
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(SentryColors.AccentRed)
                )
            }
        }
    }
}

@Composable
private fun DoorbellCard(
    onMic: () -> Unit,
    onFullscreen: () -> Unit,
) {
    GlassSurface(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        cornerRadius = 24.dp,
    ) {
        Box(Modifier.fillMaxSize()) {
            // "Live video" placeholder — a soft gradient. Swap with an AsyncImage
            // when a real feed is available.
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF7A5A3A),
                                Color(0xFF1D1D22),
                            )
                        )
                    )
            )
            // Live badge
            Row(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.size(6.dp).clip(CircleShape).background(SentryColors.AccentRed))
                Spacer(Modifier.width(6.dp))
                Text("Live", color = SentryColors.TextPrimary, style = MaterialTheme.typography.labelMedium)
            }

            // Mic + fullscreen icons
            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                IconBubble(icon = Icons.Rounded.Mic, onClick = onMic)
                IconBubble(icon = Icons.Rounded.Fullscreen, onClick = onFullscreen)
            }
        }
    }
}

@Composable
private fun IconBubble(icon: ImageVector, onClick: () -> Unit) {
    Box(
        Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.35f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun DeviceCard(
    device: Device,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (title, subtitle, icon, on) = device.summary()
    GlassSurface(
        modifier = modifier.height(120.dp),
        cornerRadius = 20.dp,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(Spacings.cardPadding),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleSmall,
                        color = SentryColors.TextPrimary,
                    )
                    subtitle?.let {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            it,
                            style = MaterialTheme.typography.labelSmall,
                            color = SentryColors.TextSecondary,
                        )
                    }
                }
                SentrySwitch(checked = on, onCheckedChange = { onToggle() })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SentryColors.TextSecondary,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
    }
}

private data class DeviceSummary(
    val title: String,
    val subtitle: String?,
    val icon: ImageVector,
    val on: Boolean,
)

private fun Device.summary(): DeviceSummary = when (val s = state) {
    is DeviceState.Light       -> DeviceSummary(name, "${(s.brightness * 100).toInt()}%", Icons.Rounded.Lightbulb, s.on)
    is DeviceState.Lock        -> DeviceSummary(name, "${s.battery}%", Icons.Rounded.Fingerprint, s.locked)
    is DeviceState.Thermostat  -> DeviceSummary(name, "${s.currentC.toInt()}°C", Icons.Rounded.Thermostat, s.heating)
    is DeviceState.Vacuum      -> DeviceSummary(name, "${s.minutesLeft} minutes left", Icons.Rounded.Pets, s.running)
    is DeviceState.LeakDetector  -> DeviceSummary(name, if (s.leaking) "Leak!" else "Normal", Icons.Rounded.WaterDrop, !s.leaking)
    is DeviceState.SmokeDetector -> DeviceSummary(name, if (s.smoke) "Smoke!" else "Normal", Icons.Rounded.WaterDrop, !s.smoke)
    is DeviceState.Doorbell    -> DeviceSummary(name, if (s.streamingLive) "Live" else "Idle", Icons.Rounded.CameraAlt, s.online)
    is DeviceState.Curtains    -> DeviceSummary(name, if (s.auto) "Auto" else s.position.name, Icons.Rounded.Lightbulb, s.auto)
}

@Composable
private fun BottomDock(
    onLock: () -> Unit,
    onEmergency: () -> Unit,
    onLightbulb: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = Spacings.sectionGap)
            .clip(RoundedCornerShape(50))
            .background(SentryColors.GlassLow)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBubble(icon = Icons.Rounded.Pets, onClick = onEmergency)
        IconBubble(icon = Icons.Rounded.Lock,          onClick = onLock)
        IconBubble(icon = Icons.Rounded.Lightbulb,     onClick = onLightbulb)
    }
}
