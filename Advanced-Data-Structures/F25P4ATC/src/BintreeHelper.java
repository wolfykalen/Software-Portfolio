/**
 * Helper methods for Bintree operations.
 * Contains static utility methods for intersection checks and formatting.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public final class BintreeHelper {

    /** Maximum objects in a leaf before considering split. */
    public static final int MAX_LEAF_OBJECTS = 3;

    /** Dimension constant for X axis. */
    public static final int X_DIM = 0;

    /** Dimension constant for Y axis. */
    public static final int Y_DIM = 1;

    /** Dimension constant for Z axis. */
    public static final int Z_DIM = 2;

    /**
     * Private constructor to prevent instantiation.
     */
    private BintreeHelper() {
        // Utility class
    }

    /**
     * Appends indentation spaces to the string builder.
     *
     * @param sb    StringBuilder to append to
     * @param depth Depth for indentation
     */
    public static void appendIndent(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
    }

    /**
     * Checks if an AirObject's bounding box intersects a region.
     * Adjacent boxes do NOT intersect.
     *
     * @param obj  The AirObject
     * @param x    Region origin x
     * @param y    Region origin y
     * @param z    Region origin z
     * @param xwid Region width x
     * @param ywid Region width y
     * @param zwid Region width z
     * @return true if they intersect
     */
    public static boolean boxesIntersect(AirObject obj,
            int x, int y, int z, int xwid, int ywid, int zwid) {
        int ox = obj.getXorig();
        int oy = obj.getYorig();
        int oz = obj.getZorig();
        int oxw = obj.getXwidth();
        int oyw = obj.getYwidth();
        int ozw = obj.getZwidth();

        return !(ox + oxw <= x || ox >= x + xwid)
            && !(oy + oyw <= y || oy >= y + ywid)
            && !(oz + ozw <= z || oz >= z + zwid);
    }

    /**
     * Checks if two AirObjects intersect.
     *
     * @param a First object
     * @param b Second object
     * @return true if they intersect
     */
    public static boolean objectsIntersect(AirObject a, AirObject b) {
        int ax = a.getXorig();
        int ay = a.getYorig();
        int az = a.getZorig();
        int axw = a.getXwidth();
        int ayw = a.getYwidth();
        int azw = a.getZwidth();

        int bx = b.getXorig();
        int by = b.getYorig();
        int bz = b.getZorig();
        int bxw = b.getXwidth();
        int byw = b.getYwidth();
        int bzw = b.getZwidth();

        return !(ax + axw <= bx || bx + bxw <= ax)
            && !(ay + ayw <= by || by + byw <= ay)
            && !(az + azw <= bz || bz + bzw <= az);
    }

    /**
     * Checks if two regions intersect.
     *
     * @param x1  First region origin x
     * @param y1  First region origin y
     * @param z1  First region origin z
     * @param xw1 First region width x
     * @param yw1 First region width y
     * @param zw1 First region width z
     * @param x2  Second region origin x
     * @param y2  Second region origin y
     * @param z2  Second region origin z
     * @param xw2 Second region width x
     * @param yw2 Second region width y
     * @param zw2 Second region width z
     * @return true if they intersect
     */
    public static boolean regionsIntersect(
            int x1, int y1, int z1, int xw1, int yw1, int zw1,
            int x2, int y2, int z2, int xw2, int yw2, int zw2) {
        return !(x1 + xw1 <= x2 || x2 + xw2 <= x1)
            && !(y1 + yw1 <= y2 || y2 + yw2 <= y1)
            && !(z1 + zw1 <= z2 || z2 + zw2 <= z1);
    }

    /**
     * Checks if a point is within a region.
     *
     * @param px   Point x
     * @param py   Point y
     * @param pz   Point z
     * @param x    Region origin x
     * @param y    Region origin y
     * @param z    Region origin z
     * @param xwid Region width x
     * @param ywid Region width y
     * @param zwid Region width z
     * @return true if point is in region
     */
    public static boolean pointInRegion(int px, int py, int pz,
            int x, int y, int z, int xwid, int ywid, int zwid) {
        return px >= x && px < x + xwid &&
               py >= y && py < y + ywid &&
               pz >= z && pz < z + zwid;
    }
}
