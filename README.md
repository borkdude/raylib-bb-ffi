# raylib-bb-ffi

[raylib](https://github.com/raysan5/raylib) bindings for
[babashka](https://babashka.org) via [babashka/ffi](https://github.com/babashka/ffi).

Status: experimental. This is an LLM-automated port. Use it at your own risk.
The API is not stable and can change without notice.

## Requirements

- babashka 1.13.225 or newer, or JDK 25 or newer on the JVM
- raylib 6.0 as a shared library: `brew install raylib` on macOS, or the raylib
  package of your Linux distribution

Set `RAYLIB_LIBRARY` to the path of `libraylib` if the library is outside the
system library directories.

## Usage

Add the library to `bb.edn` or `deps.edn`:

```clojure
{:deps {io.github.borkdude/raylib-bb-ffi
        {:git/url "https://github.com/borkdude/raylib-bb-ffi"
         :git/sha "<sha>"
         :deps/root "lib"}}}
```

Require `raylib.all` for every function, or a single module such as
`raylib.shapes`:

```clojure
(require '[raylib.all :as rl])

(rl/window! :width 400 :height 200 :title "hello")
(rl/set-target-fps 60)
(while (not (rl/window-should-close?))
  (rl/begin-drawing)
  (rl/clear-background rl/RAYWHITE)
  (rl/text! "Hello, raylib" :x 120 :y 90 :color rl/DARKGRAY)
  (rl/end-drawing))
(rl/close-window)
```

On the JVM, start with `--enable-native-access=ALL-UNNAMED`. On macOS, also
start with `-XstartOnFirstThread`.

## REPL

macOS opens a window only from the main thread. Start the nREPL server with:

```sh
bb -m raylib.repl
```

Then run window code on the main thread from the editor:

```clojure
(raylib.repl/run! -main)
```

On the JVM, use `clojure -M:repl:mac` in `lib`.

## Development

```sh
bb test        # headless tests in babashka
bb test:jvm    # headless tests on the JVM
bb check       # load every namespace
bb smoke       # open a window and check drawn pixels
bb smoke:jvm
bb lint
bb gen:all     # regenerate lib/src/raylib/all.clj
```

## Credits

This library is a port of [raylib-jlt](https://github.com/jlt-commons/raylib-jlt)
by [Burin Choomnuan](https://github.com/burinc), the raylib bindings for
[jolt](https://github.com/jolt-lang/jolt). The bindings, module layout and
drawing API are his work.
The port started from raylib-jlt commit
[`ffd97a9`](https://github.com/jlt-commons/raylib-jlt/tree/ffd97a91727b02c8f2a5ee2c194bfb64d10c2d4a).
See [NOTICE](NOTICE) for details.

The example programs in
[raylib-jolt-demo](https://github.com/jlt-commons/raylib-jolt-demo) use the
raylib-jlt API.

## License

Eclipse Public License 2.0, same as raylib-jlt. See [LICENSE](LICENSE) and [NOTICE](NOTICE).
