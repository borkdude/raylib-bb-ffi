# Sound Loading

WAV/OGG playback.

Category: audio. Controls: SPACE, ENTER.

Ported from raylib's `examples/audio/audio_sound_loading.c`.

![sound-loading](../demos/sound-loading.gif)

## Run it

```sh
cd sound-loading && bb run   # from this demo (or clojure -M:run)
bb sound-loading             # from the repo root
```

It loads `resources/sound.wav`, `resources/target.ogg` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [audio] example - sound loading

Basic sound loading and playback with WAV and OGG files.

Difficulty: ⭐☆☆☆ (1/4)
Based on: audio/audio_sound_loading.c
