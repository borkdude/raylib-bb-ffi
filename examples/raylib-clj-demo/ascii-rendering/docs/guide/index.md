# Ascii Rendering

Post-process the scene into ASCII glyphs via a render texture.

Category: shaders. Controls: LEFT/RIGHT to change the glyph cell size.

Ported from raylib's `examples/shaders/shaders_ascii_rendering.c`.

![ascii-rendering](../demos/ascii-rendering.gif)

## Run it

```sh
cd ascii-rendering && bb run   # from this demo (or clojure -M:run)
bb ascii-rendering             # from the repo root
```

It loads `resources/fudesumi.png`, `resources/raysan.png`, `resources/shaders/glsl330/ascii.fs` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [shaders] example - ascii rendering

A post-processing effect: the scene is drawn into an offscreen render
texture, then that whole texture is drawn once through a fragment shader
that replaces each cell with an ASCII glyph. Left and right change the
cell size, which is the shader's `fontSize` uniform.

Two details worth pointing at.

The source rectangle passed when blitting the render texture has a
NEGATIVE height. That is not a typo: OpenGL render targets are stored
bottom-up, and flipping the source rect is the standard way to draw one
the right way up.

And nothing here draws a glyph. The scene is two ordinary textures; every
character on screen is produced by the shader sampling the render texture
per cell and picking a glyph by brightness.

Difficulty: 3/4
Based on: shaders/shaders_ascii_rendering.c
