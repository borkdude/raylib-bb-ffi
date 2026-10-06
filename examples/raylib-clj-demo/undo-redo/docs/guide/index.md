# Undo Redo

Ring-buffer undo history on a grid.

Category: core. Controls: Arrows move, SPACE colour, CTRL+Z undo, CTRL+Y redo, Q exit.

Ported from raylib's `examples/core/core_undo_redo.c`.

![undo-redo](../demos/undo-redo.gif)

## Run it

```sh
cd undo-redo && bb run   # from this demo (or clojure -M:run)
bb undo-redo             # from the repo root
```

## About

raylib [core] example - undo and redo

Move a block around a grid with the arrows, recolour it with SPACE, then
walk your edits backwards with CTRL+Z and forwards with CTRL+Y. The strip
along the bottom is the undo buffer itself, drawn slot by slot.

A note on the port, since Clojure would normally reach for something else
here. The obvious idiomatic move is a growing vector of immutable states
plus an index - no wraparound, no bookkeeping. That would be the wrong
call: this example's whole subject is the fixed ring buffer, and the
bottom strip visualises its 26 slots with markers for first, last and
current. Swap in an unbounded vector and the picture stops meaning
anything.

So the ring semantics stay - 26 slots, indices that wrap, the oldest
entry pushed out when the buffer fills. What changes is the mechanism:
states live in an immutable vector and each edit produces a new one,
rather than memcpy into a mutable array.

Difficulty: 3/4
Based on: core/core_undo_redo.c
