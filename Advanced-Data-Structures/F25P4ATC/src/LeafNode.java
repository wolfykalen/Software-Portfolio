/**
 * Leaf node containing AirObjects.
 * Stores objects that intersect this region of the Bintree.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class LeafNode implements BintreeNode {

    /** Objects stored in this leaf. */
    private AirObject[] objects;

    /** Number of objects in this leaf. */
    private int size;

    /** Initial capacity for objects array. */
    private static final int INITIAL_CAPACITY = 4;

    /**
     * Creates an empty leaf node.
     */
    public LeafNode() {
        objects = new AirObject[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds an object to this leaf in sorted order.
     *
     * @param obj The object to add
     */
    public void addObject(AirObject obj) {
        if (size >= objects.length) { 
            AirObject[] newArr = new AirObject[objects.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i] = objects[i];
            }
            objects = newArr;
        }
        // Find insertion point to maintain sorted order
        int insertPos = 0;
        while (insertPos < size &&
               objects[insertPos].getName().compareTo(obj.getName()) < 0) {
            insertPos++;
        }
        // Shift elements to make room
        for (int i = size; i > insertPos; i--) {
            objects[i] = objects[i - 1];
        }
        objects[insertPos] = obj;
        size++;
    }

    /**
     * Removes an object from this leaf.
     *
     * @param obj The object to remove
     * @return true if removed
     */
    public boolean removeObject(AirObject obj) {
        for (int i = 0; i < size; i++) {
            if (objects[i] == obj) {
                for (int j = i; j < size - 1; j++) {
                    objects[j] = objects[j + 1];
                }
                objects[--size] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the number of objects in this leaf.
     *
     * @return The size
     */
    public int getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BintreeNode insert(AirObject obj, int x, int y, int z,
            int xwid, int ywid, int zwid, int dim, int depth) {
        if (!BintreeHelper.boxesIntersect(obj, x, y, z, xwid, ywid, zwid)) {
            return this;
        }
        addObject(obj);
        if (size > BintreeHelper.MAX_LEAF_OBJECTS && !allIntersect()) {
            return split(x, y, z, xwid, ywid, zwid, dim, depth);
        }
        return this;
    }

    /**
     * Splits this leaf into an internal node.
     *
     * @param x     Node region origin x
     * @param y     Node region origin y
     * @param z     Node region origin z
     * @param xwid  Node region width x
     * @param ywid  Node region width y
     * @param zwid  Node region width z
     * @param dim   Current split dimension
     * @param depth Current depth
     * @return The new internal node
     */
    private BintreeNode split(int x, int y, int z,
            int xwid, int ywid, int zwid, int dim, int depth) {
        InternalNode internal = new InternalNode();
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

        BintreeNode left = EmptyNode.getInstance();
        BintreeNode right = EmptyNode.getInstance();

        for (int i = 0; i < size; i++) {
            AirObject obj = objects[i];
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
        }

        internal.setLeft(left);
        internal.setRight(right);
        return internal;
    }

    /**
     * Checks if all objects have a common intersection box.
     *
     * @return true if all objects share a non empty intersection
     */
    private boolean allIntersect() {
        if (size <= 1) {
            return true;
        }
        int minX = objects[0].getXorig();
        int minY = objects[0].getYorig();
        int minZ = objects[0].getZorig();
        int maxX = minX + objects[0].getXwidth();
        int maxY = minY + objects[0].getYwidth();
        int maxZ = minZ + objects[0].getZwidth();

        for (int i = 1; i < size; i++) {
            int ox = objects[i].getXorig();
            int oy = objects[i].getYorig();
            int oz = objects[i].getZorig();
            int oxMax = ox + objects[i].getXwidth();
            int oyMax = oy + objects[i].getYwidth();
            int ozMax = oz + objects[i].getZwidth();

            minX = Math.max(minX, ox);
            minY = Math.max(minY, oy);
            minZ = Math.max(minZ, oz);
            maxX = Math.min(maxX, oxMax);
            maxY = Math.min(maxY, oyMax);
            maxZ = Math.min(maxZ, ozMax);

            if (minX >= maxX || minY >= maxY || minZ >= maxZ) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BintreeNode remove(AirObject obj, int x, int y, int z,
            int xwid, int ywid, int zwid, int dim, int depth) {
        removeObject(obj);
        if (size == 0) {
            return EmptyNode.getInstance();
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dump(StringBuilder sb, int x, int y, int z,
            int xwid, int ywid, int zwid, int depth, int[] count) {
        BintreeHelper.appendIndent(sb, depth);
        sb.append("Leaf with ").append(size).append(" objects (");
        sb.append(x).append(", ").append(y).append(", ").append(z);
        sb.append(", ").append(xwid).append(", ").append(ywid);
        sb.append(", ").append(zwid).append(") ");
        sb.append(depth).append("\n");
        count[0]++;

        for (int i = 0; i < size; i++) {
            BintreeHelper.appendIndent(sb, depth);
            sb.append("(").append(objects[i].toString()).append(")\n");
        }
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
        sb.append("In leaf node (");
        sb.append(x).append(", ").append(y).append(", ").append(z);
        sb.append(", ").append(xwid).append(", ").append(ywid);
        sb.append(", ").append(zwid).append(") ");
        sb.append(depth).append("\n");

        for (int i = 0; i < size; i++) {
            AirObject obj = objects[i];
            if (BintreeHelper.boxesIntersect(obj, qx, qy, qz,
                    qxwid, qywid, qzwid)) {
                int intX = Math.max(obj.getXorig(), qx);
                int intY = Math.max(obj.getYorig(), qy);
                int intZ = Math.max(obj.getZorig(), qz);
                if (BintreeHelper.pointInRegion(intX, intY, intZ,
                        x, y, z, xwid, ywid, zwid)) {
                    sb.append(obj.toString()).append("\n");
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collisions(StringBuilder sb, int x, int y, int z,
            int xwid, int ywid, int zwid, int depth) {
        sb.append("In leaf node (");
        sb.append(x).append(", ").append(y).append(", ").append(z);
        sb.append(", ").append(xwid).append(", ").append(ywid);
        sb.append(", ").append(zwid).append(") ");
        sb.append(depth).append("\n");

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                AirObject a = objects[i];
                AirObject b = objects[j];
                if (BintreeHelper.objectsIntersect(a, b)) {
                    int intX = Math.max(a.getXorig(), b.getXorig());
                    int intY = Math.max(a.getYorig(), b.getYorig());
                    int intZ = Math.max(a.getZorig(), b.getZorig());
                    if (BintreeHelper.pointInRegion(intX, intY, intZ,
                            x, y, z, xwid, ywid, zwid)) {
                        sb.append("(").append(a.toString()).append(") and (");
                        sb.append(b.toString()).append(")\n");
                    }
                }
            }
        }
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
    public void getObjects(AirObject[] arr, int[] index) {
        for (int i = 0; i < size; i++) {
            arr[index[0]++] = objects[i];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countObjects() {
        return size;
    }
}
