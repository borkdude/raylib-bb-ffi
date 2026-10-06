# Billboard Rendering

Camera-facing sprites drawn far-to-near so alpha blends correctly.

Category: models. Controls: Orbits on its own, no input.

Ported from raylib's `examples/models/models_billboard_rendering.c`.

![billboard-rendering](../demos/billboard-rendering.gif)

## Run it

```sh
cd billboard-rendering && bb run   # from this demo (or clojure -M:run)
bb billboard-rendering             # from the repo root
```

It loads `resources/billboard.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [models] example - billboard rendering

Two textured quads that always face the camera, orbiting overhead. One is
drawn with `draw-billboard!` (axis-aligned, sized by a scalar) and one with
`draw-billboard-pro!` (explicit up vector, source rect, origin and
rotation, so it can spin in place).

The part worth keeping is the draw ORDER. Both billboards have alpha, and
alpha-blended geometry does not depth-sort correctly on the GPU - whichever
is drawn second wins the blend regardless of which is actually nearer. So
each frame the two are sorted by distance from the camera and the FARTHER
one is drawn first. Remove that and the far billboard punches a hole
through the near one whenever they overlap.

Difficulty: 2/4
Based on: models/models_billboard_rendering.c
