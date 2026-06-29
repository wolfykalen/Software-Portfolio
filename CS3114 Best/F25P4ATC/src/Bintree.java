/**
 * A 3D Bintree for storing AirObjects.
 * Uses Composite pattern with FlyWeight for empty nodes.
 * Rotates through x, y, z dimensions for splitting.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class Bintree {

    /** The world size (1024 in each dimension). */
    private static final int WORLD_SIZE = 1024;

    /** The root node of the tree. */
    private BintreeNode root;

    /**
     * Creates a new empty Bintree.
     */
    public Bintree() {
        root = EmptyNode.getInstance();
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = EmptyNode.getInstance();
    }

    /**
     * Inserts an AirObject into the tree.
     *
     * @param obj The AirObject to insert
     */
    public void insert(AirObject obj) {
        root = root.insert(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE,
            BintreeHelper.X_DIM, 0);
    }

    /**
     * Removes an AirObject from the tree.
     *
     * @param obj The AirObject to remove
     */
    public void remove(AirObject obj) {
        root = root.remove(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE,
            BintreeHelper.X_DIM, 0);
    }

    /**
     * Returns a string representation of the tree in preorder.
     *
     * @return The tree dump string
     */
    public String dump() {
        StringBuilder sb = new StringBuilder();
        int[] count = new int[1];
        root.dump(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, count);
        sb.append(count[0]);
        sb.append(" Bintree nodes printed\n");
        return sb.toString();
    }

    /**
     * Finds all objects that intersect the given bounding box.
     *
     * @param x    Box origin x
     * @param y    Box origin y
     * @param z    Box origin z
     * @param xwid Box width in x
     * @param ywid Box width in y
     * @param zwid Box width in z
     * @return String listing intersecting objects
     */
    public String intersect(int x, int y, int z,
                            int xwid, int ywid, int zwid) {
        StringBuilder sb = new StringBuilder();
        int[] count = new int[1];
        root.intersect(sb, x, y, z, xwid, ywid, zwid,
            0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE,
            BintreeHelper.X_DIM, 0, count);
        sb.append(count[0]);
        sb.append(" nodes were visited in the bintree\n");
        return sb.toString();
    }

    /**
     * Finds all collisions between objects in the tree.
     *
     * @return String listing all collisions
     */
    public String collisions() {
        StringBuilder sb = new StringBuilder();
        root.collisions(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0);
        return sb.toString();
    }
}
