(ns raylib.textures
  "GPU textures and render targets through rlgl: upload, filter and wrap
  state, drawing a texture id as a quad, and framebuffers.
  A texture is its rlgl texture id."
  (:require
   [babashka.ffi :as ffi]
   [raylib.color :as color]
   [raylib.core :as core]
   [raylib.native :as native]
   [raylib.rlgl :as rlgl]))

(ffi/defcfn rl-load-texture       "rlLoadTexture"       [:pointer :int :int :int :int] :uint)
(ffi/defcfn rl-unload-texture     "rlUnloadTexture"     [:uint] :void)
(ffi/defcfn rl-update-texture     "rlUpdateTexture"     [:uint :int :int :int :int :int :pointer] :void)
(ffi/defcfn rl-texture-parameters "rlTextureParameters" [:uint :int :int] :void)
(ffi/defcfn rl-set-texture        "rlSetTexture"        [:uint] :void)
(ffi/defcfn rl-tex-coord-2f       "rlTexCoord2f"        [:float :float] :void)
(ffi/defcfn rl-normal-3f          "rlNormal3f"          [:float :float :float] :void)

(def ^:const RL-QUADS 7)
(def ^:const RL-TEXTURE-WRAP-S 0x2802)        (def ^:const RL-TEXTURE-WRAP-T 0x2803)
(def ^:const RL-TEXTURE-WRAP-REPEAT 0x2901)   (def ^:const RL-TEXTURE-WRAP-CLAMP 0x812F)
(def ^:const RL-TEXTURE-MAG-FILTER 0x2800)    (def ^:const RL-TEXTURE-MIN-FILTER 0x2801)
(def ^:const RL-TEXTURE-FILTER-NEAREST 0x2600)
(def ^:const RL-TEXTURE-FILTER-LINEAR 0x2601)

(defn texture-filter!
  "Set both min and mag filters on a texture id (RL-TEXTURE-FILTER-NEAREST for
  crisp pixel art, RL-TEXTURE-FILTER-LINEAR for smooth scaling)."
  [id filter]
  (rl-texture-parameters id RL-TEXTURE-MIN-FILTER filter)
  (rl-texture-parameters id RL-TEXTURE-MAG-FILTER filter))

(defn texture-wrap!
  "Set both S and T wrap modes on a texture id (REPEAT lets texcoords past 1.0
  tile the image, CLAMP stretches the edge pixel)."
  [id wrap]
  (rl-texture-parameters id RL-TEXTURE-WRAP-S wrap)
  (rl-texture-parameters id RL-TEXTURE-WRAP-T wrap))

(defn- with-pixels
  "Calls g with a native RGBA8 buffer of w by h pixels filled from (f x y).
  f returns a packed Color."
  [w h f g]
  (let [arr (int-array (* w h))]
    (dotimes [y h]
      (dotimes [x w]
        (aset arr (+ x (* y w)) (unchecked-int (f x y)))))
    (with-open [arena (ffi/confined-arena)]
      (let [buf (ffi/alloc arena (* w h 4))]
        (ffi/write-array buf :uint arr)
        (g buf)))))

(defn texture-from-fn
  "Uploads a w by h RGBA8 texture from (f x y), which returns a packed Color.
  Returns the rlgl texture id, with nearest filtering and repeat wrapping.
  Release it with unload-texture!."
  [w h f]
  (with-pixels w h f
    (fn [buf]
      (let [id (rl-load-texture buf w h native/PIXELFORMAT-R8G8B8A8 1)]
        (texture-filter! id RL-TEXTURE-FILTER-NEAREST)
        (texture-wrap! id RL-TEXTURE-WRAP-REPEAT)
        id))))

(defn update-texture-from-fn!
  "Replaces all w by h RGBA8 texels of texture id with (f x y), which returns a
  packed Color."
  [id w h f]
  (with-pixels w h f
    (fn [buf]
      (rl-update-texture id 0 0 w h native/PIXELFORMAT-R8G8B8A8 buf))))

(defn unload-texture!
  "Unloads a texture id created by texture-from-fn."
  [id]
  (rl-unload-texture id))

(defn texture!
  "Draws texture id as a quad with the winding of DrawTexturePro.
    :x :y                  position of the origin on screen
    :width :height         destination size
    :u0 :v0 :u1 :v1        source texcoords, the whole texture by default.
                           Values past 1.0 tile with RL-TEXTURE-WRAP-REPEAT.
                           v0 > v1 flips vertically, for a render texture.
    :rotation              degrees clockwise about the origin, 0 by default
    :origin-x :origin-y    pivot offset into the destination, 0 0 by default
    :tint                  packed Color multiplied into the texels, WHITE by default"
  [id & {:keys [x y width height u0 v0 u1 v1 tint rotation origin-x origin-y]
         :or {x 0
              y 0
              width 100
              height 100
              u0 0.0
              v0 0.0
              u1 1.0
              v1 1.0
              rotation 0.0
              origin-x 0.0
              origin-y 0.0
              tint color/WHITE}}]
  (let [px (double x) py (double y)
        ox (double origin-x) oy (double origin-y)
        lx (- ox) rx (- (double width) ox)
        ty (- oy) by (- (double height) oy)
        rad (Math/toRadians (double rotation))
        c (Math/cos rad) s (Math/sin rad)
        vx (fn [dx dy] (+ px (- (* dx c) (* dy s))))
        vy (fn [dx dy] (+ py (* dx s) (* dy c)))]
    (rl-set-texture id)
    (rlgl/rl-begin RL-QUADS)
    (rlgl/rl-color! tint)
    (rl-normal-3f 0.0 0.0 1.0)
    (rl-tex-coord-2f u0 v0) (rlgl/rl-vertex-2f (vx lx ty) (vy lx ty))
    (rl-tex-coord-2f u0 v1) (rlgl/rl-vertex-2f (vx lx by) (vy lx by))
    (rl-tex-coord-2f u1 v1) (rlgl/rl-vertex-2f (vx rx by) (vy rx by))
    (rl-tex-coord-2f u1 v0) (rlgl/rl-vertex-2f (vx rx ty) (vy rx ty))
    (rlgl/rl-end)
    (rl-set-texture 0)))

(ffi/defcfn rl-load-framebuffer      "rlLoadFramebuffer"      [] :uint)
(ffi/defcfn rl-framebuffer-attach    "rlFramebufferAttach"    [:uint :uint :int :int :int] :void)
(ffi/defcfn rl-enable-framebuffer    "rlEnableFramebuffer"    [:uint] :void)
(ffi/defcfn rl-disable-framebuffer   "rlDisableFramebuffer"   [] :void)
(ffi/defcfn rl-unload-framebuffer    "rlUnloadFramebuffer"    [:uint] :void)
(ffi/defcfn rl-load-texture-depth    "rlLoadTextureDepth"     [:int :int :int] :uint)
(ffi/defcfn rl-viewport              "rlViewport"             [:int :int :int :int] :void)
(ffi/defcfn rl-matrix-mode           "rlMatrixMode"           [:int] :void)
(ffi/defcfn rl-load-identity         "rlLoadIdentity"         [] :void)
(ffi/defcfn rl-ortho                 "rlOrtho"                [:double :double :double :double :double :double] :void)
(ffi/defcfn rl-set-framebuffer-width  "rlSetFramebufferWidth"  [:int] :void)
(ffi/defcfn rl-set-framebuffer-height "rlSetFramebufferHeight" [:int] :void)
(ffi/defcfn rl-get-framebuffer-width  "rlGetFramebufferWidth"  [] :int)
(ffi/defcfn rl-get-framebuffer-height "rlGetFramebufferHeight" [] :int)
(ffi/defcfn rl-mult-matrix-f         "rlMultMatrixf"          [:pointer] :void)
(ffi/defcfn get-render-width         "GetRenderWidth"         [] :int)
(ffi/defcfn get-render-height        "GetRenderHeight"        [] :int)
(ffi/defcfn ^:private framebuffer-complete? "rlFramebufferComplete" [:uint] :bool)

(def ^:const RL-PROJECTION 0x1701)
(def ^:const RL-MODELVIEW  0x1700)
(def ^:const RL-ATTACHMENT-COLOR-CHANNEL0 0)
(def ^:const RL-ATTACHMENT-DEPTH 100)
(def ^:const RL-ATTACHMENT-TEXTURE2D 100)
(def ^:const RL-ATTACHMENT-RENDERBUFFER 200)

(defn render-texture
  "Creates a render target of w by h pixels with an RGBA8 color texture and a
  depth renderbuffer.
  Returns {:fbo :texture :width :height}, or nil if the framebuffer is
  incomplete.
  Release it with unload-render-texture!."
  [w h]
  (let [fbo (rl-load-framebuffer)
        tex (texture-from-fn w h (fn [_ _] (color/rgba 0 0 0 0)))
        depth (rl-load-texture-depth w h 1)]
    (texture-filter! tex RL-TEXTURE-FILTER-LINEAR)
    (texture-wrap! tex RL-TEXTURE-WRAP-CLAMP)
    (rl-framebuffer-attach fbo tex RL-ATTACHMENT-COLOR-CHANNEL0 RL-ATTACHMENT-TEXTURE2D 0)
    (rl-framebuffer-attach fbo depth RL-ATTACHMENT-DEPTH RL-ATTACHMENT-RENDERBUFFER 0)
    (when (framebuffer-complete? fbo)
      {:fbo fbo
       :texture tex
       :width w
       :height h})))

(defn unload-render-texture!
  "Unloads the framebuffer, its color texture and its depth renderbuffer."
  [{:keys [fbo texture]}]
  (rl-unload-texture texture)
  (rl-unload-framebuffer fbo))

(defn- restore-screen-projection!
  "Restores the viewport and matrices of window drawing, including the
  modelview scale of render size over screen size on a HiDPI display."
  []
  (let [rw (get-render-width)
        rh (get-render-height)
        sx (/ (double rw) (max 1 (core/get-screen-width)))
        sy (/ (double rh) (max 1 (core/get-screen-height)))]
    (rl-viewport 0 0 rw rh)
    (rl-set-framebuffer-width rw)
    (rl-set-framebuffer-height rh)
    (rl-matrix-mode RL-PROJECTION)
    (rl-load-identity)
    (rl-ortho 0.0 rw rh 0.0 0.0 1.0)
    (rl-matrix-mode RL-MODELVIEW)
    (rl-load-identity)
    (with-open [arena (ffi/confined-arena)]
      (let [m (ffi/alloc arena native/matrix-layout)]
        (ffi/write m [:array :float 16]
                   [sx 0 0 0, 0 sy 0 0, 0 0 1 0, 0 0 0 1])
        (rl-mult-matrix-f m)))))

(defn with-render-texture
  "Calls f with drawing redirected into render target rt, then restores
  window drawing.
  The texture is bottom-up, so draw it with :v0 1.0 :v1 0.0."
  [{:keys [fbo width height]} f]
  (rlgl/flush-batch)
  (rl-enable-framebuffer fbo)
  (rl-viewport 0 0 width height)
  (rl-set-framebuffer-width width)
  (rl-set-framebuffer-height height)
  (rl-matrix-mode RL-PROJECTION)
  (rl-load-identity)
  (rl-ortho 0.0 width height 0.0 0.0 1.0)
  (rl-matrix-mode RL-MODELVIEW)
  (rl-load-identity)
  (try
    (f)
    (finally
      (rlgl/flush-batch)
      (rl-disable-framebuffer)
      (restore-screen-projection!))))
