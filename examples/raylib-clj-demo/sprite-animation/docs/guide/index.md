# Sprite Animation

Spritesheet.

Category: textures. Controls: LEFT/RIGHT.

Ported from raylib's `examples/textures/textures_sprite_animation.c`.

![sprite-animation](../demos/sprite-animation.gif)

## Run it

```sh
cd sprite-animation && bb run   # from this demo (or clojure -M:run)
bb sprite-animation             # from the repo root
```

It loads `resources/scarfy.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

Raylib [textures] example - sprite animation

Demonstrates sprite sheet animation with configurable frame speed.
Shows how to extract and display individual frames from a sprite sheet.
Based on: raylib/examples/textures/textures_sprite_animation.c

Complexity: ⭐⭐ Easy

Controls:
- LEFT/RIGHT: Adjust animation speed
- F1: Toggle debug stats
- Q: Exit
