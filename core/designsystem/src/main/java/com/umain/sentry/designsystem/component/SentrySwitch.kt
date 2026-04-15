package com.umain.sentry.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors

/** Compact pill switch with a glass look when off and a saturated green when on.
 *  Small by design (36×20dp) — matches the switches on the Sentry device cards. */
@Composable
fun SentrySwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val trackColor by animateColorAsState(
        if (checked) SentryColors.AccentGreen else Color(0x33FFFFFF),
        label = "track"
    )
    val thumbX by animateDpAsState(if (checked) 18.dp else 2.dp, label = "thumb")
    Box(
        modifier
            .size(width = 38.dp, height = 22.dp)
            .clip(RoundedCornerShape(50))
            .background(trackColor)
            .clickable { onCheckedChange(!checked) }
    ) {
        Box(
            Modifier
                .align(Alignment.CenterStart)
                .offset(x = thumbX)
                .size(18.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}
