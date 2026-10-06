# World Screen

3D to 2D coords.

Category: core. Controls: Mouse, Wheel.

Ported from raylib's `examples/core/core_world_screen.c`.

![world-screen](../demos/world-screen.gif)

## Run it

```sh
cd world-screen && bb run   # from this demo (or clojure -M:run)
bb world-screen             # from the repo root
```

## About

Raylib [core] example - world screen

Demonstrates converting 3D world coordinates to 2D screen space.
Useful for placing UI elements (health bars, labels) above 3D objects.
Based on: raylib/examples/core/core_world_screen.c

Complexity: ⭐⭐ Easy

Controls:
- Mouse: Rotate camera (third-person mode)
- Mouse Wheel: Zoom in/out
- F1: Toggle debug stats
- ESC: Exit
