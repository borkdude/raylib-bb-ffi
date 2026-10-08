(ns raylib-clj.structs
  "Layouts of the raylib structs that more than one namespace uses.")

;; #region color-alias
(def color
  [:struct
   [[:r :uint8]
    [:g :uint8]
    [:b :uint8]
    [:a :uint8]]])
;; #endregion

(def vector-2
  [:struct
   [[:x :float]
    [:y :float]]])

(def vector-3
  [:struct
   [[:x :float]
    [:y :float]
    [:z :float]]])

(def vector-4
  [:struct
   [[:x :float]
    [:y :float]
    [:z :float]
    [:w :float]]])

(def texture
  [:struct
   [[:id :int]
    [:width :int]
    [:height :int]
    [:mipmaps :int]
    [:format :int]]])

(def render-texture
  [:struct
   [[:id :int]
    [:texture texture]
    [:depth texture]]])

;; Matrix: 4x4, stored COLUMN-major the way raylib writes it - the field
;; names run m0 m4 m8 m12 across the first row, not m0 m1 m2 m3. Laid out
;; here in declaration order so the bytes match; do not "tidy" the order.
(def matrix
  [:struct
   [[:m0 :float] [:m4 :float] [:m8 :float] [:m12 :float]
    [:m1 :float] [:m5 :float] [:m9 :float] [:m13 :float]
    [:m2 :float] [:m6 :float] [:m10 :float] [:m14 :float]
    [:m3 :float] [:m7 :float] [:m11 :float] [:m15 :float]]])

;; Moved here from core/collision so models can use it without depending on
;; the collision namespace.
(def bounding-box
  [:struct
   [[:min vector-3]
    [:max vector-3]]])

;; Camera3D (raylib also calls it Camera). Lives here rather than in
;; core/camera3d because the models billboards take one by value too.
(def camera-3d
  [:struct
   [[:position vector-3]
    [:target vector-3]
    [:up vector-3]
    [:fovy :float]
    [:projection :int]]])

;; Image: CPU-side pixel data, as opposed to Texture which lives on the GPU.
;; `data` is an opaque pointer here - raylib owns the allocation and
;; unload-image! frees it.
(def image
  [:struct
   [[:data :pointer]
    [:width :int]
    [:height :int]
    [:mipmaps :int]
    [:format :int]]])

(def rectangle
  [:struct
   [[:x :float]
    [:y :float]
    [:width :float]
    [:height :float]]])
