(ns raylib-clj.core.cursor
  "Cursor visibility and lock state.

   Mirrors raylib.h's own \"Cursor-related functions\" section, which sits
   between the window and drawing groups. Three of these previously lived
   in `raylib-clj.core.camera3d`, where a first-person example had needed them
   first; they are not camera functions."
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]))

(ffi/defcfn show-cursor!
  "Show cursor"
  "ShowCursor"
  [] :void)

(ffi/defcfn hide-cursor!
  "Hide cursor"
  "HideCursor"
  [] :void)

(ffi/defcfn is-cursor-hidden?
  "Check if cursor is not visible"
  "IsCursorHidden"
  [] :bool)

(ffi/defcfn enable-cursor!
  "Enable cursor (unlock cursor)"
  "EnableCursor"
  [] :void)

(ffi/defcfn disable-cursor!
  "Disable cursor (lock cursor)"
  "DisableCursor"
  [] :void)

(ffi/defcfn is-cursor-on-screen?
  "Check if cursor is on the screen"
  "IsCursorOnScreen"
  [] :bool)
