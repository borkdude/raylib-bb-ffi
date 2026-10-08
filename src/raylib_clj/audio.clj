(ns raylib-clj.audio
  "Audio device and music stream functions"
  (:require
   [babashka.ffi :as ffi]
   [raylib-clj.core]))

;; AudioStream struct (32 bytes on 64-bit)
;; { rAudioBuffer *buffer; rAudioProcessor *processor; uint sampleRate, sampleSize, channels; }
(def audio-stream
  [:struct
   [[:buffer-lo :int] ; pointer low bits
    [:buffer-hi :int] ; pointer high bits
    [:processor-lo :int] ; pointer low bits
    [:processor-hi :int] ; pointer high bits
    [:sample-rate :int]
    [:sample-size :int]
    [:channels :int]
    [:_pad :int]]]) ; padding to 32 bytes

;; Music struct (56 bytes on 64-bit)
;; { AudioStream stream; uint frameCount; bool looping; int ctxType; void *ctxData; }
(def music
  [:struct
   [[:stream audio-stream] ; 32 bytes
    [:frame-count :int] ; 4 bytes
    [:looping :int] ; bool as int (4 bytes with padding)
    [:ctx-type :int] ; 4 bytes
    [:_pad2 :int] ; padding for pointer alignment
    [:ctx-data-lo :int] ; pointer low bits
    [:ctx-data-hi :int]]]) ; pointer high bits

;; Audio device management
(ffi/defcfn init-audio-device!
  "Initialize audio device and context"
  {:arglists '([])}
  "InitAudioDevice"
  [] :void)

(ffi/defcfn close-audio-device!
  "Close the audio device and context"
  {:arglists '([])}
  "CloseAudioDevice"
  [] :void)

(ffi/defcfn is-audio-device-ready?
  "Check if audio device has been initialized successfully"
  {:arglists '([])}
  "IsAudioDeviceReady"
  [] :uint8)

(ffi/defcfn set-master-volume!
  "Set master volume (listener)"
  {:arglists '([volume])}
  "SetMasterVolume"
  [:float] :void)

;; Music stream functions
(ffi/defcfn load-music-stream
  "Load music stream from file"
  {:arglists '([filename])}
  "LoadMusicStream"
  [:string] music)

(ffi/defcfn unload-music-stream!
  "Unload music stream"
  {:arglists '([music])}
  "UnloadMusicStream"
  [music] :void)

(ffi/defcfn play-music-stream!
  "Start music playing"
  {:arglists '([music])}
  "PlayMusicStream"
  [music] :void)

(ffi/defcfn is-music-stream-playing?
  "Check if music is playing"
  {:arglists '([music])}
  "IsMusicStreamPlaying"
  [music] :uint8)

(ffi/defcfn update-music-stream!
  "Updates buffers for music streaming"
  {:arglists '([music])}
  "UpdateMusicStream"
  [music] :void)

(ffi/defcfn stop-music-stream!
  "Stop music playing"
  {:arglists '([music])}
  "StopMusicStream"
  [music] :void)

(ffi/defcfn pause-music-stream!
  "Pause music playing"
  {:arglists '([music])}
  "PauseMusicStream"
  [music] :void)

(ffi/defcfn resume-music-stream!
  "Resume playing paused music"
  {:arglists '([music])}
  "ResumeMusicStream"
  [music] :void)

(ffi/defcfn set-music-volume!
  "Set volume for music (1.0 is max level)"
  {:arglists '([music volume])}
  "SetMusicVolume"
  [music :float] :void)

(ffi/defcfn set-music-pitch!
  "Set pitch for a music (1.0 is base level)"
  {:arglists '([music pitch])}
  "SetMusicPitch"
  [music :float] :void)

(ffi/defcfn get-music-time-length
  "Get music time length (in seconds)"
  {:arglists '([music])}
  "GetMusicTimeLength"
  [music] :float)

(ffi/defcfn get-music-time-played
  "Get current music time played (in seconds)"
  {:arglists '([music])}
  "GetMusicTimePlayed"
  [music] :float)

(ffi/defcfn set-music-pan!
  "Set pan for a music (-1.0 left, 0.0 center, 1.0 right)"
  {:arglists '([music pan])}
  "SetMusicPan"
  [music :float] :void)

;; Sound struct (40 bytes on 64-bit)
;; { AudioStream stream; uint frameCount; }
(def sound
  [:struct
   [[:stream audio-stream] ; 32 bytes
    [:frame-count :int] ; 4 bytes
    [:_pad :int]]]) ; padding to 40 bytes

;; Sound loading/unloading functions
(ffi/defcfn load-sound
  "Load sound from file"
  {:arglists '([filename])}
  "LoadSound"
  [:string] sound)

(ffi/defcfn load-sound-alias
  "Create a new sound that shares the same sample data as the source sound"
  {:arglists '([source])}
  "LoadSoundAlias"
  [sound] sound)

(ffi/defcfn unload-sound!
  "Unload sound"
  {:arglists '([sound])}
  "UnloadSound"
  [sound] :void)

(ffi/defcfn unload-sound-alias!
  "Unload a sound alias (does not deallocate sample data)"
  {:arglists '([alias])}
  "UnloadSoundAlias"
  [sound] :void)

;; Sound control functions
(ffi/defcfn play-sound!
  "Play a sound"
  {:arglists '([sound])}
  "PlaySound"
  [sound] :void)

(ffi/defcfn stop-sound!
  "Stop playing a sound"
  {:arglists '([sound])}
  "StopSound"
  [sound] :void)

(ffi/defcfn pause-sound!
  "Pause a sound"
  {:arglists '([sound])}
  "PauseSound"
  [sound] :void)

(ffi/defcfn resume-sound!
  "Resume a paused sound"
  {:arglists '([sound])}
  "ResumeSound"
  [sound] :void)

(ffi/defcfn is-sound-playing?
  "Check if a sound is currently playing"
  {:arglists '([sound])}
  "IsSoundPlaying"
  [sound] :uint8)

(ffi/defcfn set-sound-volume!
  "Set volume for a sound (1.0 is max level)"
  {:arglists '([sound volume])}
  "SetSoundVolume"
  [sound :float] :void)

(ffi/defcfn set-sound-pitch!
  "Set pitch for a sound (1.0 is base level)"
  {:arglists '([sound pitch])}
  "SetSoundPitch"
  [sound :float] :void)

(ffi/defcfn set-sound-pan!
  "Set pan for a sound (-1.0 left, 0.0 center, 1.0 right)"
  {:arglists '([sound pan])}
  "SetSoundPan"
  [sound :float] :void)
