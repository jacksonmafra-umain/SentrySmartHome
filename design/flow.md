# Sentry Smart Home — User Flow

All design credit: **Phenomenon Studio**
https://dribbble.com/shots/26781374-Smart-Home-Mobile-App-Design-Sentry

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

## Nodes

| Screen     | Entry points                        | Primary actions                             |
|------------|-------------------------------------|---------------------------------------------|
| Home       | launcher                            | tap bell, tap room tab, tap dock, toggle cards |
| Activities | Home 🔔                             | browse activity feed, play videos            |
| Device Hub | Home dock › all-devices             | rotate dimmer, quick hub actions              |
| Room Light | Home room tab (default)             | rotate dimmer, pick color, timer, power       |
| Room Thermo| Room inner tabs                     | drag slider, toggle heating/cooling           |
| Room Curtain| Room inner tabs                    | pick position, toggle auto, dim brightness    |

## Legend

- **Primary navigation** — arrows between distinct screens (back-stack push/pop)
- **In-screen tab switch** — state change inside the Room screen, no back-stack entry

---

## Generating this flow in Pencil

The Pencil desktop app wasn't running when this project was scaffolded, so the
flow diagram couldn't be emitted to `design/flow.pen`. Start Pencil, open this
project's folder, and run the batch below to recreate the diagram on a new
frame. The operations follow the Pencil `.pen` schema and can be executed
through the `mcp__pencil__batch_design` tool one batch at a time.

### Step 1 — Create the container frame

```js
flow=I(document,{type:"frame",name:"Sentry Smart Home — User Flow",layout:"none",width:1600,height:900,placeholder:true,fill:"#0A0A0C"})
```

### Step 2 — Create each screen node

```js
home=I(flow,{type:"frame",name:"Home",layout:"vertical",x:120,y:360,width:220,height:160,padding:16,gap:8,cornerRadius:20,fill:"#1A1A1F",stroke:{thickness:1,fill:"#2A2A31"}})
I(home,{type:"text",content:"HOME",fontSize:14,fontWeight:"SemiBold",fill:"#F5F5F7",textGrowth:"auto"})
I(home,{type:"text",content:"Sia's home",fontSize:12,fill:"#B5B5BC",textGrowth:"auto"})
I(home,{type:"text",content:"dashboard, doorbell, device grid",fontSize:10,fill:"#86868E",textGrowth:"fixed-width",width:180})

activities=I(flow,{type:"frame",name:"Activities",layout:"vertical",x:560,y:120,width:220,height:140,padding:16,gap:8,cornerRadius:20,fill:"#1A1A1F",stroke:{thickness:1,fill:"#2A2A31"}})
I(activities,{type:"text",content:"Activities",fontSize:14,fontWeight:"SemiBold",fill:"#F5F5F7",textGrowth:"auto"})
I(activities,{type:"text",content:"waveform + event feed",fontSize:10,fill:"#86868E",textGrowth:"fixed-width",width:180})

deviceHub=I(flow,{type:"frame",name:"Device Hub",layout:"vertical",x:120,y:640,width:220,height:140,padding:16,gap:8,cornerRadius:20,fill:"#1A1A1F",stroke:{thickness:1,fill:"#2A2A31"}})
I(deviceHub,{type:"text",content:"Device Hub",fontSize:14,fontWeight:"SemiBold",fill:"#F5F5F7",textGrowth:"auto"})
I(deviceHub,{type:"text",content:"hub dimmer, quick controls",fontSize:10,fill:"#86868E",textGrowth:"fixed-width",width:180})
```

### Step 3 — Room node with inner panes

```js
room=I(flow,{type:"frame",name:"Room (Bedroom)",layout:"vertical",x:800,y:340,width:520,height:200,padding:20,gap:12,cornerRadius:20,fill:"#1A1A1F",stroke:{thickness:1,fill:"#FF6B4A"}})
I(room,{type:"text",content:"Room (Bedroom)",fontSize:14,fontWeight:"SemiBold",fill:"#F5F5F7",textGrowth:"auto"})

panes=I(room,{type:"frame",layout:"horizontal",width:"fill_container",height:80,gap:10})
light=I(panes,{type:"frame",layout:"vertical",width:"fill_container",height:"fill_container",cornerRadius:12,padding:12,fill:"#2A2A31",justifyContent:"center",alignItems:"center"})
I(light,{type:"text",content:"Light",fontSize:12,fill:"#F5F5F7",textGrowth:"auto"})
thermo=I(panes,{type:"frame",layout:"vertical",width:"fill_container",height:"fill_container",cornerRadius:12,padding:12,fill:"#2A2A31",justifyContent:"center",alignItems:"center"})
I(thermo,{type:"text",content:"Thermostat",fontSize:12,fill:"#F5F5F7",textGrowth:"auto"})
curt=I(panes,{type:"frame",layout:"vertical",width:"fill_container",height:"fill_container",cornerRadius:12,padding:12,fill:"#2A2A31",justifyContent:"center",alignItems:"center"})
I(curt,{type:"text",content:"Curtains",fontSize:12,fill:"#F5F5F7",textGrowth:"auto"})

I(room,{type:"text",content:"inner tabs — in-screen switch",fontSize:10,fill:"#86868E",textGrowth:"auto"})
```

### Step 4 — Arrows + labels

For each primary transition, draw a `line` with `stroke.fill:"#FF6B4A"` between
screen frames and place a small `text` label beside it ("tap bell", "tap room
tab", "tap dock › all devices", "back"). Use `pencil.find_empty_space_on_canvas`
to avoid overlap with the screen frames. Then unset `placeholder:true` on the
flow frame and call `mcp__pencil__get_screenshot(flow)` to verify the result.
