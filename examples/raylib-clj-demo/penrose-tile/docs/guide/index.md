# Penrose Tile

L-system Penrose tiling drawn by turtle.

Category: shapes. Controls: UP/DOWN change generations, Q to exit.

Ported from raylib's `examples/shapes/shapes_penrose_tile.c`.

![penrose-tile](../demos/penrose-tile.gif)

## Run it

```sh
cd penrose-tile && bb run   # from this demo (or clojure -M:run)
bb penrose-tile             # from the repo root
```

## About

raylib [shapes] example - penrose tile

A Penrose tiling grown from an L-system and drawn with turtle graphics.
UP and DOWN change the generation count; each generation rewrites the
production string through four rules and halves the draw length, so the
figure gains detail without changing size.

The alphabet the turtle reads:

  F        move forward, drawing
  + / -    turn by +/- 36 degrees
  [ / ]    push / pop position and heading
  0-9      repeat the NEXT command that many times
  W X Y Z  rewrite-only symbols, never drawn

Two things carried over deliberately from the C:

- Rewriting DROPS any existing F rather than copying it. The rules
  reintroduce their own, so an F is always one generation old. Copying
  it instead doubles the line count each pass and the tiling degenerates.
- Drawing reveals 12 more symbols per frame rather than the whole
  production at once, which is what makes the figure draw itself. The C
  advances that counter inside its draw function; here it moves with the
  rest of the state in tick, since a draw that mutates is a trap for the
  next reader.

Difficulty: 3/4
Based on: shapes/shapes_penrose_tile.c
