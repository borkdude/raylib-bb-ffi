(ns raylib.models
  "3D geometry, drawn in 3D mode: rlgl immediate-mode shapes (cube!, sphere!,
  draw-grid, the rlgl matrix stack), raylib's Draw* shape calls, generated
  meshes, materials and instanced drawing.
  Mesh, Material and Matrix values are maps."
  (:require
   [babashka.ffi :as ffi]
   [raylib.color :as color]
   [raylib.native :as native]
   [raylib.rlgl :as rlgl]
   [raylib.shaders :as shaders]))

(ffi/defcfn draw-grid      "DrawGrid"     [:int :float] :void)
(ffi/defcfn rl-vertex-3f   "rlVertex3f"   [:float :float :float] :void)
(ffi/defcfn rl-push-matrix "rlPushMatrix" [] :void)
(ffi/defcfn rl-pop-matrix  "rlPopMatrix"  [] :void)
(ffi/defcfn rl-translatef  "rlTranslatef" [:float :float :float] :void)
(ffi/defcfn rl-rotatef     "rlRotatef"    [:float :float :float :float] :void)
(ffi/defcfn rl-scalef      "rlScalef"     [:float :float :float] :void)

(defn- shade-color
  "Darken a packed Color by factor f (fakes lighting so cube faces read as 3D)."
  [color f]
  (color/rgba (int (* f (bit-and color 0xff)))
              (int (* f (bit-and (bit-shift-right color 8) 0xff)))
              (int (* f (bit-and (bit-shift-right color 16) 0xff)))
              255))

(defn- quad-3f
  "Two rlgl triangles for a quad, given a shaded color and a vector of its four
  [x y z] corners in a, b, c, d winding order."
  [color [a b c d]]
  (rlgl/rl-color! color)
  (let [[ax ay az] a [bx by bz] b [cx cy cz] c [dx dy dz] d]
    (rl-vertex-3f ax ay az) (rl-vertex-3f bx by bz) (rl-vertex-3f cx cy cz)
    (rl-vertex-3f ax ay az) (rl-vertex-3f cx cy cz) (rl-vertex-3f dx dy dz)))

(defn cube!
  "Draw an axis-aligned box via rlgl immediate mode, its faces shaded from the
  packed `:color` for depth. Must be called inside a BeginMode3D block (see
  with-camera-3d). Keyword args:
    :pos   [x y z] centre           (default [0 0 0])
    :size  a number for a uniform cube, or [sx sy sz]  (default 1)
    :color a packed Color           (default BLACK)"
  [& {:keys [pos size color]
      :or {pos [0.0 0.0 0.0]
           size 1.0
           color color/BLACK}}]
  (let [[cx cy cz] pos
        [sx sy sz] (if (number? size) [size size size] size)
        hx (/ sx 2.0) hy (/ sy 2.0) hz (/ sz 2.0)
        x0 (- cx hx) x1 (+ cx hx) y0 (- cy hy) y1 (+ cy hy) z0 (- cz hz) z1 (+ cz hz)
        ;; the eight corners, named a<x><y><z> by which extreme each axis takes
        a000 [x0 y0 z0] a100 [x1 y0 z0] a010 [x0 y1 z0] a110 [x1 y1 z0]
        a001 [x0 y0 z1] a101 [x1 y0 z1] a011 [x0 y1 z1] a111 [x1 y1 z1]]
    (rlgl/rl-begin rlgl/RL-TRIANGLES)
    (quad-3f (shade-color color 1.0)  [a001 a101 a111 a011])   ; front  +z
    (quad-3f (shade-color color 0.5)  [a100 a000 a010 a110])   ; back   -z
    (quad-3f (shade-color color 0.7)  [a000 a001 a011 a010])   ; left   -x
    (quad-3f (shade-color color 0.85) [a101 a100 a110 a111])   ; right  +x
    (quad-3f (shade-color color 1.0)  [a011 a111 a110 a010])   ; top    +y
    (quad-3f (shade-color color 0.4)  [a000 a100 a101 a001])   ; bottom -y
    (rlgl/rl-end)))

(defn sphere!
  "Draw a sphere via rlgl immediate mode (lat/long tessellation), faces shaded
  from the packed `:color` for depth (brighter toward +y). Must be called inside
  a BeginMode3D block (see with-camera-3d). Keyword args:
    :pos    [x y z] centre        (default [0 0 0])
    :radius a number              (default 0.5)
    :rings  latitude bands        (default 12)
    :slices longitude sectors     (default 16)
    :color  a packed Color        (default BLACK)"
  [& {:keys [pos radius rings slices color]
      :or {pos [0.0 0.0 0.0]
           radius 0.5
           rings 12
           slices 16
           color color/BLACK}}]
  (let [[cx cy cz] pos
        two-pi (* 2.0 Math/PI)]
    (rlgl/rl-begin rlgl/RL-TRIANGLES)
    (dotimes [i rings]
      (let [lat0 (- (* Math/PI (/ (double i) rings)) (/ Math/PI 2.0))
            lat1 (- (* Math/PI (/ (double (inc i)) rings)) (/ Math/PI 2.0))
            y0 (Math/sin lat0) y1 (Math/sin lat1)
            r0 (Math/cos lat0) r1 (Math/cos lat1)
            brightness (+ 0.45 (* 0.55 (/ (+ y0 y1 2.0) 4.0)))
            shaded (shade-color color brightness)]
        (dotimes [j slices]
          (let [lon0 (* two-pi (/ (double j) slices))
                lon1 (* two-pi (/ (double (inc j)) slices))
                s0 (Math/sin lon0) c0 (Math/cos lon0)
                s1 (Math/sin lon1) c1 (Math/cos lon1)
                p00 [(+ cx (* radius r0 c0)) (+ cy (* radius y0)) (+ cz (* radius r0 s0))]
                p01 [(+ cx (* radius r0 c1)) (+ cy (* radius y0)) (+ cz (* radius r0 s1))]
                p10 [(+ cx (* radius r1 c0)) (+ cy (* radius y1)) (+ cz (* radius r1 s0))]
                p11 [(+ cx (* radius r1 c1)) (+ cy (* radius y1)) (+ cz (* radius r1 s1))]]
            (quad-3f shaded [p00 p10 p11 p01])))))
    (rlgl/rl-end)))

(def ^:private v3 native/vector3-layout)

(ffi/defcfn ^:private draw-cube-raw "DrawCube" [v3 :float :float :float :uint] :void)
(ffi/defcfn ^:private draw-cube-wires-raw "DrawCubeWires" [v3 :float :float :float :uint] :void)
(ffi/defcfn ^:private draw-sphere-raw "DrawSphere" [v3 :float :uint] :void)
(ffi/defcfn ^:private draw-sphere-wires-raw "DrawSphereWires" [v3 :float :int :int :uint] :void)
(ffi/defcfn ^:private draw-cylinder-raw "DrawCylinder" [v3 :float :float :float :int :uint] :void)
(ffi/defcfn ^:private draw-cylinder-wires-raw "DrawCylinderWires"
  [v3 :float :float :float :int :uint] :void)
(ffi/defcfn ^:private draw-capsule-raw "DrawCapsule" [v3 v3 :float :int :int :uint] :void)
(ffi/defcfn ^:private draw-capsule-wires-raw "DrawCapsuleWires"
  [v3 v3 :float :int :int :uint] :void)
(ffi/defcfn ^:private draw-plane-raw "DrawPlane" [v3 native/vector2-layout :uint] :void)

(defn draw-cube!
  "Draws a cube with DrawCube. :pos :width :height :length :color."
  [& {:keys [pos width height length color]
      :or {pos [0.0 0.0 0.0]
           width 1.0
           height 1.0
           length 1.0
           color color/BLACK}}]
  (draw-cube-raw (native/vector3 pos) width height length color))

(defn draw-cube-wires!
  "Draws cube edges with DrawCubeWires. :pos :width :height :length :color."
  [& {:keys [pos width height length color]
      :or {pos [0.0 0.0 0.0]
           width 1.0
           height 1.0
           length 1.0
           color color/BLACK}}]
  (draw-cube-wires-raw (native/vector3 pos) width height length color))

(defn draw-sphere!
  "Draws a sphere of radius at pos [x y z] with DrawSphere."
  [pos radius color]
  (draw-sphere-raw (native/vector3 pos) radius color))

(defn draw-sphere-wires!
  "Draws sphere wires with DrawSphereWires. :pos :radius :rings :slices :color."
  [& {:keys [pos radius rings slices color]
      :or {pos [0.0 0.0 0.0]
           radius 0.5
           rings 16
           slices 16
           color color/BLACK}}]
  (draw-sphere-wires-raw (native/vector3 pos) radius rings slices color))

(defn draw-cylinder!
  "Draws a cylinder with DrawCylinder.
  :pos :radius-top :radius-bottom :height :slices :color."
  [& {:keys [pos radius-top radius-bottom height slices color]
      :or {pos [0.0 0.0 0.0]
           radius-top 1.0
           radius-bottom 1.0
           height 1.0
           slices 16
           color color/BLACK}}]
  (draw-cylinder-raw (native/vector3 pos) radius-top radius-bottom height slices color))

(defn draw-cylinder-wires!
  "Draws cylinder wires with DrawCylinderWires.
  :pos :radius-top :radius-bottom :height :slices :color."
  [& {:keys [pos radius-top radius-bottom height slices color]
      :or {pos [0.0 0.0 0.0]
           radius-top 1.0
           radius-bottom 1.0
           height 1.0
           slices 16
           color color/BLACK}}]
  (draw-cylinder-wires-raw (native/vector3 pos) radius-top radius-bottom height slices color))

(defn draw-capsule!
  "Draws a capsule with DrawCapsule. :start-pos :end-pos :radius :slices :rings :color."
  [& {:keys [start-pos end-pos radius slices rings color]
      :or {start-pos [0.0 0.0 0.0]
           end-pos [0.0 1.0 0.0]
           radius 0.5
           slices 8
           rings 8
           color color/BLACK}}]
  (draw-capsule-raw (native/vector3 start-pos) (native/vector3 end-pos)
                    radius slices rings color))

(defn draw-capsule-wires!
  "Draws capsule wires with DrawCapsuleWires.
  :start-pos :end-pos :radius :slices :rings :color."
  [& {:keys [start-pos end-pos radius slices rings color]
      :or {start-pos [0.0 0.0 0.0]
           end-pos [0.0 1.0 0.0]
           radius 0.5
           slices 8
           rings 8
           color color/BLACK}}]
  (draw-capsule-wires-raw (native/vector3 start-pos) (native/vector3 end-pos)
                          radius slices rings color))

(defn draw-plane!
  "Draws an XZ plane with DrawPlane. :pos :size :color."
  [& {:keys [pos size color]
      :or {pos [0.0 0.0 0.0]
           size [1.0 1.0]
           color color/BLACK}}]
  (draw-plane-raw (native/vector3 pos) (native/vector2 size) color))

(def mesh-layout
  "Layout of raylib 6.0's Mesh, 120 bytes."
  [:struct [[:vertex-count :int] [:triangle-count :int]
            [:vertices :pointer] [:texcoords :pointer]
            [:texcoords2 :pointer] [:normals :pointer]
            [:tangents :pointer] [:colors :pointer]
            [:indices :pointer]
            [:bone-count :int]
            [:bone-indices :pointer] [:bone-weights :pointer]
            [:anim-vertices :pointer] [:anim-normals :pointer]
            [:vao-id :uint] [:vbo-id :pointer]]])

(def material-layout
  "Layout of raylib's Material, 40 bytes: {Shader shader; MaterialMap *maps;
  float params[4]}."
  [:struct [[:shader shaders/shader-layout]
            [:maps :pointer]
            [:params [:array :float 4]]]])
(def ^:private material-map-layout
  [:struct [[:texture native/texture2d-layout]
            [:color :uint]
            [:value :float]]])

(ffi/defcfn mesh-cube
  "Returns a cube Mesh, uploaded to the GPU."
  "GenMeshCube" [:float :float :float] mesh-layout)
(ffi/defcfn mesh-sphere
  "Returns a UV sphere Mesh of radius with rings by slices."
  "GenMeshSphere" [:float :int :int] mesh-layout)
(ffi/defcfn mesh-hemisphere
  "Returns a half sphere Mesh without a bottom cap."
  "GenMeshHemiSphere" [:float :int :int] mesh-layout)
(ffi/defcfn mesh-torus
  "Returns a torus Mesh. radius is the tube and size the ring it sweeps."
  "GenMeshTorus" [:float :float :int :int] mesh-layout)
(ffi/defcfn mesh-knot
  "Returns a trefoil knot Mesh, with the parameters of mesh-torus."
  "GenMeshKnot" [:float :float :int :int] mesh-layout)
(ffi/defcfn mesh-cylinder
  "Returns a cylinder Mesh of radius, height and slices."
  "GenMeshCylinder" [:float :float :int] mesh-layout)
(ffi/defcfn mesh-cone
  "Returns a cone Mesh of radius, height and slices."
  "GenMeshCone" [:float :float :int] mesh-layout)
(ffi/defcfn mesh-plane
  "Returns a plane Mesh of width by length, subdivided res-x by res-z."
  "GenMeshPlane" [:float :float :int :int] mesh-layout)

(ffi/defcfn unload-mesh!
  "Releases the GPU buffers and vertex data of mesh."
  "UnloadMesh" [mesh-layout] :void)

(ffi/defcfn material-default
  "Returns raylib's default Material: the default shader and a white diffuse map.
  Do not pass it to UnloadMaterial, which frees the shared default shader."
  "LoadMaterialDefault" [] material-layout)

(defn matrix-identity
  "Returns the identity Matrix."
  []
  (into {} (map (fn [[k _]] [k (if (#{:m0 :m5 :m10 :m15} k) 1.0 0.0)]))
        (second native/matrix-layout)))

(defn matrix-translate
  "Returns the Matrix of a translation by x, y and z."
  [x y z]
  (assoc (matrix-identity) :m12 x :m13 y :m14 z))

(ffi/defcfn draw-mesh!
  "Draws mesh with material under the Matrix transform, in 3D mode."
  "DrawMesh" [mesh-layout material-layout native/matrix-layout] :void)

(defn- material-map0 [mat]
  (ffi/reinterpret (:maps mat) (ffi/sizeof material-map-layout)))

(def ^:private map-color (ffi/place material-map-layout :color))
(def ^:private map-texture (ffi/place material-map-layout :texture))

(defn material-diffuse-color!
  "Sets the color of the diffuse map of mat, in raylib's memory, and returns mat."
  [mat color]
  (ffi/write (material-map0 mat) map-color color)
  mat)

(defn material-shader
  "Returns mat with the shader sh.
  DrawMesh draws with the shader of its material, not the one of with-shader."
  [mat sh]
  (assoc mat :shader sh))

(defn material-diffuse-texture!
  "Sets the texture of the diffuse map of mat to the RGBA8 texture tex-id of
  w by h, in raylib's memory, and returns mat."
  [mat tex-id w h]
  (ffi/write (material-map0 mat) map-texture (native/texture2d tex-id w h))
  mat)

(ffi/defcfn ^:private draw-mesh-instanced-raw "DrawMeshInstanced"
  [mesh-layout material-layout :pointer :int] :void)

(defn matrix-array-alloc
  "Returns native memory for n Matrix values, for draw-mesh-instanced!.
  The garbage collector releases the memory."
  [n]
  (ffi/alloc (ffi/auto-arena) (* 64 n)))

(defn matrix-array-set!
  "Writes the Matrix at index i of buf: a rotation of angle radians about the
  unit axis [ax ay az], then a translation to [x y z].
  Returns buf."
  [buf i [ax ay az] angle [x y z]]
  (let [c (Math/cos (double angle))
        s (Math/sin (double angle))
        t (- 1.0 c)
        ax (double ax) ay (double ay) az (double az)]
    (ffi/write buf native/matrix-layout
               {:m0 (+ (* t ax ax) c) :m4 (- (* t ax ay) (* s az))
                :m8 (+ (* t ax az) (* s ay)) :m12 x
                :m1 (+ (* t ax ay) (* s az)) :m5 (+ (* t ay ay) c)
                :m9 (- (* t ay az) (* s ax)) :m13 y
                :m2 (- (* t ax az) (* s ay)) :m6 (+ (* t ay az) (* s ax))
                :m10 (+ (* t az az) c) :m14 z
                :m3 0.0 :m7 0.0 :m11 0.0 :m15 1.0}
               (* 64 i)))
  buf)

(defn draw-mesh-instanced!
  "Draws instances copies of mesh, each under its Matrix in transforms.
  The vertex shader of material must declare in mat4 instanceTransform."
  [mesh material transforms instances]
  (draw-mesh-instanced-raw mesh material transforms instances))
