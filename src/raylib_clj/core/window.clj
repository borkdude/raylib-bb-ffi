(ns raylib-clj.core.window
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]))

(ffi/defcfn init-window!
  "Initialize window and OpenGL context"
  {:arglists '([width height title])}
  "InitWindow"
  [:int :int :string] :void)

(ffi/defcfn window-should-close?
  "Check if KEY_ESCAPE pressed or Close icon pressed"
  "WindowShouldClose"
  [] :bool)

(ffi/defcfn close-window!
  "Close window and unload OpenGL context"
  "CloseWindow" [] :void)

(ffi/defcfn is-window-ready?
  "Check if window has been initialized successfully"
  "IsWindowReady"
  [] :bool)

(ffi/defcfn is-window-fullscreen?
  "Check if window is currently fullscreen"
  "IsWindowFullscreen"
  [] :bool)

(ffi/defcfn is-window-hidden?
  "Check if window is currently hidden (only PLATFORM_DESKTOP)"
  "IsWindowHidden"
  [] :bool)

(ffi/defcfn is-window-minimized?
  "Check if window is currently minimized (only PLATFORM_DESKTOP)"
  "IsWindowMinimized"
  [] :bool)

(ffi/defcfn is-window-maximized?
  "Check if window is currently maximized (only PLATFORM_DESKTOP)"
  "IsWindowMaximized"
  [] :bool)

(ffi/defcfn is-window-focused?
  "Check if window is currently focused (only PLATFORM_DESKTOP)"
  "IsWindowFocused"
  [] :bool)

(ffi/defcfn is-window-resized?
  "Check if window has been resized last frame"
  "IsWindowResized"
  [] :bool)

; ...

(ffi/defcfn get-screen-width
  "Get current screen width"
  "GetScreenWidth"
  [] :int)

(ffi/defcfn get-screen-height
  "Get current screen height"
  "GetScreenHeight"
  [] :int)

(ffi/defcfn get-render-width
  "Get current render width (considers HiDPI)"
  "GetRenderWidth"
  [] :int)

(ffi/defcfn get-render-height
  "Get current render height (considers HiDPI)"
  "GetRenderHeight"
  [] :int)

(ffi/defcfn set-config-flags!
  "Setup init configuration flags (view FLAGS)"
  {:arglists '([flags])}
  "SetConfigFlags"
  [:int] :void)

(def config-flag
  {:flag/vsync-hint 0x00000040
   :flag/fullscreen-mode 0x00000002
   :flag/window-resizable 0x00000004
   :flag/window-undecorated 0x00000008
   :flag/window-hidden 0x00000080
   :flag/window-minimized 0x00000200
   :flag/window-maximized 0x00000400
   :flag/window-unfocused 0x00000800
   :flag/window-topmost 0x00001000
   :flag/window-always-run 0x00000100
   :flag/window-transparent 0x00000010
   :flag/window-highdpi 0x00002000
   :flag/window-mouse-passthrough 0x00004000
   :flag/borderless-windowed-mode 0x00008000
   :flag/msaa-4x-hint 0x00000020
   :flag/interlaced-hint 0x00010000})

(defn set-config-flags [& flags]
  (if (= 1 (count flags))
    (set-config-flags! (->> flags first (get config-flag)))
    (set-config-flags! (apply bit-or (map config-flag flags)))))

(ffi/defcfn toggle-fullscreen!
  "Toggle window state: fullscreen/windowed (only PLATFORM_DESKTOP)"
  "ToggleFullscreen"
  [] :void)

(ffi/defcfn toggle-borderless-windowed!
  "Toggle window state: borderless windowed (only PLATFORM_DESKTOP)"
  "ToggleBorderlessWindowed"
  [] :void)

(ffi/defcfn set-window-size!
  "Set window dimensions"
  {:arglists '([width height])}
  "SetWindowSize"
  [:int :int] :void)

(ffi/defcfn set-window-min-size!
  "Set window minimum dimensions (for FLAG_WINDOW_RESIZABLE)"
  {:arglists '([width height])}
  "SetWindowMinSize"
  [:int :int] :void)

(ffi/defcfn get-current-monitor
  "Get current connected monitor"
  "GetCurrentMonitor"
  [] :int)

(ffi/defcfn get-monitor-width
  "Get specified monitor width (current video mode used by monitor)"
  {:arglists '([monitor])}
  "GetMonitorWidth"
  [:int] :int)

(ffi/defcfn get-monitor-height
  "Get specified monitor height (current video mode used by monitor)"
  {:arglists '([monitor])}
  "GetMonitorHeight"
  [:int] :int)

;; Clipboard

(ffi/defcfn get-clipboard-text
  "Get clipboard text content"
  "GetClipboardText"
  [] :string)

(ffi/defcfn set-clipboard-text!
  "Set clipboard text content"
  {:arglists '([text])}
  "SetClipboardText"
  [:string] :void)

; ...
