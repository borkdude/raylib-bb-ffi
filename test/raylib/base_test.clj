(ns raylib.base-test
  (:require
   [babashka.ffi :as ffi]
   [clojure.test :refer [deftest is testing]]
   [raylib.color :as color]
   [raylib.log :as log]
   [raylib.native :as native]
   [raylib.util :as util]))

(deftest layouts-match-raylib-struct-sizes
  (is (= 8 (ffi/sizeof native/vector2-layout)))
  (is (= 12 (ffi/sizeof native/vector3-layout)))
  (is (= 16 (ffi/sizeof native/vector4-layout)))
  (is (= 16 (ffi/sizeof native/rectangle-layout)))
  (is (= 20 (ffi/sizeof native/texture2d-layout)))
  (is (= 64 (ffi/sizeof native/matrix-layout))))

(ffi/defcfn ^:private vector2-lerp "Vector2Lerp"
  [native/vector2-layout native/vector2-layout :float] native/vector2-layout)

(ffi/defcfn ^:private check-collision-recs "CheckCollisionRecs"
  [native/rectangle-layout native/rectangle-layout] :bool)

(deftest struct-arguments-and-returns-are-maps
  (testing "Vector2Lerp takes and returns Vector2 maps"
    (is (= [5.0 10.0]
           (native/xy (vector2-lerp (native/vector2 [0 0]) (native/vector2 [10 20]) 0.5)))))
  (testing "CheckCollisionRecs returns a C bool as a boolean"
    (is (true? (check-collision-recs (native/rectangle [0 0 10 10])
                                     (native/rectangle [5 5 10 10]))))
    (is (false? (check-collision-recs (native/rectangle [0 0 10 10])
                                      (native/rectangle [50 5 10 10]))))))

(deftest staged-writes-values-as-4-byte-elements
  (is (= [1.5 2.5] (native/staged :float [1.5 2.5] #(vec (ffi/read % [:array :float 2])))))
  (is (= [7 -8] (native/staged :int [7 -8] #(vec (ffi/read % [:array :int 2]))))))

(deftest rgba-packs-colors-little-endian
  (is (= 0xff0000ff (color/rgba 255 0 0 255)))
  (is (= color/RED (color/get-color 0xe62937ff))))

(deftest hash-functions-return-uint32-words
  (testing "MD5 of abc is 900150983cd24fb0d6963f7d28e17f72"
    (is (= [0x98500190 0xb04fd23c 0x7d3f96d6 0x727fe128] (util/compute-md5 "abc"))))
  (is (= 0x352441c2 (util/compute-crc32 "abc" 3)))
  (is (= 5 (count (util/compute-sha1 "abc"))))
  (is (= "aGVsbG8=" (util/base64-encode "hello")))
  (is (= 3 (count (util/local-time)))))

(ffi/defcfn ^:private trace-log "TraceLog" [:int :string :&] :void)

(deftest on-trace-log-receives-formatted-messages
  (let [msgs (atom [])
        entry (log/on-trace-log! (fn [level text] (swap! msgs conj [level text])))]
    (try
      (trace-log log/LOG-WARNING "x=%d s=%s" 42 "hi")
      (finally (log/free-callable! entry)))
    (is (= [[log/LOG-WARNING "x=42 s=hi"]] @msgs))))
