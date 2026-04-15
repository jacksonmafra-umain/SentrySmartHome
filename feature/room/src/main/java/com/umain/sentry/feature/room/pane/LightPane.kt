package com.umain.sentry.feature.room.pane

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.umain.sentry.data.model.Device
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.designsystem.component.ActionChip
import com.umain.sentry.designsystem.component.CircularDimmer
import com.umain.sentry.designsystem.component.ColorSwatchRow
import com.umain.sentry.designsystem.component.DefaultLightColors
import com.umain.sentry.designsystem.component.HangingLampIllustration
import com.umain.sentry.designsystem.tooling.PreviewBox
import com.umain.sentry.designsystem.tooling.SentryPreview
import com.umain.sentry.designsystem.theme.SentryColors

/**
 * Bedroom → Main light pane. A circular dimmer wraps a hanging-lamp
 * illustration; below it sits a row of glossy colour swatches and two chips
 * (Timer, Power). Adjusting the ring updates the lamp brightness in real time.
 */
@Composable
fun LightPane(
    device: Device?,
    onToggle: (Boolean) -> Unit,
    onBrightness: (Float) -> Unit,
    onColor: (Long) -> Unit,
    onTimerToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = device?.state as? DeviceState.Light ?: return

    Column(
        modifier.fillMaxSize().padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(Modifier.fillMaxWidth().height(320.dp), contentAlignment = Alignment.Center) {
            CircularDimmer(
                progress = state.brightness,
                onProgressChange = onBrightness,
                glow = Color(state.colorHex),
                size = 300.dp,
            )
            HangingLampIllustration(
                size = 200.dp,
                glow = if (state.on) state.brightness.coerceIn(0.15f, 1f) else 0f,
                warmColor = Color(state.colorHex),
            )
        }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Choose a color",
                style = MaterialTheme.typography.bodyMedium,
                color = SentryColors.TextSecondary,
            )

            ColorSwatchRow(
                colors = DefaultLightColors,
                selected = Color(state.colorHex),
                onSelect = { onColor(it.toArgb().toLong() and 0xFFFFFFFFL or 0xFF000000L) },
            )

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ActionChip(
                    title = "Timer",
                    subtitle = if (state.timerOn) "On" else "Off",
                    icon = Icons.Rounded.Timer,
                    selected = state.timerOn,
                    onClick = { onTimerToggle(!state.timerOn) },
                    modifier = Modifier.weight(1f),
                )
                ActionChip(
                    title = "Power",
                    subtitle = if (state.on) "On" else "Off",
                    icon = Icons.Rounded.PowerSettingsNew,
                    selected = state.on,
                    onClick = { onToggle(!state.on) },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(Modifier.size(8.dp))
    }
}

@SentryPreview
@Composable
private fun LightPanePreview() {
    PreviewBox {
        LightPane(
            device = Device(
                id = "lamp_bedroom",
                name = "Main light",
                roomId = "bedroom",
                state = DeviceState.Light(
                    on = true,
                    brightness = 0.55f,
                    colorHex = 0xFFFFD27AL,
                    timerOn = false,
                ),
            ),
            onToggle = {},
            onBrightness = {},
            onColor = {},
            onTimerToggle = {},
        )
    }
}
