(ns raylib-clj.utils
  "Utility functions (random values, colors, misc)"
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

;; Random value generation
(ffi/defcfn get-random-value
  "Get a random value between min and max (both included)"
  {:arglists '([min max])}
  "GetRandomValue"
  [:int :int] :int)

(ffi/defcfn set-random-seed!
  "Set the seed for the random number generator"
  {:arglists '([seed])}
  "SetRandomSeed"
  [:int] :void)

;; Color utilities
(ffi/defcfn fade
  "Get color with alpha applied, alpha goes from 0.0f to 1.0f.

   This is also raylib's `ColorAlpha`. The two are separate exported
   symbols with byte-identical bodies in `rtextures.c`, so `ColorAlpha`
   is deliberately not bound - a C example calling it ports to `fade`."
  {:arglists '([color alpha])}
  "Fade"
  [rs/color :float] rs/color)

(ffi/defcfn color-to-int
  "Get hexadecimal value for a Color"
  {:arglists '([color])}
  "ColorToInt"
  [rs/color] :int)

(ffi/defcfn color-from-hsv
  "Get a Color from HSV values, hue [0..360], saturation/value [0..1]"
  {:arglists '([hue saturation value])}
  "ColorFromHSV"
  [:float :float :float] rs/color)

;; Moved from raylib-ext (2026-08-22 consolidation)
(ffi/defcfn get-color
  "Get Color structure from hexadecimal value"
  {:arglists '([hex-value])}
  "GetColor"
  [:int] rs/color)

;; Additional shape drawing functions

(ffi/defcfn color-lerp
  "Get color lerp interpolation between two colors"
  {:arglists '([color1 color2 factor])}
  "ColorLerp"
  [rs/color rs/color :float] rs/color)
