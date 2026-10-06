# Camera FPS

FPS with physics.

Category: core. Controls: WASD, Space, Ctrl.

Ported from raylib's `examples/core/core_3d_camera_fps.c`.

![camera-fps](../demos/camera-fps.gif)

## Run it

```sh
cd camera-fps && bb run   # from this demo (or clojure -M:run)
bb camera-fps             # from the repo root
```

## About

Raylib [core] example - 3d camera fps

Advanced FPS camera with physics-based movement, head bobbing,
crouching, and strafing mechanics.
Based on: raylib/examples/core/core_3d_camera_fps.c

Complexity: ⭐⭐⭐ Intermediate

Controls:
- W/A/S/D: Move forward/left/backward/right
- Mouse: Look around
- Space: Jump
- Left-Ctrl: Crouch
- F1: Toggle debug stats
- ESC: Exit
