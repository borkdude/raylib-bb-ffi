(ns raylib.core
  "Window lifecycle, frames, config flags and window state, monitor and
  clipboard queries, window placement and hi-DPI queries, and screenshots."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native :as native]))

(ffi/defcfn init-window    "InitWindow"   [:int :int :string] :void)
(ffi/defcfn set-target-fps "SetTargetFPS" [:int] :void)
;; KEY-NULL disables the exit key.
(ffi/defcfn set-exit-key   "SetExitKey"   [:int] :void)
(ffi/defcfn close-window   "CloseWindow"  [] :void)

(ffi/defcfn begin-drawing      "BeginDrawing"     [] :void)
(ffi/defcfn end-drawing        "EndDrawing"       [] :void)
(ffi/defcfn clear-background   "ClearBackground"  [:uint] :void)
(ffi/defcfn get-frame-time     "GetFrameTime"     [] :float)
(ffi/defcfn begin-scissor-mode "BeginScissorMode" [:int :int :int :int] :void)
(ffi/defcfn end-scissor-mode   "EndScissorMode"   [] :void)

(ffi/defcfn take-screenshot "TakeScreenshot" [:string] :void)

(ffi/defcfn window-should-close?
  "Returns true if the user requested to close the window."
  "WindowShouldClose" [] :bool)

;; SetConfigFlags takes effect only before InitWindow.
(ffi/defcfn set-config-flags   "SetConfigFlags"   [:uint] :void)
(ffi/defcfn set-window-state   "SetWindowState"   [:uint] :void)
(ffi/defcfn clear-window-state "ClearWindowState" [:uint] :void)
(ffi/defcfn toggle-fullscreen  "ToggleFullscreen" [] :void)
(ffi/defcfn get-screen-width   "GetScreenWidth"   [] :int)
(ffi/defcfn get-screen-height  "GetScreenHeight"  [] :int)
(ffi/defcfn get-time           "GetTime"          [] :double)

(def ^:const FLAG-WINDOW-RESIZABLE   0x00000004)
(def ^:const FLAG-WINDOW-UNDECORATED 0x00000008)
(def ^:const FLAG-MSAA-4X-HINT       0x00000020)
(def ^:const FLAG-VSYNC-HINT         0x00000040)
(def ^:const FLAG-WINDOW-TOPMOST     0x00001000)
(def ^:const FLAG-WINDOW-HIGHDPI     0x00002000)

(ffi/defcfn window-state?
  "Returns true if the FLAG-* bit flag is set on the window."
  "IsWindowState" [:uint] :bool)

(ffi/defcfn window-resized?
  "Returns true if the window changed size in the last frame."
  "IsWindowResized" [] :bool)

(ffi/defcfn get-monitor-count        "GetMonitorCount"       [] :int)
(ffi/defcfn get-current-monitor      "GetCurrentMonitor"     [] :int)
(ffi/defcfn get-monitor-width        "GetMonitorWidth"       [:int] :int)
(ffi/defcfn get-monitor-height       "GetMonitorHeight"      [:int] :int)
(ffi/defcfn get-monitor-refresh-rate "GetMonitorRefreshRate" [:int] :int)
(ffi/defcfn get-monitor-name         "GetMonitorName"        [:int] :string)

(ffi/defcfn set-clipboard-text "SetClipboardText" [:string] :void)
(ffi/defcfn get-clipboard-text "GetClipboardText" [] :string)

(ffi/defcfn toggle-borderless-windowed! "ToggleBorderlessWindowed" [] :void)

(ffi/defcfn get-window-scale-dpi
  "Returns the window scale factor of the monitor as [x y]."
  "GetWindowScaleDPI" [] native/vector2-layout
  get-window-scale-dpi-native
  []
  (native/xy (get-window-scale-dpi-native)))

(ffi/defcfn get-window-position
  "Returns the window position on the monitor as [x y]."
  "GetWindowPosition" [] native/vector2-layout
  get-window-position-native
  []
  (native/xy (get-window-position-native)))

;; SetWindowMinSize takes effect only with FLAG-WINDOW-RESIZABLE.
(ffi/defcfn set-window-min-size "SetWindowMinSize" [:int :int] :void)
(ffi/defcfn set-window-monitor  "SetWindowMonitor" [:int] :void)
