# Srcrec Dstrec

Source/destination rects with rotation.

Category: textures. Controls: Q to exit.

Ported from raylib's `examples/textures/textures_srcrec_dstrec.c`.

![srcrec-dstrec](../demos/srcrec-dstrec.gif)

## Run it

```sh
cd srcrec-dstrec && bb run   # from this demo (or clojure -M:run)
bb srcrec-dstrec             # from the repo root
```

It loads `resources/scarfy.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [textures] example - source and destination rectangles

One draw-texture-pro! call doing four things at once: picking a single
frame out of a spritesheet (source rect), scaling it to fit a screen
rect (destination rect), rotating it, and doing that rotation about a
chosen origin rather than the corner. The two grey lines mark the
destination's x and y, which is where the origin puts the pivot.

Difficulty: 2/4
Based on: textures/textures_srcrec_dstrec.c
