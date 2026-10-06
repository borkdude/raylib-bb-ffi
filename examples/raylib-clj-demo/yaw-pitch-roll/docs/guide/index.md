# Yaw Pitch Roll

3D rotation demo.

Category: models. Controls: Arrows, SPACE, R, Q.

Ported from raylib's `examples/models/models_yaw_pitch_roll.c`.

![yaw-pitch-roll](../demos/yaw-pitch-roll.gif)

## Run it

```sh
cd yaw-pitch-roll && bb run   # from this demo (or clojure -M:run)
bb yaw-pitch-roll             # from the repo root
```

## About

Raylib [models] example - yaw pitch roll

Demonstrates airplane-style rotation with yaw, pitch, and roll.
Uses a simple wireframe airplane model built from primitives.

Complexity: ⭐⭐ Beginner-Intermediate (2/4)

Controls:
- UP/DOWN: Pitch (nose up/down)
- LEFT/RIGHT: Roll (bank left/right)
- A/S: Yaw (turn left/right)
- R: Reset orientation
- Q: Exit
