package com.umain.sentry.feature.room

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreHoriz
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.umain.sentry.designsystem.component.SegmentedTabs
import com.umain.sentry.designsystem.theme.SentryColors
import com.umain.sentry.feature.room.pane.CurtainsPane
import com.umain.sentry.feature.room.pane.LightPane
import com.umain.sentry.feature.room.pane.ThermostatPane
import com.umain.sentry.navigation.RoomPane
import com.umain.sentry.navigation.SentryRoute
import com.umain.sentry.ui.Spacings
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.roomScreen(onBack: () -> Unit) {
    composable<SentryRoute.Room> {
        RoomRoute(onBack)
    }
}

@Composable
private fun RoomRoute(
    onBack: () -> Unit,
    viewModel: RoomViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var activePane by remember { mutableStateOf(state.initialPane) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(
                horizontal = Spacings.screenHorizontal,
                vertical = Spacings.screenVertical,
            ),
        verticalArrangement = Arrangement.spacedBy(Spacings.cardGap),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RoomHeader(title = state.room?.name.orEmpty(), onBack = onBack)

        SegmentedTabs(
            items = RoomPane.entries,
            selected = activePane,
            onSelect = { activePane = it },
            label = { it.label() },
        )

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopCenter,
        ) {
            when (activePane) {
                RoomPane.Light      -> LightPane(
                    device = state.light,
                    onToggle = { viewModel.setLight(on = it) },
                    onBrightness = { viewModel.setLight(brightness = it) },
                    onColor = { viewModel.setLight(colorHex = it) },
                    onTimerToggle = { viewModel.setLight(timerOn = it) },
                )
                RoomPane.Thermostat -> ThermostatPane(
                    device = state.thermostat,
                    onTargetChange = viewModel::setTargetTemp,
                    onToggleHeating = viewModel::toggleHeating,
                    onToggleCooling = viewModel::toggleCooling,
                )
                RoomPane.Curtains   -> CurtainsPane(
                    device = state.curtains,
                    onPosition = viewModel::setCurtainPosition,
                    onAuto = viewModel::toggleCurtainAuto,
                    onBrightness = viewModel::setCurtainBrightness,
                )
            }
        }
    }
}

private fun RoomPane.label() = when (this) {
    RoomPane.Light      -> "Main light"
    RoomPane.Thermostat -> "Thermostat"
    RoomPane.Curtains   -> "Curtains"
}

@Composable
private fun RoomHeader(title: String, onBack: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleIconButton(icon = Icons.Rounded.ArrowBack, onClick = onBack)
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = SentryColors.TextPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        CircleIconButton(icon = Icons.Rounded.MoreHoriz, onClick = {})
    }
}

@Composable
private fun CircleIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Box(
        Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(SentryColors.GlassLow)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SentryColors.TextPrimary,
            modifier = Modifier.size(18.dp),
        )
    }
}
