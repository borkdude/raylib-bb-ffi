(ns raylib.splines
  "Spline segments: linear, B-spline, Catmull-Rom and cubic Bezier.
  Points are [x y] vectors."
  (:require
   [babashka.ffi :as ffi]
   [raylib.color :as color]
   [raylib.native :as native]))

(def ^:private v2 native/vector2-layout)

(ffi/defcfn ^:private draw-spline-segment-linear-raw "DrawSplineSegmentLinear"
  [v2 v2 :float :uint] :void)
(ffi/defcfn ^:private draw-spline-segment-basis-raw "DrawSplineSegmentBasis"
  [v2 v2 v2 v2 :float :uint] :void)
(ffi/defcfn ^:private draw-spline-segment-catmullrom-raw "DrawSplineSegmentCatmullRom"
  [v2 v2 v2 v2 :float :uint] :void)
(ffi/defcfn ^:private draw-spline-segment-bezier-cubic-raw "DrawSplineSegmentBezierCubic"
  [v2 v2 v2 v2 :float :uint] :void)

(defn spline-segment-linear!
  "Draws a line segment from :p1 to :p2. :thick :color."
  [& {:keys [p1 p2 thick color]
      :or {p1 [0.0 0.0]
           p2 [0.0 0.0]
           thick 1.0
           color color/BLACK}}]
  (draw-spline-segment-linear-raw (native/vector2 p1) (native/vector2 p2)
                                  thick color))

(defn spline-segment-basis!
  "Draws a B-spline segment over the control points :p1 :p2 :p3 :p4.
  :thick :color."
  [& {:keys [p1 p2 p3 p4 thick color]
      :or {p1 [0.0 0.0]
           p2 [0.0 0.0]
           p3 [0.0 0.0]
           p4 [0.0 0.0]
           thick 1.0
           color color/BLACK}}]
  (draw-spline-segment-basis-raw (native/vector2 p1) (native/vector2 p2)
                                 (native/vector2 p3) (native/vector2 p4)
                                 thick color))

(defn spline-segment-catmull-rom!
  "Draws a Catmull-Rom segment through :p2 and :p3, shaped by :p1 and :p4.
  :thick :color."
  [& {:keys [p1 p2 p3 p4 thick color]
      :or {p1 [0.0 0.0]
           p2 [0.0 0.0]
           p3 [0.0 0.0]
           p4 [0.0 0.0]
           thick 1.0
           color color/BLACK}}]
  (draw-spline-segment-catmullrom-raw (native/vector2 p1) (native/vector2 p2)
                                      (native/vector2 p3) (native/vector2 p4)
                                      thick color))

(defn spline-segment-bezier-cubic!
  "Draws a cubic Bezier segment from :p1 to :p4 with the control points :c2
  and :c3. :thick :color."
  [& {:keys [p1 c2 c3 p4 thick color]
      :or {p1 [0.0 0.0]
           c2 [0.0 0.0]
           c3 [0.0 0.0]
           p4 [0.0 0.0]
           thick 1.0
           color color/BLACK}}]
  (draw-spline-segment-bezier-cubic-raw (native/vector2 p1) (native/vector2 c2)
                                        (native/vector2 c3) (native/vector2 p4)
                                        thick color))
