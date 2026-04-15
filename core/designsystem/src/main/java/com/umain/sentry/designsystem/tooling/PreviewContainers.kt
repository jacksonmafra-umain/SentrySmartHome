package com.umain.sentry.designsystem.tooling

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.umain.sentry.designsystem.theme.SentryColors
import com.umain.sentry.designsystem.theme.SentryTheme

/**
 * Lightweight preview scaffolding. Every `@SentryPreview` composable should
 * wrap its content in one of [PreviewBox], [PreviewColumn] or [PreviewRow]
 * so the component renders under `SentryTheme`, on a real Sentry background,
 * with sensible padding.
 */

@Composable
private fun PreviewContainer(
    modifier: Modifier = Modifier,
    background: Color = SentryColors.Background,
    padding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit,
) {
    SentryTheme {
        Box(
            modifier = modifier
                .background(background)
                .padding(padding)
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            content()
        }
    }
}

/** Single-component preview. Use when showing one widget at rest. */
@Composable
fun PreviewBox(
    modifier: Modifier = Modifier,
    background: Color = SentryColors.Background,
    padding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit,
) {
    PreviewContainer(modifier, background, padding) { content() }
}

/**
 * Vertically-stacked preview for showing multiple states of the same
 * component (e.g. default, hover, selected). Scrolls so long lists never
 * overflow the preview viewport.
 */
@Composable
fun PreviewColumn(
    modifier: Modifier = Modifier,
    background: Color = SentryColors.Background,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    padding: PaddingValues = PaddingValues(16.dp),
    verticalSpacing: Dp = 16.dp,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        PreviewContainer(modifier, background, padding) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(verticalSpacing),
                    horizontalAlignment = horizontalAlignment,
                ) {
                    content()
                }
            }
        }
    }
}

/**
 * Horizontally-stacked preview for showing compact variants side by side
 * (e.g. colour swatches, chip sizes). Scrolls horizontally so wide rows
 * never clip in the IDE preview pane.
 */
@Composable
fun PreviewRow(
    modifier: Modifier = Modifier,
    background: Color = SentryColors.Background,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    padding: PaddingValues = PaddingValues(16.dp),
    horizontalSpacing: Dp = 16.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        PreviewContainer(modifier, background, padding) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
                verticalAlignment = verticalAlignment,
                modifier = Modifier.horizontalScroll(rememberScrollState()),
            ) {
                content()
            }
        }
    }
}
