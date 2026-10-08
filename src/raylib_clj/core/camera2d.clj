(ns raylib-clj.core.camera2d
  "2D Camera functions"
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

;; Camera2D struct (24 bytes)
;; { Vector2 offset; Vector2 target; float rotation; float zoom; }
(def camera-2d
  [:struct
   [[:offset rs/vector-2] ; 8 bytes - screen space offset
    [:target rs/vector-2] ; 8 bytes - world space target
    [:rotation :float] ; 4 bytes - rotation in degrees
    [:zoom :float]]]) ; 4 bytes - zoom/scale

(ffi/defcfn begin-mode-2d!
  "Begin 2D mode with custom camera (2D)"
  {:arglists '([camera])}
  "BeginMode2D"
  [camera-2d] :void)

(ffi/defcfn end-mode-2d!
  "Ends 2D mode with custom camera"
  "EndMode2D"
  [] :void)

(ffi/defcfn get-screen-to-world-2d
  "Get world space position for a 2d camera screen space position"
  {:arglists '([position camera])}
  "GetScreenToWorld2D"
  [rs/vector-2 camera-2d] rs/vector-2)

(ffi/defcfn get-world-to-screen-2d
  "Get screen space position for a 2d camera world space position"
  {:arglists '([position camera])}
  "GetWorldToScreen2D"
  [rs/vector-2 camera-2d] rs/vector-2)

;; Helper to create a Camera2D map
(defn make-camera-2d
  "Create a Camera2D map with default or specified values.
   Args:
   - target: {:x x :y y} - world position camera looks at
   - offset: {:x x :y y} - screen position where target is drawn (usually center)
   - rotation: degrees (default 0)
   - zoom: scale factor (default 1.0, must not be 0)"
  ([target offset]
   (make-camera-2d target offset 0.0 1.0))
  ([target offset rotation zoom]
   {:offset offset
    :target target
    :rotation (float rotation)
    :zoom (float zoom)}))
