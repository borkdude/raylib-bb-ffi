# raylib-clj-demo

113 [raylib](https://github.com/raysan5/raylib) examples in Clojure, each one
its own small project with a `deps.edn`, a `bb.edn` and one source file, so you
can read one, copy one out or run one without the other 112 getting in the way.
They all call raylib through [raylib-clj](https://github.com/b12n-oss/raylib-clj),
which binds it over coffi and Java's foreign-function API. The demos fetch it
as a git dependency pinned in `common/deps.edn`.

The [gallery](demos.md) shows every demo with its recording.

## Getting it running

You need a JDK 22 or newer, the Clojure CLI, babashka and raylib 6.0. The
demos are macOS-first.

```sh
brew install raylib            # or your distro's raylib package
git clone https://github.com/b12n-oss/raylib-clj-demo
cd raylib-clj-demo
bb doctor                      # checks all of the above
```

Then pick a demo:

```sh
bb asteroids                   # from the repo root
cd asteroids && bb run         # from inside the demo
```

`bb info` prints every task and every demo, grouped by category. `bb check`
compiles all 113 without opening a window, and `bb run-all 4` runs each one
for four seconds.

## How a demo is laid out

```
background-scrolling/
  deps.edn                       depends on ../common
  bb.edn                         the `run` task
  src/net/b12n/raylib_clj/scenes/background_scrolling.clj
  resources/                     the images it loads, and their LICENSE.md
  docs/guide/index.md            what it shows, its controls, where it was ported from
  docs/demos/background-scrolling.gif
```

A demo runs from its own directory, which is where `resources/` resolves.

## Where the examples came from

They were split out of raylib-clj, which now holds only the bindings. Most are
ports of raylib's own example programs, three port raysan5/raylib-games, and
each demo's page says which. The rest were written for raylib-clj, six of
them first by Ertuğrul Çetin in raylib-clojure-playground, where the project
began. The
[NOTICE](https://github.com/b12n-oss/raylib-clj-demo/blob/main/NOTICE) file
carries the licence terms of each, including the media files.
