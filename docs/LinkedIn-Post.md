# LinkedIn post — draft

> Keep this under ~1,300 chars for the unexpanded view on the LinkedIn
> feed. Emojis are fine but don't lean on them. The Dribbble + repo links
> auto-unfurl — keep them at the end.

---

## Short version (for the feed)

🏠 Just shipped **Sentry Smart Home** — a Jetpack Compose recreation of
the gorgeous *Sentry* concept designed by **Phenomenon Studio**.

Built to stress-test the bleeding edge of the Android toolchain:

🧪 Kotlin **2.3.20** (K2) · Gradle **9.3** · AGP **9.1** · JDK 21
🪝 Koin **4.2** + the brand-new **Koin Compiler Plugin 1.0.0-RC1** — compile-time DI validation, no KSP
🎨 Jetpack Compose BOM 2026.03, Material 3, multi-module (`:app` + 4 `:core:*` + 4 `:feature:*`)
🎛 Custom Canvas controls — circular dimmer, vertical temp slider, sliding curtains, animated emergency pill
🌅 Edge-to-edge, splash screen, adaptive icon, full `@SentryPreview` coverage

No backend, no real IoT — just architecture and design done right.

Design © **[Phenomenon Studio](https://phenomenon.studio/)** ·
[Dribbble shot](https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry)

Code 👉 https://github.com/jacksonmafra-umain/SentrySmartHome

\#Android #JetpackCompose #Kotlin #Koin #Gradle #DesignSystem

---

## Long version (if you want a "see more" expanded post)

🏠 **Sentry Smart Home** is live on GitHub.

Phenomenon Studio's concept was too pretty not to build. So I did — in
Jetpack Compose, with every toolchain dial cranked to maximum.

**What's inside**

🧪 **Bleeding-edge stack.** Kotlin 2.3.20 K2, Gradle 9.3.1, AGP 9.1
alpha, JDK 21, Compose BOM 2026.03, Material 3.

🪝 **Koin Compiler Plugin 1.0.0-RC1.** No KSP — the Kotlin compiler
validates every `@Singleton` / `@KoinViewModel` at build time. If a
binding is missing, the build fails. Compile-time safety for DI, finally.

🧩 **Real multi-module architecture.** `:app` at the top, four
`:core:*` libraries (designsystem, ui, data, navigation) and four
`:feature:*` modules that only depend on core. Convention plugins in a
composite build keep each module file ~10 lines.

🎛 **Custom Compose Canvas controls.** A circular dimmer with drag
gestures, a vertical cold→warm temperature slider, a curtain
illustration that slides on the rod with `animateFloatAsState`, a
waveform visualisation, an emergency call pill with three animated
chevrons.

🌅 **Production-quality plumbing.** Edge-to-edge with
`safeDrawingPadding` on every screen, a proper Core Splashscreen
handoff, an adaptive launcher icon, type-safe Navigation Compose with
`@Serializable` routes, and a `@SentryPreview` multi-preview on every
reusable component.

🤖 **Agent-friendly.** There's a `CLAUDE.md` at the repo root and a
`docs/` folder + mirrored Wiki, all written for LLMs to read in
isolation. Claude Code / Cursor / Aider will feel at home.

No real IoT, no real backend — every device is a `StateFlow` over an
in-memory seed. The point isn't the data, it's the architecture.

All visual design is © **[Phenomenon Studio](https://phenomenon.studio/)**.
The original concept is on [Dribbble](https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry) — give them the credit they deserve.

👉 https://github.com/jacksonmafra-umain/SentrySmartHome

Open to feedback, PRs, and conversations about whether Gradle 9 + AGP 9
alpha was a good idea.

\#Android #JetpackCompose #Kotlin #Koin #Gradle #Compose #DesignSystem #MultiModule #DI
