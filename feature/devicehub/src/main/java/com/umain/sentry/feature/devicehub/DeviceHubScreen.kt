package com.umain.sentry.feature.devicehub

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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.umain.sentry.designsystem.component.CircularDimmer
import com.umain.sentry.designsystem.component.HangingLampIllustration
import com.umain.sentry.designsystem.theme.SentryColors
import com.umain.sentry.navigation.SentryRoute
import com.umain.sentry.ui.Spacings
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.deviceHubScreen(onBack: () -> Unit) {
    composable<SentryRoute.DeviceHub> {
        DeviceHubRoute(onBack)
    }
}

@Composable
private fun DeviceHubRoute(
    onBack: () -> Unit,
    viewModel: DeviceHubViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = Spacings.screenHorizontal, vertical = Spacings.screenVertical),
        verticalArrangement = Arrangement.spacedBy(Spacings.sectionGap),
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
            Spacer(Modifier.size(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = "All devices,",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
                    color = SentryColors.TextPrimary,
                )
                Text(
                    text = "one smart hub",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = SentryColors.TextPrimary,
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                HubPill(icon = Icons.Rounded.Mic)
                HubPill(icon = Icons.Rounded.CameraAlt)
                HubPill(icon = Icons.Rounded.VolumeUp)
                HubPill(icon = Icons.Rounded.PowerSettingsNew)
            }
        }

        Spacer(Modifier.height(4.dp))

        Box(
            Modifier.fillMaxWidth().height(380.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularDimmer(
                progress = state.brightness,
                onProgressChange = viewModel::setBrightness,
                size = 340.dp,
                glow = Color(state.colorHex),
            )
            HangingLampIllustration(
                size = 220.dp,
                glow = if (state.on) state.brightness.coerceIn(0.15f, 1f) else 0f,
                warmColor = Color(state.colorHex),
            )
        }

        Text(
            text = "${state.deviceCount} devices",
            style = MaterialTheme.typography.labelLarge,
            color = SentryColors.TextSecondary,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HubPill(icon: ImageVector) {
    Box(
        Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(SentryColors.GlassLow),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SentryColors.TextPrimary,
            modifier = Modifier.size(16.dp),
        )
    }
}
