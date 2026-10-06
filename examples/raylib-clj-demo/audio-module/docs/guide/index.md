# Audio Module

Music visualization.

Category: audio. Controls: SPACE, P, Arrows.

Ported from raylib's `examples/audio/audio_module_playing.c`.

![audio-module](../demos/audio-module.gif)

## Run it

```sh
cd audio-module && bb run   # from this demo (or clojure -M:run)
bb audio-module             # from the repo root
```

It loads `resources/mini1111.xm` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [audio] example - module playing

Music visualization with animated circles that react to the music.

Difficulty: ⭐☆☆☆ (1/4)
Based on: audio/audio_module_playing.c
