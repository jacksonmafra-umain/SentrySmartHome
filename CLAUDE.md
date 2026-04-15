# CLAUDE.md — Agent Onboarding

This file is a compact briefing for any coding assistant landing in the repo.
Read it top-to-bottom before touching code. If you only read one file, read
this one.

## What this project is

A **Jetpack Compose demo** that recreates Phenomenon Studio's *Sentry Smart
Home* concept. No real backend, no real IoT — just a realistic multi-module
Android app with modern DI and build tooling. Showcase / practice scope; it
isn't a product.

## Tech coordinates (current)

- **Kotlin** 2.3.20 (K2)
- **Gradle** 9.3.1, **AGP** 9.1.x (alpha), **JDK** 21
- **Compose BOM** 2026.03, **Material 3** 1.4
- **Koin** 4.2.1 + **Koin Compiler Plugin** 1.0.0-RC1 (*not* KSP)
- **Navigation Compose** with type-safe `@Serializable` routes
- `minSdk = 30`, `targetSdk = 36`, `compileSdk = 36`
- Root package: `com.umain.sentry`

## Module graph

```
:app
  ├─ :feature:home
  ├─ :feature:room           (contains Light/Thermostat/Curtains panes)
  ├─ :feature:activities
  ├─ :feature:devicehub
  ├─ :core:designsystem     ─▶ :core:ui
  ├─ :core:ui
  ├─ :core:data
  └─ :core:navigation
```

**Dependency direction**
- `:app` → everything.
- `:feature:*` → `:core:*`. Features **do not** depend on each other.
- `:core:designsystem` → `:core:ui`. The design system owns the theme +
  reusable components.
- `:core:data` is the fake, in-memory state of the world (`StateFlow`
  repositories).
- `:core:navigation` is only the `SentryRoute` sealed interface + `RoomPane`.

If a change would force a `feature → feature` dependency, push the shared
thing down into `:core:*` instead.

## DI contract (Koin Compiler Plugin)

The Koin Compiler Plugin (1.0.0-RC1) is a **Kotlin compiler plugin**, not a
KSP processor. It only picks up:

- `@Singleton` / `@Factory` / `@KoinViewModel` on classes
- `@Module` + `@ComponentScan(...)` on an aggregator class per module
- `@Module(includes = [OtherModule::class])` to expose bindings to other
  modules during isolated compilation

The plugin does **not** emit a runtime `.module` extension at this release, so
every module also exposes a hand-written `val <feature>Module = module { ... }`
that `SentryApp.onCreate()` loads. The two sides must stay in sync by
convention — if you add a `@KoinViewModel`, also add it to the DSL module.

See `core/data/.../DataModule.kt` and `feature/home/.../HomeModule.kt` for the
canonical shape.

## Build system

Convention plugins live under `build-logic/` as a composite build. Per-module
`build.gradle.kts` files stay tiny:

- `sentry.android.application` — for `:app`
- `sentry.android.library` — for every `:core:*`
- `sentry.android.feature` — wraps `sentry.android.library` + `compose` + `koin`
- `sentry.android.compose` — enables Compose, pulls BOM + Material 3
- `sentry.koin` — applies `io.insert-koin.compiler.plugin` + Koin runtime deps

AGP 9 removed the `Action<T>` block-DSL forms and `kotlin-android` plugin
(bundled now). Don't reintroduce either.

## Patterns to follow

- **ViewModels** are `@KoinViewModel`, hold a single `state: StateFlow<UiState>`,
  never expose `MutableStateFlow`.
- **UI state** is an immutable `data class` with sensible defaults so the
  initial emission from `stateIn(...)` is usable.
- **Navigation** is wired in `:app/SentryNavHost.kt` — each feature exposes
  `fun NavGraphBuilder.<feature>Screen(onNavigate, onBack)`.
- **Previews**: every public composable has a `@SentryPreview` function using
  `PreviewBox` / `PreviewColumn` / `PreviewRow` from
  `com.umain.sentry.designsystem.tooling`.
- **Insets**: every screen's root `Column` has `.safeDrawingPadding()` because
  `MainActivity` uses edge-to-edge.
- **Dark only**: there is no light theme. `SentryTheme` applies a single dark
  `ColorScheme`.

## Gotchas

- **AGP 9 + Kotlin 2.3.20** are on the leading edge. Expect the occasional
  missing library version. Bump catalogue entries to the nearest stable if
  resolution fails.
- **`@KoinViewModel` on classes taking `SavedStateHandle`** works: Koin's
  ViewModel scope auto-registers it, so `get()` resolves the handle inside
  `viewModel<T> { T(get(), get()) }`.
- **Koin Compiler Plugin validates cross-module graphs in isolation.** If a
  feature ViewModel takes a `SmartHomeRepository`, the feature's aggregator
  must `@Module(includes = [DataModule::class])` — otherwise the plugin
  reports `Missing dependency`.
- **No screen previews use Koin.** ViewModel-backed screens currently don't
  have `@SentryScreenPreview` functions because a real Koin graph isn't
  available at preview time. If you add one, wire it against fake repository
  instances constructed inline.

## Commands you can run without asking

- `./gradlew :app:assembleDebug`
- `./gradlew :app:installDebug` (installs on connected device/emulator)
- `./gradlew lint`
- `./gradlew test`
- `git status`, `git diff`, `git log`, `git add`, `git commit`
- `~/Library/Android/sdk/platform-tools/adb devices`
- `~/Library/Android/sdk/emulator/emulator -list-avds`

## Commands that need explicit confirmation

- `git push` (anything that goes to the remote)
- `git reset --hard`, `git clean -fd`, any rebase that rewrites published history
- `gh issue create`, `gh pr create`, `gh release`
- Starting an emulator, installing APKs on unfamiliar devices
- Anything touching the wiki or GitHub settings

## Where things live

```
app/src/main/java/com/umain/sentry/
  ├─ SentryApp.kt          — Application: startKoin wiring
  ├─ MainActivity.kt       — installSplashScreen + enableEdgeToEdge
  └─ SentryNavHost.kt      — wires feature NavGraphBuilder extensions

core/designsystem/src/main/java/com/umain/sentry/designsystem/
  ├─ theme/                — Color, Type, Shape, Theme
  ├─ component/            — GlassSurface, SegmentedTabs, ...
  └─ tooling/              — SentryPreview annotations + PreviewBox helpers

core/data/src/main/java/com/umain/sentry/data/
  ├─ model/                — Device, DeviceState sealed hierarchy, Room, ActivityEvent
  ├─ repository/           — SmartHomeRepository + Impl (@Singleton)
  └─ di/                   — DataModule (annotations) + dataModule (DSL)

core/navigation/           — SentryRoute sealed interface + RoomPane

feature/<name>/src/main/java/com/umain/sentry/feature/<name>/
  ├─ <Feature>Screen.kt    — NavGraphBuilder extension + @Composable route
  ├─ <Feature>ViewModel.kt — @KoinViewModel
  ├─ pane/                 — (Room only) LightPane, ThermostatPane, CurtainsPane
  └─ di/                   — <Feature>Module + <feature>Module
```

## If you're about to

- **Add a new device type** → extend `DeviceState` sealed hierarchy in
  `:core:data/model/Device.kt`, seed an instance in `SmartHomeRepositoryImpl`,
  render it in `HomeScreen`'s `Device.summary()` when statically typed.
- **Add a new screen** → add a variant to `SentryRoute`, create a feature
  module under `feature/`, expose a `NavGraphBuilder.<name>Screen()` extension,
  wire it in `SentryNavHost`.
- **Add a new reusable widget** → put it under
  `core/designsystem/component/`, write a `@SentryPreview` next to it, add a
  short entry in [`docs/Design-System.md`](docs/Design-System.md).
- **Touch DI** → update both the annotation-side (`@Singleton`,
  `@KoinViewModel`, `@Module @ComponentScan`) and the DSL-side
  (`val <feature>Module = module { ... }`) in the same commit. They must stay
  in sync.

## Out of scope (on purpose)

- Real backend / IoT integrations
- Light theme
- Play Store release config, signing, ProGuard tuning
- Deep accessibility audit beyond the large-font preview variant
- iOS / KMP support

If the user asks for one of these, ask before doing it — scope creep breaks
the pedagogical clarity of the repo.
