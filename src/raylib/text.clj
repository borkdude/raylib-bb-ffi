(ns raylib.text
  "DrawText, DrawFPS and MeasureText with raylib's default font."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native]))

(ffi/defcfn draw-text    "DrawText"    [:string :int :int :int :uint] :void)
(ffi/defcfn draw-fps     "DrawFPS"     [:int :int] :void)
(ffi/defcfn measure-text "MeasureText" [:string :int] :int)
