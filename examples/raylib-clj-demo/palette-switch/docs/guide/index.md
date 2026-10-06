# Palette Switch

Fragment shader remaps index-encoded bands through a palette.

Category: shaders. Controls: LEFT/RIGHT to switch palette.

Ported from raylib's `examples/shaders/shaders_palette_switch.c`.

![palette-switch](../demos/palette-switch.gif)

## Run it

```sh
cd palette-switch && bb run   # from this demo (or clojure -M:run)
bb palette-switch             # from the repo root
```

It loads `resources/shaders/glsl330/palette_switch.fs` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [shaders] example - palette switch

Eight horizontal bands drawn in near-black greys - RGB (0,0,0) through
(7,7,7) - which a fragment shader then remaps through a palette. The
colour you see is never drawn by this program: the pixel's red channel
carries an INDEX, and the shader looks it up. Left and right swap between
a 3-bit RGB palette, a GameBoy-like AMMO-8, and a two-strip film RKBV.

This is the example that exercises
`net.b12n.raylib-clj.core.shaders/set-shader-value-ints!`. Its `count` argument is the
number of GROUPS rather than of ints - 24 ints as IVEC3 is 8 colours - and
getting that wrong reads past the end of the buffer, so the helper derives
it from the uniform type rather than taking it on trust.

Difficulty: 3/4
Based on: shaders/shaders_palette_switch.c
