# Gestures Testbed

Touch gestures.

Category: core. Controls: Touch/click.

Ported from raylib's `examples/core/core_input_gestures_testbed.c`.

![gestures-testbed](../demos/gestures-testbed.gif)

## Run it

```sh
cd gestures-testbed && bb run   # from this demo (or clojure -M:run)
bb gestures-testbed             # from the repo root
```

## About

Raylib [core] example - input gestures testbed

Visualizes touch gestures with an interactive testbed.
Shows gesture log, protractor angle display, and touch point visualization.
Based on: raylib/examples/core/core_input_gestures_testbed.c

Complexity: ⭐⭐⭐ Intermediate

Note: Optimized for touch screens. On desktop, use mouse to simulate gestures.
In web browsers, enable Touch Emulation in developer tools.

Controls:
- Touch/Mouse: Perform gestures
- Click 'Hide Repeat' button: Toggle repeated gesture logging
- Click 'Hide Hold' button: Toggle hold gesture logging
- F1: Toggle debug stats
- Q: Exit
