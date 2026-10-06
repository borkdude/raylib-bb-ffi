# Logo Raylib Anim

Animated logo construction.

Category: shapes. Controls: R to replay.

Ported from raylib's `examples/shapes/shapes_logo_raylib_anim.c`.

![logo-raylib-anim](../demos/logo-raylib-anim.gif)

## Run it

```sh
cd logo-raylib-anim && bb run   # from this demo (or clojure -M:run)
bb logo-raylib-anim             # from the repo root
```

## About

raylib [shapes] example - logo raylib animation

Animated raylib logo construction using a state machine:
0) Blinking small square
1) Top and left bars grow
2) Bottom and right bars grow
3) Letters appear one by one, then fade out
4) Press R to replay

Difficulty: 2/4
Based on: shapes/shapes_logo_raylib_anim.c
