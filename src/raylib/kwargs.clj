(ns raylib.kwargs
  "Drawing functions with keyword arguments, for example
  (text! \"hi\" :x 10 :y 20 :color RED) for DrawText.
  Positions and sizes accept any number."
  (:require
   [raylib.color :as color]
   [raylib.core :as core]
   [raylib.rlgl :as rlgl]
   [raylib.shapes :as shapes]
   [raylib.text :as text]))

(defn window!
  "InitWindow with keyword args. :width :height :title."
  [& {:keys [width height title]
      :or {width 800
           height 450
           title "raylib"}}]
  (core/init-window width height title))

(defn text!
  "DrawText. :x :y :size :color."
  [s & {:keys [x y size color]
        :or {x 0
             y 0
             size 20
             color color/BLACK}}]
  (text/draw-text s (int x) (int y) (int size) color))

(defn text-width
  "MeasureText. :size."
  [s & {:keys [size]
        :or {size 20}}]
  (text/measure-text s size))

(defn fps!
  "DrawFPS. :x :y."
  [& {:keys [x y]
      :or {x 10
           y 10}}]
  (text/draw-fps (int x) (int y)))

(defn rect!
  "DrawRectangle. :x :y :width :height :color."
  [& {:keys [x y width height color]
      :or {x 0
           y 0
           width 10
           height 10
           color color/BLACK}}]
  (shapes/draw-rectangle (int x) (int y) (int width) (int height) color))

(defn rect-lines!
  "DrawRectangleLines. :x :y :width :height :color."
  [& {:keys [x y width height color]
      :or {x 0
           y 0
           width 10
           height 10
           color color/BLACK}}]
  (shapes/draw-rectangle-lines (int x) (int y) (int width) (int height) color))

(defn rect-gradient!
  "DrawRectangleGradientV (top->bottom). :x :y :width :height :top :bottom."
  [& {:keys [x y width height top bottom]
      :or {x 0
           y 0
           width 10
           height 10
           top color/WHITE
           bottom color/BLACK}}]
  (shapes/draw-rectangle-grad-v x y width height top bottom))

(defn circle!
  "DrawCircle. :x :y :radius :color."
  [& {:keys [x y radius color]
      :or {x 0
           y 0
           radius 10
           color color/BLACK}}]
  (shapes/draw-circle (int x) (int y) (double radius) color))

(defn circle-lines!
  "DrawCircleLines. :x :y :radius :color."
  [& {:keys [x y radius color]
      :or {x 0
           y 0
           radius 10
           color color/BLACK}}]
  (shapes/draw-circle-lines (int x) (int y) (double radius) color))

(defn ellipse!
  "DrawEllipse. :x :y :rx :ry :color."
  [& {:keys [x y rx ry color]
      :or {x 0
           y 0
           rx 10
           ry 6
           color color/BLACK}}]
  (shapes/draw-ellipse (int x) (int y) (double rx) (double ry) color))

(defn line!
  "DrawLine. :x1 :y1 :x2 :y2 :color."
  [& {:keys [x1 y1 x2 y2 color]
      :or {x1 0
           y1 0
           x2 0
           y2 0
           color color/BLACK}}]
  (shapes/draw-line (int x1) (int y1) (int x2) (int y2) color))

(defn pixel!
  "DrawPixel. :x :y :color."
  [& {:keys [x y color]
      :or {x 0
           y 0
           color color/BLACK}}]
  (shapes/draw-pixel (int x) (int y) color))

(defn sector!
  "Draws a filled circular sector as an rlgl triangle fan from the center
  across start-deg to end-deg in segments triangles.
  0 degrees points up and the angle increases clockwise.
  start-deg must be less than end-deg.
    :cx :cy    center
    :radius    outer radius
    :start-deg :end-deg   sweep in degrees (0 = up, clockwise, increasing)
    :segments  fan resolution (default 32)
    :color     packed Color"
  [& {:keys [cx cy radius start-deg end-deg segments color]
      :or {cx 0
           cy 0
           radius 10
           start-deg 0
           end-deg 90
           segments 32
           color color/BLACK}}]
  (let [d->r (/ Math/PI 180.0)
        span (- end-deg start-deg)
        rim (fn [deg]
              (let [t (* deg d->r)]
                [(+ cx (* radius (Math/sin t)))
                 (- cy (* radius (Math/cos t)))]))]
    (rlgl/rl-begin rlgl/RL-TRIANGLES)
    (rlgl/rl-color! color)
    (dotimes [k segments]
      (let [[x0 y0] (rim (+ start-deg (* span (/ (double k) segments))))
            [x1 y1] (rim (+ start-deg (* span (/ (double (inc k)) segments))))]
        (rlgl/rl-vertex-2f (double x0) (double y0))
        (rlgl/rl-vertex-2f (double cx) (double cy))
        (rlgl/rl-vertex-2f (double x1) (double y1))))
    (rlgl/rl-end)))

(defn ring!
  "Draws a filled ring sector between the :inner and :outer radius from
  start-deg to end-deg, as rlgl triangles.
  Uses the angle convention of sector!.
    :cx :cy    center
    :inner :outer   radii
    :start-deg :end-deg   sweep in degrees (increasing)
    :segments  resolution (default 48)
    :color     packed Color"
  [& {:keys [cx cy inner outer start-deg end-deg segments color]
      :or {cx 0
           cy 0
           inner 20
           outer 40
           start-deg 0
           end-deg 360
           segments 48
           color color/BLACK}}]
  (let [d->r (/ Math/PI 180.0)
        span (- end-deg start-deg)
        pt (fn [deg r]
             (let [t (* deg d->r)]
               [(+ cx (* r (Math/sin t))) (- cy (* r (Math/cos t)))]))]
    (rlgl/rl-begin rlgl/RL-TRIANGLES)
    (rlgl/rl-color! color)
    (dotimes [k segments]
      (let [d0 (+ start-deg (* span (/ (double k) segments)))
            d1 (+ start-deg (* span (/ (double (inc k)) segments)))
            [ix0 iy0] (pt d0 inner) [ox0 oy0] (pt d0 outer)
            [ix1 iy1] (pt d1 inner) [ox1 oy1] (pt d1 outer)]
        (rlgl/rl-vertex-2f (double ox0) (double oy0))
        (rlgl/rl-vertex-2f (double ix0) (double iy0))
        (rlgl/rl-vertex-2f (double ix1) (double iy1))
        (rlgl/rl-vertex-2f (double ox0) (double oy0))
        (rlgl/rl-vertex-2f (double ix1) (double iy1))
        (rlgl/rl-vertex-2f (double ox1) (double oy1))))
    (rlgl/rl-end)))

(defn line-ex!
  "Draws a line from (x1,y1) to (x2,y2), :thick pixels wide, as an rlgl quad.
    :x1 :y1 :x2 :y2   endpoints
    :thick   width in px (default 2)
    :color   packed Color"
  [& {:keys [x1 y1 x2 y2 thick color]
      :or {x1 0
           y1 0
           x2 0
           y2 0
           thick 2
           color color/BLACK}}]
  (let [dx (- x2 x1) dy (- y2 y1)
        len (Math/sqrt (+ (* dx dx) (* dy dy)))
        len (if (zero? len) 1.0 len)
        h  (/ thick 2.0)
        px (* (/ dy len) h)
        py (* (/ (- dx) len) h)
        ax (+ x1 px) ay (+ y1 py)
        bx (- x1 px) by (- y1 py)
        cx (- x2 px) cy (- y2 py)
        ex (+ x2 px) ey (+ y2 py)]
    (rlgl/rl-begin rlgl/RL-TRIANGLES)
    (rlgl/rl-color! color)
    (rlgl/rl-vertex-2f (double ax) (double ay))
    (rlgl/rl-vertex-2f (double bx) (double by))
    (rlgl/rl-vertex-2f (double cx) (double cy))
    (rlgl/rl-vertex-2f (double ax) (double ay))
    (rlgl/rl-vertex-2f (double cx) (double cy))
    (rlgl/rl-vertex-2f (double ex) (double ey))
    (rlgl/rl-end)))
