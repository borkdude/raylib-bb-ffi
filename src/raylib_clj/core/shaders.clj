(ns raylib-clj.core.shaders
  "Shader loading and management functions"
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]))

;; Shader uniform types
(def SHADER_UNIFORM_FLOAT 0)
(def SHADER_UNIFORM_VEC2 1)
(def SHADER_UNIFORM_VEC3 2)
(def SHADER_UNIFORM_VEC4 3)
(def SHADER_UNIFORM_INT 4)
(def SHADER_UNIFORM_IVEC2 5)
(def SHADER_UNIFORM_IVEC3 6)
(def SHADER_UNIFORM_IVEC4 7)
;; raylib 6.0 inserted the four UINT variants at 8-11, moving SAMPLER2D to
;; 12. The constant is a plain int either way, so nothing errors if it is
;; wrong - the shader just binds the wrong slot and samples the wrong thing.
(def SHADER_UNIFORM_UINT 8)
(def SHADER_UNIFORM_UIVEC2 9)
(def SHADER_UNIFORM_UIVEC3 10)
(def SHADER_UNIFORM_UIVEC4 11)
(def SHADER_UNIFORM_SAMPLER2D 12)

;; Shader location indices
(def SHADER_LOC_VERTEX_POSITION 0)
(def SHADER_LOC_VERTEX_TEXCOORD01 1)
(def SHADER_LOC_VERTEX_TEXCOORD02 2)
(def SHADER_LOC_VERTEX_NORMAL 3)
(def SHADER_LOC_VERTEX_TANGENT 4)
(def SHADER_LOC_VERTEX_COLOR 5)
(def SHADER_LOC_MATRIX_MVP 6)
(def SHADER_LOC_MATRIX_VIEW 7)
(def SHADER_LOC_MATRIX_PROJECTION 8)
(def SHADER_LOC_MATRIX_MODEL 9)
(def SHADER_LOC_MATRIX_NORMAL 10)
(def SHADER_LOC_VECTOR_VIEW 11)
(def SHADER_LOC_COLOR_DIFFUSE 12)
(def SHADER_LOC_COLOR_SPECULAR 13)
(def SHADER_LOC_COLOR_AMBIENT 14)
(def SHADER_LOC_MAP_ALBEDO 15)
(def SHADER_LOC_MAP_METALNESS 16)
(def SHADER_LOC_MAP_NORMAL 17)
(def SHADER_LOC_MAP_ROUGHNESS 18)
(def SHADER_LOC_MAP_OCCLUSION 19)
(def SHADER_LOC_MAP_EMISSION 20)
(def SHADER_LOC_MAP_HEIGHT 21)
(def SHADER_LOC_MAP_CUBEMAP 22)
(def SHADER_LOC_MAP_IRRADIANCE 23)
(def SHADER_LOC_MAP_PREFILTER 24)
(def SHADER_LOC_MAP_BRDF 25)

;; Shader struct: { unsigned int id; int *locs; }
;; On 64-bit: id (4) + padding (4) + locs pointer (8) = 16 bytes
;; We treat this as an opaque 16-byte struct to avoid alignment issues
(def shader
  [:struct
   [[:id :int]
    [:_pad :int] ; padding for 8-byte alignment
    [:locs-lo :int] ; pointer as two ints
    [:locs-hi :int]]])

(ffi/defcfn load-shader
  "Load shader from files and bind default locations"
  {:arglists '([vs-filename fs-filename])}
  "LoadShader"
  [:string :string] shader)

(ffi/defcfn unload-shader!
  "Unload shader from GPU memory (VRAM)"
  {:arglists '([shader])}
  "UnloadShader"
  [shader] :void)

(ffi/defcfn get-shader-location
  "Get shader uniform location"
  {:arglists '([shader uniform-name])}
  "GetShaderLocation"
  [shader :string] :int)

(ffi/defcfn get-shader-location-attrib
  "Get shader attribute location"
  {:arglists '([shader attrib-name])}
  "GetShaderLocationAttrib"
  [shader :string] :int)

;; SetShaderValue needs special handling for different value types
;; We'll create helper functions for each type

(ffi/defcfn set-shader-value-raw!
  "Set shader uniform value (internal)"
  {:arglists '([shader loc-index value uniform-type])}
  "SetShaderValue"
  [shader :int :pointer :int] :void)

(ffi/defcfn set-shader-value-v-raw!
  "Set shader uniform value vector (internal)"
  {:arglists '([shader loc-index value uniform-type count])}
  "SetShaderValueV"
  [shader :int :pointer :int :int] :void)

(defn set-shader-value-ints!
  "Set a shader uniform to an array of ints.

   `values` is a flat sequence, `uniform-type` says how the shader groups
   it: SHADER_UNIFORM_IVEC3 with 30 ints is 10 vec3s, and `count` is the
   number of GROUPS, not the number of ints. Getting that wrong reads past
   the buffer, so it is derived here rather than passed in."
  [shader loc-index values uniform-type]
  (let [values (vec values)
        per-group (case (int uniform-type)
                    4 1     ; SHADER_UNIFORM_INT
                    5 2     ; SHADER_UNIFORM_IVEC2
                    6 3     ; SHADER_UNIFORM_IVEC3
                    7 4)    ; SHADER_UNIFORM_IVEC4
        n (count values)]
    (when-not (zero? (mod n per-group))
      (throw (ex-info "value count is not a multiple of the uniform's group size"
                      {:values n
                       :per-group per-group
                       :uniform-type uniform-type})))
    (with-open [arena (ffi/confined-arena)]
      (let [buf (ffi/alloc arena (* 4 (max 1 n)))]
        (when (pos? n) (ffi/write buf [:array :int n] values))
        (set-shader-value-v-raw! shader loc-index buf uniform-type (quot n per-group))))))

(defn- set-shader-value! [shader loc-index t values uniform-type]
  (with-open [arena (ffi/confined-arena)]
    (let [n (count values)
          buf (ffi/alloc arena (* 4 n))]
      (ffi/write buf [:array t n] values)
      (set-shader-value-raw! shader loc-index buf uniform-type))))

(defn set-shader-value-float!
  "Sets a float shader uniform."
  [shader loc-index value]
  (set-shader-value! shader loc-index :float [value] SHADER_UNIFORM_FLOAT))

(defn set-shader-value-vec2!
  "Sets a vec2 shader uniform from [x y]."
  [shader loc-index [x y]]
  (set-shader-value! shader loc-index :float [x y] SHADER_UNIFORM_VEC2))

(defn set-shader-value-vec3!
  "Sets a vec3 shader uniform from [x y z]."
  [shader loc-index [x y z]]
  (set-shader-value! shader loc-index :float [x y z] SHADER_UNIFORM_VEC3))

(defn set-shader-value-vec4!
  "Sets a vec4 shader uniform from [x y z w]."
  [shader loc-index [x y z w]]
  (set-shader-value! shader loc-index :float [x y z w] SHADER_UNIFORM_VEC4))

(defn set-shader-value-int!
  "Sets an int shader uniform."
  [shader loc-index value]
  (set-shader-value! shader loc-index :int [value] SHADER_UNIFORM_INT))

(ffi/defcfn begin-shader-mode!
  "Begin custom shader drawing"
  {:arglists '([shader])}
  "BeginShaderMode"
  [shader] :void)

(ffi/defcfn end-shader-mode!
  "End custom shader drawing (use default shader)"
  {:arglists '([])}
  "EndShaderMode"
  [] :void)
