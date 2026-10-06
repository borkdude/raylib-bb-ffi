# Kaleidoscope

Mouse strokes repeated around six-fold symmetry and mirrored.

Category: shapes. Controls: Drag to draw, < > to step through lines, Reset to clear.

Ported from raylib's `examples/shapes/shapes_kaleidoscope.c`.

![kaleidoscope](../demos/kaleidoscope.gif)

## Run it

```sh
cd kaleidoscope && bb run   # from this demo (or clojure -M:run)
bb kaleidoscope             # from the repo root
```

## About

raylib [shapes] example - kaleidoscope

Drag the mouse to draw. Each stroke is repeated around `symmetry`
rotations and mirrored across the horizontal, so a single gesture becomes
a symmetric figure. The < and > buttons walk back and forward through the
stored lines, and Reset clears them.

Two observations about the C, handled differently here and worth naming.

First, its draw pass wraps the line loop in `for (s = 0; s < symmetry;
s++)` - but the loop body never reads `s`. The rotations are already
baked into the stored lines when they are recorded, so that outer loop
redraws identical opaque lines six times over. It cannot change a pixel;
it just costs six times the draw calls. Drawn once here.

Second, the C sets its button flags during the draw pass and acts on them
at the top of the NEXT frame, so a click takes effect one frame late.
That is an artifact of where the calls sit rather than an intent, and
immediate-mode controls that return a value let the click be handled in
the frame it happens. Invisible either way at 20 FPS.

Difficulty: 3/4
Based on: shapes/shapes_kaleidoscope.c
