(ns raylib.check
  "Loads every namespace of the library without opening a window.
  Run with: bb check"
  (:require
   [raylib.all]
   [raylib.audio]
   [raylib.camera]
   [raylib.color]
   [raylib.core]
   [raylib.files]
   [raylib.images]
   [raylib.input]
   [raylib.kwargs]
   [raylib.log]
   [raylib.models]
   [raylib.native]
   [raylib.rays]
   [raylib.repl]
   [raylib.rlgl]
   [raylib.shaders]
   [raylib.shapes]
   [raylib.splines]
   [raylib.text]
   [raylib.textures]
   [raylib.util]))

(defn -main [& _]
  (println "All namespaces loaded."))
