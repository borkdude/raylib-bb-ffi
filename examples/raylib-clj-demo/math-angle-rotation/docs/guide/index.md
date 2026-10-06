# Math Angle Rotation

Fixed and sweeping angle lines.

Category: shapes. Controls: Q to exit.

Ported from raylib's `examples/shapes/shapes_math_angle_rotation.c`.

![math-angle-rotation](../demos/math-angle-rotation.gif)

## Run it

```sh
cd math-angle-rotation && bb run   # from this demo (or clojure -M:run)
bb math-angle-rotation             # from the repo root
```

## About

raylib [shapes] example - math angle rotation

Four fixed lines at 0/30/60/90 degrees, each labelled, plus one line
sweeping a full turn at one degree per frame. The sweeping line takes
its colour from its own angle through HSV, so hue and heading stay in
step.

Difficulty: 1/4
Based on: shapes/shapes_math_angle_rotation.c
