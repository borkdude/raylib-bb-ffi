# Circle Sector Drawing

Circle sector angles, radius and segments on raygui sliders.

Category: shapes. Controls: Drag the four sliders on the right.

Ported from raylib's `examples/shapes/shapes_circle_sector_drawing.c`.

![circle-sector-drawing](../demos/circle-sector-drawing.gif)

## Run it

```sh
cd circle-sector-drawing && bb run   # from this demo (or clojure -M:run)
bb circle-sector-drawing             # from the repo root
```

## About

raylib [shapes] example - circle sector drawing

A circle sector with its start angle, end angle, radius and segment count
on sliders, showing what each does to the shape. Below a certain segment
count raylib picks the count itself, which is what the MODE readout is
reporting: MANUAL once you ask for at least as many segments as the arc
needs, AUTO while you ask for fewer.

First example here built on `net.b12n.raylib-clj.raygui`, the Clojure port of the
raygui controls. raygui is header-only C compiled into whatever includes
it, so the bundled libraylib exports no `Gui*` symbols and there was
nothing to bind - see that namespace for why the controls return values
instead of writing through pointers.

Difficulty: 2/4
Based on: shapes/shapes_circle_sector_drawing.c
