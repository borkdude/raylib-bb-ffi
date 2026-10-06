# Input Gamepad

Gamepad demo.

Category: core. Controls: Connect gamepad.

Ported from raylib's `examples/core/core_input_gamepad.c`.

![input-gamepad](../demos/input-gamepad.gif)

## Run it

```sh
cd input-gamepad && bb run   # from this demo (or clojure -M:run)
bb input-gamepad             # from the repo root
```

It loads `resources/ps3.png`, `resources/xbox.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

Raylib [core] example - input gamepad

Displays gamepad input state with visual feedback.
Shows Xbox and PlayStation controller layouts with button/axis visualization.
Based on: raylib/examples/core/core_input_gamepad.c

Complexity: ⭐⭐ Easy

Requirements:
- Gamepad connected to the system (Xbox, PlayStation, or generic)

Controls:
- LEFT/RIGHT arrows: Switch between gamepads
- F1: Toggle debug stats
- Q: Exit
