(ns raylib.audio
  "The audio device, and AudioStream loading, playback, refills, panning and
  callbacks.
  An AudioStream is a map of its struct fields."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native :as native]))

(def ^:private audio-stream-layout
  [:struct [[:buffer :pointer]
            [:processor :pointer]
            [:sample-rate :uint32]
            [:sample-size :uint32]
            [:channels :uint32]]])

(ffi/defcfn init-audio-device  "InitAudioDevice"  [] :void)
(ffi/defcfn close-audio-device "CloseAudioDevice" [] :void)
(ffi/defcfn set-audio-stream-buffer-size-default
  "SetAudioStreamBufferSizeDefault" [:int] :void)

(ffi/defcfn load-audio-stream
  "Returns a new AudioStream. Release it with unload-audio-stream."
  "LoadAudioStream" [:uint32 :uint32 :uint32] audio-stream-layout)
(ffi/defcfn unload-audio-stream "UnloadAudioStream"
  [audio-stream-layout] :void)
(ffi/defcfn play-audio-stream "PlayAudioStream"
  [audio-stream-layout] :void)
(ffi/defcfn audio-stream-processed?
  "Returns true if stream needs a refill."
  "IsAudioStreamProcessed" [audio-stream-layout] :bool)
(ffi/defcfn set-audio-stream-pan "SetAudioStreamPan"
  [audio-stream-layout :float] :void)
(ffi/defcfn ^:private update-audio-stream-raw "UpdateAudioStream"
  [audio-stream-layout :pointer :int] :void)
(ffi/defcfn ^:private set-audio-stream-callback-raw "SetAudioStreamCallback"
  [audio-stream-layout :pointer] :void)

(defn update-audio-stream
  "Refills stream with samples, a seq of floats.
  The count of samples must match the frame count of one stream buffer."
  [stream samples]
  (native/staged :float samples
                 (fn [p] (update-audio-stream-raw stream p (count samples)))))

(defn on-audio-stream!
  "Calls (f buffer frames) on the audio thread of raylib to fill stream.
  f writes frames samples per channel to the pointer buffer, for example as
  :float at 4-byte strides for a 32-bit mono stream.
  Returns an entry to pass to free-audio-callback! after
  clear-audio-stream-callback!."
  [stream f]
  (let [arena (ffi/shared-arena)
        cb (ffi/callback
            arena
            (fn [buffer frames]
              (try
                (f (ffi/reinterpret buffer (* frames
                                              (max 1 (:channels stream))
                                              (quot (:sample-size stream) 8)))
                   frames)
                (catch Throwable e
                  (binding [*out* *err*]
                    (println "Exception in on-audio-stream! fn:" (ex-message e))))))
            [:pointer :uint32] :void)]
    (set-audio-stream-callback-raw stream cb)
    {:arena arena :callback cb}))

(defn clear-audio-stream-callback!
  "Removes the callback of stream, which then waits for update-audio-stream."
  [stream]
  (set-audio-stream-callback-raw stream ffi/null))

(defn free-audio-callback!
  "Releases the callback of on-audio-stream!.
  Call after clear-audio-stream-callback!."
  [{:keys [arena]}]
  (.close ^java.lang.AutoCloseable arena))
