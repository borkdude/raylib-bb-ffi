# Clock Of Clocks

Digits drawn from a grid of analogue clocks.

Category: shapes. Controls: Space to toggle 12/24h, Q to exit.

Ported from raylib's `examples/shapes/shapes_clock_of_clocks.c`.

![clock-of-clocks](../demos/clock-of-clocks.gif)

## Run it

```sh
cd clock-of-clocks && bb run   # from this demo (or clojure -M:run)
bb clock-of-clocks             # from the repo root
```

## About

raylib [shapes] example - clock of clocks

A digital clock whose digits are drawn by an array of little analogue
clocks. Each digit is a 4x6 grid of 24 faces, and the two hands of every
face rotate to positions that together trace the digit's shape. When the
time changes the hands sweep to their new angles over half a second.

The lookup table is the whole design. Each cell holds a [big little]
angle pair chosen from seven named positions - the four corners, a
horizontal, a vertical, and one "parked" position for cells that are
not part of the digit. Reading a digit's row of the table is close to
seeing the digit.

Difficulty: 3/4
Based on: shapes/shapes_clock_of_clocks.c
