/**
 * Internal node with two children.
 * Represents a split region in the Bintree.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class InternalNode implements BintreeNode {

    /** Left child . */
    private BintreeNode left;

    /** Right child . */
    private BintreeNode right;

    /**
     * Creates an internal node with empty children.
     */
    public InternalNode() {
        left = EmptyNode.getInstance();
        right = EmptyNode.getInstance();
    }

    /**
     * Sets the left child.
     *
     * @param node The left child
     */
    public void setLeft(BintreeNode node) {
        left = node;
    }

    /**
     * Sets the right child.
     *
     * @param node The right child
     */
    public void setRight(BintreeNode node) {
        right = node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BintreeNode insert(AirObject obj, int x, int y, int z,
            int xwid, int ywid, int zwid, int dim, int depth) {
        int nextDim = (dim + 1) % 3;
        int halfX = xwid;
        int halfY = ywid;
        int halfZ = zwid;
        int leftX = x;
        int leftY = y;
        int leftZ = z;
        int rightX = x;
        int rightY = y;
        int rightZ = z;

        if (dim == BintreeHelper.X_DIM) {
            halfX = xwid / 2;
            rightX = x + halfX;
        }
        else if (dim == BintreeHelper.Y_DIM) {
            halfY = ywid / 2;
            rightY = y + halfY;
        }
        else {
            halfZ = zwid / 2;
            rightZ = z + halfZ;
        }

        left = left.insert(obj, leftX, leftY, leftZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            nextDim, depth + 1);
        right = right.insert(obj, rightX, rightY, rightZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            nextDim, depth + 1);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BintreeNode remove(AirObject obj, int x, int y, int z,
            int xwid, int ywid, int zwid, int dim, int depth) {
        int nextDim = (dim + 1) % 3;
        int halfX = xwid;
        int halfY = ywid;
        int halfZ = zwid;
        int leftX = x;
        int leftY = y;
        int leftZ = z;
        int rightX = x;
        int rightY = y;
        int rightZ = z;

        if (dim == BintreeHelper.X_DIM) {
            halfX = xwid / 2;
            rightX = x + halfX;
        }
        else if (dim == BintreeHelper.Y_DIM) {
            halfY = ywid / 2;
            rightY = y + halfY;
        }
        else {
            halfZ = zwid / 2;
            rightZ = z + halfZ;
        }

        left = left.remove(obj, leftX, leftY, leftZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            nextDim, depth + 1);
        right = right.remove(obj, rightX, rightY, rightZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            nextDim, depth + 1);

        if (left.isEmpty() && right.isEmpty()) {
            return EmptyNode.getInstance();
        }

        // Try to merge children if few enough objects
        int totalRefs = left.countObjects() + right.countObjects();
        if (totalRefs <= BintreeHelper.MAX_LEAF_OBJECTS * 2) {
            // Collect all objects from both children
            AirObject[] allObjects = new AirObject[totalRefs];
            int[] idx = new int[1];
            left.getObjects(allObjects, idx);
            right.getObjects(allObjects, idx);

            // Count unique objects (same object may be in both children)
            int uniqueCount = 0;
            for (int i = 0; i < totalRefs; i++) {
                boolean duplicate = false;
                for (int j = 0; j < i; j++) {
                    if (allObjects[i] == allObjects[j]) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    uniqueCount++;
                }
            }

            if (uniqueCount <= BintreeHelper.MAX_LEAF_OBJECTS) {
                return mergeToLeaf(allObjects, totalRefs);
            }
        }

        return this;
    }

    /**
     * Merges children into a single leaf node.
     *
     * @param allObjects Array of all objects from children
     * @param totalRefs  Total number of object references
     * @return The merged leaf node
     */
    private BintreeNode mergeToLeaf(AirObject[] allObjects, int totalRefs) {
        LeafNode merged = new LeafNode();

        for (int i = 0; i < totalRefs; i++) {
            boolean duplicate = false;
            for (int j = 0; j < i; j++) {
                if (allObjects[i] == allObjects[j]) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                merged.addObject(allObjects[i]);
            }
        }
        if (merged.getSize() == 0) {
            return EmptyNode.getInstance();
        }
        return merged;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dump(StringBuilder sb, int x, int y, int z,
            int xwid, int ywid, int zwid, int depth, int[] count) {
        BintreeHelper.appendIndent(sb, depth);
        sb.append("I (");
        sb.append(x).append(", ").append(y).append(", ").append(z);
        sb.append(", ").append(xwid).append(", ").append(ywid);
        sb.append(", ").append(zwid).append(") ");
        sb.append(depth).append("\n");
        count[0]++;

        int dim = depth % 3;
        int halfX = xwid;
        int halfY = ywid;
        int halfZ = zwid;
        int leftX = x;
        int leftY = y;
        int leftZ = z;
        int rightX = x;
        int rightY = y;
        int rightZ = z;

        if (dim == BintreeHelper.X_DIM) {
            halfX = xwid / 2;
            rightX = x + halfX;
        }
        else if (dim == BintreeHelper.Y_DIM) {
            halfY = ywid / 2;
            rightY = y + halfY;
        }
        else {
            halfZ = zwid / 2;
            rightZ = z + halfZ;
        }

        left.dump(sb, leftX, leftY, leftZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            depth + 1, count);
        right.dump(sb, rightX, rightY, rightZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            depth + 1, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void intersect(StringBuilder sb, int qx, int qy, int qz,
            int qxwid, int qywid, int qzwid,
            int x, int y, int z, int xwid, int ywid, int zwid,
            int dim, int depth, int[] count) {
        count[0]++;
        sb.append("In Internal node (");
        sb.append(x).append(", ").append(y).append(", ").append(z);
        sb.append(", ").append(xwid).append(", ").append(ywid);
        sb.append(", ").append(zwid).append(") ");
        sb.append(depth).append("\n");

        int nextDim = (dim + 1) % 3;
        int halfX = xwid;
        int halfY = ywid;
        int halfZ = zwid;
        int leftX = x;
        int leftY = y;
        int leftZ = z;
        int rightX = x;
        int rightY = y;
        int rightZ = z;

        if (dim == BintreeHelper.X_DIM) {
            halfX = xwid / 2;
            rightX = x + halfX;
        }
        else if (dim == BintreeHelper.Y_DIM) {
            halfY = ywid / 2;
            rightY = y + halfY;
        }
        else {
            halfZ = zwid / 2;
            rightZ = z + halfZ;
        }

        int lxwid = (dim == BintreeHelper.X_DIM) ? halfX : xwid;
        int lywid = (dim == BintreeHelper.Y_DIM) ? halfY : ywid;
        int lzwid = (dim == BintreeHelper.Z_DIM) ? halfZ : zwid;
        if (BintreeHelper.regionsIntersect(qx, qy, qz, qxwid, qywid, qzwid,
                leftX, leftY, leftZ, lxwid, lywid, lzwid)) {
            left.intersect(sb, qx, qy, qz, qxwid, qywid, qzwid,
                leftX, leftY, leftZ, lxwid, lywid, lzwid,
                nextDim, depth + 1, count);
        }

        int rxwid = (dim == BintreeHelper.X_DIM) ? halfX : xwid;
        int rywid = (dim == BintreeHelper.Y_DIM) ? halfY : ywid;
        int rzwid = (dim == BintreeHelper.Z_DIM) ? halfZ : zwid;
        if (BintreeHelper.regionsIntersect(qx, qy, qz, qxwid, qywid, qzwid,
                rightX, rightY, rightZ, rxwid, rywid, rzwid)) {
            right.intersect(sb, qx, qy, qz, qxwid, qywid, qzwid,
                rightX, rightY, rightZ, rxwid, rywid, rzwid,
                nextDim, depth + 1, count);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collisions(StringBuilder sb, int x, int y, int z,
            int xwid, int ywid, int zwid, int depth) {
        int dim = depth % 3;
        int halfX = xwid;
        int halfY = ywid;
        int halfZ = zwid;
        int leftX = x;
        int leftY = y;
        int leftZ = z;
        int rightX = x;
        int rightY = y;
        int rightZ = z;

        if (dim == BintreeHelper.X_DIM) {
            halfX = xwid / 2;
            rightX = x + halfX;
        }
        else if (dim == BintreeHelper.Y_DIM) {
            halfY = ywid / 2;
            rightY = y + halfY;
        }
        else {
            halfZ = zwid / 2;
            rightZ = z + halfZ;
        }

        left.collisions(sb, leftX, leftY, leftZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            depth + 1);
        right.collisions(sb, rightX, rightY, rightZ,
            (dim == BintreeHelper.X_DIM) ? halfX : xwid,
            (dim == BintreeHelper.Y_DIM) ? halfY : ywid,
            (dim == BintreeHelper.Z_DIM) ? halfZ : zwid,
            depth + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getObjects(AirObject[] objects, int[] index) {
        left.getObjects(objects, index);
        right.getObjects(objects, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countObjects() {
        return left.countObjects() + right.countObjects();
    }
}
