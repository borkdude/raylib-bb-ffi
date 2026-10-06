# Input Multitouch

Numbered circle per touch point.

Category: core. Controls: Touch/click, Q to exit.

Ported from raylib's `examples/core/core_input_multitouch.c`.

![input-multitouch](../demos/input-multitouch.gif)

## Run it

```sh
cd input-multitouch && bb run   # from this demo (or clojure -M:run)
bb input-multitouch             # from the repo root
```

## About

raylib [core] example - input multitouch

Draws a numbered circle under every active touch point. On a desktop
without a touchscreen the mouse registers as touch point 0, so one
circle follows the cursor while a button is held.

Difficulty: 1/4
Based on: core/core_input_multitouch.c
