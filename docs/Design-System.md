# Design System

The design system lives in `:core:designsystem`. It owns the theme, the
tokens and every reusable component.

All visual design is **© [Phenomenon Studio](https://phenomenon.studio/)** —
see the [Dribbble source](https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry).

## Theme

`SentryTheme` wraps Material 3 with a single dark `ColorScheme` and a shared
background `Brush`. There is no light theme.

| Token | File | Role |
| --- | --- | --- |
| `SentryColors` | `theme/Color.kt` | Background, glass tints, text, accents, lamp swatches |
| `SentryTypography` | `theme/Type.kt` | Display / headline / title / body / label scales |
| `SentryShapes` | `theme/Shape.kt` | Corner radius scale (10 / 14 / 20 / 28 / 36 dp) |

### Palette

- **Background** — `#0A0A0C`, raised `#121214`
- **Glass** — white at α 0.08 / 0.12 / 0.20 (low / mid / high)
- **Text** — primary `#F5F5F7`, secondary `#B5B5BC`, muted `#86868E`
- **Accents** — orange `#FF6B4A`, green `#6BD16B`, blue `#5EC6FF`,
  warm `#FFB86B`, red `#FF5A5F`
- **Lamp swatches** — green, blue, warm, pink, purple (see `Color.kt`)

## Components

Every component lives in `component/*.kt` alongside its `@SentryPreview`.
Launch any of these in Android Studio's preview pane to iterate without
running the full app.

| Component | File | Summary |
| --- | --- | --- |
| `GlassSurface` | `component/GlassSurface.kt` | Frosted glass container — translucent fill + hairline border + soft highlight. Used instead of `Card`. |
| `SegmentedTabs<T>` | `component/SegmentedTabs.kt` | Pill-style segmented control with optional numeric badge. Horizontally scrollable so long labels don't wrap. |
| `SentrySwitch` | `component/SentrySwitch.kt` | Compact 38×22dp glass/green switch used on device cards. |
| `CircularDimmer` | `component/CircularDimmer.kt` | Drag-to-adjust arc with notch ticks + glowing knob. Used on Main-light and Device-hub screens. |
| `HangingLampIllustration` | `component/HangingLamp.kt` | Canvas-drawn pendant lamp with configurable `glow` (0..1) and warm colour. |
| `VerticalTempSlider` | `component/VerticalTempSlider.kt` | Cold→warm vertical temperature track with a bubble handle. |
| `WaveformBar` | `component/Waveform.kt` | Decorative audio waveform visualisation for the Activities header. |
| `ActionChip` | `component/ActionChip.kt` | Icon+text chip, horizontal or stacked, with a selected state. Used by Timer / Power / Heating / Cooling / Open / Half / Closed / Auto. |
| `ColorSwatchRow` | `component/ColorSwatchRow.kt` | Row of glossy colour dots; selected gets a white ring. |
| `EmergencyCallButton` | `component/EmergencyCallButton.kt` | Red gradient pill with three animated chevrons. |

## Preview tooling

```kotlin
import com.umain.sentry.designsystem.tooling.SentryPreview
import com.umain.sentry.designsystem.tooling.PreviewBox

@SentryPreview
@Composable
private fun MyComponentPreview() {
    PreviewBox { MyComponent() }
}
```

- `@SentryPreview` renders each composable in Dark default font, Dark large
  font, Light (+ large), and dark landscape variants — one shot covers the
  common visual regressions.
- `@SentryScreenPreview` renders on a Pixel 8 spec, dark.
- `PreviewBox` wraps content in `SentryTheme` + the Sentry background +
  default padding.
- `PreviewColumn` and `PreviewRow` are the same but arrange children
  vertically or horizontally, with scroll guards so long content never
  clips the preview pane.

Every public composable in `:core:designsystem` has a preview. Screens and
panes have previews under their feature module that use fake `Device`
instances constructed inline.

## Adding a new component

1. Create `component/Xyz.kt` in `:core:designsystem`.
2. Write the composable.
3. Write `@SentryPreview` + `PreviewBox { Xyz(...) }` in the same file.
4. Add an entry to the table above.
5. Commit as a micro commit:
   `core:designsystem: add Xyz component`.

## Using a design token, not a hex

Pull from `SentryColors` / `MaterialTheme.typography` / `MaterialTheme.shapes`
instead of writing literal hex or sp values. If you're reaching for a value
that isn't in the tokens, add it to the token file first — a two-commit PR
("extend tokens" + "use tokens in Xyz") beats inlining a one-off.
