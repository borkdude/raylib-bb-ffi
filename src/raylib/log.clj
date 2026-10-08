(ns raylib.log
  "raylib's trace log levels, and on-trace-log! to receive its messages in a
  Clojure fn."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native]))

(ffi/defcfn set-trace-log-level "SetTraceLogLevel" [:int] :void)
(ffi/defcfn set-trace-log-callback "SetTraceLogCallback" [:pointer] :void)
(ffi/defcfn ^:private vsnprintf "vsnprintf" [:pointer :size_t :pointer :pointer] :int)

(def ^:const LOG-ALL 0)     (def ^:const LOG-TRACE 1)
(def ^:const LOG-DEBUG 2)   (def ^:const LOG-INFO 3)
(def ^:const LOG-WARNING 4) (def ^:const LOG-ERROR 5)
(def ^:const LOG-FATAL 6)   (def ^:const LOG-NONE 7)

(def ^:const TRACE-LOG-BUFFER 1024)

(defn on-trace-log!
  "Calls (f level text) for every message raylib logs.
  level is one of the LOG-* constants and text is the formatted message,
  truncated to TRACE-LOG-BUFFER bytes.
  Returns an entry to pass to free-callable! after the window is closed.
  Call before init-window to receive the startup messages."
  [f]
  (let [arena (ffi/shared-arena)
        cb (ffi/callback
            arena
            (fn [level fmt va]
              (try
                (with-open [scratch (ffi/confined-arena)]
                  (let [buf (ffi/alloc scratch TRACE-LOG-BUFFER)]
                    (vsnprintf buf TRACE-LOG-BUFFER fmt va)
                    (f level (ffi/ptr->string buf))))
                (catch Throwable e
                  (binding [*out* *err*]
                    (println "Exception in on-trace-log! fn:" (ex-message e))))))
            [:int :pointer :pointer] :void)]
    (set-trace-log-callback cb)
    {:arena arena :callback cb}))

(defn free-callable!
  "Restores raylib's own logger and releases the callback of on-trace-log!."
  [{:keys [arena]}]
  (set-trace-log-callback ffi/null)
  (.close ^java.lang.AutoCloseable arena))
