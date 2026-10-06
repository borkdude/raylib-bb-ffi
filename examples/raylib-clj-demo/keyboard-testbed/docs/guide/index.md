# Keyboard Testbed

On-screen ENG-US keyboard showing what raylib reports per key.

Category: core. Controls: Press any key to light it up, hover to highlight, close via title bar.

Ported from raylib's `examples/core/core_keyboard_testbed.c`.

![keyboard-testbed](../demos/keyboard-testbed.gif)

## Run it

```sh
cd keyboard-testbed && bb run   # from this demo (or clojure -M:run)
bb keyboard-testbed             # from the repo root
```

## About

raylib [core] example - keyboard testbed

An on-screen ENG-US keyboard that lights up as you press keys, useful for
checking what raylib actually reports for a given physical key. Hover a
key to highlight it; every keycode and character raylib sees is logged to
stdout.

raylib's key constants describe an ENG-US layout. On any other physical
layout the keycode still reflects the US position of that key, which is
exactly what this testbed makes visible: press the key right of L on an
AZERTY board and it lights up SEMICOLON.

The C carries a 100-case switch mapping keycode to display label. Here
the layout is a table of [key-name label width] rows and the labels live
in it directly, so a row is the single place a key's code, caption and
width are declared.

Difficulty: 2/4
Based on: core/core_keyboard_testbed.c
