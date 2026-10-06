# Music Stream

MP3 streaming.

Category: audio. Controls: SPACE, P, Arrows.

Ported from raylib's `examples/audio/audio_music_stream.c`.

![music-stream](../demos/music-stream.gif)

## Run it

```sh
cd music-stream && bb run   # from this demo (or clojure -M:run)
bb music-stream             # from the repo root
```

It loads `resources/country.mp3` by relative path, so run it from this directory. Those files carry their own terms: see `resources/LICENSE.md`.

## About

raylib [audio] example - music stream

Music streaming with volume and pan control.

Difficulty: ⭐☆☆☆ (1/4)
Based on: audio/audio_music_stream.c
