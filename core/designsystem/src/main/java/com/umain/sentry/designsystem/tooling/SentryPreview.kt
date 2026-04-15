package com.umain.sentry.designsystem.tooling

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multi-preview annotation for every Sentry Smart Home composable.
 *
 * The product is dark-only, so both rendered variants run in night mode —
 * one at the default font scale and one scaled up to 1.35× to catch
 * accessibility breakage. A light variant is rendered on top of a neutral
 * grey background for contrast auditing; components should still be legible
 * against it even though we never ship a light theme.
 *
 * Usage:
 * ```
 *   @SentryPreview
 *   @Composable
 *   private fun GlassSurfacePreview() {
 *       PreviewBox { GlassSurface { ... } }
 *   }
 * ```
 */

@Preview(
    name = "Light",
    group = "Light",
    showBackground = true,
    backgroundColor = 0xffEEEEEE,
    uiMode = UI_MODE_NIGHT_NO,
    fontScale = 1f,
)
@Preview(
    name = "Light - Large font",
    group = "Light",
    showBackground = true,
    backgroundColor = 0xffEEEEEE,
    uiMode = UI_MODE_NIGHT_NO,
    fontScale = 1.5f,
)
@Preview(
    name = "Light - Landscape",
    group = "Light - Landscape",
    showBackground = true,
    backgroundColor = 0xffEEEEEE,
    uiMode = UI_MODE_NIGHT_NO,
    fontScale = 1f,
    device = LANDSCAPE_DEVICE,
)
@Preview(
    name = "Light - Landscape - Large font",
    group = "Light - Landscape",
    showBackground = true,
    backgroundColor = 0xffEEEEEE,
    uiMode = UI_MODE_NIGHT_NO,
    fontScale = 1.5f,
    device = LANDSCAPE_DEVICE,
)
@Preview(
    name = "Dark - Landscape - Large font",
    group = "Dark - Landscape",
    showBackground = true,
    backgroundColor = 0xff444444,
    uiMode = UI_MODE_NIGHT_YES,
    fontScale = 1.5f,
    device = LANDSCAPE_DEVICE,
)
@Preview(
    name = "Dark",
    group = "Dark",
    showBackground = true,
    backgroundColor = 0xFF0A0A0C,
    uiMode = UI_MODE_NIGHT_YES,
    fontScale = 1f,
)
@Preview(
    name = "Dark - Large font",
    group = "Dark",
    showBackground = true,
    backgroundColor = 0xFF0A0A0C,
    uiMode = UI_MODE_NIGHT_YES,
    fontScale = 1.35f,
)
@Preview(
    name = "Contrast check",
    group = "Contrast",
    showBackground = true,
    backgroundColor = 0xFFEEEEEE,
    fontScale = 1f,
)
annotation class SentryPreview

/**
 * Full-screen Pixel 8 dark preview — used for entire screens when they need
 * realistic dimensions and the dark theme.
 */
@Preview(
    name = "Pixel 8 — Dark",
    showBackground = true,
    backgroundColor = 0xFF0A0A0C,
    uiMode = UI_MODE_NIGHT_YES,
    device = "spec:width=412dp,height=915dp,dpi=420",
)
annotation class SentryScreenPreview

private const val LANDSCAPE_DEVICE = "spec:width=915dp,height=412dp,orientation=landscape"