(ns raylib-clj.core.mouse
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

(ffi/defcfn is-mouse-button-pressed?
  "Check if a mouse button has been pressed once"
  {:arglists '([button])}
  "IsMouseButtonPressed"
  [:int] :bool)

(ffi/defcfn is-mouse-button-down?
  "Check if a mouse button is being pressed"
  {:arglists '([button])}
  "IsMouseButtonDown"
  [:int] :bool)

(ffi/defcfn is-mouse-button-released?
  "Check if a mouse button has been released once"
  {:arglists '([button])}
  "IsMouseButtonReleased"
  [:int] :bool)

(ffi/defcfn is-mouse-button-up?
  "Check if a mouse button is NOT being pressed"
  {:arglists '([button])}
  "IsMouseButtonUp"
  [:int] :bool)

(ffi/defcfn get-mouse-x
  "Get mouse position X"
  "GetMouseX"
  [] :int)

(ffi/defcfn get-mouse-y
  "Get mouse position Y"
  "GetMouseY"
  [] :int)

(ffi/defcfn get-mouse-position
  "Get mouse position XY"
  "GetMousePosition"
  [] rs/vector-2)

(ffi/defcfn get-mouse-delta
  "Get mouse delta between frams"
  "GetMouseDelta"
  [] rs/vector-2)

(ffi/defcfn get-mouse-wheel-move
  "Get mouse wheel movement for X or Y, whichever is larger"
  "GetMouseWheelMove"
  [] :float)

(ffi/defcfn set-mouse-cursor!
  "Set mouse cursor shape"
  {:arglists '([cursor])}
  "SetMouseCursor"
  [:int] :void)

(ffi/defcfn get-mouse-wheel-move-v
  "Get mouse wheel movement for both X and Y"
  "GetMouseWheelMoveV"
  [] rs/vector-2)

; ...
