# Background Scrolling

Parallax demo.

Category: textures. Controls: Watch.

Ported from raylib's `examples/textures/textures_background_scrolling.c`.

![background-scrolling](../demos/background-scrolling.gif)

## Run it

```sh
cd background-scrolling && bb run   # from this demo (or clojure -M:run)
bb background-scrolling             # from the repo root
```

It loads `resources/cyberpunk_street_background.png`, `resources/cyberpunk_street_foreground.png`, `resources/cyberpunk_street_midground.png` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

Raylib [textures] example - background scrolling

Parallax scrolling effect with three texture layers moving at different speeds.
Creates a cyberpunk street scene with depth perception.
Based on: raylib/examples/textures/textures_background_scrolling.c

Complexity: ⭐ Beginner

Controls:
- F1: Toggle debug stats
- Q: Exit
