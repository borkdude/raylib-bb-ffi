# Examples

300 raylib programs that run with babashka against this library.
Examples with a `-clj` suffix, and the others whose API column says
`raylib-clj`, use the `raylib-clj.*` namespaces. The rest use `raylib.*`.

Run them from this directory:

```sh
bb run asteroids          # run one example
bb list                   # list the examples
bb run-all 2              # run each example for 2 seconds
bb run-all 2 --random 10  # run 10 random examples for 2 seconds each
bb check                  # load every example without opening a window
```

On the JVM, with JDK 25 or newer:

```sh
clojure -M:jvm -m examples.asteroids       # add :mac on macOS: -M:jvm:mac
```

The assets in `resources/` are raylib example assets under their own terms,
listed in [resources/LICENSE.md](resources/LICENSE.md).

| Example | API | Description | Ported from |
|---|---|---|---|
| [amp-envelope](src/examples/amp_envelope.clj) | raylib | ADSR amplitude envelope on a 440Hz tone | raylib `examples/audio/audio_amp_envelope.c` |
| [analog-clock](src/examples/analog_clock.clj) | raylib | a live analog clock (libc local time) | raylib `examples/shapes/shapes_clock_of_clocks.c` |
| [ascii-rendering](src/examples/ascii_rendering.clj) | raylib | ascii art from a post-process shader | raylib `examples/shaders/shaders_ascii_rendering.c` |
| [ascii-rendering-clj](src/examples/ascii_rendering_clj.clj) | raylib-clj | Post-process the scene into ASCII glyphs via a render texture | raylib `examples/shaders/shaders_ascii_rendering.c` |
| [asteroids](src/examples/asteroids.clj) | raylib | the classic vector shooter (rotate/thrust/fire) |  |
| [asteroids-clj](src/examples/asteroids_clj.clj) | raylib-clj | Shoot asteroids |  |
| [asteroids2](src/examples/asteroids2.clj) | raylib-clj | Alternate version |  |
| [audio-module](src/examples/audio_module.clj) | raylib-clj | Music visualization | raylib `examples/audio/audio_module_playing.c` |
| [audio-raw-stream](src/examples/audio_raw_stream.clj) | raylib | arrow keys steer a live sine tone's pitch/pan | raylib `examples/audio/audio_raw_stream.c` |
| [audio-stream-callback](src/examples/audio_stream_callback.clj) | raylib | raudio pulls samples from its own thread | raylib `examples/audio/audio_stream_callback.c` |
| [background-scrolling](src/examples/background_scrolling.clj) | raylib | three parallax skyline layers, each scrolling | raylib `examples/textures/textures_background_scrolling.c` |
| [background-scrolling-clj](src/examples/background_scrolling_clj.clj) | raylib-clj | Parallax demo | raylib `examples/textures/textures_background_scrolling.c` |
| [ball-physics](src/examples/ball_physics.clj) | raylib | 2D balls under gravity, SPACE respawns |  |
| [ball-physics-clj](src/examples/ball_physics_clj.clj) | raylib-clj | Grab and throw balls | raylib `examples/shapes/shapes_ball_physics.c` |
| [basic-lighting](src/examples/basic_lighting.clj) | raylib | four point lights, custom vertex shader | raylib `examples/shaders/shaders_basic_lighting.c` |
| [basic-lighting-clj](src/examples/basic_lighting_clj.clj) | raylib-clj | Dynamic lighting | raylib `examples/shaders/shaders_basic_lighting.c` |
| [basic-screen-manager](src/examples/basic_screen_manager.clj) | raylib | a LOGO/TITLE/GAMEPLAY/ENDING flow | raylib `examples/core/core_basic_screen_manager.c` |
| [basic-shapes](src/examples/basic_shapes.clj) | raylib-clj | Circles, rectangles, triangles, polygons | raylib `examples/shapes/shapes_basic_shapes.c` |
| [basic-voxel](src/examples/basic_voxel.clj) | raylib | an 8x8x8 voxel block, click one out | raylib `examples/models/models_basic_voxel.c` |
| [basic-voxel-clj](src/examples/basic_voxel_clj.clj) | raylib-clj | First-person 8x8x8 voxel world, left-click to remove a cube | raylib `examples/models/models_basic_voxel.c` |
| [billboard-rendering](src/examples/billboard_rendering.clj) | raylib | camera-facing quads, one spins |  |
| [billboard-rendering-clj](src/examples/billboard_rendering_clj.clj) | raylib-clj | Camera-facing sprites drawn far-to-near so alpha blends correctly | raylib `examples/models/models_billboard_rendering.c` |
| [blend-modes](src/examples/blend_modes.clj) | raylib | four 2D blend modes over a night skyline | raylib `examples/textures/textures_blend_modes.c` |
| [boids](src/examples/boids.clj) | raylib | flocking birds (separation/alignment/cohesion) |  |
| [bounce](src/examples/bounce.clj) | raylib | a ball bouncing around the window | raylib `examples/shapes/shapes_bouncing_ball.c` |
| [bouncing-ball](src/examples/bouncing_ball.clj) | raylib-clj | Physics demo | raylib `examples/shapes/shapes_bouncing_ball.c` |
| [bouncing-spheres](src/examples/bouncing_spheres.clj) | raylib | spheres bouncing in a 3D box (rl/sphere!) |  |
| [bouncing-spheres-clj](src/examples/bouncing_spheres_clj.clj) | raylib-clj | Physics in 3D box |  |
| [box-collisions](src/examples/box_collisions.clj) | raylib | a player cube colliding with 3D boxes | raylib `examples/models/models_box_collisions.c` |
| [box-collisions-clj](src/examples/box_collisions_clj.clj) | raylib-clj | 3D collision detection | raylib `examples/models/models_box_collisions.c` |
| [breakout](src/examples/breakout.clj) | raylib | paddle + ball + brick grid (mouse paddle) |  |
| [bullet-hell](src/examples/bullet_hell.clj) | raylib | a rotating bullet spiral | raylib `examples/shapes/shapes_bullet_hell.c` |
| [bullet-hell-clj](src/examples/bullet_hell_clj.clj) | raylib-clj | Throughput test firing rows of bullets from a rotating circle | raylib `examples/shapes/shapes_bullet_hell.c` |
| [bunnymark](src/examples/bunnymark.clj) | raylib | the sprite-count benchmark (click to add) |  |
| [camera-2d](src/examples/camera_2d.clj) | raylib-clj | 2D camera | raylib `examples/core/core_2d_camera.c` |
| [camera-2d-mouse-zoom](src/examples/camera_2d_mouse_zoom.clj) | raylib | zoom toward the cursor, pinning the point under it | raylib `examples/core/core_2d_camera_mouse_zoom.c` |
| [camera-2d-platformer](src/examples/camera_2d_platformer.clj) | raylib | five ways a camera can follow a jumping player | raylib `examples/core/core_2d_camera_platformer.c` |
| [camera-2d-platformer-clj](src/examples/camera_2d_platformer_clj.clj) | raylib-clj | 5 camera follow modes | raylib `examples/core/core_2d_camera_platformer.c` |
| [camera-2d-split-screen](src/examples/camera_2d_split_screen.clj) | raylib | two players, two cameras, two render textures | raylib `examples/core/core_2d_camera_split_screen.c` |
| [camera-2d-split-screen-clj](src/examples/camera_2d_split_screen_clj.clj) | raylib-clj | Two players, two cameras, one grid | raylib `examples/core/core_2d_camera_split_screen.c` |
| [camera-3d](src/examples/camera_3d.clj) | raylib | an orbiting 3D camera (Camera3D by value) | raylib `examples/core/core_3d_camera_mode.c` |
| [camera-3d-first-person](src/examples/camera_3d_first_person.clj) | raylib | walk a yard of columns in first person | raylib `examples/core/core_3d_camera_first_person.c` |
| [camera-3d-free](src/examples/camera_3d_free.clj) | raylib | a free-look camera around a cube, UpdateCamera | raylib `examples/core/core_3d_camera_free.c` |
| [camera-3d-free-clj](src/examples/camera_3d_free_clj.clj) | raylib-clj | Free 3D camera | raylib `examples/core/core_3d_camera_free.c` |
| [camera-3d-mode](src/examples/camera_3d_mode.clj) | raylib-clj | Minimal 3D scene - cube on a grid | raylib `examples/core/core_3d_camera_mode.c` |
| [camera-3d-split-screen](src/examples/camera_3d_split_screen.clj) | raylib | two players, two render-texture halves | raylib `examples/core/core_3d_camera_split_screen.c` |
| [camera-fps](src/examples/camera_fps.clj) | raylib-clj | FPS with physics | raylib `examples/core/core_3d_camera_fps.c` |
| [camera-modes](src/examples/camera_modes.clj) | raylib-clj | Free/Orbital/FPS cameras |  |
| [camera2d](src/examples/camera2d.clj) | raylib | a 2D camera over a skyline (struct-by-value) | raylib `examples/core/core_2d_camera.c` |
| [cellular-automata](src/examples/cellular_automata.clj) | raylib | Wolfram's elementary automata |  |
| [circle-sector-drawing](src/examples/circle_sector_drawing.clj) | raylib | a sector whose segment count you can starve | raylib `examples/shapes/shapes_circle_sector_drawing.c` |
| [circle-sector-drawing-clj](src/examples/circle_sector_drawing_clj.clj) | raylib-clj | Circle sector angles, radius and segments on raygui sliders | raylib `examples/shapes/shapes_circle_sector_drawing.c` |
| [clipboard-text](src/examples/clipboard_text.clj) | raylib | type, C copies, V pastes |  |
| [clipboard-text-clj](src/examples/clipboard_text_clj.clj) | raylib-clj | Cut, copy and paste against the system clipboard | raylib `examples/core/core_clipboard_text.c` |
| [clock-of-clocks](src/examples/clock_of_clocks.clj) | raylib | six digits spelled by a grid of clock hands | raylib `examples/shapes/shapes_clock_of_clocks.c` |
| [clock-of-clocks-clj](src/examples/clock_of_clocks_clj.clj) | raylib-clj | Digits drawn from a grid of analogue clocks | raylib `examples/shapes/shapes_clock_of_clocks.c` |
| [collision-area](src/examples/collision_area.clj) | raylib | AABB collision between two boxes | raylib `examples/shapes/shapes_collision_area.c` |
| [collision-area-clj](src/examples/collision_area_clj.clj) | raylib-clj | Collision detection | raylib `examples/shapes/shapes_collision_area.c` |
| [color-correction](src/examples/color_correction.clj) | raylib | contrast/saturation/brightness shader | raylib `examples/shaders/shaders_color_correction.c` |
| [color-wheel](src/examples/color_wheel.clj) | raylib | an HSV color wheel (rlgl triangle fan) | raylib `examples/shapes/shapes_rlgl_color_wheel.c` |
| [colors](src/examples/colors.clj) | raylib | every named raylib color in a grid |  |
| [colors-palette](src/examples/colors_palette.clj) | raylib-clj | Color showcase | raylib `examples/shapes/shapes_colors_palette.c` |
| [compute-hash](src/examples/compute_hash.clj) | raylib | CRC32/MD5/SHA1/SHA256 + Base64 of typed text | raylib `examples/core/core_compute_hash.c` |
| [core](src/examples/core.clj) | raylib | the minimal raylib window + text | raylib `examples/core/core_basic_window.c` |
| [cubicmap-rendering](src/examples/cubicmap_rendering.clj) | raylib-clj | A 3D maze mesh generated from a 32x16 PNG, one cube per lit pixel | raylib `examples/models/models_cubicmap_rendering.c` |
| [custom-logging](src/examples/custom_logging.clj) | raylib | raylib's log captured by a jolt callback | raylib `examples/core/core_custom_logging.c` |
| [custom-uniform](src/examples/custom_uniform.clj) | raylib | a mouse-steered swirl over a scene |  |
| [dashed-line](src/examples/dashed_line.clj) | raylib | a dashed line follows the mouse | raylib `examples/shapes/shapes_dashed_line.c` |
| [dashed-line-clj](src/examples/dashed_line_clj.clj) | raylib-clj | Interactive dashed line | raylib `examples/shapes/shapes_dashed_line.c` |
| [delta-time](src/examples/delta_time.clj) | raylib | per-frame vs delta-time movement | raylib `examples/core/core_delta_time.c` |
| [delta-time-clj](src/examples/delta_time_clj.clj) | raylib-clj | Frame-rate independent vs fixed-step motion | raylib `examples/core/core_delta_time.c` |
| [digital-clock](src/examples/digital_clock.clj) | raylib | a seven-segment HH:MM:SS clock (libc time) | raylib `examples/shapes/shapes_digital_clock.c` |
| [directional-billboard](src/examples/directional_billboard.clj) | raylib | a billboard whose facing row turns with the camera | raylib `examples/models/models_directional_billboard.c` |
| [directory-files](src/examples/directory_files.clj) | raylib | a file browser over LoadDirectoryFilesEx | raylib `examples/core/core_directory_files.c` |
| [dna-helix](src/examples/dna_helix.clj) | raylib | a turning double helix, coloured bases |  |
| [dna-helix-clj](src/examples/dna_helix_clj.clj) | raylib-clj | Double helix |  |
| [doom](src/examples/doom.clj) | raylib | a textured raycaster: one ray per screen column | `babashka/ffi examples/doom.clj` |
| [double-pendulum](src/examples/double_pendulum.clj) | raylib | chaotic double-pendulum motion + trail |  |
| [double-pendulum-clj](src/examples/double_pendulum_clj.clj) | raylib-clj | Chaotic pendulum simulation | raylib `examples/shapes/shapes_double_pendulum.c` |
| [drop-files](src/examples/drop_files.clj) | raylib | drag files in: FilePathList by value | raylib `examples/core/core_drop_files.c` |
| [easings](src/examples/easings.clj) | raylib | a grid of balls, each on a different easing curve |  |
| [easings-ball](src/examples/easings_ball.clj) | raylib | slide, swell and fade, one curve each | raylib `examples/shapes/shapes_easings_ball.c` |
| [easings-ball-clj](src/examples/easings_ball_clj.clj) | raylib-clj | Easing function animation | raylib `examples/shapes/shapes_easings_ball.c` |
| [easings-box](src/examples/easings_box.clj) | raylib | drop, flatten, spin, grow, fade: five curves | raylib `examples/shapes/shapes_easings_box.c` |
| [easings-box-clj](src/examples/easings_box_clj.clj) | raylib-clj | Box animation with easing functions | raylib `examples/shapes/shapes_easings_box.c` |
| [easings-rectangles](src/examples/easings_rectangles.clj) | raylib | a grid easing out in size and rotation at once | raylib `examples/shapes/shapes_easings_rectangles.c` |
| [easings-rectangles-clj](src/examples/easings_rectangles_clj.clj) | raylib-clj | Grid animation with easing functions | raylib `examples/shapes/shapes_easings_rectangles.c` |
| [easings-testbed](src/examples/easings_testbed.clj) | raylib | one curve at a time, plotted and run | raylib `examples/shapes/shapes_easings_testbed.c` |
| [easings-testbed-clj](src/examples/easings_testbed_clj.clj) | raylib-clj | All 28 easing curves, one per axis | raylib `examples/shapes/shapes_easings_testbed.c` |
| [ellipse-collision](src/examples/ellipse_collision.clj) | raylib | two ellipses that redden when they overlap | raylib `examples/shapes/shapes_ellipse_collision.c` |
| [ellipse-collision-clj](src/examples/ellipse_collision_clj.clj) | raylib-clj | Steer one ellipse into another | raylib `examples/shapes/shapes_ellipse_collision.c` |
| [eratosthenes-sieve](src/examples/eratosthenes_sieve.clj) | raylib | the Sieve of Eratosthenes, one test per pixel |  |
| [eyes](src/examples/eyes.clj) | raylib | two eyes track the mouse | raylib `examples/shapes/shapes_following_eyes.c` |
| [fireworks](src/examples/fireworks.clj) | raylib | rockets + fading particle bursts |  |
| [first-person-3d](src/examples/first_person_3d.clj) | raylib-clj | FPS camera | raylib `examples/core/core_3d_camera_first_person.c` |
| [first-person-maze](src/examples/first_person_maze.clj) | raylib | walk a grid maze, with a minimap |  |
| [first-person-maze-clj](src/examples/first_person_maze_clj.clj) | raylib-clj | Navigate 3D maze | raylib `examples/models/models_first_person_maze.c` |
| [flappy-bird](src/examples/flappy_bird.clj) | raylib | flap through the pipe gaps (SPACE) |  |
| [floppy](src/examples/floppy.clj) | raylib-clj | Flappy bird clone | `raysan5/raylib-games: floppy.c` |
| [flow-field](src/examples/flow_field.clj) | raylib | particles steered by a flow field (trails) |  |
| [fog-of-war](src/examples/fog_of_war.clj) | raylib | fog lifted by a 25x15 render texture | raylib `examples/textures/textures_fog_of_war.c` |
| [fog-rendering](src/examples/fog_rendering.clj) | raylib | exponential distance fog in the light shader | raylib `examples/shaders/shaders_fog_rendering.c` |
| [following-eyes](src/examples/following_eyes.clj) | raylib-clj | Mouse tracking | raylib `examples/shapes/shapes_following_eyes.c` |
| [format-text](src/examples/format_text.clj) | raylib | padded score + MM:SS timer readouts | raylib `examples/text/text_format_text.c` |
| [format-text-clj](src/examples/format_text_clj.clj) | raylib-clj | Formatted score/timer display | raylib `examples/text/text_format_text.c` |
| [fourier-epicycles](src/examples/fourier_epicycles.clj) | raylib | rotating circles trace a square wave |  |
| [framebuffer-rendering](src/examples/framebuffer_rendering.clj) | raylib | two cameras, two framebuffers, one scene | raylib `examples/textures/textures_framebuffer_rendering.c` |
| [game-2048](src/examples/game_2048.clj) | raylib | 2048: 4x4 tile-merge puzzle (arrow keys) |  |
| [game-of-life](src/examples/game_of_life.clj) | raylib | Conway's Game of Life (SPACE reseeds) |  |
| [geometric-shapes](src/examples/geometric_shapes.clj) | raylib | cubes, spheres, cylinders, cones, capsules | raylib `examples/models/models_geometric_shapes.c` |
| [geometric-shapes-clj](src/examples/geometric_shapes_clj.clj) | raylib-clj | 3D primitives | raylib `examples/models/models_geometric_shapes.c` |
| [gestures-testbed](src/examples/gestures_testbed.clj) | raylib-clj | Touch gestures | raylib `examples/core/core_input_gestures_testbed.c` |
| [gradient](src/examples/gradient.clj) | raylib | a vertical two-color gradient |  |
| [heightmap-rendering](src/examples/heightmap_rendering.clj) | raylib-clj | Terrain mesh generated from a greyscale PNG, brightness as elevation | raylib `examples/models/models_heightmap_rendering.c` |
| [helitorus](src/examples/helitorus.clj) | raylib | a helix wound around a torus, swept into a tube | `babashka/ffi examples/helitorus.clj` |
| [hello-world](src/examples/hello_world.clj) | raylib-clj | Basic window test | raylib `examples/core/core_basic_window.c` |
| [highdpi-demo](src/examples/highdpi_demo.clj) | raylib | logical points vs physical pixels, two rulers | raylib `examples/core/core_highdpi_demo.c` |
| [highdpi-testbed](src/examples/highdpi_testbed.clj) | raylib | diagnostic overlay: monitors, DPI, crosshair | raylib `examples/core/core_highdpi_testbed.c` |
| [hilbert-curve](src/examples/hilbert_curve.clj) | raylib | a rainbow Hilbert space-filling curve |  |
| [hilbert-curve-clj](src/examples/hilbert_curve_clj.clj) | raylib-clj | Space-filling curve drawn stroke by stroke with hue along its length | raylib `examples/shapes/shapes_hilbert_curve.c` |
| [image-channel](src/examples/image_channel.clj) | raylib | R/G/B/A split; alpha masked to show structure | raylib `examples/textures/textures_image_channel.c` |
| [image-drawing](src/examples/image_drawing.clj) | raylib | shapes baked once, then drawn live each frame | raylib `examples/textures/textures_image_drawing.c` |
| [image-generation](src/examples/image_generation.clj) | raylib | nine procedural textures, none of them loaded | raylib `examples/textures/textures_image_generation.c` |
| [image-kernel](src/examples/image_kernel.clj) | raylib | sharpen, sobel and gaussian, one call each | raylib `examples/textures/textures_image_kernel.c` |
| [image-processing](src/examples/image_processing.clj) | raylib | nine CPU-side image operations, picked live | raylib `examples/textures/textures_image_processing.c` |
| [image-rotate](src/examples/image_rotate.clj) | raylib | 0/90/180/270 exact, one angle grows the buffer | raylib `examples/textures/textures_image_rotate.c` |
| [image-text](src/examples/image_text.clj) | raylib | text baked into the image, pixelates at 4x | raylib `examples/textures/textures_image_text.c` |
| [inline-styling](src/examples/inline_styling.clj) | raylib | colour markup inside the string itself | raylib `examples/text/text_inline_styling.c` |
| [input](src/examples/input.clj) | raylib | steer a ball with the arrow keys | raylib `examples/core/core_input_keys.c` |
| [input-actions](src/examples/input_actions.clj) | raylib | abstract input actions: keyboard + gamepad | raylib `examples/core/core_input_actions.c` |
| [input-actions-clj](src/examples/input_actions_clj.clj) | raylib-clj | Remappable action layer over keys and gamepad buttons | raylib `examples/core/core_input_actions.c` |
| [input-box](src/examples/input_box.clj) | raylib | type into a text box (GetCharPressed) | raylib `examples/text/text_input_box.c` |
| [input-box-clj](src/examples/input_box_clj.clj) | raylib-clj | Text input field | raylib `examples/text/text_input_box.c` |
| [input-gamepad](src/examples/input_gamepad.clj) | raylib | sticks, triggers and buttons for pad 0 |  |
| [input-gamepad-clj](src/examples/input_gamepad_clj.clj) | raylib-clj | Gamepad demo | raylib `examples/core/core_input_gamepad.c` |
| [input-gestures](src/examples/input_gestures.clj) | raylib | tap, hold, drag and swipe, named as they happen | raylib `examples/core/core_input_gestures.c` |
| [input-gestures-clj](src/examples/input_gestures_clj.clj) | raylib-clj | Log of detected touch gestures | raylib `examples/core/core_input_gestures.c` |
| [input-keys](src/examples/input_keys.clj) | raylib-clj | Keyboard input | raylib `examples/core/core_input_keys.c` |
| [input-mouse](src/examples/input_mouse.clj) | raylib-clj | Mouse input | raylib `examples/core/core_input_mouse.c` |
| [input-multitouch](src/examples/input_multitouch.clj) | raylib | touch points (the mouse is point 0) |  |
| [input-multitouch-clj](src/examples/input_multitouch_clj.clj) | raylib-clj | Numbered circle per touch point | raylib `examples/core/core_input_multitouch.c` |
| [input-virtual-controls](src/examples/input_virtual_controls.clj) | raylib | an on-screen D-pad and action button |  |
| [input-virtual-controls-clj](src/examples/input_virtual_controls_clj.clj) | raylib-clj | On-screen D-pad | raylib `examples/core/core_input_virtual_controls.c` |
| [julia-set](src/examples/julia_set.clj) | raylib | the Julia set, mouse-steered, in a shader |  |
| [kaleidoscope](src/examples/kaleidoscope.clj) | raylib | strokes mirrored with 6-fold symmetry |  |
| [kaleidoscope-clj](src/examples/kaleidoscope_clj.clj) | raylib-clj | Mouse strokes repeated around six-fold symmetry and mirrored | raylib `examples/shapes/shapes_kaleidoscope.c` |
| [keyboard-testbed](src/examples/keyboard_testbed.clj) | raylib | an on-screen ENG-US keyboard, every key lit | raylib `examples/core/core_keyboard_testbed.c` |
| [keyboard-testbed-clj](src/examples/keyboard_testbed_clj.clj) | raylib-clj | On-screen ENG-US keyboard showing what raylib reports per key | raylib `examples/core/core_keyboard_testbed.c` |
| [l-system](src/examples/l_system.clj) | raylib | an L-system fractal plant (grows + regrows) |  |
| [lines-bezier](src/examples/lines_bezier.clj) | raylib | a cubic Bézier that follows the mouse | raylib `examples/shapes/shapes_lines_bezier.c` |
| [lines-bezier-clj](src/examples/lines_bezier_clj.clj) | raylib-clj | Interactive bezier curve | raylib `examples/shapes/shapes_lines_bezier.c` |
| [lines-drawing](src/examples/lines_drawing.clj) | raylib | a rotating fan of thick lines (line-ex!) | raylib `examples/shapes/shapes_lines_drawing.c` |
| [lines-drawing-clj](src/examples/lines_drawing_clj.clj) | raylib-clj | Draw rainbow lines on canvas | raylib `examples/shapes/shapes_lines_drawing.c` |
| [lissajous-3d](src/examples/lissajous_3d.clj) | raylib-clj | Parametric curves |  |
| [logo](src/examples/logo.clj) | raylib | the raylib logo from rectangles + text |  |
| [logo-anim](src/examples/logo_anim.clj) | raylib | the raylib logo assembling itself, unsmoothed | raylib `examples/shapes/shapes_logo_raylib_anim.c` |
| [logo-anim-clj](src/examples/logo_anim_clj.clj) | raylib-clj | Logo animation | raylib `examples/shapes/shapes_logo_raylib_anim.c` |
| [logo-raylib](src/examples/logo_raylib.clj) | raylib-clj | Raylib logo drawn with shapes | raylib `examples/shapes/shapes_logo_raylib.c` |
| [logo-raylib-anim](src/examples/logo_raylib_anim.clj) | raylib-clj | Animated logo construction | raylib `examples/shapes/shapes_logo_raylib_anim.c` |
| [lorenz-attractor](src/examples/lorenz_attractor.clj) | raylib | the Lorenz attractor traced in 3D |  |
| [lorenz-attractor-clj](src/examples/lorenz_attractor_clj.clj) | raylib-clj | Chaos theory |  |
| [magnifying-glass](src/examples/magnifying_glass.clj) | raylib | a round lens that reveals hidden markers | raylib `examples/textures/textures_magnifying_glass.c` |
| [mandelbrot-set](src/examples/mandelbrot_set.clj) | raylib | the Mandelbrot set, zoomable, in a shader |  |
| [math-angle-rotation](src/examples/math_angle_rotation.clj) | raylib | fixed spokes + a spinning line | raylib `examples/shapes/shapes_math_angle_rotation.c` |
| [math-angle-rotation-clj](src/examples/math_angle_rotation_clj.clj) | raylib-clj | Fixed and sweeping angle lines | raylib `examples/shapes/shapes_math_angle_rotation.c` |
| [math-sine-cosine](src/examples/math_sine_cosine.clj) | raylib | a live unit-circle trig visualization | raylib `examples/shapes/shapes_math_sine_cosine.c` |
| [math-sine-cosine-clj](src/examples/math_sine_cosine_clj.clj) | raylib-clj | Unit circle with sine, cosine, tangent and related angles drawn live | raylib `examples/shapes/shapes_math_sine_cosine.c` |
| [mesh-generation](src/examples/mesh_generation.clj) | raylib | eight GenMesh shapes, drawn with DrawMesh | raylib `examples/models/models_mesh_generation.c` |
| [mesh-generation-clj](src/examples/mesh_generation_clj.clj) | raylib-clj | Procedural 3D shapes | raylib `examples/models/models_mesh_generation.c` |
| [mesh-instancing](src/examples/mesh_instancing.clj) | raylib | 10000 lit cubes in one draw call | raylib `examples/shaders/shaders_mesh_instancing.c` |
| [minesweeper](src/examples/minesweeper.clj) | raylib | reveal/flag grid (mouse L reveal, R flag) |  |
| [monitor-detector](src/examples/monitor_detector.clj) | raylib | every attached display, current one lit |  |
| [mouse](src/examples/mouse.clj) | raylib | a ball follows the mouse; click to recolor | raylib `examples/core/core_input_mouse.c` |
| [mouse-painting](src/examples/mouse_painting.clj) | raylib | a paint program on a render-texture canvas | raylib `examples/textures/textures_mouse_painting.c` |
| [mouse-trail](src/examples/mouse_trail.clj) | raylib | a fading trail follows the cursor | raylib `examples/shapes/shapes_mouse_trail.c` |
| [mouse-trail-clj](src/examples/mouse_trail_clj.clj) | raylib-clj | Circles following mouse cursor | raylib `examples/shapes/shapes_mouse_trail.c` |
| [mouse-wheel](src/examples/mouse_wheel.clj) | raylib-clj | Scroll input | raylib `examples/core/core_input_mouse_wheel.c` |
| [multi-sampler](src/examples/multi_sampler.clj) | raylib | two textures blended by a second sampler |  |
| [music-stream](src/examples/music_stream.clj) | raylib-clj | MP3 streaming | raylib `examples/audio/audio_music_stream.c` |
| [npatch-drawing](src/examples/npatch_drawing.clj) | raylib | nine-patch stretching, corners held fixed | raylib `examples/textures/textures_npatch_drawing.c` |
| [orthographic-projection](src/examples/orthographic_projection.clj) | raylib | perspective vs orthographic (SPACE toggles) | raylib `examples/models/models_orthographic_projection.c` |
| [orthographic-projection-clj](src/examples/orthographic_projection_clj.clj) | raylib-clj | Perspective vs orthographic | raylib `examples/models/models_orthographic_projection.c` |
| [outlines-thickness](src/examples/outlines_thickness.clj) | raylib | thick outlines, and what a negative one does | raylib `examples/shapes/shapes_outlines_thickness.c` |
| [pacman](src/examples/pacman.clj) | raylib | pac-man, with the classic ghost personalities | `babashka/ffi examples/pacman.clj` |
| [palette-switch](src/examples/palette_switch.clj) | raylib | bands recolored by an ivec3 palette |  |
| [palette-switch-clj](src/examples/palette_switch_clj.clj) | raylib-clj | Fragment shader remaps index-encoded bands through a palette | raylib `examples/shaders/shaders_palette_switch.c` |
| [particle-system](src/examples/particle_system.clj) | raylib-clj | 3D particles |  |
| [particles](src/examples/particles.clj) | raylib | water/smoke/fire particles follow the mouse | raylib `examples/shapes/shapes_simple_particles.c` |
| [particles-blending](src/examples/particles_blending.clj) | raylib | 200 sparks trail the mouse, alpha vs additive | raylib `examples/textures/textures_particles_blending.c` |
| [penrose-tile](src/examples/penrose_tile.clj) | raylib-clj | L-system Penrose tiling drawn by turtle | raylib `examples/shapes/shapes_penrose_tile.c` |
| [penrose-tiling](src/examples/penrose_tiling.clj) | raylib | a P3 Penrose rhombus tiling (deflation) | raylib `examples/shapes/shapes_penrose_tile.c` |
| [picking-3d](src/examples/picking_3d.clj) | raylib | click a box: a real GetScreenToWorldRay pick | raylib `examples/core/core_3d_picking.c` |
| [picking-3d-clj](src/examples/picking_3d_clj.clj) | raylib-clj | Ray casting | raylib `examples/core/core_3d_picking.c` |
| [pie-chart](src/examples/pie_chart.clj) | raylib | labelled pie slices via rl/sector! | raylib `examples/shapes/shapes_pie_chart.c` |
| [point-cloud](src/examples/point_cloud.clj) | raylib | ~1500 points as tiny rlgl cubes, rotating |  |
| [point-cloud-clj](src/examples/point_cloud_clj.clj) | raylib-clj | Spherical points | raylib `examples/models/models_point_rendering.c` |
| [polygon-drawing](src/examples/polygon_drawing.clj) | raylib | hue-wheel texture on a spinning polygon | raylib `examples/textures/textures_polygon_drawing.c` |
| [pong](src/examples/pong.clj) | raylib | two-paddle classic, you (W/S) vs a CPU |  |
| [pong-clj](src/examples/pong_clj.clj) | raylib-clj | Two-player paddle game |  |
| [postprocessing](src/examples/postprocessing.clj) | raylib | post-process shaders cycled over a scene |  |
| [random-sequence](src/examples/random_sequence.clj) | raylib | bars in a shuffled order, each height used once | raylib `examples/core/core_random_sequence.c` |
| [random-values](src/examples/random_values.clj) | raylib | a new random value every two seconds | raylib `examples/core/core_random_values.c` |
| [random-values-clj](src/examples/random_values_clj.clj) | raylib-clj | Random numbers | raylib `examples/core/core_random_values.c` |
| [raw-data](src/examples/raw_data.clj) | raylib | textures built from a hand-filled byte buffer | raylib `examples/textures/textures_raw_data.c` |
| [ray-picking](src/examples/ray_picking.clj) | raylib-clj | Click to select cubes | raylib `examples/core/core_3d_picking.c` |
| [raymarching](src/examples/raymarching.clj) | raylib | a raymarched SDF scene in a shader |  |
| [rectangle-advanced](src/examples/rectangle_advanced.clj) | raylib | per-side roundness with a horizontal gradient | raylib `examples/shapes/shapes_rectangle_advanced.c` |
| [rectangle-bounds](src/examples/rectangle_bounds.clj) | raylib | draggable word-wrap text container | raylib `examples/text/text_rectangle_bounds.c` |
| [rectangle-scaling](src/examples/rectangle_scaling.clj) | raylib | drag the corner handle to resize a rect | raylib `examples/shapes/shapes_rectangle_scaling.c` |
| [rectangle-scaling-clj](src/examples/rectangle_scaling_clj.clj) | raylib-clj | Drag to resize rectangle | raylib `examples/shapes/shapes_rectangle_scaling.c` |
| [recursive-tree](src/examples/recursive_tree.clj) | raylib | a binary fractal tree |  |
| [recursive-tree-clj](src/examples/recursive_tree_clj.clj) | raylib-clj | Binary tree grown by splitting each branch in two | raylib `examples/shapes/shapes_recursive_tree.c` |
| [render-texture](src/examples/render_texture.clj) | raylib | a scene drawn off-screen, then reused |  |
| [render-texture-clj](src/examples/render_texture_clj.clj) | raylib-clj | Bouncing ball drawn into an offscreen target | raylib `examples/core/core_render_texture.c` |
| [retro-maze-3d](src/examples/retro_maze_3d.clj) | raylib-clj | GameBoy-style maze escape | `raysan5/raylib-games: retro_maze_3d.c` |
| [ring-drawing](src/examples/ring_drawing.clj) | raylib | an animated annulus via rl/ring! | raylib `examples/shapes/shapes_ring_drawing.c` |
| [ring-drawing-clj](src/examples/ring_drawing_clj.clj) | raylib-clj | Ring inner/outer radius, angles and segments on raygui sliders | raylib `examples/shapes/shapes_ring_drawing.c` |
| [rlgl-color-wheel](src/examples/rlgl_color_wheel.clj) | raylib | a hue wheel as a triangle fan, per-vertex colour | raylib `examples/shapes/shapes_rlgl_color_wheel.c` |
| [rlgl-solar-system](src/examples/rlgl_solar_system.clj) | raylib | Sun/Earth/Moon via the rlgl matrix stack | raylib `examples/models/models_rlgl_solar_system.c` |
| [rlgl-triangle](src/examples/rlgl_triangle.clj) | raylib | a Gouraud triangle with draggable corners | raylib `examples/shapes/shapes_rlgl_triangle.c` |
| [rotating-cube](src/examples/rotating_cube.clj) | raylib | a single cube spinning via the rlgl matrix stack |  |
| [rotating-cube-clj](src/examples/rotating_cube_clj.clj) | raylib-clj | 3D rotation | raylib `examples/models/models_rotating_cube.c` |
| [rounded-rect-shader](src/examples/rounded_rect_shader.clj) | raylib | SDF rounded rects: fill, border, shadow |  |
| [rounded-rectangle](src/examples/rounded_rectangle.clj) | raylib | rounded rects via sector! corners | raylib `examples/shapes/shapes_rounded_rectangle_drawing.c` |
| [rounded-rectangle-drawing](src/examples/rounded_rectangle_drawing.clj) | raylib-clj | Corner roundness, size, thickness and segments on raygui sliders | raylib `examples/shapes/shapes_rounded_rectangle_drawing.c` |
| [scissor-test](src/examples/scissor_test.clj) | raylib | a scissor rectangle clips a grid | raylib `examples/core/core_scissor_test.c` |
| [scissor-test-clj](src/examples/scissor_test_clj.clj) | raylib-clj | Scissor clipping | raylib `examples/core/core_scissor_test.c` |
| [screen-buffer](src/examples/screen_buffer.clj) | raylib | the classic DOS fire effect | raylib `examples/textures/textures_screen_buffer.c` |
| [screen-manager](src/examples/screen_manager.clj) | raylib-clj | State machine | raylib `examples/core/core_basic_screen_manager.c` |
| [shader-hot-reload](src/examples/shader_hot_reload.clj) | raylib | swap and recompile the GLSL at runtime |  |
| [shapes](src/examples/shapes.clj) | raylib | shape primitives + an rlgl triangle | raylib `examples/shapes/shapes_basic_shapes.c` |
| [simple-particles](src/examples/simple_particles.clj) | raylib-clj | Water/smoke/fire effects | raylib `examples/shapes/shapes_simple_particles.c` |
| [smooth-pixelperfect](src/examples/smooth_pixelperfect.clj) | raylib | sub-pixel camera smoothing at 5x upscale | raylib `examples/core/core_smooth_pixelperfect.c` |
| [smooth-pixelperfect-clj](src/examples/smooth_pixelperfect_clj.clj) | raylib-clj | Pixel-aligned world, sub-pixel smooth camera | raylib `examples/core/core_smooth_pixelperfect.c` |
| [snake](src/examples/snake.clj) | raylib | the classic snake (arrow keys, grow, don't crash) |  |
| [snake-clj](src/examples/snake_clj.clj) | raylib-clj | Classic snake game | `raysan5/raylib-games: snake.c` |
| [solar-system](src/examples/solar_system.clj) | raylib-clj | Orbiting planets | raylib `examples/models/models_rlgl_solar_system.c` |
| [sound-loading](src/examples/sound_loading.clj) | raylib-clj | WAV/OGG playback | raylib `examples/audio/audio_sound_loading.c` |
| [sound-multi](src/examples/sound_multi.clj) | raylib-clj | Multiple sounds | raylib `examples/audio/audio_sound_multi.c` |
| [space-invaders](src/examples/space_invaders.clj) | raylib | marching aliens (arrows + SPACE to shoot) |  |
| [spinning-cubes](src/examples/spinning_cubes.clj) | raylib | a row of cubes each spinning with a phase offset |  |
| [spinning-cubes-clj](src/examples/spinning_cubes_clj.clj) | raylib-clj | Color-cycling cubes |  |
| [spirograph](src/examples/spirograph.clj) | raylib | animated hypotrochoid roulette curves |  |
| [splines](src/examples/splines.clj) | raylib | Catmull-Rom / Bezier / B-spline (SPACE cycles) | raylib `examples/shapes/shapes_splines_drawing.c` |
| [split-screen-3d](src/examples/split_screen_3d.clj) | raylib-clj | Two-player 3D | raylib `examples/core/core_3d_camera_split_screen.c` |
| [sprite-animation](src/examples/sprite_animation.clj) | raylib | six generated poses, one source rectangle | raylib `examples/textures/textures_sprite_animation.c` |
| [sprite-animation-clj](src/examples/sprite_animation_clj.clj) | raylib-clj | Spritesheet | raylib `examples/textures/textures_sprite_animation.c` |
| [sprite-button](src/examples/sprite_button.clj) | raylib | one sheet, three states, sliced by v | raylib `examples/textures/textures_sprite_button.c` |
| [sprite-stacking](src/examples/sprite_stacking.clj) | raylib | 40 generated slices faking a 3D car | raylib `examples/textures/textures_sprite_stacking.c` |
| [srcrec-dstrec](src/examples/srcrec_dstrec.clj) | raylib | srcrec picks the frame, dstrec scales+spins it | raylib `examples/textures/textures_srcrec_dstrec.c` |
| [srcrec-dstrec-clj](src/examples/srcrec_dstrec_clj.clj) | raylib-clj | Source/destination rects with rotation | raylib `examples/textures/textures_srcrec_dstrec.c` |
| [starfield-effect](src/examples/starfield_effect.clj) | raylib | flying starfield (wheel=speed, SPACE=mode) | raylib `examples/shapes/shapes_starfield_effect.c` |
| [starfield-effect-clj](src/examples/starfield_effect_clj.clj) | raylib-clj | 3D starfield simulation | raylib `examples/shapes/shapes_starfield_effect.c` |
| [stars](src/examples/stars.clj) | raylib | a twinkling starfield |  |
| [storage-values](src/examples/storage_values.clj) | raylib | values that survive a restart, via a file | raylib `examples/core/core_storage_values.c` |
| [storage-values-clj](src/examples/storage_values_clj.clj) | raylib-clj | Save and load scores to a file | raylib `examples/core/core_storage_values.c` |
| [strings-management](src/examples/strings_management.clj) | raylib | bouncing text you slice, shatter and glue | raylib `examples/text/text_strings_management.c` |
| [terrain-generation](src/examples/terrain_generation.clj) | raylib-clj | Procedural terrain |  |
| [tesseract-view](src/examples/tesseract_view.clj) | raylib | a rotating 4D hypercube projected to 2D |  |
| [tesseract-view-clj](src/examples/tesseract_view_clj.clj) | raylib-clj | 4D hypercube | raylib `examples/models/models_tesseract_view.c` |
| [tetris](src/examples/tetris.clj) | raylib | the block-stacking puzzle (move/rotate/drop) |  |
| [tetris-clj](src/examples/tetris_clj.clj) | raylib-clj | Block-stacking puzzle |  |
| [text](src/examples/text.clj) | raylib | font sizes + MeasureText centering |  |
| [texture-outline](src/examples/texture_outline.clj) | raylib | a shader outline around a sprite's alpha edge | raylib `examples/shaders/shaders_texture_outline.c` |
| [texture-painting](src/examples/texture_painting.clj) | raylib | a blank texture painted by a shader |  |
| [texture-procedural](src/examples/texture_procedural.clj) | raylib | four textures built pixel by pixel |  |
| [texture-rendering](src/examples/texture_rendering.clj) | raylib | a grid of squares painted entirely by a shader | raylib `examples/shaders/shaders_texture_rendering.c` |
| [texture-tiling](src/examples/texture_tiling.clj) | raylib | one tile repeated across the window |  |
| [texture-waves](src/examples/texture_waves.clj) | raylib | a starfield rippled by a UV-displacement shader | raylib `examples/shaders/shaders_texture_waves.c` |
| [textured-cube](src/examples/textured_cube.clj) | raylib | two cubes, one textured atlas, one sub-rect |  |
| [textured-curve](src/examples/textured_curve.clj) | raylib | a texture laid along a cubic Bezier | raylib `examples/textures/textures_textured_curve.c` |
| [tiled-drawing](src/examples/tiled_drawing.clj) | raylib-clj | Tile a texture patch with selectable pattern, tint, scale and rotation | raylib `examples/textures/textures_tiled_drawing.c` |
| [to-image](src/examples/to_image.clj) | raylib | one image, VRAM to RAM to VRAM and back up | raylib `examples/textures/textures_to_image.c` |
| [top-down-lights](src/examples/top_down_lights.clj) | raylib | lights and shadow volumes in an alpha mask | raylib `examples/shapes/shapes_top_down_lights.c` |
| [triangle-strip](src/examples/triangle_strip.clj) | raylib | a rainbow strip via rlgl immediate mode | raylib `examples/shapes/shapes_triangle_strip.c` |
| [undo-redo](src/examples/undo_redo.clj) | raylib | move a square, then step back through the history | raylib `examples/core/core_undo_redo.c` |
| [undo-redo-clj](src/examples/undo_redo_clj.clj) | raylib-clj | Ring-buffer undo history on a grid | raylib `examples/core/core_undo_redo.c` |
| [vampire-survivors](src/examples/vampire_survivors.clj) | raylib | auto-fire survival: move, waves chase you |  |
| [vampire-survivors-clj](src/examples/vampire_survivors_clj.clj) | raylib-clj | Survival action |  |
| [vector-angle](src/examples/vector_angle.clj) | raylib | the angle between two vectors (arc + readout) | raylib `examples/shapes/shapes_vector_angle.c` |
| [vertex-displacement](src/examples/vertex_displacement.clj) | raylib | a flat plane made terrain in the vertex stage | raylib `examples/shaders/shaders_vertex_displacement.c` |
| [viewport-scaling](src/examples/viewport_scaling.clj) | raylib | 6 viewport-scaling policies, resize live | raylib `examples/core/core_viewport_scaling.c` |
| [viewport-scaling-clj](src/examples/viewport_scaling_clj.clj) | raylib-clj | Six ways to fit a fixed-resolution game onto a resizable window | raylib `examples/core/core_viewport_scaling.c` |
| [waving-cubes](src/examples/waving_cubes.clj) | raylib | an NxN grid of cubes rippling in 3D | raylib `examples/models/models_waving_cubes.c` |
| [waving-cubes-clj](src/examples/waving_cubes_clj.clj) | raylib-clj | Animated cube wave | raylib `examples/models/models_waving_cubes.c` |
| [wheel](src/examples/wheel.clj) | raylib | scroll a box with the mouse wheel | raylib `examples/core/core_input_mouse_wheel.c` |
| [window-flags](src/examples/window_flags.clj) | raylib | toggle vsync/resizable/topmost live |  |
| [window-letterbox](src/examples/window_letterbox.clj) | raylib | a fixed picture letterboxed into the window |  |
| [window-letterbox-clj](src/examples/window_letterbox_clj.clj) | raylib-clj | Resolution-independent rendering | raylib `examples/core/core_window_letterbox.c` |
| [window-should-close](src/examples/window_should_close.clj) | raylib | close is a question: ESC asks before it exits | raylib `examples/core/core_window_should_close.c` |
| [window-should-close-clj](src/examples/window_should_close_clj.clj) | raylib-clj | Custom close confirmation | raylib `examples/core/core_window_should_close.c` |
| [wireframe-shapes](src/examples/wireframe_shapes.clj) | raylib | pyramid/octahedron/torus/helix in 3D lines |  |
| [wireframe-shapes-clj](src/examples/wireframe_shapes_clj.clj) | raylib-clj | Custom wireframes |  |
| [words-alignment](src/examples/words_alignment.clj) | raylib | align a word inside a box (MeasureText) | raylib `examples/text/text_words_alignment.c` |
| [world-screen](src/examples/world_screen.clj) | raylib | a 2D label tracks a cube via GetWorldToScreen | raylib `examples/core/core_world_screen.c` |
| [world-screen-clj](src/examples/world_screen_clj.clj) | raylib-clj | 3D to 2D coords | raylib `examples/core/core_world_screen.c` |
| [writing-anim](src/examples/writing_anim.clj) | raylib | a message types itself out | raylib `examples/text/text_writing_anim.c` |
| [writing-anim-clj](src/examples/writing_anim_clj.clj) | raylib-clj | Typewriter text effect | raylib `examples/text/text_writing_anim.c` |
| [yaw-pitch-roll](src/examples/yaw_pitch_roll.clj) | raylib | the three aircraft rotations in 3D |  |
| [yaw-pitch-roll-clj](src/examples/yaw_pitch_roll_clj.clj) | raylib-clj | 3D rotation demo | raylib `examples/models/models_yaw_pitch_roll.c` |
