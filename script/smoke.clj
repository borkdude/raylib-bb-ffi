(ns smoke
  "Opens a window, draws with each drawing module and checks screenshot pixels.
  Run with: bb smoke"
  (:require
   [babashka.ffi :as ffi]
   [babashka.fs :as fs]
   [raylib.all :as rl]))

(def image-layout
  [:struct [[:data :pointer] [:width :int] [:height :int]
            [:mipmaps :int] [:format :int]]])

(ffi/defcfn load-image "LoadImage" [:string] image-layout)
(ffi/defcfn unload-image "UnloadImage" [image-layout] :void)
(ffi/defcfn get-image-color "GetImageColor" [image-layout :int :int] :uint)

(def W 320)
(def H 240)

(defn draw-frame [tex mesh material]
  (rl/begin-drawing)
  (rl/clear-background rl/RAYWHITE)
  (rl/rect! :x 0 :y 0 :width 80 :height 60 :color rl/RED)
  (rl/texture! tex :x 240 :y 0 :width 80 :height 60)
  (rl/rect-pro! :x 40.5 :y 210.5 :width 60 :height 40 :origin-x 30 :origin-y 20
                :rotation 0 :color rl/GOLD)
  (rl/sector! :cx 280 :cy 210 :radius 25 :start-deg 0 :end-deg 360 :color rl/PINK)
  (rl/with-camera-3d {:pos-x 3 :pos-y 3 :pos-z 3 :fovy 45}
    (fn []
      (rl/draw-mesh! mesh material (rl/matrix-identity))))
  (rl/text! "smoke" :x 130 :y 10 :size 10 :color rl/DARKGRAY)
  (rl/end-drawing))

(defn -main [& _]
  (let [shot "smoke.png"]
    (rl/window! :width W :height H :title "smoke")
    (try
      (rl/set-target-fps 60)
      (let [tex (rl/image-color 8 8 rl/BLUE)
            mesh (rl/mesh-cube 1 1 1)
            material (rl/material-diffuse-color! (rl/material-default) rl/GREEN)]
        (dotimes [_ 3] (draw-frame tex mesh material))
        (rl/take-screenshot shot)
        (rl/unload-texture! tex)
        (rl/unload-mesh! mesh))
      (let [img (load-image shot)
            sx (/ (:width img) W)
            sy (/ (:height img) H)
            pixel #(get-image-color img (int (* sx %1)) (int (* sy %2)))
            checks {"rect! is RED" [(pixel 20 20) rl/RED]
                    "texture! is BLUE" [(pixel 280 20) rl/BLUE]
                    "rect-pro! is GOLD" [(pixel 40 210) rl/GOLD]
                    "sector! is PINK" [(pixel 280 210) rl/PINK]
                    "draw-mesh! is GREEN" [(pixel (/ W 2) (/ H 2)) rl/GREEN]
                    "background is RAYWHITE" [(pixel 160 230) rl/RAYWHITE]}
            failed (remove (fn [[_ [actual expected]]] (= actual expected)) checks)]
        (unload-image img)
        (doseq [[label [actual expected]] (sort checks)]
          (println (if (= actual expected) "ok  " "FAIL") label
                   (format "%08x" actual) (format "%08x" expected)))
        (when (seq failed)
          (throw (ex-info "Smoke test failed" {:failed (keys failed)}))))
      (finally
        (fs/delete-if-exists shot)
        (rl/close-window)))))
