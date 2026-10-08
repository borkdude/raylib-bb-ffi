(ns raylib-clj.textures.drawing
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

(ffi/defcfn draw-texture!
  "Draws texture at x y with tint."
  {:arglists '([texture x y tint])}
  "DrawTexture"
  [rs/texture :int :int rs/color] :void)
