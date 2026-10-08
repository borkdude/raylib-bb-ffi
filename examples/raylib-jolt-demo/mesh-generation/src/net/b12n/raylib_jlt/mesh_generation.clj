(ns net.b12n.raylib-jlt.mesh-generation
  "raylib [models] example - mesh generation (`bb mesh-generation`).

  Port of raylib's examples/models/models_mesh_generation.c. Eight shapes from
  the GenMesh* family, laid out on a grid and turning together, each drawn with
  DrawMesh under its own transform.

  GenMesh* returns a Mesh by value as a map, and DrawMesh takes the Mesh,
  Material and Matrix maps by value.

  GenMesh* uploads to the GPU on its own, so each mesh has a live vao id the
  moment it is generated and there is no UploadMesh call here.

  UP and DOWN change how fast the grid turns; SPACE pauses it."
  (:require
   [net.b12n.raylib-jlt.app :as app]
   [raylib.all :as rl]))

(def ^:const W 800)
(def ^:const H 450)

;; name, the fn that generates the mesh, and where it sits on the grid
(def shapes
  [["cube" #(rl/mesh-cube 1.0 1.0 1.0) -3.0 1.2 [230 80 70]]
   ["sphere" #(rl/mesh-sphere 0.6 16 20) -1.0 1.2 [240 170 60]]
   ["hemisphere" #(rl/mesh-hemisphere 0.6 12 20) 1.0 1.2 [235 225 90]]
   ["cylinder" #(rl/mesh-cylinder 0.5 1.1 20) 3.0 1.2 [110 210 110]]
   ["cone" #(rl/mesh-cone 0.6 1.2 20) -3.0 -1.2 [80 195 215]]
   ["torus" #(rl/mesh-torus 0.25 0.7 14 20) -1.0 -1.2 [90 140 235]]
   ["knot" #(rl/mesh-knot 0.3 0.7 14 20) 1.0 -1.2 [170 110 230]]
   ["plane" #(rl/mesh-plane 1.3 1.3 4 4) 3.0 -1.2 [225 120 190]]])

(defn -main
  [& _]
  (rl/window! {:width W
               :height H
               :title "raylib [models] example - mesh generation"})
  (rl/set-target-fps 60)
  (let [deadline (app/auto-quit-deadline)
        material (rl/material-default)
        meshes (mapv (fn [[nm gen x z [r g b]]]
                       {:name nm
                        :mesh (gen)
                        :x x
                        :z z
                        :color (rl/rgba r g b 255)})
                     shapes)]
    (loop [frame 0
           angle 0.0
           speed 0.35
           paused? false]
      (if-not (app/keep-running? deadline)
        (doseq [{:keys [mesh]} meshes]
          (rl/unload-mesh! mesh))
        (let [paused?' (if (rl/key-pressed? rl/KEY-SPACE) (not paused?) paused?)
              speed' (cond
                       (rl/key-down? rl/KEY-UP) (min 2.0 (+ speed 0.02))
                       (rl/key-down? rl/KEY-DOWN) (max 0.0 (- speed 0.02))
                       :else speed)
              angle' (if paused?' angle (+ angle (* speed' (rl/get-frame-time))))
              ;; the camera orbits, the meshes stay put
              cam-x (* 9.0 (Math/sin angle'))
              cam-z (* 9.0 (Math/cos angle'))]
          (rl/begin-drawing)
          (rl/clear-background (rl/rgba 26 30 42 255))
          (rl/with-camera-3d
            {:pos-x cam-x
             :pos-y 5.0
             :pos-z cam-z
             :target-x 0.0
             :target-y 0.0
             :target-z 0.0
             :up-x 0.0
             :up-y 1.0
             :up-z 0.0
             :fovy 45.0}
            (fn []
              (rl/draw-grid 12 1.0)
              (doseq [{:keys [mesh x z color]} meshes]
                ;; one shared material, re-tinted per mesh. raylib's default
                ;; shader is unlit, so without this every shape is the same
                ;; flat white silhouette and the geometry is unreadable
                (rl/material-diffuse-color! material color)
                (rl/draw-mesh! mesh material (rl/matrix-translate x 0.0 z)))))
          (rl/text! "raylib [models] example - mesh generation"
                    {:x 16
                     :y 14
                     :size 20
                     :color rl/RAYWHITE})
          (rl/text! "GenMesh* returns a 120-byte Mesh by value; DrawMesh takes three structs"
                    {:x 16
                     :y 40
                     :size 14
                     :color (rl/rgba 160 175 200 255)})
          (rl/text! (str "UP/DOWN orbit speed " (format "%.2f" speed')
                         (if paused?' "   PAUSED (SPACE)" ""))
                    {:x 16
                     :y (- H 30)
                     :size 16
                     :color (rl/rgba 160 175 200 255)})
          (let [total (reduce + (map (fn [{:keys [mesh]}] (:triangle-count mesh)) meshes))]
            (rl/text! (str (count meshes) " meshes, " total " triangles")
                      {:x (- W 260)
                       :y (- H 30)
                       :size 16
                       :color (rl/rgba 160 175 200 255)}))
          (app/maybe-screenshot! frame 40)
          (rl/end-drawing)
          (recur (inc frame) angle' speed' paused?'))))))
