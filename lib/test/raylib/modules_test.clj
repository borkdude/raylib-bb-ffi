(ns raylib.modules-test
  (:require
   [babashka.fs :as fs]
   [clojure.test :refer [deftest is testing]]
   [raylib.files :as files]
   [raylib.rays :as rays]))

(deftest ray-collision-box-returns-hit-and-distance
  (testing "a ray along +z from z=-5 hits the unit box at z=-1"
    (let [{:keys [hit? distance point normal]}
          (rays/ray-collision-box {:position [0 0 -5] :direction [0 0 1]}
                                  [-1 -1 -1] [1 1 1])]
      (is (true? hit?))
      (is (== 4.0 distance))
      (is (= [0.0 0.0 -1.0] point))
      (is (= [0.0 0.0 -1.0] normal))))
  (testing "a ray beside the box misses"
    (is (false? (:hit? (rays/ray-collision-box {:position [5 0 -5] :direction [0 0 1]}
                                               [-1 -1 -1] [1 1 1]))))))

(deftest directory-files-lists-entries
  (let [dir (fs/create-temp-dir)]
    (try
      (fs/create-dirs (fs/path dir "sub"))
      (spit (str (fs/path dir "a.png")) "")
      (spit (str (fs/path dir "b.txt")) "")
      (is (true? (files/directory-exists? (str dir))))
      (is (false? (files/directory-exists? (str (fs/path dir "missing")))))
      (is (= 3 (count (files/directory-files (str dir) "*.*" false))))
      (is (= ["a.png"] (map files/get-file-name
                            (files/directory-files (str dir) ".png" false))))
      (finally (fs/delete-tree dir)))))
