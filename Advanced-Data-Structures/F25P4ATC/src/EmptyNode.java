/**
 * Empty leaf node FlyWeight pattern (single shared instance).
 * Represents an empty region in the Bintree.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class EmptyNode implements BintreeNode {

    /** Singleton instance of the empty node (FlyWeight). */
    private static final EmptyNode INSTANCE = new EmptyNode();

    /**
     * Private constructor for singleton pattern.
     */
    private EmptyNode() {
    }

    /**
     * Gets the singleton instance.
     *
     * @return The empty node instance
     */
    public static EmptyNode getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BintreeNode insert(AirObject obj, int x, int y, int z,
            int xwid, int ywid, int zwid, int dim, int depth) {
        // Check if object intersects this region
        if (!BintreeHelper.boxesIntersect(obj, x, y, z, xwid, ywid, zwid)) {
            return this;
        }
        // Create new leaf with this object
        LeafNode leaf = new LeafNode();
        leaf.addObject(obj);
        return leaf;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BintreeNode remove(AirObject obj, int x, int y, int z,
            int xwid, int ywid, int zwid, int dim, int depth) {
        return this; // Nothing to remove
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dump(StringBuilder sb, int x, int y, int z,
            int xwid, int ywid, int zwid, int depth, int[] count) {
        BintreeHelper.appendIndent(sb, depth);
        sb.append("E (");
        sb.append(x).append(", ").append(y).append(", ").append(z);
        sb.append(", ").append(xwid).append(", ").append(ywid);
        sb.append(", ").append(zwid).append(") ");
        sb.append(depth).append("\n");
        count[0]++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void intersect(StringBuilder sb, int qx, int qy, int qz,
            int qxwid, int qywid, int qzwid,
            int x, int y, int z, int xwid, int ywid, int zwid,
            int dim, int depth, int[] count) {
        count[0]++; // Count this node as visited
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collisions(StringBuilder sb, int x, int y, int z,
            int xwid, int ywid, int zwid, int depth) {
        // No collisions in empty node
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getObjects(AirObject[] objects, int[] index) {
        // No objects
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countObjects() {
        return 0;
    }
}
