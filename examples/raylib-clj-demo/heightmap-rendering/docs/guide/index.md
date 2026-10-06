# Heightmap Rendering

Terrain mesh generated from a greyscale PNG, brightness as elevation.

Category: models. Controls: Orbits on its own, no input.

Ported from raylib's `examples/models/models_heightmap_rendering.c`.

![heightmap-rendering](../demos/heightmap-rendering.gif)

## Run it

```sh
cd heightmap-rendering && bb run   # from this demo (or clojure -M:run)
bb heightmap-rendering             # from the repo root
```

It loads `resources/heightmap.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [models] example - heightmap rendering

Terrain from a greyscale PNG: `gen-mesh-heightmap` reads each pixel's
brightness as an elevation and emits two triangles per pixel quad. The
128x128 source therefore yields exactly (128-1)^2 * 2 = 32,258 triangles,
which is a useful sanity check that the mesh generated is the mesh meant.

The same image is then used twice over - as the terrain's shape via the
mesh, and as its surface via the texture uploaded from it - which is why
the inset preview in the corner looks like a height map and the terrain
looks like the same map draped over hills.

Difficulty: 3/4
Based on: models/models_heightmap_rendering.c
