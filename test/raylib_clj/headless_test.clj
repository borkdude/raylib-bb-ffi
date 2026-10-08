(ns raylib-clj.headless-test
  (:require
   [babashka.ffi :as ffi]
   [clojure.test :refer [deftest is testing]]
   [raylib-clj.audio :as audio]
   [raylib-clj.colors :as colors]
   [raylib-clj.core.camera2d :as camera2d]
   [raylib-clj.core.camera3d :as camera3d]
   [raylib-clj.core.collision :as collision]
   [raylib-clj.core.shaders :as shaders]
   [raylib-clj.easings :as easings]
   [raylib-clj.models :as models]
   [raylib-clj.raymath :as rm]
   [raylib-clj.structs :as rs]
   [raylib-clj.utils :as utils]))

(deftest layouts-match-raylib-struct-sizes
  (is (= {:color 4 :vector-2 8 :vector-3 12 :vector-4 16 :texture 20
          :render-texture 44 :matrix 64 :bounding-box 24 :camera-3d 44
          :image 24 :rectangle 16 :camera-2d 24 :ray 24 :ray-collision 32
          :mesh 120 :material 40 :model 136 :shader 16 :audio-stream 32
          :music 56 :sound 40}
         (update-vals {:color rs/color :vector-2 rs/vector-2 :vector-3 rs/vector-3
                       :vector-4 rs/vector-4 :texture rs/texture
                       :render-texture rs/render-texture :matrix rs/matrix
                       :bounding-box rs/bounding-box :camera-3d rs/camera-3d
                       :image rs/image :rectangle rs/rectangle
                       :camera-2d camera2d/camera-2d :ray collision/ray
                       :ray-collision collision/ray-collision :mesh models/mesh
                       :material models/material :model models/model
                       :shader shaders/shader :audio-stream audio/audio-stream
                       :music audio/music :sound audio/sound}
                      ffi/sizeof))))

(deftest collision-functions-take-struct-maps
  (testing "a ray along +z from z=-5 hits the unit box at distance 4"
    (let [c (collision/get-ray-collision-box
             {:position {:x 0 :y 0 :z -5} :direction {:x 0 :y 0 :z 1}}
             {:min {:x -1 :y -1 :z -1} :max {:x 1 :y 1 :z 1}})]
      (is (= 1 (:hit c)))
      (is (== 4.0 (:distance c)))
      (is (= {:x 0.0 :y 0.0 :z -1.0} (:point c)))))
  (testing "a ray beside the box misses"
    (is (zero? (:hit (collision/get-ray-collision-box
                      {:position {:x 5 :y 0 :z -5} :direction {:x 0 :y 0 :z 1}}
                      {:min {:x -1 :y -1 :z -1} :max {:x 1 :y 1 :z 1}})))))
  (is (= 1 (collision/check-collision-point-rec? {:x 5 :y 5} {:x 0 :y 0 :width 10 :height 10})))
  (is (= 0 (collision/check-collision-circles? {:x 0 :y 0} 1 {:x 5 :y 0} 1)))
  (is (= 1 (collision/check-collision-boxes?
            (collision/make-bounding-box {:x 0 :y 0 :z 0} {:x 2 :y 2 :z 2})
            (collision/make-bounding-box {:x 1 :y 0 :z 0} {:x 2 :y 2 :z 2})))))

(deftest camera2d-converts-between-screen-and-world
  (let [cam (camera2d/make-camera-2d {:x 100 :y 100} {:x 400 :y 300})]
    (is (= {:x 400.0 :y 300.0} (camera2d/get-world-to-screen-2d {:x 100 :y 100} cam)))
    (is (= {:x 100.0 :y 100.0} (camera2d/get-screen-to-world-2d {:x 400 :y 300} cam)))))

(deftest update-camera-returns-a-camera-map
  (let [cam {:position {:x 10.0 :y 10.0 :z 10.0} :target {:x 0.0 :y 0.0 :z 0.0}
             :up {:x 0.0 :y 1.0 :z 0.0} :fovy 45.0 :projection 0}]
    (is (= cam (camera3d/update-camera cam camera3d/CAMERA_CUSTOM)))))

(deftest raymath-works-on-vector-maps
  (is (= {:x 4.0 :y 6.0} (rm/v2-add {:x 1.0 :y 2.0} {:x 3.0 :y 4.0})))
  (is (== 5.0 (rm/v2-length {:x 3.0 :y 4.0})))
  (is (= {:x 0.0 :y 0.0 :z 1.0} (rm/v3-cross {:x 1.0 :y 0.0 :z 0.0} {:x 0.0 :y 1.0 :z 0.0})))
  (is (== 5.0 (rm/lerp 0.0 10.0 0.5))))

(deftest easings-follow-reasings
  (is (== 0.0 (easings/linear-none 0 0 10 1)))
  (is (== 10.0 (easings/linear-none 1 0 10 1)))
  (is (== 10.0 (easings/quad-out 1 0 10 1))))

(deftest colors-are-rgba-maps
  (is (= {:r 230 :g 41 :b 55 :a 255} colors/red))
  (testing "GetColor returns a Color map"
    (is (= {:r 0x11 :g 0x22 :b 0x33 :a 0x44} (utils/get-color 0x11223344))))
  (testing "Fade passes and returns Color by value"
    (is (= {:r 230 :g 41 :b 55 :a 127} (utils/fade colors/red 0.5)))))
