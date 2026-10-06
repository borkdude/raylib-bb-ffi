# raylib-clj-demo

113 [raylib](https://github.com/raysan5/raylib) examples in Clojure, each one
its own small project. They started life in
[raylib-clj](https://github.com/b12n-oss/raylib-clj), which now holds only the
bindings, `net.b12n.raylib-clj.*` over coffi and Java's foreign-function API.
The demos pull those in as a git dependency pinned to one commit.

The gallery of every demo is at
<https://b12n-oss.github.io/raylib-clj-demo/>.

You need a JDK 22 or newer, the Clojure CLI, babashka for the tasks, and raylib
6.0 (`brew install raylib` on macOS). The demos are macOS-first: every one runs
with `-XstartOnFirstThread`, which macOS requires for OpenGL and other JVMs
reject.

## Layout

```
deps.edn                      root: one alias per demo, plus :check
bb.edn                        root tasks: info, list, run, run-all, check, lint,
                              doctor, gen, record, sync-recordings, plus one
                              task per demo
demos.edn                     every demo's name, category, title, description,
                              controls and upstream source
src/net/b12n/raylib_clj_demo/check.clj
common/
  deps.edn                    pins raylib-clj by :git/sha, the only place it's named
<demo>/                       113 of these, e.g. asteroids/
  deps.edn                    depends on ../common; :run carries the JVM options
  bb.edn                      a `run` task
  src/net/b12n/raylib_clj/scenes/<demo>.clj
  resources/                  only for the 15 demos that load files, with LICENSE.md
  docs/guide/index.md         what it shows, its controls, where it was ported from
  docs/demos/<demo>.gif       its recording
docs/                         the site: site.edn, guide/index.md, the generated
                              guide/demos.md gallery, and a second copy of every
                              recording in demos/, since docs-engine only
                              publishes assets from under docs/
scripts/gen.clj               rebuilds the files that list every demo
scripts/demo_manifest.edn     screen-grab manifest for `bb record`
scripts/sync_recordings.clj   copies docs/demos/ recordings into each demo
.clj-kondo/                   lint config, and the hook that reads coffi's defcfn
.github/workflows/            ci.yml (doctor, gen --check, lint, compile), site.yml (Pages)
```

Each demo's namespace is `net.b12n.raylib-clj.scenes.<demo>`, matching its
folder. In raylib-clj they were `examples.<demo>`, with some under
`examples.games.` and `examples.models.`.

## Running

Start with `bb doctor` in a fresh checkout. It checks the JDK, the Clojure CLI
and libraylib, and fetches the pinned raylib-clj.

From inside a demo:

```sh
cd asteroids
bb run            # or: clojure -M:run
```

From the root, every demo has a task of its own:

```sh
bb asteroids      # runs it from its own directory
bb run asteroids  # the same
bb info           # grouped cheat-sheet, every demo included
bb list           # flat list with descriptions
bb run-all 4      # every demo for 4 seconds each, exits 1 if any fail
bb check          # compile every scene, no window
```

Use `clojure`, not `clj`: rlwrap breaks GUI apps. A demo always runs from its
own directory, because the 15 that load images, sounds or shaders find them in
`./resources` by relative path. The root `deps.edn` also has an alias per demo
(`clojure -M:asteroids`), and those work for every demo that loads no files.

Most demos start an embedded nREPL on port 7888 for live development, and carry
on without it when the port is taken.

## Moving to a newer raylib-clj

The bindings are pinned in exactly one place, `common/deps.edn`. Every demo
depends on `common/`, so they all follow it. Change its `:git/sha` to the
raylib-clj commit you want, then run `bb check` and `bb run-all`.

## Adding a demo

1. Create `<demo>/src/net/b12n/raylib_clj/scenes/<demo>.clj`, with the namespace
   `net.b12n.raylib-clj.scenes.<demo>` and a `-main`.
2. Copy `deps.edn` and `bb.edn` from any existing demo and change the names in
   them. Put any files it loads under `<demo>/resources/`, with a LICENSE.md
   that says where they came from.
3. Add a line for it to `demos.edn`.
4. Run `bb gen`. It adds the root alias, the `:check` require, the root task and
   the gallery entry.
5. Write its `docs/guide/index.md`, and put its recording in both
   `<demo>/docs/demos/` and `docs/demos/`.

`bb gen --check` fails when a demo directory and `demos.edn` disagree, or when
step 4 was skipped.

## Recording the GIFs

Every recording is committed, so you never need to make one. Regenerating them
is a maintainer task: `bb record` drives every demo through
[screen-grab](https://github.com/burinc/b12n-screen-grab), an internal capture
tool that isn't publicly released, and says so if it's missing.

```sh
bb record --dry-run          # what would be captured, and why
bb record --only asteroids   # one demo (ids are demo names)
bb record                    # everything not already up to date
```

The manifest is `scripts/demo_manifest.edn`, with the per-demo `:input`
timelines and durations. screen-grab writes into `docs/demos/` and keeps
`docs/demos/ledger.edn`, so unchanged demos are skipped. Afterwards `bb record`
runs `bb sync-recordings`, which copies each new recording into
`<demo>/docs/demos/`, and then `bb gen`, which refreshes the gallery. A take
steals the screen while it runs, so leave the machine alone.

## Lint

`bb lint` runs clj-kondo over every demo. Errors fail it and warnings are
reported, the same gate raylib-clj uses. The 68 warnings today, mostly unused
bindings, came over with the code. `bb lint:strict` fails on warnings too.

## CI and the site

`ci.yml` runs on macOS with Homebrew's raylib: `bb doctor`, `bb gen --check`,
`bb sync-recordings --check`, `bb lint` and `bb check`, on every push and pull
request. `site.yml` builds `docs/` with
[docs-engine](https://github.com/jlt-commons/docs-engine) into the gallery,
runs `docs/check-site.sh` against it, and deploys to GitHub Pages from main. To
build it locally with a docs-engine checkout next to this repo:

```sh
(cd ../docs-engine && jolt run build ../raylib-clj-demo)
BASE_PATH=/raylib-clj-demo bash docs/check-site.sh
```

## License

EPL 2.0, inherited from raylib-clj and the project it began as. See `LICENSE`.
Many demos are ports of raylib's examples (zlib) and three of
raysan5/raylib-games. The files under each `resources/` carry their own terms,
two of them non-commercial. `NOTICE` covers all of it.
