# Split Screen 3D

Two-player 3D.

Category: core. Controls: W/S, UP/DOWN.

Ported from raylib's `examples/core/core_3d_camera_split_screen.c`.

![split-screen-3d](../demos/split-screen-3d.gif)

## Run it

```sh
cd split-screen-3d && bb run   # from this demo (or clojure -M:run)
bb split-screen-3d             # from the repo root
```

## About

Raylib [core] example - 3D camera split screen

Two-player split screen with independent 3D cameras.
Each player can move forward/backward in a 3D world of cube trees.
Based on: raylib/examples/core/core_3d_camera_split_screen.c

Complexity: ⭐⭐⭐ Intermediate

Controls:
- W/S: Move Player 1 (left screen) forward/backward
- UP/DOWN: Move Player 2 (right screen) forward/backward
- F1: Toggle debug stats
- Q: Exit
