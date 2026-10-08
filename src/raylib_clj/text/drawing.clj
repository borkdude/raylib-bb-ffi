(ns raylib-clj.text.drawing
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

(ffi/defcfn draw-fps!
  "Draw current FPS"
  {:arglists '([x y])}
  "DrawFPS"
  [:int :int] :void)

(ffi/defcfn draw-text!
  "Draw text (using default font)"
  {:arglists '([text x y size color])}
  "DrawText"
  [:string :int :int :int rs/color] :void)

(defmacro draw-text [{:keys [text x y size color]}]
  `(draw-text! ~text ~x ~y ~size ~color))

;; Moved from raylib-ext (2026-08-22 consolidation)
(ffi/defcfn measure-text
  "Measure string width for default font"
  {:arglists '([text font-size])}
  "MeasureText"
  [:string :int] :int)

