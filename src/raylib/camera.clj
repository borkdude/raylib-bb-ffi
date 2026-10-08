(ns raylib.camera
  "Camera2D and Camera3D: with-camera-2d and with-camera-3d, a Camera3D in
  native memory for update-camera!, and world-to-screen."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native :as native]))

(def camera2d-layout
  "Layout of raylib's Camera2D: {Vector2 offset, target; float rotation, zoom}."
  [:struct [[:offset native/vector2-layout]
            [:target native/vector2-layout]
            [:rotation :float]
            [:zoom :float]]])

(def camera3d-layout
  "Layout of raylib's Camera3D: {Vector3 position, target, up; float fovy;
  int projection}."
  [:struct [[:position native/vector3-layout]
            [:target native/vector3-layout]
            [:up native/vector3-layout]
            [:fovy :float]
            [:projection :int]]])

(defn camera2d
  "Returns the Camera2D map of the keys :offset-x :offset-y :target-x
  :target-y :rotation :zoom.
  Each key is 0 if absent, except :zoom, which is 1.0 if absent."
  [{:keys [offset-x offset-y target-x target-y rotation zoom]
    :or {offset-x 0 offset-y 0 target-x 0 target-y 0 rotation 0 zoom 1.0}}]
  {:offset {:x offset-x :y offset-y}
   :target {:x target-x :y target-y}
   :rotation rotation
   :zoom zoom})

(defn camera3d
  "Returns the Camera3D map of the keys :pos-x/y/z :target-x/y/z :up-x/y/z
  :fovy :projection.
  Each key is 0 if absent, except :up-y, which is 1 if absent, and :fovy,
  which is 45 if absent.
  :projection 0 is perspective."
  [{:keys [pos-x pos-y pos-z target-x target-y target-z up-x up-y up-z fovy projection]
    :or {pos-x 0 pos-y 0 pos-z 0
         target-x 0 target-y 0 target-z 0
         up-x 0 up-y 1 up-z 0
         fovy 45 projection 0}}]
  {:position {:x pos-x :y pos-y :z pos-z}
   :target {:x target-x :y target-y :z target-z}
   :up {:x up-x :y up-y :z up-z}
   :fovy fovy
   :projection projection})

(ffi/defcfn begin-mode-2d "BeginMode2D" [camera2d-layout] :void)
(ffi/defcfn end-mode-2d "EndMode2D" [] :void)
(ffi/defcfn begin-mode-3d "BeginMode3D" [camera3d-layout] :void)
(ffi/defcfn end-mode-3d "EndMode3D" [] :void)

(defn with-camera-2d
  "Calls f in 2D mode with the camera of opts.
  opts takes the keys of camera2d."
  [opts f]
  (begin-mode-2d (camera2d opts))
  (try (f)
       (finally (end-mode-2d))))

(defn with-camera-3d
  "Calls f in 3D mode with the camera of opts.
  opts takes the keys of camera3d."
  [opts f]
  (begin-mode-3d (camera3d opts))
  (try (f)
       (finally (end-mode-3d))))

(ffi/defcfn ^:private get-world-to-screen-raw "GetWorldToScreen"
  [native/vector3-layout camera3d-layout] native/vector2-layout)

(defn world-to-screen
  "Returns the screen position [x y] of the world position [x y z].
  camera takes the keys of camera3d."
  [pos camera]
  (native/xy (get-world-to-screen-raw (native/vector3 pos) (camera3d camera))))

(ffi/defcfn ^:private update-camera-raw "UpdateCamera" [:pointer :int] :void)
(ffi/defcfn disable-cursor! "DisableCursor" [] :void)
(ffi/defcfn enable-cursor! "EnableCursor" [] :void)

(def ^:const CAMERA-CUSTOM 0)
(def ^:const CAMERA-FREE 1)
(def ^:const CAMERA-ORBITAL 2)
(def ^:const CAMERA-FIRST-PERSON 3)
(def ^:const CAMERA-THIRD-PERSON 4)

(defn camera3d-alloc
  "Returns a pointer to a Camera3D in native memory, for update-camera!.
  Takes the keys of camera3d.
  The garbage collector releases the memory."
  [& {:as opts}]
  (let [cam (ffi/alloc (ffi/auto-arena) camera3d-layout)]
    (ffi/write cam camera3d-layout (camera3d opts))
    cam))

(defn update-camera!
  "Updates the Camera3D at pointer cam from input, in camera mode mode."
  [cam mode]
  (update-camera-raw cam mode))

(defn camera3d-read
  "Returns the Camera3D map at pointer cam."
  [cam]
  (ffi/read cam camera3d-layout))

(defn begin-mode-3d-ptr
  "Begins 3D mode with the Camera3D at pointer cam."
  [cam]
  (begin-mode-3d (camera3d-read cam)))

(def ^:private camera3d-target (ffi/place camera3d-layout :target))

(defn camera3d-set-target!
  "Sets the target of the Camera3D at pointer cam to [x y z]."
  [cam target]
  (ffi/write cam camera3d-target (native/vector3 target)))
