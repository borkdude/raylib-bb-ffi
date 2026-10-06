# Ring Drawing

Ring inner/outer radius, angles and segments on raygui sliders.

Category: shapes. Controls: Drag the sliders, toggle the three draw modes.

Ported from raylib's `examples/shapes/shapes_ring_drawing.c`.

![ring-drawing](../demos/ring-drawing.gif)

## Run it

```sh
cd ring-drawing && bb run   # from this demo (or clojure -M:run)
bb ring-drawing             # from the repo root
```

## About

raylib [shapes] example - ring drawing

A ring with its inner and outer radius, start and end angle, and segment
count on sliders, plus three checkboxes selecting which of the three
drawing calls to show at once - filled ring, ring outline, and the
circle-sector outline for comparison.

The MODE readout works the same way as in `circle-sector-drawing`:
raylib substitutes its own segment count when you ask for fewer than the
arc needs, and AUTO is reporting that substitution.

Angles here run -450 to 450, so unlike the sector example the arc can be
negative. `min-segments` uses ceil alone, matching the C, which means a
backwards arc yields a negative threshold that any segment count clears.

Difficulty: 2/4
Based on: shapes/shapes_ring_drawing.c
