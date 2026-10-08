(ns raylib.images
  "raylib's CPU-side Image: generators, color and geometry processing, the
  ImageDraw family, and the round trip to and from GPU textures.
  The generators return an rlgl texture id.
  The other functions take an Image, a pointer to a native Image struct that
  the Image* functions change in place.
  Release an Image with unload-image!."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native :as native]))

(def ^:private image-layout
  [:struct [[:data :pointer] [:width :int] [:height :int]
            [:mipmaps :int] [:format :int]]])

(def ^:private width-place (ffi/place image-layout :width))
(def ^:private height-place (ffi/place image-layout :height))

(defn- image-ptr
  "Returns a new Image pointer holding the Image map m."
  [m]
  (doto (ffi/alloc (ffi/auto-arena) image-layout)
    (ffi/write image-layout m)))

(defn- image-val
  "Returns the Image map at pointer img."
  [img]
  (ffi/read img image-layout))

(defn image-width
  "Returns the current width of img."
  [img]
  (ffi/read img width-place))

(defn image-height
  "Returns the current height of img."
  [img]
  (ffi/read img height-place))

(ffi/defcfn ^:private gen-image-color-raw "GenImageColor" [:int :int :uint] image-layout)
(ffi/defcfn ^:private gen-image-checked-raw "GenImageChecked" [:int :int :int :int :uint :uint] image-layout)
(ffi/defcfn ^:private gen-image-gradient-linear-raw "GenImageGradientLinear" [:int :int :int :uint :uint] image-layout)
(ffi/defcfn ^:private gen-image-gradient-radial-raw "GenImageGradientRadial" [:int :int :float :uint :uint] image-layout)
(ffi/defcfn ^:private gen-image-gradient-square-raw "GenImageGradientSquare" [:int :int :float :uint :uint] image-layout)
(ffi/defcfn ^:private gen-image-white-noise-raw "GenImageWhiteNoise" [:int :int :float] image-layout)
(ffi/defcfn ^:private gen-image-perlin-noise-raw "GenImagePerlinNoise" [:int :int :int :int :float] image-layout)
(ffi/defcfn ^:private gen-image-cellular-raw "GenImageCellular" [:int :int :int] image-layout)
(ffi/defcfn ^:private gen-image-text-raw "GenImageText" [:int :int :string] image-layout)
(ffi/defcfn ^:private unload-image-raw "UnloadImage" [image-layout] :void)
(ffi/defcfn ^:private load-texture-from-image-raw "LoadTextureFromImage"
  [image-layout] native/texture2d-layout)

(defn- with-image
  "Uploads the Image map that (f) returns, unloads it, and returns the texture
  id, or 0 if the upload fails."
  [f]
  (let [img (f)]
    (try
      (:id (load-texture-from-image-raw img))
      (finally (unload-image-raw img)))))

(defn image-color
  "GenImageColor as a texture id: a plain `w` x `h` field of one colour."
  [w h color]
  (with-image (fn [] (gen-image-color-raw w h color))))

(defn image-checked
  "GenImageChecked as a texture id: `checks-x` by `checks-y` squares alternating
  between two colours."
  [w h checks-x checks-y c1 c2]
  (with-image (fn [] (gen-image-checked-raw w h checks-x checks-y c1 c2))))

(defn image-gradient-linear
  "GenImageGradientLinear as a texture id. `direction` is in degrees, 0 vertical."
  [w h direction start end]
  (with-image (fn [] (gen-image-gradient-linear-raw w h direction start end))))

(defn image-gradient-radial
  "GenImageGradientRadial as a texture id, `density` shaping the falloff."
  [w h density inner outer]
  (with-image (fn [] (gen-image-gradient-radial-raw w h density inner outer))))

(defn image-gradient-square
  "GenImageGradientSquare as a texture id, `density` shaping the falloff."
  [w h density inner outer]
  (with-image (fn [] (gen-image-gradient-square-raw w h density inner outer))))

(defn image-white-noise
  "GenImageWhiteNoise as a texture id. `factor` is the fraction of white pixels."
  [w h factor]
  (with-image (fn [] (gen-image-white-noise-raw w h factor))))

(defn image-perlin-noise
  "GenImagePerlinNoise as a texture id. The offsets slide the sample window, so
  animating one of them scrolls the field rather than regenerating it."
  [w h offset-x offset-y scale]
  (with-image (fn [] (gen-image-perlin-noise-raw w h offset-x offset-y scale))))

(defn image-cellular
  "GenImageCellular as a texture id. A bigger `tile-size` means bigger cells."
  [w h tile-size]
  (with-image (fn [] (gen-image-cellular-raw w h tile-size))))

(defn image-text
  "GenImageText as a texture id: `text` rasterised with raylib's default font
  into a `w` x `h` greyscale field."
  [w h text]
  (with-image (fn [] (gen-image-text-raw w h text))))

(ffi/defcfn ^:private load-image-from-texture-raw "LoadImageFromTexture"
  [native/texture2d-layout] image-layout)
(ffi/defcfn ^:private image-copy-raw "ImageCopy" [image-layout] image-layout)
(ffi/defcfn ^:private image-format-raw         "ImageFormat"          [:pointer :int] :void)
(ffi/defcfn ^:private image-color-invert-raw   "ImageColorInvert"     [:pointer] :void)
(ffi/defcfn ^:private image-color-grayscale-raw "ImageColorGrayscale" [:pointer] :void)
(ffi/defcfn ^:private image-color-tint-raw     "ImageColorTint"       [:pointer :uint] :void)
(ffi/defcfn ^:private image-color-contrast-raw "ImageColorContrast"   [:pointer :float] :void)
(ffi/defcfn ^:private image-color-brightness-raw "ImageColorBrightness" [:pointer :int] :void)
(ffi/defcfn ^:private image-flip-horizontal-raw "ImageFlipHorizontal" [:pointer] :void)
(ffi/defcfn ^:private image-flip-vertical-raw  "ImageFlipVertical"    [:pointer] :void)
(ffi/defcfn ^:private image-blur-gaussian-raw  "ImageBlurGaussian"    [:pointer :int] :void)

(defn image-from-texture!
  "Reads the RGBA8 texture tex-id of size w by h back from the GPU.
  Returns a new Image."
  [tex-id w h]
  (image-ptr (load-image-from-texture-raw (native/texture2d tex-id w h))))

(defn image-copy!
  "Returns a new Image with a copy of img."
  [img]
  (image-ptr (image-copy-raw (image-val img))))

(defn unload-image!
  "Unloads the pixel data of img."
  [img]
  (unload-image-raw (image-val img)))

(defn image->texture
  "Uploads img to the GPU and returns the rlgl texture id.
  img stays valid."
  [img]
  (:id (load-texture-from-image-raw (image-val img))))

(defn image-format!
  "Converts img in place to pixel format, such as PIXELFORMAT-R8G8B8A8."
  [img format]
  (image-format-raw img format))

(defn image-color-invert! [img] (image-color-invert-raw img))
(defn image-color-grayscale! [img] (image-color-grayscale-raw img))
(defn image-color-tint! [img color] (image-color-tint-raw img color))
(defn image-color-contrast! [img contrast] (image-color-contrast-raw img contrast))
(defn image-color-brightness! [img brightness] (image-color-brightness-raw img brightness))
(defn image-flip-horizontal! [img] (image-flip-horizontal-raw img))
(defn image-flip-vertical! [img] (image-flip-vertical-raw img))
(defn image-blur-gaussian! [img size] (image-blur-gaussian-raw img size))

(ffi/defcfn ^:private image-clear-background-raw "ImageClearBackground" [:pointer :uint] :void)
(ffi/defcfn ^:private image-draw-pixel-raw     "ImageDrawPixel"     [:pointer :int :int :uint] :void)
(ffi/defcfn ^:private image-draw-line-raw      "ImageDrawLine"      [:pointer :int :int :int :int :uint] :void)
(ffi/defcfn ^:private image-draw-circle-raw    "ImageDrawCircle"    [:pointer :int :int :int :uint] :void)
(ffi/defcfn ^:private image-draw-rectangle-raw "ImageDrawRectangle" [:pointer :int :int :int :int :uint] :void)
(ffi/defcfn ^:private image-draw-text-raw      "ImageDrawText"      [:pointer :string :int :int :int :uint] :void)
(ffi/defcfn ^:private image-rotate-raw    "ImageRotate"    [:pointer :int] :void)
(ffi/defcfn ^:private image-rotate-cw-raw  "ImageRotateCW"  [:pointer] :void)
(ffi/defcfn ^:private image-rotate-ccw-raw "ImageRotateCCW" [:pointer] :void)
(ffi/defcfn ^:private image-from-channel-raw "ImageFromChannel"
  [image-layout :int] image-layout)
(ffi/defcfn ^:private image-alpha-mask-raw "ImageAlphaMask"
  [:pointer image-layout] :void)

(defn image-clear-background!
  "ImageClearBackground, in place: fill the whole buffer with `color`."
  [img color]
  (image-clear-background-raw img color))

(defn image-draw-pixel!
  "ImageDrawPixel, in place, at (`x`,`y`)."
  [img x y color]
  (image-draw-pixel-raw img x y color))

(defn image-draw-line!
  "ImageDrawLine, in place, from (`start-x`,`start-y`) to (`end-x`,`end-y`)."
  [img start-x start-y end-x end-y color]
  (image-draw-line-raw img start-x start-y end-x end-y color))

(defn image-draw-circle!
  "ImageDrawCircle, in place: a filled circle centred at (`center-x`,`center-y`)."
  [img center-x center-y radius color]
  (image-draw-circle-raw img center-x center-y radius color))

(defn image-draw-rectangle!
  "ImageDrawRectangle, in place, top-left at (`x`,`y`)."
  [img x y width height color]
  (image-draw-rectangle-raw img x y width height color))

(defn image-draw-text!
  "ImageDrawText, in place: text in raylib's default font at (`x`,`y`)."
  [img text x y font-size color]
  (image-draw-text-raw img text x y font-size color))

(defn image-rotate!
  "ImageRotate, in place, by degrees from -359 to 359.
  An angle that is not a multiple of 90 changes the size of img."
  [img degrees]
  (image-rotate-raw img degrees))

(defn image-rotate-cw! [img] (image-rotate-cw-raw img))
(defn image-rotate-ccw! [img] (image-rotate-ccw-raw img))

(defn image-from-channel
  "Returns a new grayscale Image of one channel of img.
  selected-channel is 0 for red, 1 for green, 2 for blue or 3 for alpha."
  [img selected-channel]
  (image-ptr (image-from-channel-raw (image-val img) selected-channel)))

(defn image-alpha-mask!
  "ImageAlphaMask, in place: copies the pixels of mask into the alpha channel
  of img.
  mask stays valid."
  [img mask]
  (image-alpha-mask-raw img (image-val mask)))

(ffi/defcfn ^:private image-crop-raw "ImageCrop"
  [:pointer native/rectangle-layout] :void)
(ffi/defcfn ^:private image-kernel-convolution-raw "ImageKernelConvolution"
  [:pointer :pointer :int] :void)
(ffi/defcfn ^:private image-resize-raw "ImageResize" [:pointer :int :int] :void)

(defn image-crop!
  "ImageCrop, in place. :x :y :width :height in pixels."
  [img & {:keys [x y width height]
          :or {x 0
               y 0
               width 1
               height 1}}]
  (image-crop-raw img {:x x :y y :width width :height height}))

(defn image-resize!
  "ImageResize, in place, bicubic."
  [img w h]
  (image-resize-raw img w h))

(defn image-convolve!
  "ImageKernelConvolution, in place.
  kernel is a flat sequence of floats with a square count, 9 for a 3x3 kernel."
  [img kernel]
  (native/staged :float kernel (fn [p] (image-kernel-convolution-raw img p (count kernel)))))
