(ns raylib.files
  "Dropped files, directory listings, and working-directory and file name
  lookups."
  (:require
   [babashka.ffi :as ffi]
   [raylib.native]))

(def ^:private file-path-list-layout
  [:struct [[:count :uint] [:paths :pointer]]])

(ffi/defcfn file-dropped?
  "Returns true if files were dropped on the window since the last check."
  "IsFileDropped" [] :bool)
(ffi/defcfn directory-exists?
  "Returns true if path is an existing directory."
  "DirectoryExists" [:string] :bool)
(ffi/defcfn ^:private load-dropped-files-raw "LoadDroppedFiles"
  [] file-path-list-layout)
(ffi/defcfn ^:private unload-dropped-files-raw "UnloadDroppedFiles"
  [file-path-list-layout] :void)
(ffi/defcfn ^:private load-directory-files-ex-raw "LoadDirectoryFilesEx"
  [:string :string :bool] file-path-list-layout)
(ffi/defcfn ^:private unload-directory-files-raw "UnloadDirectoryFiles"
  [file-path-list-layout] :void)
(ffi/defcfn get-working-directory   "GetWorkingDirectory"  [] :string)
(ffi/defcfn get-prev-directory-path "GetPrevDirectoryPath" [:string] :string)
(ffi/defcfn get-file-name           "GetFileName"          [:string] :string)

(defn- file-path-list->vec [{:keys [count paths]}]
  (if (zero? count)
    []
    (mapv ffi/ptr->string
          (ffi/read (ffi/reinterpret paths (* count (ffi/sizeof :pointer)))
                    [:array :pointer count]))))

(defn dropped-files
  "Returns the paths of the files dropped on the window as a vector of strings.
  Call after file-dropped? returns true."
  []
  (let [fpl (load-dropped-files-raw)]
    (try (file-path-list->vec fpl)
         (finally (unload-dropped-files-raw fpl)))))

(defn directory-files
  "Returns the paths in dir as a vector of strings.
  filter is a raylib filter string. nil or \"\" selects files only.
  \"*.*\" selects all entries, \"DIRS*\" directories and \"FILES*\" files.
  Extensions such as \".png;.c\" select files by extension and combine with
  DIRS* and FILES* over a semicolon.
  scan-subdirs? recurses into subdirectories."
  [dir filter scan-subdirs?]
  (let [fpl (load-directory-files-ex-raw dir (or filter "") (boolean scan-subdirs?))]
    (try (file-path-list->vec fpl)
         (finally (unload-directory-files-raw fpl)))))
