# F25P4 Air Traffic Control - Video Presentation Script
## Total Time: ~6 minutes

---

## 1. PROJECT OVERVIEW (~30 seconds)

**[Show Eclipse with project open]**

"Hi, I'm presenting my Air Traffic Control project. The goal was to build a system that manages air objects in 3D space using two main data structures: a SkipList for name-based lookups and a 3D Bintree for spatial queries.

The project requirements included:
- Storing different types of air objects (Airplanes, Balloons, Birds, Drones, Rockets)
- Supporting operations like insert, delete, print, range search, intersection queries, and collision detection
- All objects exist in a 1024x1024x1024 world space

My initial confusion was understanding how the Bintree rotates through X, Y, and Z dimensions at each level, but once I understood it cycles through dimensions as depth increases, the implementation became clearer."

---

## 2. CLASS STRUCTURE (~2 minutes)

### AirObject Hierarchy (~40 seconds)
**[Show AirObject.java]**

"The `AirObject` class is the abstract base class for all air objects. It stores the 3D bounding box with origin coordinates (x, y, z) and dimensions (xWidth, yWidth, zWidth), plus a name for identification.

Key design decisions:
- Implements `Comparable` to enable sorting by name in the SkipList
- Has `isValidBoundingBox()` for common validation - coordinates must be 0-1023, widths 1-1024, and the box must fit within the world
- Abstract `isValid()` method lets each subclass add type-specific validation

**[Show Bird.java or Drone.java briefly]**

The five subclasses - AirPlane, Balloon, Bird, Drone, and Rocket - each extend AirObject and add their own fields. For example, Bird has a type and number, while Rocket has ascentRate and trajectory. Each overrides `isValid()` to validate their specific fields."

### SkipList (~30 seconds)
**[Show SkipList.java]**

"The `SkipList` is a probabilistic data structure that provides O(log n) average-case operations. It stores AirObjects indexed by name.

Key features:
- Generic implementation with `<K extends Comparable<K>, V>`
- Uses a random level generator with geometric distribution
- Supports insert, find, remove, and range search operations
- The `dump()` method outputs all nodes in order for debugging"

### Bintree Structure (~50 seconds)
**[Show BintreeNode.java interface]**

"The Bintree uses the Composite design pattern with three node types implementing `BintreeNode`:

**[Show EmptyNode.java]**
1. `EmptyNode` - Uses the Flyweight pattern with a singleton instance. Returns itself for most operations, converts to LeafNode on insert.

**[Show LeafNode.java]**
2. `LeafNode` - Stores up to 3 objects. When a 4th object is inserted, it splits into an InternalNode. Objects are stored sorted by name for consistent output.

**[Show InternalNode.java]**
3. `InternalNode` - Has left and right children. The key insight is that it splits space by alternating dimensions: depth 0 splits X, depth 1 splits Y, depth 2 splits Z, then back to X.

**[Show BintreeHelper.java]**
`BintreeHelper` contains static utility methods for intersection checks between boxes and objects, plus dimension constants."

### WorldDB (~20 seconds)
**[Show WorldDB.java]**

"`WorldDB` is the facade that coordinates the SkipList and Bintree. When adding an object, it validates, inserts into SkipList (checking for duplicates), then inserts into Bintree. Deletion removes from both structures."

---

## 3. IMPLEMENTATION DETAILS (~1.5 minutes)

### Primary Data Structures (~30 seconds)
**[Show Bintree.java]**

"The two primary data structures are:
1. **SkipList** - O(log n) average for insert, find, delete, and range search
2. **3D Bintree** - Spatial partitioning tree that cycles through X, Y, Z dimensions

The Bintree starts at the root covering the entire 1024³ world. Each internal node splits its region in half along the current dimension."

### Dimension Rotation (~30 seconds)
**[Show InternalNode insert method]**

"The dimension rotation is handled by `nextDim = (dim + 1) % 3`. When inserting:
- At depth 0 (X dimension): split x-coordinate, left gets x, right gets x + halfWidth
- At depth 1 (Y dimension): split y-coordinate similarly
- At depth 2 (Z dimension): split z-coordinate
- Depth 3 returns to X

Objects are inserted into BOTH children if they span the split boundary."

### Collision Detection (~30 seconds)
**[Show LeafNode collisions method]**

"Collision detection uses a clever optimization. In each leaf node, we check all pairs of objects. But we only report a collision if the intersection point's origin falls within THIS leaf's region. This prevents duplicate reporting when objects span multiple leaves.

The `BintreeHelper.objectsIntersect()` method checks if two bounding boxes overlap in all three dimensions."

---

## 4. MUTATION TESTING (~1 minute)

**[Show AirControlMutationTest.java]**

"For mutation testing, I created targeted test cases to achieve high coverage.

**Methodology:**
- Analyzed the mutation report to identify uncovered lines
- Created tests that exercise boundary conditions - like testing `number = 1` for Bird, `numEngines = 1` for Drone, and `ascentRate = 0` for Rocket
- Added tests that force the Bintree to split in all three dimensions

**[Show testBoundaryConditions method]**

**Key edge cases:**
- Origin coordinates at 0 and 1023
- Widths at 1 and 1024
- Origin + width exactly equals 1024
- Objects in left vs right halves of each dimension split

**[Show testInternalNodeMutations method]**

For InternalNode coverage, I created objects specifically positioned to test:
- Left and right X splits (x < 512 vs x >= 512)
- Top and bottom Y splits
- Front and back Z splits

This ensures all branches of the dimension-checking conditionals are exercised."

---

## 5. LLM USAGE (~1 minute)

"I used an LLM (Claude via Windsurf/Cascade) to assist with this project.

**How it helped:**
- Writing test cases for mutation coverage - the LLM analyzed mutation reports and suggested specific boundary values to test
- Debugging test failures by identifying root causes
- Creating comprehensive Javadoc comments
- Splitting test files when they exceeded the 600-line limit

**Problems encountered:**
- Some mutations in InternalNode involving arithmetic operations like `x + halfX` are inherently difficult to kill because replacing them with just `x` or `halfX` may still produce valid tree structures
- Had to be careful that LLM-suggested tests didn't overlap with existing tests

**Overall assessment:**
The LLM was very helpful for the tedious work of achieving mutation coverage. It could quickly identify which boundary conditions weren't being tested and generate appropriate test cases. However, I still needed to understand the code myself to verify the suggestions were correct and to explain the implementation in this video."

---

## CLOSING (~10 seconds)

"That concludes my presentation of the Air Traffic Control project. The combination of SkipList for name lookups and 3D Bintree for spatial queries provides efficient operations for managing air objects in 3D space. Thank you."

---

## TIMING CHECKLIST
- [ ] Project Overview: ~30 sec
- [ ] Class Structure: ~2 min
- [ ] Implementation: ~1.5 min
- [ ] Mutation Testing: ~1 min
- [ ] LLM Usage: ~1 min
- [ ] **TOTAL: ~6 min**

## RECORDING TIPS
1. Have Eclipse open with all relevant files in tabs
2. Use Ctrl+Shift+L to show keyboard shortcuts if needed
3. Zoom code to readable size (Ctrl + mouse wheel)
4. Practice transitions between files
5. Speak clearly and at moderate pace
6. Show your face in corner via Zoom/OBS picture-in-picture
