# Smooth Pixelperfect

Pixel-aligned world, sub-pixel smooth camera.

Category: core. Controls: Q to exit.

Ported from raylib's `examples/core/core_smooth_pixelperfect.c`.

![smooth-pixelperfect](../demos/smooth-pixelperfect.gif)

## Run it

```sh
cd smooth-pixelperfect && bb run   # from this demo (or clojure -M:run)
bb smooth-pixelperfect             # from the repo root
```

## About

raylib [core] example - smooth pixel-perfect camera

A 160x90 world rendered into a tiny render texture and blown up to fill
the window, so every world pixel becomes a chunky block. The trick is in
splitting one camera into two.

A pixel-art game wants the world drawn on whole-pixel boundaries, or
sprites shimmer as they move. But snapping the camera to whole pixels
makes motion visibly stutter. So the camera target is split: the integer
part goes to the world camera, which keeps rendering pixel-aligned, and
the leftover fraction goes to a screen-space camera that shifts the
already-rendered image by a sub-pixel amount. Crisp pixels, smooth
motion.

Difficulty: 3/4
Based on: core/core_smooth_pixelperfect.c
