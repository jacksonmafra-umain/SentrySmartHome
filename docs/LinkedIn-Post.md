# LinkedIn post — draft

> Keep this under ~1,300 chars for the unexpanded view on the LinkedIn
> feed. Informal tone, no emojis, no library versions — the post is
> about the *what* and the *why*, not the dependency list.

---

## Short version (for the feed)

Spent the weekend rebuilding Phenomenon Studio's Sentry smart-home
concept in Jetpack Compose. Dark glass everywhere, circular dimmers,
curtains that slide when you tap them, a vertical temperature slider
that actually feels nice.

No backend, no real IoT — the whole point was the architecture. Six
screens, a small design system, compile-time validated DI, a proper
multi-module setup. The kind of scaffolding that's a pain to put
together for a "real" app but pays for itself the second time you add
a feature.

All the visual design credit goes to Phenomenon Studio. I just
translated it into Kotlin.

Dribbble: https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry
Repo: https://github.com/jacksonmafra-umain/SentrySmartHome

---

## Long version (for the "see more" expand)

I had a free weekend and Phenomenon Studio's Sentry smart-home concept
on my mind, so I rebuilt it in Jetpack Compose.

Everything you see is drawn in Compose — the circular dimmer is a
Canvas with drag gestures, the hanging lamp is a handful of paths with
a radial gradient, the curtains slide across the rod with a
spring-ish tween, the waveform on the activities screen is a row of
tiny lines. No bitmaps, no WebViews, no PNGs hiding under the UI.

Under the hood it's a multi-module project — a design system, a fake
data layer, a navigation contract, and four feature modules that
don't know about each other. Convention plugins keep each module's
build file tiny. Dependency injection is validated at compile time:
if you forget to bind something, the build fails before you even hit
run.

There's no backend. Every device is a StateFlow over an in-memory
seed. That's deliberate — the point wasn't to model a real smart
home, it was to put together the kind of scaffolding I'd want on day
one of a production app.

Also spent some time making the repo friendly to coding agents — a
CLAUDE.md at the root, a docs folder mirrored to the wiki, previews
on every component, crisp module boundaries. If you drop Claude Code,
Cursor or Aider into this repo they should feel oriented in about
thirty seconds.

All design credit to Phenomenon Studio. I just translated their
concept into Kotlin.

Dribbble: https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry
Code: https://github.com/jacksonmafra-umain/SentrySmartHome

Happy to talk architecture, Compose tricks, or whether I went
overboard on module splits.
