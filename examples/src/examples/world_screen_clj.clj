(ns examples.world-screen-clj
  "Raylib [core] example - world screen

   Demonstrates converting 3D world coordinates to 2D screen space.
   Useful for placing UI elements (health bars, labels) above 3D objects.
   Based on: raylib/examples/core/core_world_screen.c

   Complexity: ⭐⭐ Easy

   Controls:
   - Mouse: Rotate camera (third-person mode)
   - Mouse Wheel: Zoom in/out
   - F1: Toggle debug stats
   - ESC: Exit"
  (:require
   [raylib-clj.core.window :as rcw]
   [raylib-clj.core.timing :as rct]
   [raylib-clj.core.drawing :as rcd]
   [raylib-clj.core.camera3d :as rc3]
   [raylib-clj.core.cursor :as rcur]
   [raylib-clj.text.drawing :as rtd]
   [raylib-clj.colors :as colors]
   [raylib-clj.nrepl :as nrepl]
   [babashka.ffi :as ffi]
   [raylib-clj.debug-stats :as debug-stats]))

;; Constants
(def WIDTH 800)
(def HEIGHT 450)

(defn initial-state []
  {:camera {:position {:x 10.0
                       :y 10.0
                       :z 10.0}
            :target {:x 0.0
                     :y 0.0
                     :z 0.0}
            :up {:x 0.0
                 :y 1.0
                 :z 0.0}
            :fovy 45.0
            :projection rc3/CAMERA_PERSPECTIVE}
   :cube-position {:x 0.0
                   :y 0.0
                   :z 0.0}
   :cube-screen-position {:x 0.0
                          :y 0.0}
   :camera-ptr nil})

(def game-atom (atom (initial-state)))

(defn camera->native!
  "Writes the camera map to the Camera3D at ptr."
  [camera ptr]
  (ffi/write ptr rc3/camera3d camera))

(defn native->camera
  "Returns the camera map of the Camera3D at ptr."
  [ptr]
  (ffi/read ptr rc3/camera3d))

(defn init []
  (rcw/init-window! WIDTH HEIGHT "raylib [core] example - world screen")
  (rct/set-target-fps! 60)
  (debug-stats/enable!)

  ;; UpdateCamera takes a Camera3D pointer.
  (let [camera-ptr (ffi/alloc (ffi/auto-arena) rc3/camera3d)]
    (camera->native! (:camera @game-atom) camera-ptr)
    (swap! game-atom assoc :camera-ptr camera-ptr)
    ;; Disable cursor for camera control
    (rcur/disable-cursor!)))

(defn tick [{:keys [camera-ptr cube-position]
             :as game}]
  (debug-stats/update!)

  ;; Update camera using raylib's built-in third-person mode
  (rc3/update-camera! camera-ptr rc3/CAMERA_THIRD_PERSON)

  ;; Read back updated camera from native memory
  (let [updated-camera (native->camera camera-ptr)
        ;; Calculate cube screen position (with offset to be above cube)
        cube-top {:x (:x cube-position)
                  :y (+ (:y cube-position) 2.5)
                  :z (:z cube-position)}
        screen-pos (rc3/get-world-to-screen cube-top updated-camera)]
    (assoc game
           :camera updated-camera
           :cube-screen-position screen-pos)))

(defn draw [{:keys [camera cube-position cube-screen-position]}]
  (rcd/begin-drawing!)
  (rcd/clear-background! colors/raywhite)

  ;; 3D rendering
  (rc3/begin-mode-3d! camera)

  ;; Draw cube at origin
  (rc3/draw-cube! cube-position 2.0 2.0 2.0 colors/red)
  (rc3/draw-cube-wires! cube-position 2.0 2.0 2.0 colors/maroon)

  ;; Draw grid
  (rc3/draw-grid! 10 1.0)

  (rc3/end-mode-3d!)

  ;; 2D UI elements (drawn after 3D, so they appear on top)
  ;; Draw label above the cube using screen coordinates
  (let [label "Enemy: 100/100"
        text-width (rtd/measure-text label 20)
        x (- (int (:x cube-screen-position)) (/ text-width 2))
        y (int (:y cube-screen-position))]
    (rtd/draw-text! label (int x) y 20 colors/black))

  ;; Info text
  (rtd/draw-text!
   (format "Cube position in screen space coordinates: [%d, %d]"
           (int (:x cube-screen-position))
           (int (:y cube-screen-position)))
   10 10 20 colors/lime)
  (rtd/draw-text! "Text 2d should be always on top of the cube" 10 40 20 colors/gray)

  ;; Draw debug stats overlay
  (debug-stats/draw!)

  (rcd/end-drawing!))

(defn cleanup []
  (rcur/enable-cursor!))

(defn start []
  (nrepl/start {:port 7888})
  (init)
  (loop []
    (when-not (rcw/window-should-close?)
      (let [game (tick @game-atom)]
        (reset! game-atom game)
        (draw game)
        (recur))))
  (cleanup)
  (rcw/close-window!))

(defn -main [& _args]
  (start))

(comment
  ;; For REPL development - connect to port 7888 after running the game
  @game-atom

  ;; Check screen position
  (:cube-screen-position @game-atom)

  ;; Check camera
  (:camera @game-atom)
  ;;
  )
