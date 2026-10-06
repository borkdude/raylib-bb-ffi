# Tiled Drawing

Tile a texture patch with selectable pattern, tint, scale and rotation.

Category: textures. Controls: Click a pattern or colour, UP/DOWN scale, LEFT/RIGHT rotate, SPACE reset.

Ported from raylib's `examples/textures/textures_tiled_drawing.c`.

![tiled-drawing](../demos/tiled-drawing.gif)

## Run it

```sh
cd tiled-drawing && bb run   # from this demo (or clojure -M:run)
bb tiled-drawing             # from the repo root
```

It loads `resources/patterns.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [textures] example - tiled drawing

Tiles one patch of a texture atlas across the window, with the pattern,
tint, scale and rotation all selectable. Resize the window and the tiling
re-fits.

The interesting part is `tile-rects`, a Clojure version of the example's
own `DrawTextureTiled` helper - which is example code in the C, not raylib
API. It has four cases: the tile is bigger than the area in both axes (draw
one, cropped), bigger in one axis (a single column or row), or smaller in
both (a grid). The edges of each case draw a partial tile, cropping the
SOURCE proportionally so the pattern is truncated rather than squashed.

Written here as a pure function returning the (source, dest) pairs rather
than as nested draw loops, so the geometry can be checked without a window
- which matters, because the C's own comment on the guard at the top of
that function is "Wanna see a infinite loop?!...just delete this line!".

Difficulty: 3/4
Based on: textures/textures_tiled_drawing.c
