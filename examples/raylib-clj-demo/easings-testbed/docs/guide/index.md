# Easings Testbed

All 28 easing curves, one per axis.

Category: shapes. Controls: LEFT/RIGHT x-curve, UP/DOWN y-curve, ENTER play, SPACE restart, Q/W/A/S duration, T bound.

Ported from raylib's `examples/shapes/shapes_easings_testbed.c`.

![easings-testbed](../demos/easings-testbed.gif)

## Run it

```sh
cd easings-testbed && bb run   # from this demo (or clojure -M:run)
bb easings-testbed             # from the repo root
```

## About

raylib [shapes] example - easings testbed

Drive a ball with a different easing curve on each axis and watch how
they combine. Pick the x curve with LEFT/RIGHT and the y curve with
UP/DOWN, ENTER plays or pauses, SPACE restarts. Q/W change the duration
in steps of 20, A/S hold for finer steps of 2. T toggles whether time
stops at the duration or keeps running past it, which is how you see
what a curve does outside its intended range.

Both axes default to "none", so nothing moves until you choose a curve
- that is the C's behaviour and it is deliberate, not a broken start.

The 28 curves live in net.b12n.raylib-clj.easings, a port of raylib's reasings.h.
They were extracted rather than written inline because three other
examples already carry private partial copies.

Difficulty: 2/4
Based on: shapes/shapes_easings_testbed.c
