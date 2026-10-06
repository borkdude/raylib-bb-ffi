# Input Virtual Controls

On-screen D-pad.

Category: core. Controls: Click/touch the D-pad, Q to exit.

Ported from raylib's `examples/core/core_input_virtual_controls.c`.

![input-virtual-controls](../demos/input-virtual-controls.gif)

## Run it

```sh
cd input-virtual-controls && bb run   # from this demo (or clojure -M:run)
bb input-virtual-controls             # from the repo root
```

## About

raylib [core] example - input virtual controls

An on-screen D-pad. Press a button with the mouse (or a finger on a
touchscreen) and the maroon circle moves. This is how a touch-only build
gets directional input without a keyboard.

The hit test is a diamond, not a circle: it sums the x and y distances
and compares against the radius, which is Manhattan distance. That makes
the pressable area a rotated square, so the four buttons tile without
gaps or overlap. Kept as-is rather than 'corrected' to a circle.

Difficulty: 2/4
Based on: core/core_input_virtual_controls.c
