# Rounded Rectangle Drawing

Corner roundness, size, thickness and segments on raygui sliders.

Category: shapes. Controls: Drag the sliders, toggle the three draw modes.

Ported from raylib's `examples/shapes/shapes_rounded_rectangle_drawing.c`.

![rounded-rectangle-drawing](../demos/rounded-rectangle-drawing.gif)

## Run it

```sh
cd rounded-rectangle-drawing && bb run   # from this demo (or clojure -M:run)
bb rounded-rectangle-drawing             # from the repo root
```

## About

raylib [shapes] example - rounded rectangle drawing

A rounded rectangle with its size, corner roundness, outline thickness
and segment count on sliders, and checkboxes to overlay the plain
rectangle and the rounded outline for comparison.

The MODE readout is a fixed threshold here rather than one derived from
the shape: raylib substitutes its own corner segment count below 4, so
the C compares against a literal 4 instead of computing a minimum the way
the ring and sector examples do.

Difficulty: 2/4
Based on: shapes/shapes_rounded_rectangle_drawing.c
