# User Flow

The six Sentry screens connect in a hub-and-spoke flow: **Home** is the entry
point, everything else is one navigation hop away. Inside a Room, the three
inner tabs (Light / Thermostat / Curtains) switch state without pushing a
new back-stack entry.

All visual design is **© [Phenomenon Studio](https://phenomenon.studio/)** —
see the [Dribbble source](https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry).

## Diagram

ASCII mirror of the live Pencil source at [`design/sentry.pen`](../design/sentry.pen):

```
                             ┌──────────────────────┐
                             │      Activities      │◀──── tap bell 🔔
                             │  (waveform + events) │
                             └──────────────────────┘
                                        ▲
                                        │ navigate
                                        │
  ┌──────────────┐   tap room tab   ┌───────────────────────────────────┐
  │              │ ───────────────▶ │        Room (Bedroom)             │
  │     HOME     │                  │ ┌─────────┬──────────┬──────────┐ │
  │  "Sia's home"│                  │ │  Light  │ Thermo   │ Curtains │ │
  │              │ ◀─────────────── │ └─────────┴──────────┴──────────┘ │
  └──────────────┘     back         │       (inner tabs, bidirectional) │
           │                         └───────────────────────────────────┘
           │ tap dock › all devices
           ▼
  ┌─────────────────┐
  │   Device Hub    │
  │  (hub dimmer)   │
  └─────────────────┘
```

## Screens

| Screen | Entry points | Primary actions |
|---|---|---|
| Home | launcher | tap bell, tap room tab, tap dock, toggle device card switches |
| Activities | Home 🔔 | browse activity feed, play videos |
| Device Hub | Home dock › all-devices | rotate dimmer, quick hub actions (mic, cam, speaker, power) |
| Room — Light | Home room tab (default pane) | rotate dimmer, pick colour, toggle timer, toggle power |
| Room — Thermostat | Room inner tabs | drag temperature slider, toggle heating/cooling |
| Room — Curtains | Room inner tabs | pick Open / Half / Closed / Auto, adjust brightness |

## Legend

- **Primary navigation** — arrows between distinct screens; push/pop on the
  back stack.
- **In-screen tab switch** — state change inside the Room screen, no back
  stack entry.
- **Home (orange border)** — the app's entry point.

## Pencil source

The canonical `.pen` file is at [`design/sentry.pen`](../design/sentry.pen).

- The contents are encrypted and only readable through the Pencil app or the
  Pencil MCP tools — you can't open it with plain text editors.
- The top-level frame `bi8Au` is the whole flow diagram; each screen node is
  labelled with the same name as the screen it represents.
- The orange border on **HOME** marks it as the user entry point.

If you change the navigation graph, regenerate the diagram: open
`design/sentry.pen` in Pencil, update the nodes/arrows, save (⌘S), commit the
`.pen` file alongside the code change.
