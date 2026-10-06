# Window Letterbox

Resolution-independent rendering.

Category: core. Controls: SPACE, Resize window.

Ported from raylib's `examples/core/core_window_letterbox.c`.

![window-letterbox](../demos/window-letterbox.gif)

## Run it

```sh
cd window-letterbox && bb run   # from this demo (or clojure -M:run)
bb window-letterbox             # from the repo root
```

## About

raylib [core] example - window letterbox

Demonstrates resolution-independent rendering using a render texture.
Resize the window and the game content scales with letterboxing.

Difficulty: 2/4
Based on: core/core_window_letterbox.c
