(ns raylib-clj.textures.texture-loading
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

(ffi/defcfn load-texture!
  "Load texture from file into GPU memory (VRAM)"
  {:arglists '([filename])}
  "LoadTexture"
  [:string] rs/texture)

; ...

;; Moved from raylib-ext (2026-08-22 consolidation)
(ffi/defcfn load-image
  "Load image from file into CPU memory (RAM)"
  {:arglists '([filename])}
  "LoadImage"
  [:string] rs/image)

(ffi/defcfn is-image-valid?
  "Check if an image is valid (data loaded, dimensions and format set)"
  {:arglists '([image])}
  "IsImageValid"
  [rs/image] :bool)

(ffi/defcfn unload-image!
  "Unload image from CPU memory (RAM)"
  {:arglists '([image])}
  "UnloadImage"
  [rs/image] :void)

(ffi/defcfn load-texture-from-image
  "Load texture from image data. The image stays on the CPU and is yours to
   unload separately - this uploads a copy to the GPU."
  {:arglists '([image])}
  "LoadTextureFromImage"
  [rs/image] rs/texture)

(ffi/defcfn load-render-texture!
  "Load texture for rendering (framebuffer)"
  {:arglists '([width height])}
  "LoadRenderTexture"
  [:int :int] rs/render-texture)

(ffi/defcfn unload-render-texture!
  "Unload render texture from GPU memory (VRAM)"
  {:arglists '([target])}
  "UnloadRenderTexture"
  [rs/render-texture] :void)

(ffi/defcfn begin-texture-mode!
  "Begin drawing to render texture"
  {:arglists '([target])}
  "BeginTextureMode"
  [rs/render-texture] :void)

(ffi/defcfn end-texture-mode!
  "End drawing to render texture"
  "EndTextureMode"
  [] :void)

;; Advanced texture drawing

(ffi/defcfn draw-texture-pro!
  "Draw a part of a texture defined by a rectangle with 'pro' parameters"
  {:arglists '([texture source dest origin rotation tint])}
  "DrawTexturePro"
  [rs/texture rs/rectangle rs/rectangle rs/vector-2 :float rs/color] :void)

(ffi/defcfn draw-texture-v!
  "Draw a Texture2D with position defined as Vector2"
  {:arglists '([texture position tint])}
  "DrawTextureV"
  [rs/texture rs/vector-2 rs/color] :void)

(ffi/defcfn draw-texture-rec!
  "Draw a part of a texture defined by a rectangle"
  {:arglists '([texture source position tint])}
  "DrawTextureRec"
  [rs/texture rs/rectangle rs/vector-2 rs/color] :void)

(ffi/defcfn draw-texture-ex!
  "Draw a texture with extended parameters"
  {:arglists '([texture position rotation scale tint])}
  "DrawTextureEx"
  [rs/texture rs/vector-2 :float :float rs/color] :void)

(ffi/defcfn unload-texture!
  "Unload texture from GPU memory (VRAM)"
  {:arglists '([texture])}
  "UnloadTexture"
  [rs/texture] :void)

(def texture-filter
  "Texture scaling filter modes, for `set-texture-filter!`."
  {:point 0
   :bilinear 1
   :trilinear 2
   :anisotropic-4x 3
   :anisotropic-8x 4
   :anisotropic-16x 5})

(ffi/defcfn set-texture-filter!
  "Set texture scaling filter mode"
  {:arglists '([texture filter])}
  "SetTextureFilter"
  [rs/texture :int] :void)
