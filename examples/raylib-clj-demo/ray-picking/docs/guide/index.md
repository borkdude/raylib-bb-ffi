# Ray Picking

Click to select cubes.

Category: models. Controls: Click, Right-click, Q.

Ported from raylib's `examples/core/core_3d_picking.c`.

![ray-picking](../demos/ray-picking.gif)

## Run it

```sh
cd ray-picking && bb run   # from this demo (or clojure -M:run)
bb ray-picking             # from the repo root
```

## About

Raylib [models] example - ray picking

Click on cubes to select them. Selected cubes change color.
Demonstrates ray casting for 3D object selection.
Based on: core_3d_picking.c

Complexity: ⭐⭐ Intermediate (2/4)

Controls:
- Left Click: Select cube
- Right Click: Toggle camera control
- WASD: Move camera (when unlocked)
- Q: Exit
