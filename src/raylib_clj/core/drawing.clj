(ns raylib-clj.core.drawing
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

(ffi/defcfn clear-background!
  "Set background color (framebuffer clear color)"
  {:arglists '([color])}
  "ClearBackground"
  [rs/color] :void)

(ffi/defcfn begin-drawing!
  "Setup canvas (framebuffer) to start drawing"
  "BeginDrawing"
  [] :void)

(ffi/defcfn end-drawing!
  "End canvas drawing and swap buffers (double buffering)"
  "EndDrawing"
  [] :void)

(ffi/defcfn begin-scissor-mode!
  "Begin scissor mode (define screen area for following drawing)"
  {:arglists '([x y width height])}
  "BeginScissorMode"
  [:int :int :int :int] :void)

(ffi/defcfn end-scissor-mode!
  "End scissor mode"
  "EndScissorMode"
  [] :void)
