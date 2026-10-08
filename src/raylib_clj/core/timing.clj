(ns raylib-clj.core.timing
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]))

(ffi/defcfn set-target-fps!
  "Set target FPS (maximum)"
  {:arglists '([fps])}
  "SetTargetFPS"
  [:int] :void)

(ffi/defcfn get-fps
  "Get current FPS"
  "GetFPS"
  [] :int)

(ffi/defcfn get-frame-time
  "Get time in seconds for last frame drawn (delta time)"
  "GetFrameTime"
  [] :float)

(ffi/defcfn get-time
  "Get elapsed time in seconds since `init-window!`"
  "GetTime"
  [] :double)
