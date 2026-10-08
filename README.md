# raylib-bb-ffi

[raylib](https://github.com/raysan5/raylib) bindings for
[babashka](https://babashka.org) via [babashka/ffi](https://github.com/babashka/ffi).

Status: experimental. This is an LLM-automated port. Use it at your own risk.
The API is not stable and can change without notice.

This repository is an experiment. It exists only to test whether the examples
of [raylib-jlt](https://github.com/jlt-commons/raylib-jlt) and
[raylib-clj](https://github.com/b12n-oss/raylib-clj) run in babashka and in
Clojure on the JVM through babashka/ffi, each through its own API. That is why
it ships both APIs: `raylib.*` from raylib-jlt and `raylib-clj.*` from
raylib-clj.

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
         :git/sha "<sha>"}}}
```

Require `raylib.all` for every function, or a single module such as
`raylib.shapes`. The `raylib-clj.*` namespaces are a second API, ported from
raylib-clj:

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

On the JVM, use `clojure -M:repl:mac`.

## Examples

[examples/](examples) holds 300 example programs, each a port of a raylib
example or an original. Run one from that directory:

```sh
cd examples
bb run asteroids
```

## Development

```sh
bb test        # headless tests in babashka
bb test:jvm    # headless tests on the JVM
bb check       # load every namespace
bb smoke       # open a window and check drawn pixels
bb smoke:jvm
bb lint
bb gen:all     # regenerate src/raylib/all.clj
```

## Credits

This library is a port of two libraries by
[Burin Choomnuan](https://github.com/burinc):

- [raylib-jlt](https://github.com/jlt-commons/raylib-jlt), the raylib
  bindings for [jolt](https://github.com/jolt-lang/jolt), from commit
  [`ffd97a9`](https://github.com/jlt-commons/raylib-jlt/tree/ffd97a91727b02c8f2a5ee2c194bfb64d10c2d4a).
  It became `raylib.*`.
- [raylib-clj](https://github.com/b12n-oss/raylib-clj), the raylib bindings
  for Clojure through coffi, from commit
  [`1b5f69b`](https://github.com/b12n-oss/raylib-clj/tree/1b5f69b786c1a264430d01abd2cae564d91349bc).
  It became `raylib-clj.*`. raylib-clj began as
  [raylib-clojure-playground](https://github.com/ertugrulcetin/raylib-clojure-playground)
  by [Ertuğrul Çetin](https://github.com/ertugrulcetin).

The bindings, module layout and drawing APIs are their work. The examples
come from [raylib-jolt-demo](https://github.com/jlt-commons/raylib-jolt-demo)
and [raylib-clj-demo](https://github.com/b12n-oss/raylib-clj-demo), also by
Burin Choomnuan. See
[NOTICE](NOTICE) for details.

## License

Eclipse Public License 2.0, same as raylib-jlt and raylib-clj. See [LICENSE](LICENSE) and [NOTICE](NOTICE).
