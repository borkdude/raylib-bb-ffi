(ns raylib.rlgl
  "rlgl, raylib's immediate-mode drawing layer: vertices, colors, backface
  culling, and flush-batch."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native]))

(ffi/defcfn rl-begin     "rlBegin"     [:int] :void)
(ffi/defcfn rl-end       "rlEnd"       [] :void)
(ffi/defcfn rl-vertex-2f "rlVertex2f"  [:float :float] :void)
(ffi/defcfn rl-color-4ub "rlColor4ub"  [:uint8 :uint8 :uint8 :uint8] :void)

(def ^:const RL-LINES 1)
(def ^:const RL-TRIANGLES 4)

(defn rl-color!
  "Sets the rlgl vertex color from a packed Color."
  [color]
  (rl-color-4ub (bit-and color 0xff)
                (bit-and (bit-shift-right color 8) 0xff)
                (bit-and (bit-shift-right color 16) 0xff)
                (bit-and (bit-shift-right color 24) 0xff)))

(ffi/defcfn flush-batch
  "Draws the pending render batch.
  Call before reading pixels in the middle of a frame."
  "rlDrawRenderBatchActive" [] :void)

(ffi/defcfn rl-disable-backface-culling "rlDisableBackfaceCulling" [] :void)
(ffi/defcfn rl-enable-backface-culling  "rlEnableBackfaceCulling"  [] :void)
