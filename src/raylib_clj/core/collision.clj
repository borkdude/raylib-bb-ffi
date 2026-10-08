(ns raylib-clj.core.collision
  "Ray casting and collision detection functions"
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

;; Ray struct: position (Vector3) + direction (Vector3) = 24 bytes
(def ray
  [:struct
   [[:position rs/vector-3]
    [:direction rs/vector-3]]])

;; RayCollision: bool hit, then 3 bytes of padding, 32 bytes in all.
(def ray-collision
  [:struct
   [[:hit :uint8]
    [:distance :float]
    [:point rs/vector-3]
    [:normal rs/vector-3]]])

(ffi/defcfn get-screen-to-world-ray
  "Get a ray trace from screen position (i.e. mouse)"
  {:arglists '([position camera])}
  "GetScreenToWorldRay"
  [rs/vector-2 rs/camera-3d] ray)

(ffi/defcfn get-ray-collision-box
  "Get collision info between ray and box"
  {:arglists '([ray box])}
  "GetRayCollisionBox"
  [ray rs/bounding-box] ray-collision)

(ffi/defcfn draw-ray!
  "Draw a ray line"
  {:arglists '([ray color])}
  "DrawRay"
  [ray rs/color] :void)

(ffi/defcfn get-ray-collision-sphere
  "Get collision info between ray and sphere"
  {:arglists '([ray center radius])}
  "GetRayCollisionSphere"
  [ray rs/vector-3 :float] ray-collision)

(ffi/defcfn get-ray-collision-triangle
  "Get collision info between ray and triangle"
  {:arglists '([ray p1 p2 p3])}
  "GetRayCollisionTriangle"
  [ray rs/vector-3 rs/vector-3 rs/vector-3] ray-collision)

(ffi/defcfn get-ray-collision-quad
  "Get collision info between ray and quad"
  {:arglists '([ray p1 p2 p3 p4])}
  "GetRayCollisionQuad"
  [ray rs/vector-3 rs/vector-3 rs/vector-3 rs/vector-3] ray-collision)

;; Helper to create a bounding box from position and size
(defn make-bounding-box
  "Create a bounding box from center position and size"
  [{px :x
    py :y
    pz :z} {sx :x
            sy :y
            sz :z}]
  {:min {:x (- px (/ sx 2))
         :y (- py (/ sy 2))
         :z (- pz (/ sz 2))}
   :max {:x (+ px (/ sx 2))
         :y (+ py (/ sy 2))
         :z (+ pz (/ sz 2))}})

;; 3D Collision detection functions
(ffi/defcfn check-collision-boxes?
  "Check collision between two bounding boxes"
  {:arglists '([box1 box2])}
  "CheckCollisionBoxes"
  [rs/bounding-box rs/bounding-box] :int8)

(ffi/defcfn check-collision-box-sphere?
  "Check collision between box and sphere"
  {:arglists '([box center radius])}
  "CheckCollisionBoxSphere"
  [rs/bounding-box rs/vector-3 :float] :int8)

;; Moved from raylib-ext (2026-08-22 consolidation)
(ffi/defcfn check-collision-circle-rec?
  "Check collision between circle and rectangle"
  {:arglists '([center radius rec])}
  "CheckCollisionCircleRec"
  [rs/vector-2 :float rs/rectangle] :int8)

(ffi/defcfn check-collision-point-circle?
  "Check if point is inside circle"
  {:arglists '([point center radius])}
  "CheckCollisionPointCircle"
  [rs/vector-2 rs/vector-2 :float] :int8)

(ffi/defcfn check-collision-circles?
  "Check collision between two circles"
  {:arglists '([center1 radius1 center2 radius2])}
  "CheckCollisionCircles"
  [rs/vector-2 :float rs/vector-2 :float] :int8)

(ffi/defcfn check-collision-point-rec?
  "Check if point is inside rectangle"
  {:arglists '([point rec])}
  "CheckCollisionPointRec"
  [rs/vector-2 rs/rectangle] :int8)
