# Render Texture

Bouncing ball drawn into an offscreen target.

Category: core. Controls: Q to exit.

Ported from raylib's `examples/core/core_render_texture.c`.

![render-texture](../demos/render-texture.gif)

## Run it

```sh
cd render-texture && bb run   # from this demo (or clojure -M:run)
bb render-texture             # from the repo root
```

## About

raylib [core] example - render texture

A ball bounces inside a 300x300 offscreen target, and that whole target
is then drawn to the window as a single rotating texture. Render textures
are how you get post-processing, minimaps and split-screen: draw a scene
once, then treat the result as an image.

Note the negative source height in the final draw. OpenGL render targets
are stored bottom-up, so sampling with a negative height flips the image
back the right way - without it the ball bounces upside down.

Difficulty: 2/4
Based on: core/core_render_texture.c
