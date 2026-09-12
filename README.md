# Previous Prayer Tracker

A RuneLite plugin that displays your active, recent, or previous overhead prayers directly on-screen via a clean, customizable overlay. 

Designed for both PvM and PvP scenarios to help players track flicking cadence, confirm overhead switches, and review recent prayer usage without clutter.

---

## Features

- **Overhead Tracking:** Accurately detects and renders your player's active and switched overhead prayers (Protect from Melee, Missiles, Magic, Retribution, Redemption, and Smite).
- **Flexible Display Modes:**
  - **Previous:** Displays the prayer you had active immediately before your latest switch or deactivation.
  - **Recent:** Displays the most recently activated overhead prayer.
- **Custom Update Timing:**
  - **Instant:** Immediately updates the overlay upon every prayer action.
  - **When Switched:** Holds the display until a distinct prayer switch occurs or your overhead drops.
- **Customizable UI:**
  - Multiple icon sizing presets (Small, Medium, Large).
  - Fully integrated with RuneLite's drag-and-drop overlay system (`Alt` + Drag to reposition anywhere on the game canvas).

---

## Configuration

Access plugin settings via the RuneLite configuration sidebar under **Previous Prayer Tracker**:

| Setting | Options | Default | Description |
| :--- | :--- | :--- | :--- |
| **Show Prayer** | `Previous`, `Recent` | `Previous` | Choose whether to show your previous prayer or the most recent one. |
| **Update Timing** | `Instant`, `When switched` | `Instant` | Select whether updates trigger instantly on click or only upon switching. |
| **Icon Size** | `Small`, `Medium`, `Large` | `Medium` | Adjust the display size of the rendered overhead prayer icon. |

---

## Compliance & Security

This plugin strictly adheres to:
- [Jagex Third-Party Client Guidelines](https://secure.runescape.com/m=news/third-party-client-guidelines?oldschool=1)
- [RuneLite Plugin Hub Rules](https://github.com/runelite/plugin-hub)

It provides **no automated inputs**, does **not predict opponent or boss actions**, and only reads local player prayer states already exposed by the official client API.

---

## License

This project is licensed under the BSD 2-Clause License. See the [LICENSE](LICENSE) file for details.
