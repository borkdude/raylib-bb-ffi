# Math Sine Cosine

Unit circle with sine, cosine, tangent and related angles drawn live.

Category: shapes. Controls: Angle slider, Pause toggle.

Ported from raylib's `examples/shapes/shapes_math_sine_cosine.c`.

![math-sine-cosine](../demos/math-sine-cosine.gif)

## Run it

```sh
cd math-sine-cosine && bb run   # from this demo (or clojure -M:run)
bb math-sine-cosine             # from the repo root
```

## About

raylib [shapes] example - math sine cosine

The unit circle, animated. A point travels the circle while its sine,
cosine, tangent and cotangent are drawn as lines against it, the related
angles (complementary, supplementary, explementary) as arcs, and the sine
and cosine waves as graphs in the corner with a marker tracking the
current angle.

Two things it needs that the bundled raylib does not provide.
The dashed guides go through `net.b12n.raylib-clj.shapes.basic/draw-dashed-line!`, a
Clojure implementation of raylib's `DrawLineDashed`. It was written when
this project bundled 5.5.0, which lacks that function; 6.0 has it, so the
Clojure version is now a choice rather than a necessity. `GuiToggle` and `GuiGroupBox` come from
`net.b12n.raylib-clj.raygui`.

Note the arcs are drawn with negative angles - raylib measures the sector
clockwise from the positive x-axis while the point is placed counter-
clockwise, so the arc has to be negated to sit under the point.

Difficulty: 3/4
Based on: shapes/shapes_math_sine_cosine.c
