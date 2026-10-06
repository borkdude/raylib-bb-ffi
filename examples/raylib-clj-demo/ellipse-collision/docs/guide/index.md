# Ellipse Collision

Steer one ellipse into another.

Category: shapes. Controls: A/B to switch control, Mouse to move, Q to exit.

Ported from raylib's `examples/shapes/shapes_ellipse_collision.c`.

![ellipse-collision](../demos/ellipse-collision.gif)

## Run it

```sh
cd ellipse-collision && bb run   # from this demo (or clojure -M:run)
bb ellipse-collision             # from the repo root
```

## About

raylib [shapes] example - ellipse collision

Two ellipses, one following the mouse. Press A or B to choose which one
you steer; both turn red while they overlap.

The collision test is example-local logic, not a raylib call: the C
source defines CheckCollisionEllipses and CheckCollisionPointEllipse as
static helpers, so they are written here in Clojure rather than bound.

Difficulty: 2/4
Based on: shapes/shapes_ellipse_collision.c
