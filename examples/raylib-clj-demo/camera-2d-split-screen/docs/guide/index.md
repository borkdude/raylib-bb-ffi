# Camera 2D Split Screen

Two players, two cameras, one grid.

Category: core. Controls: P1 W/S/A/D, P2 arrows, Q to exit.

Ported from raylib's `examples/core/core_2d_camera_split_screen.c`.

![camera-2d-split-screen](../demos/camera-2d-split-screen.gif)

## Run it

```sh
cd camera-2d-split-screen && bb run   # from this demo (or clojure -M:run)
bb camera-2d-split-screen             # from the repo root
```

## About

raylib [core] example - 2D camera split screen

Two players on one shared grid, each with their own camera, each
rendered into its own half-width render texture and then blitted side by
side. Both halves show the same world from different viewpoints, which
is what makes it a split screen rather than two windows.

Player 1: W/S/A/D. Player 2: arrow keys.

Difficulty: 3/4
Based on: core/core_2d_camera_split_screen.c
