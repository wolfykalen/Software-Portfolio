import java.util.Random;

/**
 * The world for this project. We have a Skip List and a Bintree
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class WorldDB implements ATC {

    /** The world size in each dimension. */
    private static final int WORLD_SIZE = 1024;

    /** Random number generator for SkipList. */
    private Random rnd;

    /** SkipList storing AirObjects by name. */
    private SkipList<String, AirObject> skipList;

    /** Bintree storing AirObjects spatially. */
    private Bintree bintree;

    /**
     * Create a brave new World.
     *
     * @param r A random number generator to use
     */
    public WorldDB(Random r) {
        rnd = r;
        if (rnd == null) {
            rnd = new Random();
        }
        clear();
    }

    /**
     * Clear the world.
     */
    public void clear() {
        skipList = new SkipList<>(rnd);
        bintree = new Bintree();
    }

    // ----------------------------------------------------------
    /**
     * insert an AirObject into the database.
     *
     * @param a An AirObject.
     * @return True iff the AirObject is successfully entered into the database
     */
    public boolean add(AirObject a) {
        // Validate the object
        if (a == null || !a.isValid()) {
            return false;
        }

        // Try to insert into SkipList (fails if duplicate name)
        if (!skipList.insert(a.getName(), a)) {
            return false;
        }

        // Insert into Bintree
        bintree.insert(a);
        return true;
    }

    // ----------------------------------------------------------
    /**
     * The AirObject with this name is deleted from the database (if it exists).
     * Print the AirObject's toString value if one with that name exists.
     * If no such AirObject with this name exists, return null.
     *
     * @param name AirObject name.
     * @return A string representing the AirObject, or null if no such name.
     */
    
    public String delete(String name) {
        if (name == null) {
            return null;
        }

        // Remove from SkipList
        AirObject removed = skipList.remove(name);
        if (removed == null) {
            return null;
        }

        // Remove from Bintree
        bintree.remove(removed);
        return removed.toString();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of the Skiplist in alphabetical order on the names.
     * See the sample test cases for details on format.
     *
     * @return String listing the AirObjects in the Skiplist as specified.
     */
    public String printskiplist() {
        return skipList.dump();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of the Bintree nodes in preorder.
     * See the sample test cases for details on format.
     *
     * @return String listing the Bintree nodes as specified.
     */
    public String printbintree() {
        return bintree.dump();
    }

    // ----------------------------------------------------------
    /**
     * Print an AirObject with a given name if it exists.
     *
     * @param name The name of the AirObject to print
     * @return String showing the toString for the AirObject if it exists
     *         Return null if there is no such name
     */
    public String print(String name) {
        if (name == null) {
            return null;
        }
        AirObject obj = skipList.find(name);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of the AirObjects found in the database between the
     * min and max values for names.
     * See the sample test cases for details on format.
     *
     * @param start Minimum of range
     * @param end   Maximum of range
     * @return String listing the AirObjects in the range as specified.
     *         Null if the parameters are bad
     */
    public String rangeprint(String start, String end) {
        if (start == null || end == null) {
            return null;
        }
        if (start.compareTo(end) > 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Found these records in the range ");
        sb.append(start).append(" to ").append(end).append("\n");

        Object[] results = skipList.rangeSearch(start, end);
        if (results != null) {
            for (Object obj : results) {
                AirObject air = (AirObject) obj;
                sb.append(air.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of all collisions between AirObjects bounding boxes
     * that are found in the database.
     * See the sample test cases for details on format.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     *
     * @return String listing the AirObjects that participate in collisions.
     */
    public String collisions() {
        StringBuilder sb = new StringBuilder();
        sb.append("The following collisions exist in the database:\n");
        sb.append(bintree.collisions());
        return sb.toString();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of all AirObjects whose bounding boxes
     * that intersect the given bounding box.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     * See the sample test cases for details on format.
     *
     * @param x    Bounding box upper left x
     * @param y    Bounding box upper left y
     * @param z    Bounding box upper left z
     * @param xwid Bounding box x width
     * @param ywid Bounding box y width
     * @param zwid Bounding box z width
     * @return String listing the AirObjects that intersect the given box.
     *         Return null if any input parameters are bad
     */
    public String intersect(int x, int y, int z,
                            int xwid, int ywid, int zwid) {
        // Validate parameters
        if (!isValidBox(x, y, z, xwid, ywid, zwid)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (");
        sb.append(x).append(", ").append(y).append(", ").append(z);
        sb.append(", ").append(xwid).append(", ").append(ywid);
        sb.append(", ").append(zwid).append("):\n");
        sb.append(bintree.intersect(x, y, z, xwid, ywid, zwid));
        return sb.toString();
    }

    /**
     * Validates a bounding box for intersect queries.
     *
     * @param x    Box origin x
     * @param y    Box origin y
     * @param z    Box origin z
     * @param xwid Box width x
     * @param ywid Box width y
     * @param zwid Box width z
     * @return true if valid
     */
    private boolean isValidBox(int x, int y, int z,
                               int xwid, int ywid, int zwid) {
        // Check coordinates are in valid range [0, 1023]
        // Check widths are in valid range [1, 1024]
        // Check box fits within world
        return x >= 0 && x <= 1023
            && y >= 0 && y <= 1023
            && z >= 0 && z <= 1023
            && xwid >= 1 && xwid <= 1024
            && ywid >= 1 && ywid <= 1024
            && zwid >= 1 && zwid <= 1024
            && x + xwid <= WORLD_SIZE
            && y + ywid <= WORLD_SIZE
            && z + zwid <= WORLD_SIZE;
    }
}
