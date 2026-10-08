(ns raylib-clj.core.gamepad
  "Gamepad input functions"
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]))

;; Gamepad buttons
(def GAMEPAD_BUTTON_UNKNOWN 0)
(def GAMEPAD_BUTTON_LEFT_FACE_UP 1)
(def GAMEPAD_BUTTON_LEFT_FACE_RIGHT 2)
(def GAMEPAD_BUTTON_LEFT_FACE_DOWN 3)
(def GAMEPAD_BUTTON_LEFT_FACE_LEFT 4)
(def GAMEPAD_BUTTON_RIGHT_FACE_UP 5)
(def GAMEPAD_BUTTON_RIGHT_FACE_RIGHT 6)
(def GAMEPAD_BUTTON_RIGHT_FACE_DOWN 7)
(def GAMEPAD_BUTTON_RIGHT_FACE_LEFT 8)
(def GAMEPAD_BUTTON_LEFT_TRIGGER_1 9)
(def GAMEPAD_BUTTON_LEFT_TRIGGER_2 10)
(def GAMEPAD_BUTTON_RIGHT_TRIGGER_1 11)
(def GAMEPAD_BUTTON_RIGHT_TRIGGER_2 12)
(def GAMEPAD_BUTTON_MIDDLE_LEFT 13)
(def GAMEPAD_BUTTON_MIDDLE 14)
(def GAMEPAD_BUTTON_MIDDLE_RIGHT 15)
(def GAMEPAD_BUTTON_LEFT_THUMB 16)
(def GAMEPAD_BUTTON_RIGHT_THUMB 17)

;; Gamepad axis
(def GAMEPAD_AXIS_LEFT_X 0)
(def GAMEPAD_AXIS_LEFT_Y 1)
(def GAMEPAD_AXIS_RIGHT_X 2)
(def GAMEPAD_AXIS_RIGHT_Y 3)
(def GAMEPAD_AXIS_LEFT_TRIGGER 4)
(def GAMEPAD_AXIS_RIGHT_TRIGGER 5)

;; Gamepad functions
(ffi/defcfn is-gamepad-available?
  "Check if a gamepad is available"
  {:arglists '([gamepad])}
  "IsGamepadAvailable"
  [:int] :int8)

(ffi/defcfn get-gamepad-name
  "Get gamepad internal name id"
  {:arglists '([gamepad])}
  "GetGamepadName"
  [:int] :string)

(ffi/defcfn is-gamepad-button-pressed?
  "Check if a gamepad button has been pressed once"
  {:arglists '([gamepad button])}
  "IsGamepadButtonPressed"
  [:int :int] :int8)

(ffi/defcfn is-gamepad-button-down?
  "Check if a gamepad button is being pressed"
  {:arglists '([gamepad button])}
  "IsGamepadButtonDown"
  [:int :int] :int8)

(ffi/defcfn is-gamepad-button-released?
  "Check if a gamepad button has been released once"
  {:arglists '([gamepad button])}
  "IsGamepadButtonReleased"
  [:int :int] :int8)

(ffi/defcfn is-gamepad-button-up?
  "Check if a gamepad button is NOT being pressed"
  {:arglists '([gamepad button])}
  "IsGamepadButtonUp"
  [:int :int] :int8)

(ffi/defcfn get-gamepad-button-pressed
  "Get the last gamepad button pressed"
  "GetGamepadButtonPressed"
  [] :int)

(ffi/defcfn get-gamepad-axis-count
  "Get gamepad axis count for a gamepad"
  {:arglists '([gamepad])}
  "GetGamepadAxisCount"
  [:int] :int)

(ffi/defcfn get-gamepad-axis-movement
  "Get axis movement value for a gamepad axis"
  {:arglists '([gamepad axis])}
  "GetGamepadAxisMovement"
  [:int :int] :float)

(ffi/defcfn set-gamepad-vibration!
  "Set gamepad vibration for both motors"
  {:arglists '([gamepad left-motor right-motor duration])}
  "SetGamepadVibration"
  [:int :float :float :float] :void)
