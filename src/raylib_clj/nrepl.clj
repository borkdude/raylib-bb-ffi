(ns raylib-clj.nrepl
  "Starts and stops an nREPL server inside a running game.
  Uses babashka's built-in server in babashka and nrepl/nrepl on the JVM.")

(defn- bb? []
  (some? (System/getProperty "babashka.version")))

(defn start
  "Starts an nREPL server on :port and returns it.
  On the JVM, :bind, :transport-fn, :handler, :ack-port and :greeting-fn go
  to nrepl.server/start-server. In babashka, :bind is the host.
  Returns nil and prints a warning if the port is in use."
  [{:keys [port bind transport-fn handler ack-port greeting-fn]}]
  (binding [*out* *err*]
    (println "Starting nREPL server on port" port))
  (try
    (if (bb?)
      ((requiring-resolve 'babashka.nrepl.server/start-server!)
       (cond-> {:port port} bind (assoc :host bind)))
      ((requiring-resolve 'nrepl.server/start-server)
       :port port :bind bind :transport-fn transport-fn :handler handler
       :ack-port ack-port :greeting-fn greeting-fn))
    (catch java.net.BindException _
      (binding [*out* *err*]
        (println (str "nREPL port " port " is in use. Continuing without nREPL.")))
      nil)))

(defn stop
  "Stops a server returned by start."
  [server]
  (when server
    (if (bb?)
      ((requiring-resolve 'babashka.nrepl.server/stop-server!) server)
      ((requiring-resolve 'nrepl.server/stop-server) server))
    (binding [*out* *err*]
      (println "nREPL server stopped"))))
