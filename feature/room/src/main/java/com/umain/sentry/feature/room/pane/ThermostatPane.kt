package com.umain.sentry.feature.room.pane

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.umain.sentry.data.model.Device
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.designsystem.component.ActionChip
import com.umain.sentry.designsystem.component.GlassSurface
import com.umain.sentry.designsystem.component.VerticalTempSlider
import com.umain.sentry.designsystem.theme.SentryColors

/**
 * Bedroom → Thermostat pane. Left column shows two big stat cards (current
 * room temperature, humidity); right column shows the vertical temperature
 * slider with integer markers. Footer exposes Heating / Cooling chips as
 * action items.
 */
@Composable
fun ThermostatPane(
    device: Device?,
    onTargetChange: (Float) -> Unit,
    onToggleHeating: () -> Unit,
    onToggleCooling: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = device?.state as? DeviceState.Thermostat ?: return

    Column(
        modifier.fillMaxSize().padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                StatCard(
                    primary = "${state.currentC.toInt()}",
                    primaryUnit = "°C",
                    caption = "Outside ${state.outsideC.toInt()}°C",
                    accentColor = SentryColors.AccentBlue,
                )
                StatCard(
                    primary = "${(state.humidity * 100).toInt()}",
                    primaryUnit = "%",
                    caption = "Humidity level",
                    accentColor = SentryColors.AccentBlue,
                )
            }
            VerticalTempSlider(
                value = state.targetC,
                onValueChange = onTargetChange,
                height = 304.dp,
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ActionChip(
                title = "Heating",
                subtitle = if (state.heating) "On" else "Off",
                icon = Icons.Rounded.LocalFireDepartment,
                selected = state.heating,
                onClick = onToggleHeating,
                modifier = Modifier.weight(1f),
            )
            ActionChip(
                title = "Cooling",
                subtitle = if (state.cooling) "On" else "Off",
                icon = Icons.Rounded.AcUnit,
                selected = state.cooling,
                onClick = onToggleCooling,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(Modifier.height(4.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ActionChip(
                title = "Timer",
                subtitle = "Off",
                icon = Icons.Rounded.Timer,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            ActionChip(
                title = "Power",
                subtitle = "On",
                icon = Icons.Rounded.PowerSettingsNew,
                selected = true,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun StatCard(
    primary: String,
    primaryUnit: String,
    caption: String,
    accentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
) {
    GlassSurface(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = primary,
                    style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Light),
                    color = SentryColors.TextPrimary,
                )
                Text(
                    text = primaryUnit,
                    style = MaterialTheme.typography.titleMedium,
                    color = accentColor,
                    modifier = Modifier.padding(bottom = 10.dp, start = 2.dp),
                )
            }
            Text(
                text = caption,
                style = MaterialTheme.typography.labelMedium,
                color = SentryColors.TextSecondary,
            )
        }
    }
}
