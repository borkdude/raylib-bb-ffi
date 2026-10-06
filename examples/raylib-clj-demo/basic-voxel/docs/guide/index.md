# Basic Voxel

First-person 8x8x8 voxel world, left-click to remove a cube.

Category: models. Controls: WASD to move, mouse to look, left-click to remove.

Ported from raylib's `examples/models/models_basic_voxel.c`.

![basic-voxel](../demos/basic-voxel.gif)

## Run it

```sh
cd basic-voxel && bb run   # from this demo (or clojure -M:run)
bb basic-voxel             # from the repo root
```

## About

raylib [models] example - basic voxel

An 8x8x8 block of cubes you walk through in first person and remove by
left-clicking. The pick is a ray cast from the screen centre, tested
against every voxel's bounding box; the nearest hit is the one removed.

First example built on `net.b12n.raylib-clj.models`. Worth knowing what it exercises:
the `Model` struct is passed BY VALUE to every `draw-model!` call, so the
whole 136-byte layout - including the nested skeleton that raylib 6.0
added - has to be right or the draw reads garbage.

It also sets the cube's material colour through
`set-model-material-color!`, which walks into memory the model owns
because raylib exposes no function for it. That matters visually: raylib
MULTIPLIES the material colour by the tint passed to `draw-model!`, and
the C sets both to BEIGE, so the cubes are a darker beige than either
alone. Setting only the tint would render them noticeably lighter.

Difficulty: 3/4
Based on: models/models_basic_voxel.c
