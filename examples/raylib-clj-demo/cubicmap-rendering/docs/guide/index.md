# Cubicmap Rendering

A 3D maze mesh generated from a 32x16 PNG, one cube per lit pixel.

Category: models. Controls: P to pause the orbit.

Ported from raylib's `examples/models/models_cubicmap_rendering.c`.

![cubicmap-rendering](../demos/cubicmap-rendering.gif)

## Run it

```sh
cd cubicmap-rendering && bb run   # from this demo (or clojure -M:run)
bb cubicmap-rendering             # from the repo root
```

It loads `resources/cubicmap.png`, `resources/cubicmap_atlas.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [models] example - cubicmap rendering

A 3D maze generated from a 32x16 PNG: `gen-mesh-cubicmap` reads the image
and emits a cube for every non-black pixel, so the little bitmap shown in
the corner IS the level. A separate atlas texture supplies the wall and
floor faces.

Two things this leans on that are easy to get wrong. The image is CPU-side
and the texture is GPU-side: `load-texture-from-image` uploads a copy, so
the image can be - and is - unloaded immediately afterward while both the
texture and the generated mesh live on. And the atlas is attached with
`set-model-material-texture!`, because raylib's own examples assign
`model.materials[0].maps[DIFFUSE].texture` directly and there is no other
way to reach that slot.

Difficulty: 3/4
Based on: models/models_cubicmap_rendering.c
