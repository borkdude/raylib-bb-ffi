# Basic Lighting

Dynamic lighting.

Category: shaders. Controls: Mouse, Y/R/G/B.

Ported from raylib's `examples/shaders/shaders_basic_lighting.c`.

![basic-lighting](../demos/basic-lighting.gif)

## Run it

```sh
cd basic-lighting && bb run   # from this demo (or clojure -M:run)
bb basic-lighting             # from the repo root
```

It loads `resources/shaders/glsl330/lighting.fs`, `resources/shaders/glsl330/lighting.vs` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

Raylib [shaders] example - basic lighting

Demonstrates shader-based lighting with multiple colored lights.
Based on: raylib/examples/shaders/shaders_basic_lighting.c

Complexity: ⭐⭐⭐⭐ Advanced

Controls:
- Mouse: Orbital camera rotation
- Y: Toggle yellow light
- R: Toggle red light
- G: Toggle green light
- B: Toggle blue light
- F1: Toggle debug stats
- ESC: Exit
