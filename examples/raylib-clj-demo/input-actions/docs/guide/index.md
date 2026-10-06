# Input Actions

Remappable action layer over keys and gamepad buttons.

Category: core. Controls: WASD or arrows to move, SPACE to recentre, TAB swaps keyset.

Ported from raylib's `examples/core/core_input_actions.c`.

![input-actions](../demos/input-actions.gif)

## Run it

```sh
cd input-actions && bb run   # from this demo (or clojure -M:run)
bb input-actions             # from the repo root
```

## About

raylib [core] example - input actions

An indirection layer between physical inputs and game actions. Instead of
asking "is LEFT held", the game asks "is the LEFT action active", and a
keyset decides which key and which gamepad button that means. TAB swaps
between two keysets to show the same game reading different hardware.

The C keeps a `MAX_ACTION`-sized array of {key, button} structs plus a
file-scope `gamepadIndex`, and mutates the array in place from two setter
functions. Here a keyset is just a map from action to {:key :button}, and
swapping keysets is picking the other map - so both are visible at once
rather than being two functions that overwrite shared state.

One thing to be careful of, because it fails silently: the keyboard
predicates return real booleans, while the gamepad ones return a byte.
In Clojure 0 is truthy, so using a gamepad result directly would report
every button as permanently held. They go through `pos?` below.

Difficulty: 2/4
Based on: core/core_input_actions.c
