# Terrain Generation

Procedural terrain.

Category: models. Controls: 1-3, Arrows, G, W, SPACE, Q.

Written for this collection, not a port of a raylib example.

![terrain-generation](../demos/terrain-generation.gif)

## Run it

```sh
cd terrain-generation && bb run   # from this demo (or clojure -M:run)
bb terrain-generation             # from the repo root
```

## About

Raylib [models] example - procedural terrain generation

Generate and visualize 3D terrain using Perlin-like noise.
Features real-time terrain modification and color mapping.

Complexity: ⭐⭐⭐ (3/4)

Controls:
- LEFT/RIGHT: Adjust frequency (detail level)
- UP/DOWN: Adjust amplitude (height)
- 1/2/3: Preset terrains (hills/mountains/plains)
- G: Toggle grid overlay
- W: Toggle wireframe mode
- SPACE: Regenerate with new seed
- R: Reset
- Q: Exit
