# Recursive Tree

Binary tree grown by splitting each branch in two.

Category: shapes. Controls: Drag Angle/Length/Decay/Depth/Thick, toggle Bezier.

Ported from raylib's `examples/shapes/shapes_recursive_tree.c`.

![recursive-tree](../demos/recursive-tree.gif)

## Run it

```sh
cd recursive-tree && bb run   # from this demo (or clojure -M:run)
bb recursive-tree             # from the repo root
```

## About

raylib [shapes] example - recursive tree

A binary tree grown by repeatedly splitting each branch in two, with the
split angle, length, decay rate, depth and thickness on sliders.

The C grows the tree with a fixed `Branch branches[1030]` array and a
loop whose bound moves as it appends - it walks index `i` up while
`count` grows ahead of it, so it is a breadth-first expansion written as
a single pass. `branches` below keeps exactly that shape, because the
ORDER matters: the `count < maxBranches` guard is checked against the
running total, so which branches get children depends on how far the
expansion has already got. A depth-first or lazy formulation would grow a
visibly different tree.

The same guard is why the branch count can exceed `maxBranches`: it is
tested before appending TWO children, so the last accepted split
overshoots by one. Kept, since it is what the C draws.

Difficulty: 3/4
Based on: shapes/shapes_recursive_tree.c
