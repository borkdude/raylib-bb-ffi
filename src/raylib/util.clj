(ns raylib.util
  "Local time through libc time and localtime, and raylib's hashing and Base64
  helpers."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native]))

(ffi/defcfn ^:private c-time      "time"      [:pointer] :long)
(ffi/defcfn ^:private c-localtime "localtime" [:pointer] :pointer)

(def ^:private tm-layout
  [:struct [[:sec :int] [:min :int] [:hour :int]]])

(defn local-time
  "Returns the local wall-clock time as [hour minute second]."
  []
  (with-open [arena (ffi/confined-arena)]
    (let [t (ffi/alloc arena :long)]
      (c-time t)
      (let [{:keys [hour min sec]}
            (ffi/read (ffi/reinterpret (c-localtime t) (ffi/sizeof tm-layout))
                      tm-layout)]
        [hour min sec]))))

(ffi/defcfn compute-crc32 "ComputeCRC32" [:string :int] :uint)
(ffi/defcfn ^:private compute-md5-raw "ComputeMD5" [:string :int] :pointer)
(ffi/defcfn ^:private compute-sha1-raw "ComputeSHA1" [:string :int] :pointer)
(ffi/defcfn ^:private compute-sha256-raw "ComputeSHA256" [:string :int] :pointer)
(ffi/defcfn ^:private encode-base64-raw "EncodeDataBase64" [:string :int :pointer] :pointer)
(ffi/defcfn ^:private mem-free "MemFree" [:pointer] :void)

(defn- utf8-count [^String s]
  (alength (.getBytes s "UTF-8")))

(defn- read-words
  "Returns n uint32 words at ptr, which points into a static buffer of raylib."
  [ptr n]
  (mapv #(bit-and % 0xffffffff)
        (ffi/read (ffi/reinterpret ptr (* 4 n)) [:array :uint n])))

(defn compute-md5
  "Returns the MD5 hash of s as 4 uint32 words."
  [s]
  (read-words (compute-md5-raw s (utf8-count s)) 4))

(defn compute-sha1
  "Returns the SHA-1 hash of s as 5 uint32 words."
  [s]
  (read-words (compute-sha1-raw s (utf8-count s)) 5))

(defn compute-sha256
  "Returns the SHA-256 hash of s as 8 uint32 words."
  [s]
  (read-words (compute-sha256-raw s (utf8-count s)) 8))

(defn base64-encode
  "Returns the Base64 encoding of s."
  [s]
  (with-open [arena (ffi/confined-arena)]
    (let [out-size (ffi/alloc arena :int)
          p (encode-base64-raw s (utf8-count s) out-size)]
      (if (ffi/null? p)
        ""
        (try (ffi/ptr->string p)
             (finally (mem-free p)))))))
