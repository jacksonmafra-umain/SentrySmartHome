package com.umain.sentry.feature.room.pane

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Blinds
import androidx.compose.material.icons.rounded.Brightness2
import androidx.compose.material.icons.rounded.Brightness5
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.umain.sentry.data.model.CurtainPosition
import com.umain.sentry.data.model.Device
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.designsystem.component.ActionChip
import com.umain.sentry.designsystem.theme.SentryColors

/**
 * Bedroom → Curtains pane. A stylised pair of curtains that opens/closes
 * based on position, a brightness slider with sun/moon icons, and a 2×2 grid
 * of position chips (Open fully / Half / Closed / Auto).
 */
@Composable
fun CurtainsPane(
    device: Device?,
    onPosition: (CurtainPosition) -> Unit,
    onAuto: () -> Unit,
    onBrightness: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = device?.state as? DeviceState.Curtains ?: return

    Column(
        modifier.fillMaxSize().padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        CurtainsIllustration(
            position = state.position,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
        )

        BrightnessSlider(
            value = state.brightness,
            onValueChange = onBrightness,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ActionChip(
                title = "Open fully",
                icon = Icons.Rounded.ExpandLess,
                selected = state.position == CurtainPosition.Open && !state.auto,
                onClick = { onPosition(CurtainPosition.Open) },
                modifier = Modifier.weight(1f),
            )
            ActionChip(
                title = "Half",
                icon = Icons.Rounded.Brightness2,
                selected = state.position == CurtainPosition.Half && !state.auto,
                onClick = { onPosition(CurtainPosition.Half) },
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ActionChip(
                title = "Closed",
                icon = Icons.Rounded.Circle,
                selected = state.position == CurtainPosition.Closed && !state.auto,
                onClick = { onPosition(CurtainPosition.Closed) },
                modifier = Modifier.weight(1f),
            )
            ActionChip(
                title = "Auto",
                icon = Icons.Rounded.AccessTime,
                selected = state.auto,
                onClick = onAuto,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun CurtainsIllustration(
    position: CurtainPosition,
    modifier: Modifier = Modifier,
) {
    val target = when (position) {
        CurtainPosition.Open   -> 1f
        CurtainPosition.Half   -> 0.5f
        CurtainPosition.Closed -> 0f
    }
    // Smooth open/close — the curtains slide on the rod instead of snapping
    // when the user taps Open fully / Half / Closed / Auto.
    val openness by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "curtainOpenness",
    )
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val curtainWidth = (w / 2f) * (1f - openness * 0.8f)
        // rod
        drawLine(
            color = Color.White.copy(alpha = 0.35f),
            start = Offset(0f, 12f),
            end = Offset(w, 12f),
            strokeWidth = 4f,
        )
        // rod ends
        drawCircle(Color.White.copy(alpha = 0.6f), radius = 8f, center = Offset(4f, 12f))
        drawCircle(Color.White.copy(alpha = 0.6f), radius = 8f, center = Offset(w - 4f, 12f))

        // left curtain
        drawRect(
            brush = Brush.verticalGradient(
                0f to Color(0xFFE5D8C5),
                1f to Color(0xFFB9A68A),
            ),
            topLeft = Offset(0f, 12f),
            size = Size(curtainWidth, h - 24f),
        )
        // pleats
        val pleats = 6
        for (i in 1 until pleats) {
            val x = (curtainWidth / pleats) * i
            drawLine(
                color = Color.Black.copy(alpha = 0.18f),
                start = Offset(x, 12f),
                end = Offset(x, h - 12f),
                strokeWidth = 1.5f,
            )
        }

        // right curtain — mirrored
        drawRect(
            brush = Brush.verticalGradient(
                0f to Color(0xFFE5D8C5),
                1f to Color(0xFFB9A68A),
            ),
            topLeft = Offset(w - curtainWidth, 12f),
            size = Size(curtainWidth, h - 24f),
        )
        for (i in 1 until pleats) {
            val x = (w - curtainWidth) + (curtainWidth / pleats) * i
            drawLine(
                color = Color.Black.copy(alpha = 0.18f),
                start = Offset(x, 12f),
                end = Offset(x, h - 12f),
                strokeWidth = 1.5f,
            )
        }
    }
}

@Composable
private fun BrightnessSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    var width by remember { mutableStateOf(0f) }
    Row(
        modifier = modifier.height(36.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        androidx.compose.material3.Icon(
            imageVector = Icons.Rounded.Brightness5,
            contentDescription = null,
            tint = SentryColors.AccentGreen,
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.size(12.dp))
        Box(
            Modifier
                .weight(1f)
                .height(6.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        if (width > 0f)
                            onValueChange((change.position.x / width).coerceIn(0f, 1f))
                    }
                }
        ) {
            Canvas(Modifier.fillMaxSize()) {
                width = size.width
                val x = size.width * value
                drawRect(
                    color = SentryColors.AccentGreen,
                    topLeft = Offset(0f, 0f),
                    size = Size(x, size.height),
                )
                drawCircle(Color.White, radius = 10f, center = Offset(x, size.height / 2f))
            }
        }
        Spacer(Modifier.size(12.dp))
        androidx.compose.material3.Icon(
            imageVector = Icons.Rounded.Blinds,
            contentDescription = null,
            tint = SentryColors.TextSecondary,
            modifier = Modifier.size(20.dp),
        )
    }
}
