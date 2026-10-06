# Window Should Close

Custom close confirmation.

Category: core. Controls: Y/N to confirm/cancel.

Ported from raylib's `examples/core/core_window_should_close.c`.

![window-should-close](../demos/window-should-close.gif)

## Run it

```sh
cd window-should-close && bb run   # from this demo (or clojure -M:run)
bb window-should-close             # from the repo root
```

## About

raylib [core] example - window should close

Demonstrates custom window close handling. ESC and X-button
trigger a confirmation dialog instead of immediately closing.
Press Y to confirm exit, N to cancel.

Difficulty: 1/4
Based on: core/core_window_should_close.c
