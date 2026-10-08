(ns raylib-clj.models
  "3D model loading, generation and drawing.

   Mirrors raylib.h's \"Model 3d Loading and Drawing Functions\" and the mesh
   generation group beneath it.

   IMPORTANT, because getting it wrong corrupts memory silently rather than
   erroring: the `Model` struct CHANGED between raylib 5.5 and 6.0. 5.5 ended
   with `boneCount`, `bones*`, `bindPose*`; 6.0 replaces those with a nested
   `ModelSkeleton`, a `currentPose` pointer and a `boneMatrices` pointer. The
   layouts here are 6.0's. Check them against raylib.h after a raylib
   upgrade."
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

;; ----------------------------------------------------------------- structs

;; int, int, then 14 pointers. vertexCount+triangleCount fill the first 8
;; bytes so `vertices` is already aligned; boneCount and vaoId each need a
;; pad after them.
(def mesh
  [:struct
   [[:vertex-count :int]
    [:triangle-count :int]
    [:vertices :pointer]
    [:texcoords :pointer]
    [:texcoords2 :pointer]
    [:normals :pointer]
    [:tangents :pointer]
    [:colors :pointer]
    [:indices :pointer]
    [:bone-count :int]
    [:_pad0 :int]
    [:bone-indices :pointer]
    [:bone-weights :pointer]
    [:anim-vertices :pointer]
    [:anim-normals :pointer]
    [:vao-id :int]
    [:_pad1 :int]
    [:vbo-id :pointer]]])

(def material-map
  [:struct
   [[:texture rs/texture]
    [:color rs/color]
    [:value :float]]])

;; Shader is {uint id; int *locs}: 4 bytes then a padded pointer.
(def material
  [:struct
   [[:shader-id :int]
    [:_pad0 :int]
    [:shader-locs :pointer]
    [:maps :pointer]
    [:param0 :float] [:param1 :float]
    [:param2 :float] [:param3 :float]]])

(def model-skeleton
  [:struct
   [[:bone-count :int]
    [:_pad0 :int]
    [:bones :pointer]
    [:bind-pose :pointer]]])

;; 6.0 layout. See the namespace docstring before changing anything here.
(def model
  [:struct
   [[:transform rs/matrix]
    [:mesh-count :int]
    [:material-count :int]
    [:meshes :pointer]
    [:materials :pointer]
    [:mesh-material :pointer]
    [:skeleton model-skeleton]
    [:current-pose :pointer]
    [:bone-matrices :pointer]]])

;; ------------------------------------------------------- loading, lifetime

(ffi/defcfn load-model
  "Load model from files (meshes and materials)"
  {:arglists '([filename])}
  "LoadModel"
  [:string] model)

(ffi/defcfn load-model-from-mesh
  "Load model from a generated mesh. The model takes ownership of the mesh -
   unloading the model frees it, so do not also unload the mesh."
  {:arglists '([mesh])}
  "LoadModelFromMesh"
  [mesh] model)

(ffi/defcfn is-model-valid?
  "Check if a model is valid (loaded in GPU, VAO/VBOs)"
  {:arglists '([model])}
  "IsModelValid"
  [model] :bool)

(ffi/defcfn unload-model!
  "Unload model (including meshes) from memory (RAM and VRAM)"
  {:arglists '([model])}
  "UnloadModel"
  [model] :void)

(ffi/defcfn get-model-bounding-box
  "Compute model bounding box limits (considers all meshes)"
  {:arglists '([model])}
  "GetModelBoundingBox"
  [model] rs/bounding-box)

;; ---------------------------------------------------------------- drawing

(ffi/defcfn draw-model!
  "Draw a model (with texture if set)"
  {:arglists '([model position scale tint])}
  "DrawModel"
  [model rs/vector-3 :float rs/color] :void)

(ffi/defcfn draw-model-ex!
  "Draw a model with extended parameters"
  {:arglists '([model position rotation-axis rotation-angle scale tint])}
  "DrawModelEx"
  [model rs/vector-3 rs/vector-3 :float rs/vector-3 rs/color] :void)

(ffi/defcfn draw-model-wires!
  "Draw a model as wireframe (with texture if set)"
  {:arglists '([model position scale tint])}
  "DrawModelWires"
  [model rs/vector-3 :float rs/color] :void)

(ffi/defcfn draw-model-wires-ex!
  "Draw a model as wireframe with extended parameters"
  {:arglists '([model position rotation-axis rotation-angle scale tint])}
  "DrawModelWiresEx"
  [model rs/vector-3 rs/vector-3 :float rs/vector-3 rs/color] :void)

(ffi/defcfn draw-bounding-box!
  "Draw a bounding box (wires)"
  {:arglists '([box color])}
  "DrawBoundingBox"
  [rs/bounding-box rs/color] :void)

(ffi/defcfn draw-billboard!
  "Draw a billboard texture, always facing the camera"
  {:arglists '([camera texture position scale tint])}
  "DrawBillboard"
  [rs/camera-3d rs/texture rs/vector-3 :float rs/color] :void)

(ffi/defcfn draw-billboard-rec!
  "Draw a billboard texture defined by a source rectangle"
  {:arglists '([camera texture source position size tint])}
  "DrawBillboardRec"
  [rs/camera-3d rs/texture rs/rectangle rs/vector-3 rs/vector-2 rs/color] :void)

(ffi/defcfn draw-billboard-pro!
  "Draw a billboard texture with source, rotation and an explicit up vector"
  {:arglists '([camera texture source position up size origin rotation tint])}
  "DrawBillboardPro"
  [rs/camera-3d rs/texture rs/rectangle rs/vector-3 rs/vector-3
   rs/vector-2 rs/vector-2 :float rs/color] :void)

;; ------------------------------------------------------- mesh generation

(ffi/defcfn get-mesh-bounding-box
  "Compute mesh bounding box limits"
  {:arglists '([mesh])}
  "GetMeshBoundingBox"
  [mesh] rs/bounding-box)

(ffi/defcfn unload-mesh!
  "Unload mesh data from CPU and GPU. Only for a mesh NOT handed to
   load-model-from-mesh - that transfers ownership."
  {:arglists '([mesh])}
  "UnloadMesh"
  [mesh] :void)

(ffi/defcfn gen-mesh-cubicmap
  "Generate a cubes-based map mesh from an image, one cube per non-black pixel"
  {:arglists '([cubicmap cube-size])}
  "GenMeshCubicmap"
  [rs/image rs/vector-3] mesh)

(ffi/defcfn gen-mesh-heightmap
  "Generate a heightmap mesh from an image, using pixel brightness as height"
  {:arglists '([heightmap size])}
  "GenMeshHeightmap"
  [rs/image rs/vector-3] mesh)

(ffi/defcfn gen-mesh-poly
  "Generate a polygonal mesh"
  {:arglists '([sides radius])}
  "GenMeshPoly"
  [:int :float] mesh)

(ffi/defcfn gen-mesh-plane
  "Generate a plane mesh (with subdivisions)"
  {:arglists '([width length res-x res-z])}
  "GenMeshPlane"
  [:float :float :int :int] mesh)

(ffi/defcfn gen-mesh-cube
  "Generate a cuboid mesh"
  {:arglists '([width height length])}
  "GenMeshCube"
  [:float :float :float] mesh)

(ffi/defcfn gen-mesh-sphere
  "Generate a sphere mesh (standard uv-sphere)"
  {:arglists '([radius rings slices])}
  "GenMeshSphere"
  [:float :int :int] mesh)

(ffi/defcfn gen-mesh-hemi-sphere
  "Generate a half-sphere mesh (no bottom cap)"
  {:arglists '([radius rings slices])}
  "GenMeshHemiSphere"
  [:float :int :int] mesh)

(ffi/defcfn gen-mesh-cylinder
  "Generate a cylinder mesh"
  {:arglists '([radius height slices])}
  "GenMeshCylinder"
  [:float :float :int] mesh)

(ffi/defcfn gen-mesh-cone
  "Generate a cone/pyramid mesh"
  {:arglists '([radius height slices])}
  "GenMeshCone"
  [:float :float :int] mesh)

(ffi/defcfn gen-mesh-torus
  "Generate a torus mesh"
  {:arglists '([radius size rad-seg sides])}
  "GenMeshTorus"
  [:float :float :int :int] mesh)

(ffi/defcfn gen-mesh-knot
  "Generate a trefoil knot mesh"
  {:arglists '([radius size rad-seg sides])}
  "GenMeshKnot"
  [:float :float :int :int] mesh)

;; ------------------------------------------------------- material access

(def material-map-index
  "Which slot of a material's `maps` array a texture/colour applies to."
  {:albedo 0
   :diffuse 0        ; raylib defines these as the same slot
   :metalness 1
   :specular 1
   :normal 2
   :roughness 3
   :occlusion 4
   :emission 5
   :height 6
   :cubemap 7
   :irradiance 8
   :prefilter 9
   :brdf 10})

(def ^:private material-size 40)   ; Shader(16) + maps ptr(8) + params[4](16)
(def ^:private material-maps-offset 16)
(def ^:private material-map-size 28) ; Texture2D(20) + Color(4) + float(4)
(def ^:private material-map-color-offset 20)

(defn set-model-material-color!
  "Set the tint colour of one of a model's material maps, in place.

   raylib exposes no function for this - its own examples reach into
   `model.materials[i].maps[j].color` directly - so this walks the same
   pointers. It is a MUTATION of GPU-adjacent memory the model owns, so it
   must be called on a live model and has no effect after `unload-model!`.

   Note this multiplies with the tint passed to `draw-model!` rather than
   replacing it: a BEIGE material drawn with a BEIGE tint is darker than
   either. raylib's examples often set both.

   `map-key` is a key of `material-map-index`, defaulting to `:diffuse`."
  ([model color] (set-model-material-color! model 0 :diffuse color))
  ([model material-index map-key color]
   (let [materials (ffi/reinterpret (:materials model) (* material-size (inc material-index)))
         maps (ffi/read materials :pointer (+ (* material-size material-index)
                                              material-maps-offset))
         idx (material-map-index map-key)
         seg (ffi/reinterpret maps (* material-map-size (inc idx)))]
     (ffi/write seg rs/color color (+ (* material-map-size idx) material-map-color-offset))
     model)))

(ffi/defcfn set-material-texture!
  "Set a texture into one of a material's map slots.

   Takes a POINTER to the Material, which raylib mutates in place. For a
   model's own material, prefer `set-model-material-texture!` below."
  {:arglists '([material-ptr map-type texture])}
  "SetMaterialTexture"
  [:pointer :int rs/texture] :void)

(defn set-model-material-texture!
  "Set a texture into one of a model's material map slots, in place.

   raylib's own examples write `model.materials[0].maps[MAP].texture = tex`
   directly. There IS a function for this one - unlike the colour case - so
   this defers to `SetMaterialTexture` rather than walking pointers by hand.

   `(:materials model)` is already the address of `materials[0]`, so the
   zero-index case needs no arithmetic; a later index is offset by whole
   Material structs."
  ([model texture] (set-model-material-texture! model 0 :diffuse texture))
  ([model material-index map-key texture]
   (let [base (:materials model)
         ptr (if (zero? material-index)
               base
               (ffi/slice (ffi/reinterpret base (* material-size (inc material-index)))
                          (* material-size material-index)))]
     (set-material-texture! ptr (material-map-index map-key) texture)
     model)))
