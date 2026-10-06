# First Person 3D

FPS camera.

Category: core. Controls: WASD, Mouse, 1-4.

Ported from raylib's `examples/core/core_3d_camera_first_person.c`.

![first-person-3d](../demos/first-person-3d.gif)

## Run it

```sh
cd first-person-3d && bb run   # from this demo (or clojure -M:run)
bb first-person-3d             # from the repo root
```

## About

Raylib [core] example - 3D camera first person

First-person camera with mouse look and WASD movement.
Features multiple camera modes and random columns in a walled arena.
Based on: raylib/examples/core/core_3d_camera_first_person.c

Complexity: ⭐⭐⭐ Intermediate

Controls:
- W/A/S/D: Move forward/left/backward/right
- Mouse: Look around
- Space: Move up
- Left-Ctrl: Move down
- 1/2/3/4: Switch camera mode (Free/First-person/Third-person/Orbital)
- P: Toggle perspective/orthographic projection
- F1: Toggle debug stats
- Q: Exit (re-enables cursor)
