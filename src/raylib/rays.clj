(ns raylib.rays
  "Picking in the 3D scene: screen-to-world-ray, ray-collision-box, draw-ray!
  and cursor-hidden?."
  (:require
   [babashka.ffi :as ffi]
   [raylib.camera :as camera]
   [raylib.native :as native]))

(def ray-layout
  "Layout of raylib's Ray: {Vector3 position, direction}."
  [:struct [[:position native/vector3-layout]
            [:direction native/vector3-layout]]])

(def bounding-box-layout
  "Layout of raylib's BoundingBox: {Vector3 min, max}."
  [:struct [[:min native/vector3-layout]
            [:max native/vector3-layout]]])

(def ray-collision-layout
  "Layout of raylib's RayCollision: {bool hit; float distance; Vector3 point,
  normal}."
  [:struct [[:hit :bool]
            [:distance :float]
            [:point native/vector3-layout]
            [:normal native/vector3-layout]]])

(ffi/defcfn ^:private get-screen-to-world-ray-raw "GetScreenToWorldRay"
  [native/vector2-layout camera/camera3d-layout] ray-layout)

(ffi/defcfn ^:private get-ray-collision-box-raw "GetRayCollisionBox"
  [ray-layout bounding-box-layout] ray-collision-layout)

(ffi/defcfn ^:private draw-ray-raw "DrawRay" [ray-layout :uint] :void)

(ffi/defcfn cursor-hidden?
  "Returns true if the cursor is hidden."
  "IsCursorHidden" [] :bool)

(defn- ->ray [{:keys [position direction]}]
  {:position (native/vector3 position)
   :direction (native/vector3 direction)})

(defn screen-to-world-ray
  "Returns the ray through the window position [x y] as
  {:position [x y z] :direction [x y z]}, with a unit direction.
  camera takes the keys of raylib.camera/camera3d."
  [screen camera]
  (let [{:keys [position direction]}
        (get-screen-to-world-ray-raw (native/vector2 screen) (camera/camera3d camera))]
    {:position (native/xyz position)
     :direction (native/xyz direction)}))

(defn ray-collision-box
  "Returns the collision of ray with the axis-aligned box from lo to hi, both
  [x y z], as {:hit? :distance :point :normal}.
  :distance, :point and :normal are undefined if :hit? is false."
  [ray lo hi]
  (let [{:keys [hit distance point normal]}
        (get-ray-collision-box-raw (->ray ray)
                                   {:min (native/vector3 lo) :max (native/vector3 hi)})]
    {:hit? hit
     :distance distance
     :point (native/xyz point)
     :normal (native/xyz normal)}))

(defn draw-ray!
  "Draws ray as a line from its position, in 3D mode."
  [ray color]
  (draw-ray-raw (->ray ray) color))
