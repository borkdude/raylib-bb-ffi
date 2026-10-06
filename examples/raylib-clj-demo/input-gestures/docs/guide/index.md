# Input Gestures

Log of detected touch gestures.

Category: core. Controls: Click/drag/flick in the test area, Q to exit.

Ported from raylib's `examples/core/core_input_gestures.c`.

![input-gestures](../demos/input-gestures.gif)

## Run it

```sh
cd input-gestures && bb run   # from this demo (or clojure -M:run)
bb input-gestures             # from the repo root
```

## About

raylib [core] example - input gestures

Perform gestures inside the test area and each newly detected one is
appended to a log on the left. Only transitions are recorded, so holding
a drag logs DRAG once rather than sixty times a second.

On desktop the mouse stands in for a finger: click for TAP, click twice
for DOUBLETAP, press and wait for HOLD, press and move for DRAG, and
flick for the SWIPE gestures.

Difficulty: 2/4
Based on: core/core_input_gestures.c
