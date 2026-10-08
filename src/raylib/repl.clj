(ns raylib.repl
  "Starts an nREPL server and runs raylib calls from it on the main thread.

  Start the REPL with:

      bb -m raylib.repl [port]

  Then open a window from the editor with (raylib.repl/run! -main)."
  (:refer-clojure :exclude [run!])
  (:require [babashka.fs :as fs])
  (:import [java.util.concurrent LinkedBlockingQueue]))

(def ^:private tasks (LinkedBlockingQueue.))

(def ^:private pump-running? (atom false))

(defn run!
  "Runs f on the main thread.
  If the main thread runs -main of this namespace, queues f and returns nil.
  Otherwise calls f on the current thread and returns its result.
  macOS opens a window only from the main thread."
  [f]
  (if @pump-running?
    (do (.put tasks f) nil)
    (f)))

(defn- start-server! [port]
  (if (System/getProperty "babashka.version")
    ((requiring-resolve 'babashka.nrepl.server/start-server!) {:port port})
    (let [server ((requiring-resolve 'nrepl.server/start-server)
                  :bind "127.0.0.1" :port port)]
      (println (format "Started nREPL server at 127.0.0.1:%d" (:port server)))
      server)))

(defn -main
  "Starts an nREPL server on port, or on 1667 if absent, and writes .nrepl-port.
  Then runs each fn passed to run! on the main thread, until the process exits.
  On the JVM, nrepl/nrepl must be on the classpath.
  On the JVM on macOS, start with -XstartOnFirstThread."
  [& [port]]
  (let [server (start-server! (if port (parse-long port) 1667))]
    (spit ".nrepl-port" (:port server))
    (fs/delete-on-exit ".nrepl-port")
    (reset! pump-running? true)
    (loop []
      (let [f (.take tasks)]
        (try
          (f)
          (catch Throwable e
            (binding [*out* *err*]
              (println "Exception in run!:" (ex-message e))
              (.printStackTrace e)))))
      (recur))))
