# Hilbert Curve

Space-filling curve drawn stroke by stroke with hue along its length.

Category: shapes. Controls: Spinner sets order, sliders set thickness and size.

Ported from raylib's `examples/shapes/shapes_hilbert_curve.c`.

![hilbert-curve](../demos/hilbert-curve.gif)

## Run it

```sh
cd hilbert-curve && bb run   # from this demo (or clojure -M:run)
bb hilbert-curve             # from the repo root
```

## About

raylib [shapes] example - hilbert curve

A Hilbert curve drawn stroke by stroke, hue cycling along its length. The
order spinner sets how many times the pattern subdivides, and changing
either the order or the total size regenerates the path - replaying the
animation if ANIMATE is ticked.

The curve is built by `hilbert-step`, which maps an index to a grid cell
directly rather than by recursion: it reads the index two bits at a time,
and each pair says how to transform the point accumulated so far. Watch
the case-2 branch - in the C it FALLS THROUGH into case 1, so it adds the
quadrant length to BOTH axes, not just to x. That fallthrough is easy to
lose in translation and turns the curve inside out when you do.

Difficulty: 3/4
Based on: shapes/shapes_hilbert_curve.c
