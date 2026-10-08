(ns raylib-clj.core.gestures
  "Touch gesture detection functions"
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]
   [raylib-clj.structs :as rs]))

;; Gesture constants
(def GESTURE_NONE 0)
(def GESTURE_TAP 1)
(def GESTURE_DOUBLETAP 2)
(def GESTURE_HOLD 4)
(def GESTURE_DRAG 8)
(def GESTURE_SWIPE_RIGHT 16)
(def GESTURE_SWIPE_LEFT 32)
(def GESTURE_SWIPE_UP 64)
(def GESTURE_SWIPE_DOWN 128)
(def GESTURE_PINCH_IN 256)
(def GESTURE_PINCH_OUT 512)

;; Gesture functions
(ffi/defcfn set-gestures-enabled!
  "Enable a set of gestures using flags"
  {:arglists '([flags])}
  "SetGesturesEnabled"
  [:int] :void)

(ffi/defcfn is-gesture-detected?
  "Check if a gesture have been detected"
  {:arglists '([gesture])}
  "IsGestureDetected"
  [:int] :int8)

(ffi/defcfn get-gesture-detected
  "Get latest detected gesture"
  "GetGestureDetected"
  [] :int)

(ffi/defcfn get-gesture-hold-duration
  "Get gesture hold time in seconds"
  "GetGestureHoldDuration"
  [] :float)

(ffi/defcfn get-gesture-drag-vector
  "Get gesture drag vector"
  "GetGestureDragVector"
  [] rs/vector-2)

(ffi/defcfn get-gesture-drag-angle
  "Get gesture drag angle"
  "GetGestureDragAngle"
  [] :float)

(ffi/defcfn get-gesture-pinch-vector
  "Get gesture pinch delta"
  "GetGesturePinchVector"
  [] rs/vector-2)

(ffi/defcfn get-gesture-pinch-angle
  "Get gesture pinch angle"
  "GetGesturePinchAngle"
  [] :float)

;; Touch functions
(ffi/defcfn get-touch-x
  "Get touch position X for touch point 0 (relative to screen size)"
  "GetTouchX"
  [] :int)

(ffi/defcfn get-touch-y
  "Get touch position Y for touch point 0 (relative to screen size)"
  "GetTouchY"
  [] :int)

(ffi/defcfn get-touch-position
  "Get touch position XY for a touch point index (relative to screen size)"
  {:arglists '([index])}
  "GetTouchPosition"
  [:int] rs/vector-2)

(ffi/defcfn get-touch-point-id
  "Get touch point identifier for given index"
  {:arglists '([index])}
  "GetTouchPointId"
  [:int] :int)

(ffi/defcfn get-touch-point-count
  "Get number of touch points"
  "GetTouchPointCount"
  [] :int)
