(ns check-signatures
  "Compares every defcfn under src/raylib and src/raylib_clj with the
  prototypes in raylib.h, rlgl.h and raymath.h.
  Run with: bb check:signatures [include-dir]"
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [java.io PushbackReader]))

(def non-raylib #{"time" "localtime" "vsnprintf"})

(defn- forms [f]
  (with-open [r (PushbackReader. (io/reader f))]
    (binding [*read-eval* false]
      (doall (take-while #(not= ::eof %)
                         (repeatedly #(read {:eof ::eof :read-cond :allow} r)))))))

(defn- defcfns [x]
  (cond (and (seq? x) (= 'ffi/defcfn (first x)))
        (let [xs (vec (rest x))
              i (first (keep-indexed (fn [i v] (when (and (string? v) (vector? (get xs (inc i)))) i)) xs))]
          (when i [[(xs i) (xs (inc i)) (xs (+ i 2))]]))
        (coll? x) (mapcat defcfns x)))

(defn- param-type [p]
  (str/trim (str/replace p #"\b\w+\s*$" "")))

(defn- prototypes [dir]
  (into {}
        (for [h ["raylib.h" "rlgl.h" "raymath.h"]
              [_ ret nm params] (re-seq #"(?m)^\s*(?:RLAPI|RMAPI)\s+([\w\s\*]+?)\s*\b(\w+)\s*\(([^)]*)\)"
                                       (slurp (io/file dir h)))]
          [nm [ret (if (#{"void" ""} (str/trim params))
                     []
                     (mapv param-type (str/split params #",")))]])))

(defn- c-kind [c]
  (let [c (str/trim (str/replace c #"\bconst\b" ""))]
    (cond (or (str/includes? c "*") (str/ends-with? c "Callback")) :ptr
          (= "..." c) :va
          (= "float" c) :float
          (= "double" c) :double
          (= "bool" c) :bool
          (= "Color" c) :color
          (= "void" c) :void
          (re-matches #"(unsigned )?(int|char|short|long)|unsigned" c) :int
          :else :struct)))

(defn- ffi-kind [t]
  (cond (#{:float :double :bool :void} t) t
        (#{:int8 :uint8 :char :byte} t) :byte
        (#{:pointer :string} t) :ptr
        (= :& t) :va
        (keyword? t) :int
        :else :struct))

(defn- compatible-arg? [c ffi]
  (or (= c ffi)
      (and (= :color c) (contains? #{:int :struct} ffi))
      (and (= :bool c) (contains? #{:int :byte} ffi))
      (and (= :int c) (= :byte ffi))))

(defn- compatible-ret?
  "Like compatible-arg?, but a C bool return needs a one-byte type, because
  the rest of the register is undefined."
  [c ffi]
  (if (= :bool c)
    (contains? #{:bool :byte} ffi)
    (compatible-arg? c ffi)))

(defn mismatches [include-dir]
  (let [protos (prototypes include-dir)]
    (for [dir ["src/raylib" "src/raylib_clj"]
          f (file-seq (io/file dir))
          :when (str/ends-with? (str f) ".clj")
          [sym args ret] (mapcat defcfns (forms f))
          :when (not (non-raylib sym))
          :let [[cret cparams] (protos sym)]
          :when (or (nil? cret)
                    (not= (count cparams) (count args))
                    (not (every? true? (map compatible-arg? (map c-kind cparams) (map ffi-kind args))))
                    (not (compatible-ret? (c-kind cret) (ffi-kind ret))))]
      [(.getName f) sym (if cret [cret cparams] :no-prototype) [args ret]])))

(defn -main [& [include-dir]]
  (let [dir (or include-dir (System/getenv "RAYLIB_INCLUDE") "/opt/homebrew/include")
        ms (mismatches dir)]
    (doseq [m ms] (prn m))
    (when (seq ms)
      (throw (ex-info (str (count ms) " signatures differ from the raylib headers") {})))
    (println "Signatures match the raylib headers.")))
