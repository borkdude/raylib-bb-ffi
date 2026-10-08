(ns raylib.native
  "Loads the raylib shared library and defines the struct layouts and helpers
  that more than one module uses.

  Set the RAYLIB_LIBRARY environment variable to the path of libraylib to skip
  the search of the system library directories."
  (:require
   [babashka.ffi :as ffi]))

(def library
  "The library map of the loaded raylib."
  (if-let [path (System/getenv "RAYLIB_LIBRARY")]
    (ffi/load-library path)
    (ffi/load-system-library "raylib")))

(def vector2-layout
  "Layout of raylib's Vector2: {float x, y}."
  [:struct [[:x :float] [:y :float]]])

(def vector3-layout
  "Layout of raylib's Vector3: {float x, y, z}."
  [:struct [[:x :float] [:y :float] [:z :float]]])

(def vector4-layout
  "Layout of raylib's Vector4 and Quaternion: {float x, y, z, w}."
  [:struct [[:x :float] [:y :float] [:z :float] [:w :float]]])

(def rectangle-layout
  "Layout of raylib's Rectangle: {float x, y, width, height}."
  [:struct [[:x :float] [:y :float] [:width :float] [:height :float]]])

(def texture2d-layout
  "Layout of raylib's Texture2D: {uint id; int width, height, mipmaps, format}."
  [:struct [[:id :uint] [:width :int] [:height :int]
            [:mipmaps :int] [:format :int]]])

(def matrix-layout
  "Layout of raylib's Matrix, 64 bytes, in raylib's memory order m0 m4 m8 m12,
  m1 m5 m9 m13, and so on."
  [:struct [[:m0 :float] [:m4 :float] [:m8 :float] [:m12 :float]
            [:m1 :float] [:m5 :float] [:m9 :float] [:m13 :float]
            [:m2 :float] [:m6 :float] [:m10 :float] [:m14 :float]
            [:m3 :float] [:m7 :float] [:m11 :float] [:m15 :float]]])

(def ^:const PIXELFORMAT-R8G8B8A8 7)
(def ^:const PIXELFORMAT-R8G8B8 4)

(defn vector2
  "Returns the Vector2 map of [x y]."
  [[x y]]
  {:x x :y y})

(defn vector3
  "Returns the Vector3 map of [x y z]."
  [[x y z]]
  {:x x :y y :z z})

(defn rectangle
  "Returns the Rectangle map of [x y width height]."
  [[x y width height]]
  {:x x :y y :width width :height height})

(defn xy
  "Returns [x y] of a Vector2 map."
  [{:keys [x y]}]
  [x y])

(defn xyz
  "Returns [x y z] of a Vector3 map."
  [{:keys [x y z]}]
  [x y z])

(defn texture2d
  "Returns the Texture2D map of an RGBA8 texture with one mipmap."
  [id width height]
  {:id id :width width :height height :mipmaps 1 :format PIXELFORMAT-R8G8B8A8})

(defn staged
  "Copies values into native memory as 4-byte elements and calls f with the
  pointer.
  write-type is :float for floats, or any other keyword for ints.
  Releases the memory when f returns."
  [write-type values f]
  (let [t (if (= :float write-type) :float :int)
        n (count values)]
    (with-open [arena (ffi/confined-arena)]
      (let [p (ffi/alloc arena (* 4 (max 1 n)))]
        (when (pos? n)
          (ffi/write p [:array t n] values))
        (f p)))))
