(ns raylib.shaders
  "Shader compile and link, with-shader, and the uniform setters.
  A Shader is a map with :id and :locs."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native :as native]))

(def shader-layout
  "Layout of raylib's Shader: {uint id; int *locs}."
  [:struct [[:id :uint] [:locs :pointer]]])

(ffi/defcfn ^:private load-shader-from-memory "LoadShaderFromMemory"
  [:string :string] shader-layout)
(ffi/defcfn ^:private begin-shader-mode "BeginShaderMode" [shader-layout] :void)
(ffi/defcfn end-shader-mode "EndShaderMode" [] :void)
(ffi/defcfn ^:private get-shader-location "GetShaderLocation"
  [shader-layout :string] :int)
(ffi/defcfn ^:private set-shader-value-raw "SetShaderValue"
  [shader-layout :int :pointer :int] :void)
(ffi/defcfn ^:private set-shader-value-v-raw "SetShaderValueV"
  [shader-layout :int :pointer :int :int] :void)
(ffi/defcfn ^:private unload-shader-raw "UnloadShader" [shader-layout] :void)
(ffi/defcfn ^:private set-shader-value-texture-raw "SetShaderValueTexture"
  [shader-layout :int native/texture2d-layout] :void)

;; ShaderUniformDataType of raylib 6.0.
(def ^:const UNIFORM-FLOAT 0)  (def ^:const UNIFORM-VEC2 1)
(def ^:const UNIFORM-VEC3 2)   (def ^:const UNIFORM-VEC4 3)
(def ^:const UNIFORM-INT 4)    (def ^:const UNIFORM-IVEC2 5)
(def ^:const UNIFORM-IVEC3 6)  (def ^:const UNIFORM-IVEC4 7)
(def ^:const UNIFORM-SAMPLER2D 12)

(defn- linked [sh]
  (when (pos? (:id sh)) sh))

(defn shader
  "Compiles fs-source as a fragment shader with raylib's default vertex shader.
  Returns the Shader, or nil if the program does not link.
  raylib prints the compiler log.
  The source must start with #version 330."
  [fs-source]
  (linked (load-shader-from-memory nil fs-source)))

(defn shader-vf
  "Compiles vs-source and fs-source into one program.
  Returns the Shader, or nil if the program does not link."
  [vs-source fs-source]
  (linked (load-shader-from-memory vs-source fs-source)))

(defn unload-shader!
  "Unloads the Shader from the GPU."
  [sh]
  (unload-shader-raw sh))

(defn uniform-loc
  "Returns the location of the uniform name, or -1 if the shader does not
  declare it."
  [sh name]
  (get-shader-location sh name))

(defn with-shader
  "Calls f with sh active."
  [sh f]
  (begin-shader-mode sh)
  (try
    (f)
    (finally (end-shader-mode))))

;; Each setter skips a location of -1.
(defn set-uniform-float!
  [sh loc v]
  (when (nat-int? loc)
    (native/staged :float [v] (fn [p] (set-shader-value-raw sh loc p UNIFORM-FLOAT)))))

(defn set-uniform-vec2!
  [sh loc x y]
  (when (nat-int? loc)
    (native/staged :float [x y] (fn [p] (set-shader-value-raw sh loc p UNIFORM-VEC2)))))

(defn set-uniform-vec3!
  [sh loc x y z]
  (when (nat-int? loc)
    (native/staged :float [x y z] (fn [p] (set-shader-value-raw sh loc p UNIFORM-VEC3)))))

(defn set-uniform-vec4!
  [sh loc x y z w]
  (when (nat-int? loc)
    (native/staged :float [x y z w] (fn [p] (set-shader-value-raw sh loc p UNIFORM-VEC4)))))

(defn set-uniform-int!
  [sh loc v]
  (when (nat-int? loc)
    (native/staged :int [v] (fn [p] (set-shader-value-raw sh loc p UNIFORM-INT)))))

(defn set-uniform-ivec3-array!
  "Sets an array of n ivec3 uniforms from a flat sequence of 3n ints."
  [sh loc ints n]
  (when (nat-int? loc)
    (native/staged :int ints (fn [p] (set-shader-value-v-raw sh loc p UNIFORM-IVEC3 n)))))

(defn set-uniform-texture!
  "Binds the RGBA8 texture tex-id of size w by h to a sampler2D uniform.
  raylib binds the drawn texture to slot 0."
  [sh loc tex-id w h]
  (when (nat-int? loc)
    (set-shader-value-texture-raw sh loc (native/texture2d tex-id w h))))

(ffi/defcfn rl-enable-shader "rlEnableShader" [:uint] :void)
(ffi/defcfn rl-active-texture-slot "rlActiveTextureSlot" [:int] :void)
(ffi/defcfn rl-enable-texture "rlEnableTexture" [:uint] :void)
(ffi/defcfn rl-set-uniform-sampler "rlSetUniformSampler" [:int :uint] :void)

(defn bind-sampler!
  "Binds tex-id to texture slot and points the sampler at loc to that slot.
  Use this instead of set-uniform-texture! for a sampler of the vertex stage
  or for DrawMesh.
  Call once after the shader links.
  Returns sh."
  [sh loc tex-id slot]
  (when (nat-int? loc)
    (rl-enable-shader (:id sh))
    (rl-active-texture-slot slot)
    (rl-enable-texture tex-id)
    (rl-set-uniform-sampler loc slot))
  sh)
