# Clipboard Text

Cut, copy and paste against the system clipboard.

Category: core. Controls: Buttons or CTRL+X / CTRL+C / CTRL+V, click the box to edit.

Ported from raylib's `examples/core/core_clipboard_text.c`.

![clipboard-text](../demos/clipboard-text.gif)

## Run it

```sh
cd clipboard-text && bb run   # from this demo (or clojure -M:run)
bb clipboard-text             # from the repo root
```

## About

raylib [core] example - clipboard text

Cut, copy and paste against the system clipboard, by button or by
CTRL+X / CTRL+C / CTRL+V. The lower box is a read-only mirror of what the
clipboard currently holds, so pasting from another application shows up
there too.

First example here using `raygui`'s text box and its icon captions. A
caption like `"#17#CUT"` carries a leading icon id; `net.b12n.raylib-clj.raygui` ports
the five icons these buttons need rather than raygui's full 200+ set.

Two departures from the C, both consequences of not having a mutable
`char *`. Its `inputBuffer` is a 256-byte array that the buttons write
into directly and the text box edits in place; here the text is a value
threaded through state, and the 256 limit is passed to the text box as a
bound rather than being a property of the buffer. And `TextCopy` becomes
ordinary string assignment.

Difficulty: 2/4
Based on: core/core_clipboard_text.c
