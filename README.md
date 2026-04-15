# Sentry Smart Home

A **Jetpack Compose** demo app that reproduces the *Sentry Smart Home* UI.
Strictly a practice / showcase project — no real IoT, no backend.

## Design credit

The entire visual design (dark glass aesthetic, screens, interactions and
illustrations) is the work of **[Phenomenon Studio](https://phenomenon.studio/)**,
published on Dribbble:

> [Smart Home Mobile App Design — Sentry](https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry)

This repo is an unaffiliated Compose reimplementation used solely for
educational purposes.

## Stack

- **Kotlin** `2.3.20` (K2, built-in Compose compiler plugin)
- **Gradle** `9.0.0`, **AGP** `8.14.x`, JDK **21**
- **Jetpack Compose** BOM + Material 3
- **Koin 4** + **Koin Compiler Plugin `1.0.0-RC1`** via KSP2 — wiring is generated at compile time, `KOIN_CONFIG_CHECK` enabled so unresolved injections fail the build ([announcement](https://blog.insert-koin.io/unlocking-koin-compile-safety-6278840ab171) · [docs](https://insert-koin.io/docs/intro/koin-compiler-plugin/))
- **Navigation Compose** with type-safe routes (`kotlinx-serialization`)
- **Core Splashscreen** for the app launch splash + adaptive launcher icon
- `minSdk = 30`, `targetSdk = 36`, `compileSdk = 36`

## Module graph

```
:app ─▶ everything
:core:designsystem ─▶ :core:ui
:core:data
:core:navigation
:feature:home ─▶ :core:*
:feature:room ─▶ :core:*
:feature:activities ─▶ :core:*
:feature:devicehub ─▶ :core:*
```

Each feature module contributes its own `@Module @ComponentScan` Koin module
(e.g. `HomeModule`, `RoomModule`), and the app assembles them at `startKoin`
using the KSP-generated `.module` extensions.

Convention plugins live in the `build-logic/` composite build so per-module
`build.gradle.kts` files stay tiny.

## Screens

1. **Home** — "Sia's home" dashboard, room tabs, doorbell feed, emergency call pill, device card grid, bottom dock
2. **Activities** — waveform + Live video / Activities / Devices tabs + activity feed
3. **Device Hub** — all-devices hub view with the hero hanging-lamp dimmer
4. **Bedroom / Main light** — circular dimmer, color swatches, Timer/Power chips
5. **Bedroom / Thermostat** — temperature + humidity cards, vertical slider, Heating/Cooling
6. **Bedroom / Curtains** — curtain illustration, brightness slider, position chips

User flow diagram → [`design/flow.md`](design/flow.md). A ready-to-run script
for regenerating the flow in Pencil (`design/flow.pen`) lives at the bottom of
the same file.

## Building

```bash
# macOS / Linux
./gradlew :app:assembleDebug

# then install on a connected device/emulator (API 30+)
./gradlew :app:installDebug
```

> The Gradle wrapper `.jar` is not committed (binary file). If `./gradlew`
> fails with *"Could not find or load main class org.gradle.wrapper.GradleWrapperMain"*,
> run `gradle wrapper --gradle-version 9.0.0` once to generate it, or open the
> project in Android Studio which will do it automatically on first sync.

## Known caveats

- **Preview versions**: Kotlin `2.3.20`, AGP `8.14`, Compose BOM `2025.10` and
  KSP `2.3.20-2.0.x` are at the bleeding edge — bump to the latest stable that
  pairs with Gradle 9 if you hit resolution failures.
- Fonts, lamp textures and the doorbell video are intentionally stubs —
  real assets live with Phenomenon Studio, not in this repo.
- The live "doorbell feed" is a static gradient; swap with a `VideoPlayer`
  composable if you want motion.

## License / attribution

Code is released under the MIT license. The visual design is **© Phenomenon
Studio** — please credit the original authors when referencing screens,
illustrations or interactions from this demo.
