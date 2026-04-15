# Architecture

Sentry Smart Home is a Jetpack Compose Android app split into **four `:core:*`
modules** and **four `:feature:*` modules** orchestrated from `:app`. The
split keeps the design system, shared data layer, navigation contract and
base utilities out of individual features, so each feature module is small,
isolated and swappable.

## Module graph

```
┌──────────┐
│   :app   │  Application class, MainActivity, SentryNavHost, splash theme
└────┬─────┘
     │
     ├──▶ :feature:home
     ├──▶ :feature:room         (Light / Thermostat / Curtains panes)
     ├──▶ :feature:activities
     ├──▶ :feature:devicehub
     │        │
     │        ▼
     │   ┌──────────────────────┐
     │   │ :core:designsystem   │─▶ :core:ui
     │   │ :core:data           │
     │   │ :core:navigation     │
     │   │ :core:ui             │
     │   └──────────────────────┘
     ▼
```

### Rules

- `:app` may depend on **any** module.
- `:feature:*` may depend on `:core:*` but **not** on another feature. Two
  features that need the same logic push that logic down into `:core:*`.
- `:core:designsystem` owns the theme and every reusable component. The
  design system depends on `:core:ui` for low-level utilities; `:core:ui`
  has no downstream deps.
- `:core:data` exposes the in-memory `SmartHomeRepository`. Every other
  module treats it as read-only data.
- `:core:navigation` contains only the `SentryRoute` sealed interface + the
  `RoomPane` enum. The actual `NavHost` is assembled in `:app`.

### Why this split

- **Fast incremental builds.** Edits inside a feature only recompile that
  feature + downstream `:app`. Edits in the design system rebuild a lot more,
  which is appropriate — changing a shared token is a real change.
- **Feature teams are independent.** The Activities team can't accidentally
  import `HomeViewModel`.
- **Swappable data layer.** Today `:core:data` is an in-memory fake;
  swapping it for a real network+DB implementation would touch no feature
  module.

## Build system

Convention plugins live under `build-logic/` as a [Gradle composite build](https://docs.gradle.org/current/userguide/composite_builds.html).
Every per-module `build.gradle.kts` is ~10 lines.

| Plugin ID | Applied to | What it does |
| --- | --- | --- |
| `sentry.android.application` | `:app` | AGP 9 `application` plugin, `compileSdk`/`targetSdk` from catalog, Java 21 compile options |
| `sentry.android.library` | `:core:*` | AGP 9 `library` plugin, same Java 21 setup |
| `sentry.android.compose` | `:app`, `:core:designsystem`, `:feature:*` | `kotlin.plugin.compose`, Compose BOM + Material 3 deps |
| `sentry.android.feature` | `:feature:*` | Pulls in `android.library` + `android.compose` + `koin` + the four `:core:*` deps |
| `sentry.koin` | `:core:data`, `:feature:*` | Applies `io.insert-koin.compiler.plugin`, adds Koin runtime deps |

AGP 9 bundles Kotlin Android support, so the standalone `kotlin-android`
plugin is **not** applied anywhere.

## Dependency injection

Koin 4.2.1 + **Koin Compiler Plugin 1.0.0-RC1** (a Kotlin compiler plugin,
not KSP).

Every module that contributes DI follows a dual-sided shape:

1. **Annotation side** (for compile-time validation)
   ```kotlin
   @Singleton(binds = [SmartHomeRepository::class])
   class SmartHomeRepositoryImpl(...)

   @KoinViewModel
   class HomeViewModel(private val repo: SmartHomeRepository)

   @Module(includes = [DataModule::class])
   @ComponentScan("com.umain.sentry.feature.home")
   class HomeModule
   ```

2. **Runtime side** (what `SentryApp.onCreate` actually loads)
   ```kotlin
   val homeModule: KoinModule = module {
       viewModelOf(::HomeViewModel)
   }
   ```

`SentryApp` imports the `<feature>Module` values and calls
`modules(dataModule, homeModule, ...)` once. Adding a new injectable means
updating **both** sides in the same commit.

The compiler plugin enforces cross-module graph validity. If a feature VM
needs a binding from `:core:data`, the feature's `@Module` must
`includes = [DataModule::class]`, otherwise `:app:compileDebugKotlin` fails
with `Missing dependency: com.umain.sentry.data.repository.SmartHomeRepository`.

## Navigation

`:core:navigation` declares a sealed interface:

```kotlin
sealed interface SentryRoute {
    @Serializable data object Home : SentryRoute
    @Serializable data object Activities : SentryRoute
    @Serializable data object DeviceHub : SentryRoute
    @Serializable data class Room(val roomId: String, val initialPane: RoomPane = Light) : SentryRoute
}
```

Each feature exposes an extension:

```kotlin
fun NavGraphBuilder.homeScreen(onNavigate: (SentryRoute) -> Unit) {
    composable<SentryRoute.Home> { HomeRoute(onNavigate) }
}
```

`SentryNavHost` in `:app` is the only place that knows how the graph is wired
together. Features can't navigate to each other directly — they raise a
`(SentryRoute) -> Unit` callback and `:app` decides.

## Data layer

`:core:data` is an intentionally minimal fake.

- `Device` + sealed `DeviceState` hierarchy (Light, Thermostat, Curtains,
  Lock, Vacuum, LeakDetector, SmokeDetector, Doorbell).
- `Room` + `ActivityEvent`.
- `SmartHomeRepository` exposes `StateFlow` lists seeded in `seedDevices()` /
  `seedRooms()` / `seedActivities()`, mutated through `updateDevice(id,
  transform)`.
- `SmartHomeRepositoryImpl` is a Koin `@Singleton` bound to the interface.

Swap for a real implementation by replacing the `@Singleton` binding in
`DataModule` + `dataModule`. No feature module imports the impl.

## UI patterns

- Every screen is a `@Composable` route + a `@KoinViewModel`.
- State flows through a single `state: StateFlow<UiState>` exposed by the VM.
- Mutations go through VM methods that call repo writers.
- Every screen's root `Column` uses `.safeDrawingPadding()` because
  `MainActivity` enables edge-to-edge.
- Every reusable composable in `:core:designsystem` has a multi-preview
  (`@SentryPreview`) with a helper container (`PreviewBox` / `PreviewColumn` /
  `PreviewRow`).

See [`Design-System.md`](Design-System.md) for the component catalogue.
