# Viewport Scaling

Six ways to fit a fixed-resolution game onto a resizable window.

Category: core. Controls: Click < > to change resolution and viewport type, resize the window.

Ported from raylib's `examples/core/core_viewport_scaling.c`.

![viewport-scaling](../demos/viewport-scaling.gif)

## Run it

```sh
cd viewport-scaling && bb run   # from this demo (or clojure -M:run)
bb viewport-scaling             # from the repo root
```

## About

raylib [core] example - viewport scaling

Six ways to fit a fixed-resolution game onto a resizable window. The game
is rendered to a small render texture (64x64 up to 3840x2160) and then
blitted to the window; the six viewport types differ in how the source
and destination rectangles are computed. Resize the window and the
readout updates live.

The three INTEGER variants use integer division for the scale factor, so
they only ever upscale by a whole number - the right choice for pixel art,
where a fractional scale produces uneven pixels. The other three accept a
fractional ratio and can also downscale, which is why 3840x2160 is in the
resolution list: it cannot scale integrally into an 800x450 window at all.

Worth knowing before comparing against the C: it exposes six named modes
but only four distinct behaviours. KEEP_HEIGHT_INTEGER and KEEP_HEIGHT
compute byte-identical rectangles, as do KEEP_WIDTH_INTEGER and
KEEP_WIDTH - both members of each pair divide a float by an int, so the
extra casts in the _INTEGER variants change nothing. Only KEEP_ASPECT
differs from KEEP_ASPECT_INTEGER, because there the C divides int by int
and gets truncation. Verified by transcribing both C functions literally
and comparing across 100 window/game size combinations: zero differed.
All six stay selectable here so the UI matches upstream.

The source rectangle carries a NEGATIVE height throughout. That is not a
quirk of this example - OpenGL render textures are stored bottom-up, so
flipping the source rect is the standard way to blit one the right way up.

Difficulty: 2/4
Based on: core/core_viewport_scaling.c
