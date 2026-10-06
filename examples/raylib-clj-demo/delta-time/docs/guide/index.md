# Delta Time

Frame-rate independent vs fixed-step motion.

Category: core. Controls: Wheel to change FPS, R to reset, Q to exit.

Ported from raylib's `examples/core/core_delta_time.c`.

![delta-time](../demos/delta-time.gif)

## Run it

```sh
cd delta-time && bb run   # from this demo (or clojure -M:run)
bb delta-time             # from the repo root
```

## About

raylib [core] example - delta time

Two circles crossing the screen. The red one advances by
(get-frame-time) * speed, the blue one by a fixed step per frame. Change
the FPS target with the scroll wheel and the difference is the whole
lesson: the red circle keeps its real-world speed, the blue one speeds
up or slows down with the frame rate.

Difficulty: 1/4
Based on: core/core_delta_time.c
