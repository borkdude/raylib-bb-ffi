# Camera 3D Mode

Minimal 3D scene - cube on a grid.

Category: core. Controls: Q to exit.

Ported from raylib's `examples/core/core_3d_camera_mode.c`.

![camera-3d-mode](../demos/camera-3d-mode.gif)

## Run it

```sh
cd camera-3d-mode && bb run   # from this demo (or clojure -M:run)
bb camera-3d-mode             # from the repo root
```

## About

raylib [core] example - 3d camera mode

The minimal 3D scene: a perspective camera looking at a red cube on a
grid. This is the 3D counterpart of hello-world - the smallest program
that proves BeginMode3D, a Camera3D struct crossing the FFI boundary,
and the depth buffer are all working.

Difficulty: 1/4
Based on: core/core_3d_camera_mode.c
