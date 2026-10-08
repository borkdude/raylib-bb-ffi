(ns raylib-clj.shapes.basic
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

; ...

(ffi/defcfn draw-circle-v!
  "Draw a color-filled circle (Vector version)"
  {:arglists '([center radius color])}
  "DrawCircleV"
  [rs/vector-2 :float rs/color] :void)

; ...

(ffi/defcfn draw-rectangle!
  "Draw a color-filled rectangle"
  {:arglists '([x y width height color])}
  "DrawRectangle"
  [:int :int :int :int rs/color] :void)

(ffi/defcfn draw-rectangle-lines!
  "Draw rectangle outline"
  {:arglists '([x y width height color])}
  "DrawRectangleLines"
  [:int :int :int :int rs/color] :void)

(ffi/defcfn draw-rectangle-rec!
  "Draw a color-filled rectangle from Rectangle struct"
  {:arglists '([rec color])}
  "DrawRectangleRec"
  [rs/rectangle rs/color] :void)

(ffi/defcfn draw-rectangle-lines-ex!
  "Draw rectangle outline with extended parameters"
  {:arglists '([rec line-thick color])}
  "DrawRectangleLinesEx"
  [rs/rectangle :float rs/color] :void)

(ffi/defcfn draw-line!
  "Draw a line"
  {:arglists '([start-x start-y end-x end-y color])}
  "DrawLine"
  [:int :int :int :int rs/color] :void)

;; #region draw-circle-binding
(ffi/defcfn draw-circle!
  "Draw a color-filled circle"
  {:arglists '([center-x center-y radius color])}
  "DrawCircle"
  [:int :int :float rs/color] :void)
;; #endregion

(ffi/defcfn draw-circle-gradient!
  "Draw a gradient-filled circle.

   NOTE: raylib 6.0 changed this signature. It took (int centerX, int centerY,
   float radius, ...) up to 5.5 and takes a Vector2 centre from 6.0 onward.
   The exported symbol name did not change, so a symbol-resolution check
   cannot catch this - only a signature diff can."
  {:arglists '([center radius inner-color outer-color])}
  "DrawCircleGradient"
  [rs/vector-2 :float rs/color rs/color] :void)

(ffi/defcfn draw-circle-lines!
  "Draw circle outline"
  {:arglists '([center-x center-y radius color])}
  "DrawCircleLines"
  [:int :int :float rs/color] :void)

(ffi/defcfn draw-ellipse!
  "Draw ellipse"
  {:arglists '([center-x center-y radius-h radius-v color])}
  "DrawEllipse"
  [:int :int :float :float rs/color] :void)

(ffi/defcfn draw-ellipse-lines!
  "Draw ellipse outline"
  {:arglists '([center-x center-y radius-h radius-v color])}
  "DrawEllipseLines"
  [:int :int :float :float rs/color] :void)

(ffi/defcfn draw-rectangle-gradient-h!
  "Draw a horizontal-gradient-filled rectangle"
  {:arglists '([x y width height left-color right-color])}
  "DrawRectangleGradientH"
  [:int :int :int :int rs/color rs/color] :void)

(ffi/defcfn draw-triangle!
  "Draw a color-filled triangle (vertex in counter-clockwise order!)"
  {:arglists '([v1 v2 v3 color])}
  "DrawTriangle"
  [rs/vector-2 rs/vector-2 rs/vector-2 rs/color] :void)

(ffi/defcfn draw-triangle-lines!
  "Draw triangle outline (vertex in counter-clockwise order!)"
  {:arglists '([v1 v2 v3 color])}
  "DrawTriangleLines"
  [rs/vector-2 rs/vector-2 rs/vector-2 rs/color] :void)

(ffi/defcfn draw-poly!
  "Draw a regular polygon (Vector version)"
  {:arglists '([center sides radius rotation color])}
  "DrawPoly"
  [rs/vector-2 :int :float :float rs/color] :void)

(ffi/defcfn draw-poly-lines!
  "Draw a polygon outline of n sides"
  {:arglists '([center sides radius rotation color])}
  "DrawPolyLines"
  [rs/vector-2 :int :float :float rs/color] :void)

(ffi/defcfn draw-poly-lines-ex!
  "Draw a polygon outline of n sides with extended parameters"
  {:arglists '([center sides radius rotation line-thick color])}
  "DrawPolyLinesEx"
  [rs/vector-2 :int :float :float :float rs/color] :void)

(ffi/defcfn draw-line-bezier!
  "Draw line segment cubic-bezier in-out interpolation"
  {:arglists '([start-pos end-pos thick color])}
  "DrawLineBezier"
  [rs/vector-2 rs/vector-2 :float rs/color] :void)

(ffi/defcfn draw-circle-lines-v!
  "Draw circle outline (Vector version)"
  {:arglists '([center radius color])}
  "DrawCircleLinesV"
  [rs/vector-2 :float rs/color] :void)

;; Moved from raylib-ext (2026-08-22 consolidation)
(ffi/defcfn draw-rectangle-rounded!
  "Draw rectangle with rounded edges"
  {:arglists '([rec roundness segments color])}
  "DrawRectangleRounded"
  [rs/rectangle :float :int rs/color] :void)

(ffi/defcfn draw-rectangle-rounded-lines!
  "Draw rectangle lines with rounded edges"
  {:arglists '([rec roundness segments color])}
  "DrawRectangleRoundedLines"
  [rs/rectangle :float :int rs/color] :void)

(ffi/defcfn draw-rectangle-rounded-lines-ex!
  "Draw rectangle with rounded edges outline"
  {:arglists '([rec roundness segments line-thick color])}
  "DrawRectangleRoundedLinesEx"
  [rs/rectangle :float :int :float rs/color] :void)

(ffi/defcfn draw-line-ex!
  "Draw a line with thickness"
  {:arglists '([start-pos end-pos thick color])}
  "DrawLineEx"
  [rs/vector-2 rs/vector-2 :float rs/color] :void)

(ffi/defcfn draw-ring!
  "Draw ring"
  {:arglists '([center inner-radius outer-radius start-angle end-angle segments color])}
  "DrawRing"
  [rs/vector-2 :float :float :float :float :int rs/color] :void)

(ffi/defcfn draw-ring-lines!
  "Draw ring outline"
  {:arglists '([center inner-radius outer-radius start-angle end-angle segments color])}
  "DrawRingLines"
  [rs/vector-2 :float :float :float :float :int rs/color] :void)

(ffi/defcfn draw-circle-sector!
  "Draw a piece of a circle"
  {:arglists '([center radius start-angle end-angle segments color])}
  "DrawCircleSector"
  [rs/vector-2 :float :float :float :int rs/color] :void)

(ffi/defcfn draw-circle-sector-lines!
  "Draw circle sector outline"
  {:arglists '([center radius start-angle end-angle segments color])}
  "DrawCircleSectorLines"
  [rs/vector-2 :float :float :float :int rs/color] :void)

(ffi/defcfn draw-rectangle-v!
  "Draw a color-filled rectangle (Vector version)"
  {:arglists '([position size color])}
  "DrawRectangleV"
  [rs/vector-2 rs/vector-2 rs/color] :void)

(ffi/defcfn draw-line-v!
  "Draw a line (Vector version)"
  {:arglists '([start-pos end-pos color])}
  "DrawLineV"
  [rs/vector-2 rs/vector-2 rs/color] :void)

(ffi/defcfn draw-rectangle-pro!
  "Draw a color-filled rectangle with pro parameters"
  {:arglists '([rec origin rotation color])}
  "DrawRectanglePro"
  [rs/rectangle rs/vector-2 :float rs/color] :void)

;; A Clojure port of DrawLineDashed from rshapes.c, with an extra
;; 6-argument form that takes a line thickness.

(defn draw-dashed-line!
  "Draw a dashed line from `start-pos` to `end-pos`.

   Mirrors raylib's own `DrawLineDashed`, including its fallback to a solid
   line when the span is shorter than one dash plus one gap, or when
   `dash-size` is not positive."
  ([start-pos end-pos dash-size space-size color]
   (draw-dashed-line! start-pos end-pos dash-size space-size color 1.0))
  ([start-pos end-pos dash-size space-size color thickness]
   (let [dx (- (:x end-pos) (:x start-pos))
         dy (- (:y end-pos) (:y start-pos))
         line-length (Math/sqrt (+ (* dx dx) (* dy dy)))]
     (if (or (< line-length (+ dash-size space-size)) (<= dash-size 0))
       (draw-line-ex! {:x (float (:x start-pos))
                       :y (float (:y start-pos))}
                      {:x (float (:x end-pos))
                       :y (float (:y end-pos))}
                      (float thickness) color)
       (let [dir-x (/ dx line-length)
             dir-y (/ dy line-length)
             stride (+ dash-size space-size)]
         (loop [travelled 0.0]
           (when (< travelled line-length)
             (let [dash-end (min (+ travelled dash-size) line-length)]
               (draw-line-ex!
                {:x (float (+ (:x start-pos) (* dir-x travelled)))
                 :y (float (+ (:y start-pos) (* dir-y travelled)))}
                {:x (float (+ (:x start-pos) (* dir-x dash-end)))
                 :y (float (+ (:y start-pos) (* dir-y dash-end)))}
                (float thickness) color)
               (recur (+ travelled stride))))))))))

(ffi/defcfn draw-spline-linear-raw!
  "Draw spline: Linear, minimum 2 points (internal - takes a Vector2 array)"
  {:arglists '([points point-count thick color])}
  "DrawSplineLinear"
  [:pointer :int :float rs/color] :void)

(defn draw-spline-linear!
  "Draws a linear spline through points, a seq of {:x :y} maps.
  Needs at least two points. Fewer is a no-op."
  [points thick color]
  (let [pts (vec points)
        n (count pts)]
    (when (>= n 2)
      (with-open [arena (ffi/confined-arena)]
        (let [t [:array rs/vector-2 n]
              buf (ffi/alloc arena t)]
          (ffi/write buf t (mapv (fn [{:keys [x y]}] {:x x :y y}) pts))
          (draw-spline-linear-raw! buf n thick color))))))
