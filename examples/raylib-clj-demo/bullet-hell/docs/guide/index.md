# Bullet Hell

Throughput test firing rows of bullets from a rotating circle.

Category: shapes. Controls: RIGHT/LEFT rows, UP/DOWN speed, Z/X cooldown, SPACE angle, ENTER draw method, C clear.

Ported from raylib's `examples/shapes/shapes_bullet_hell.c`.

![bullet-hell](../demos/bullet-hell.gif)

## Run it

```sh
cd bullet-hell && bb run   # from this demo (or clojure -M:run)
bb bullet-hell             # from the repo root
```

## About

raylib [shapes] example - bullet hell

A rotating magic circle firing rows of bullets outward, as a throughput
test. ENTER switches between drawing each bullet as a pre-rendered
texture and drawing it as two circles, and the FPS readout shows what
that costs.

One deliberate departure from the C. It preallocates 500,000 bullet
slots and never removes anything: an off-screen bullet gets a `disabled`
flag and stays in the array, because compacting a C array every frame
would cost more than skipping dead entries. When the slot count is
finally exhausted the whole array resets and every bullet on screen
vanishes at once.

Here off-screen bullets are simply dropped. That is not a shortcut - the
flag exists to avoid a cost Clojure does not pay, and dropping them
removes the periodic mass-vanish, which was an artifact of the array
rather than anything the example set out to show. The cap remains as a
safety limit, now on live bullets rather than on total ever spawned.

Difficulty: 3/4
Based on: shapes/shapes_bullet_hell.c
