<div align="center">

# Sentry Smart Home

**The dark-glass smart-home dashboard, rebuilt in Jetpack Compose.**

A showcase of what modern Compose can deliver when the whole toolchain is on the
bleeding edge — Kotlin 2.3 K2, Gradle 9, AGP 9, Koin's new compile-time DI —
wrapped around the stunning *Sentry* concept by
[**Phenomenon Studio**](https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry).

[Features](#features) · [Screens](#screens) · [For humans](#for-humans) · [For agents](#for-agents) · [Architecture](docs/Architecture.md) · [Roadmap](docs/Roadmap.md)

![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-2026.03-4285F4?logo=jetpackcompose&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-9.3-02303A?logo=gradle&logoColor=white)
![AGP](https://img.shields.io/badge/AGP-9.1-3DDC84?logo=android&logoColor=white)
![Koin](https://img.shields.io/badge/Koin-4.2-F05123)
![JDK](https://img.shields.io/badge/JDK-21-ED8B00?logo=openjdk&logoColor=white)
![minSdk](https://img.shields.io/badge/minSdk-30-0A0A0C)
![targetSdk](https://img.shields.io/badge/targetSdk-36-0A0A0C)

</div>

---

## Why this repo exists

Sentry Smart Home is a practice project with a clear thesis: **a polished
glassmorphic dashboard is a lot easier to ship when the architecture, DI and
build system are already pulling their weight.** Every screen is recreated from
scratch in Compose, with no WebViews, no bitmap compositing — just Canvas and
layered gradients — so the whole UI scales from 360dp phones to foldables and
tablets without a single pixel-hardcoded asset.

No backend, no real IoT. Every device is fake, every stream is a gradient.
What *is* real is the **architecture you'd reach for on a production app**:
multi-module, type-safe navigation, reactive `StateFlow` repositories,
compile-time validated dependency injection, full screenshot-ready previews.

## Features

- 🏠 **Six fully laid-out screens** — Home dashboard, Activities feed, Device
  Hub, and a Room detail with inner Light / Thermostat / Curtains panes.
- 🎛 **Custom Canvas controls** — circular dimmer with drag gestures, vertical
  temperature slider, curtain illustration that slides on the rod, waveform
  visualisation, animated emergency-call pill.
- 🧩 **Full multi-module architecture** — `:app` + `:core:{designsystem, ui,
  data, navigation}` + `:feature:{home, room, activities, devicehub}`. Clear
  dependency direction, zero circular deps, convention plugins in a composite
  build.
- 🪝 **Koin Compiler Plugin (1.0.0-RC1)** — every `@Singleton` /
  `@KoinViewModel` / `@Module` is validated at compile time; unresolved
  injections fail the build.
- 🧭 **Type-safe Navigation Compose** — `@Serializable` sealed routes, each
  feature exposes its own `NavGraphBuilder` extension.
- 🌓 **Dark-glass design system** — shared tokens, `GlassSurface`,
  `SegmentedTabs`, `SentrySwitch`, `CircularDimmer`, `HangingLamp`,
  `VerticalTempSlider`, `WaveformBar`, `ActionChip`, `ColorSwatchRow`,
  `EmergencyCallButton` — every component has a multi-preview (`@SentryPreview`).
- 🌅 **Splashscreen + adaptive launcher icon** — the AndroidX Core Splashscreen
  API hands off to `Theme.Sentry` after boot.
- 🧪 **Edge-to-edge aware** — every screen is wrapped in `safeDrawingPadding`;
  the status bar and nav bar are transparent end-to-end.

## Screens

> Design © [Phenomenon Studio](https://phenomenon.studio/). These are
> Compose recreations, not the original Figma.

<table>
  <tr>
    <td align="center" width="33%"><strong>Home — Sia's home</strong></td>
    <td align="center" width="33%"><strong>Activities feed</strong></td>
    <td align="center" width="33%"><strong>Device Hub</strong></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/531eca00-6e00-4f8f-9cf9-fcf5521b237e" alt="Home" width="100%" /></td>
    <td><img src="https://github.com/user-attachments/assets/7d0cd1a2-a5d8-4101-911a-02b9096bb3fc" alt="Activities" width="100%" /></td>
    <td><img src="https://github.com/user-attachments/assets/e8197f79-9c10-4560-a990-8d937c55cd6f" alt="Device Hub" width="100%" /></td>
  </tr>
  <tr>
    <td align="center"><strong>Room › Main light</strong></td>
    <td align="center"><strong>Room › Thermostat</strong></td>
    <td align="center"><strong>Room › Curtains</strong></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6bece482-672e-415c-a11b-1fa399ca726f" alt="Main light" width="100%" /></td>
    <td><img src="https://github.com/user-attachments/assets/f35d4580-5285-4814-b3f3-9a38bc8aef18" alt="Thermostat" width="100%" /></td>
    <td><img src="https://github.com/user-attachments/assets/e9723456-a8e2-4e44-a955-3a8b23394f7f" alt="Curtains" width="100%" /></td>
  </tr>
</table>

User flow diagram → [`docs/User-Flow.md`](docs/User-Flow.md) (Pencil source at
`design/sentry.pen`).

---

## For humans

```bash
# 1. Clone
git clone https://github.com/jacksonmafra-umain/SentrySmartHome.git
cd SentrySmartHome

# 2. Build — JDK 21 required
./gradlew :app:assembleDebug

# 3. Install on a connected device or an API 30+ emulator
./gradlew :app:installDebug
```

Android Studio Ladybug+ will pick up the project on first sync. All previews
live in `:core:designsystem` and each `feature/*/pane` — open a Compose file
and hit the Preview split to iterate on components in isolation.

**Recommended editor setup**

- JDK 21 (the build configures a 21 toolchain)
- Android Studio *Ladybug Feature Drop* or newer (for Kotlin 2.3 + AGP 9)
- Enable *"Gradle JDK" → JDK 21* in Settings → Build Tools → Gradle

## For agents

This repo is intentionally **agent-friendly**. If you're a coding assistant
(Claude Code, Cursor, Copilot Workspace, Aider, Zed AI…), start here:

1. Read **[`CLAUDE.md`](CLAUDE.md)** at the repo root — module graph, patterns,
   common gotchas, and the commands you're allowed to run without asking.
2. Skim **[`docs/Architecture.md`](docs/Architecture.md)** for the dependency
   direction between modules and the DI contract.
3. Skim **[`docs/Design-System.md`](docs/Design-System.md)** for the reusable
   components — avoid reinventing a chip or a slider.
4. Check **[`docs/Roadmap.md`](docs/Roadmap.md)** + open GitHub issues for
   unfinished work you can pick up.

Every file has been written with the expectation that an LLM will read it in
isolation: docstrings are generous, naming is explicit, and module boundaries
are crisp.

---

## Tech stack

| Layer | Choice |
|---|---|
| Language | Kotlin 2.3.20 (K2) |
| Build | Gradle 9.3.1 · AGP 9.1.x · JDK 21 toolchain |
| UI | Jetpack Compose BOM 2026.03 · Material 3 |
| DI | Koin 4.2.1 + Koin Compiler Plugin 1.0.0-RC1 |
| Nav | Navigation Compose (type-safe) + kotlinx-serialization |
| Async | kotlinx-coroutines 1.10 · StateFlow |
| Splash | androidx.core:core-splashscreen |
| Image loading | Coil 3 (for when real assets land) |

## Documentation

- 📐 [**Architecture**](docs/Architecture.md) — module graph, layering rules,
  DI contract, convention plugins.
- 🎨 [**Design System**](docs/Design-System.md) — every reusable component with
  anchor links, preview pointers and intended use.
- 🧭 [**User Flow**](docs/User-Flow.md) — six-screen flow, Pencil `.pen` source,
  regeneration script.
- 🗺 [**Roadmap**](docs/Roadmap.md) — what's next, mirrored as GitHub issues.
- 🤖 [**`CLAUDE.md`**](CLAUDE.md) — agent onboarding (at repo root).

The long-form docs also mirror to the GitHub Wiki.

## License & attribution

Code is released under the **MIT License**.

The visual design — palette, typography, illustrations, interactions — is
**© [Phenomenon Studio](https://phenomenon.studio/)**. Please credit the
original authors when referencing screens from this demo. See the
[Dribbble shot](https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry)
for the canonical source.
