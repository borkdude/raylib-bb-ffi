(ns raylib-clj.core.keyboard
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]))

(ffi/defcfn is-key-pressed?
  "Check if a key has been pressed once"
  {:arglists '([key])}
  "IsKeyPressed"
  [:int] :bool)

(ffi/defcfn is-key-down?
  "Check if a key is being pressed"
  {:arglists '([key])}
  "IsKeyDown"
  [:int] :bool)

(ffi/defcfn is-key-released?
  "Check if a key has been released once"
  {:arglists '([key])}
  "IsKeyReleased"
  [:int] :bool)

(ffi/defcfn is-key-up?
  "Check if a key is NOT being pressed"
  {:arglists '([key])}
  "IsKeyUp"
  [:int] :bool)

(ffi/defcfn set-exit-key!
  "Set a custom key to exit program (default is ESC)"
  {:arglists '([key])}
  "SetExitKey"
  [:int] :void)

(ffi/defcfn get-key-pressed
  "Get key pressed (keycode), call it multiple times for keys queued, returns 0 when the queue is empty"
  "GetKeyPressed"
  [] :int)

(ffi/defcfn get-char-pressed
  "Get char pressed (unicode), call it multiple times for chars queued, returns 0 when the queue is empty"
  "GetCharPressed"
  [] :int)
