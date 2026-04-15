# Roadmap

A living list of known follow-ups. **Each bullet maps to a GitHub issue** —
see the [issues page](https://github.com/jacksonmafra-umain/SentrySmartHome/issues)
for the authoritative version. This file is the human-friendly mirror.

## Near term

### Previews & visual regression
- [ ] **Screenshot tests for every `@SentryPreview`** — Paparazzi or Roborazzi
  pipeline that renders each preview on CI and fails on pixel diffs.
- [ ] **Screen-level previews with fake Koin graph** — use `KoinApplication { ... }`
  preview containers so `@SentryScreenPreview` on full screens works.

### Animations & polish
- [ ] **Animated room switch** — cross-fade or shared-element transition when
  moving Home → Room rather than a hard swap.
- [ ] **CircularDimmer haptics** — subtle tick feedback every 10° of rotation.
- [ ] **Activities waveform drives off fake audio** — animate bars via
  `rememberInfiniteTransition` so the header looks alive.
- [ ] **Doorbell feed gradient pans** — subtle parallax on the doorbell card
  background to suggest a live stream.

### Real-ish data
- [ ] **Pluggable data source** — split `:core:data` into `:core:data:fake`
  (current) and `:core:data:real` behind a shared `SmartHomeRepository`
  interface.
- [ ] **Persistence** — DataStore for the last selected room + lamp state so
  the app remembers across process death.
- [ ] **Coroutine-driven vacuum progress** — decrement `minutesLeft` while the
  vacuum is running instead of a static number.

### CI & tooling
- [ ] **GitHub Actions** — `./gradlew lint test :app:assembleDebug` on every PR.
- [ ] **Detekt + Ktlint** via convention plugin, warnings-as-errors on CI.
- [ ] **Dependency updates** — Renovate or Dependabot with a grouping rule for
  Compose BOM / AndroidX / Kotlin.

## Medium term

- [ ] **Real video feed** — swap the doorbell gradient for an ExoPlayer-backed
  `VideoPlayer` composable with a looped sample stream.
- [ ] **Tablet / foldable layout** — side-by-side room + device detail on wide
  screens.
- [ ] **Live activities stream** — simulate new `ActivityEvent`s arriving
  every ~30s to exercise the reactive `StateFlow` pipeline.

## Long term / nice-to-have

- [ ] **KMP** — lift `:core:data` + `:core:navigation` into a Kotlin Multiplatform
  module for an iOS target.
- [ ] **Accessibility audit** — content descriptions, Talkback labels, contrast
  checks beyond the large-font preview.
- [ ] **Play Store release config** — signing, R8 rules, Play Feature Delivery
  for individual features.

## Intentionally out of scope

- Real IoT integration (Matter, Thread, Zigbee).
- Real authentication / account system.
- Voice control.
- Push notifications.

If you want any of these, open an issue first so we can talk scope before
code lands.
