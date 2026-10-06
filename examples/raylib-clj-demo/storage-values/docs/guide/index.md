# Storage Values

Save and load scores to a file.

Category: core. Controls: R random, ENTER save, SPACE load, Q to exit.

Ported from raylib's `examples/core/core_storage_values.c`.

![storage-values](../demos/storage-values.gif)

## Run it

```sh
cd storage-values && bb run   # from this demo (or clojure -M:run)
bb storage-values             # from the repo root
```

## About

raylib [core] example - storage values

Generate two random scores, save them to a file, quit, come back and
load them. R randomises, ENTER saves, SPACE loads.

The C carries 60-odd lines of example-local SaveStorageValue and
LoadStorageValue built on LoadFileData, RL_REALLOC and pointer
arithmetic, growing the file by hand when a position lies past its end.
None of that is raylib API, and none of it is needed here: the file is
just an array of little-endian 32-bit ints, which java.nio reads and
writes directly. Same file format, same storage.data name - a file
written by the C version loads here and vice versa.

Difficulty: 2/4
Based on: core/core_storage_values.c
