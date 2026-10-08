(ns raylib.shapes
  "2D shapes: pixels, lines, rectangles, circles and ellipses, gradients,
  rotated and rounded rectangles, and blend modes."
  (:require
   [babashka.ffi :as ffi]
   [raylib.color :as color]
   [raylib.native :as native]))

(ffi/defcfn draw-pixel            "DrawPixel"              [:int :int :uint] :void)
(ffi/defcfn draw-line             "DrawLine"               [:int :int :int :int :uint] :void)
(ffi/defcfn draw-rectangle        "DrawRectangle"          [:int :int :int :int :uint] :void)
(ffi/defcfn draw-rectangle-lines  "DrawRectangleLines"     [:int :int :int :int :uint] :void)
(ffi/defcfn draw-rectangle-grad-v "DrawRectangleGradientV" [:int :int :int :int :uint :uint] :void)
(ffi/defcfn draw-rectangle-grad-h "DrawRectangleGradientH" [:int :int :int :int :uint :uint] :void)
(ffi/defcfn draw-circle           "DrawCircle"             [:int :int :float :uint] :void)
(ffi/defcfn draw-circle-lines     "DrawCircleLines"        [:int :int :float :uint] :void)
(ffi/defcfn draw-ellipse          "DrawEllipse"            [:int :int :float :float :uint] :void)
(ffi/defcfn begin-blend-mode      "BeginBlendMode"         [:int] :void)
(ffi/defcfn end-blend-mode        "EndBlendMode"           [] :void)

(ffi/defcfn ^:private draw-circle-gradient-raw "DrawCircleGradient"
  [native/vector2-layout :float :uint :uint] :void)
(ffi/defcfn ^:private draw-rectangle-pro-raw "DrawRectanglePro"
  [native/rectangle-layout native/vector2-layout :float :uint] :void)
(ffi/defcfn ^:private draw-rectangle-lines-ex-raw "DrawRectangleLinesEx"
  [native/rectangle-layout :float :uint] :void)
(ffi/defcfn ^:private draw-rectangle-rounded-raw "DrawRectangleRounded"
  [native/rectangle-layout :float :int :uint] :void)
(ffi/defcfn ^:private draw-rectangle-rounded-lines-ex-raw "DrawRectangleRoundedLinesEx"
  [native/rectangle-layout :float :int :float :uint] :void)

(def ^:const BLEND-ALPHA 0)      (def ^:const BLEND-ADDITIVE 1)
(def ^:const BLEND-MULTIPLIED 2) (def ^:const BLEND-ADD-COLORS 3)
(def ^:const BLEND-SUBTRACT-COLORS 4)
(def ^:const BLEND-CUSTOM 6)

;; Takes GL enums. Set the factors before begin-blend-mode with BLEND-CUSTOM.
(ffi/defcfn set-blend-factors "rlSetBlendFactors" [:int :int :int] :void)
(def ^:const GL-SRC-ALPHA 0x0302)
(def ^:const GL-MIN 0x8007)      (def ^:const GL-MAX 0x8008)

(defn circle-gradient!
  "Draws a gradient-filled circle. :x :y :radius :inner :outer."
  [& {:keys [x y radius inner outer]
      :or {x 0
           y 0
           radius 10
           inner color/WHITE
           outer color/BLACK}}]
  (draw-circle-gradient-raw (native/vector2 [x y]) radius inner outer))

(defn rect-pro!
  "Draws a rectangle rotated about its origin.
  :x :y place the origin, and the rectangle is offset by :origin-x :origin-y
  from that point.
  :rotation is in degrees, clockwise.
  :width :height :color."
  [& {:keys [x y width height origin-x origin-y rotation color]
      :or {x 0
           y 0
           width 10
           height 10
           origin-x 0
           origin-y 0
           rotation 0
           color color/BLACK}}]
  (draw-rectangle-pro-raw (native/rectangle [x y width height])
                          (native/vector2 [origin-x origin-y])
                          rotation color))

(defn rect-gradient-h!
  "Draws a rectangle with a gradient from left to right.
  :x :y :width :height :left :right."
  [& {:keys [x y width height left right]
      :or {x 0
           y 0
           width 10
           height 10
           left color/WHITE
           right color/BLACK}}]
  (draw-rectangle-grad-h x y width height left right))

(defn rect-lines-ex!
  "Draws a rectangle outline :thick pixels wide, inward from the edge.
  Draws nothing if :thick is 0 or less.
  :x :y :width :height :thick :color."
  [& {:keys [x y width height thick color]
      :or {x 0
           y 0
           width 10
           height 10
           thick 1.0
           color color/BLACK}}]
  (draw-rectangle-lines-ex-raw (native/rectangle [x y width height]) thick color))

(defn rect-rounded!
  "Draws a filled rectangle with rounded corners.
  :roundness runs from 0.0, square, to 1.0, a corner radius of half the
  shorter side.
  :segments is the number of triangles per corner.
  :x :y :width :height :color."
  [& {:keys [x y width height roundness segments color]
      :or {x 0
           y 0
           width 10
           height 10
           roundness 0.2
           segments 9
           color color/BLACK}}]
  (draw-rectangle-rounded-raw (native/rectangle [x y width height])
                              roundness segments color))

(defn rect-rounded-lines-ex!
  "Draws the outline of a rounded rectangle :thick pixels wide, inward from the
  edge.
  :x :y :width :height :roundness :segments :thick :color."
  [& {:keys [x y width height roundness segments thick color]
      :or {x 0
           y 0
           width 10
           height 10
           roundness 0.2
           segments 9
           thick 1.0
           color color/BLACK}}]
  (draw-rectangle-rounded-lines-ex-raw (native/rectangle [x y width height])
                                       roundness segments thick color))
