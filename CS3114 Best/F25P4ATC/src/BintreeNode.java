/**
 * Interface for Bintree nodes .
 * Defines operations that all node types must support.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public interface BintreeNode {

    /**
     * Inserts an object into this node.
     *
     * @param obj   The object to insert
     * @param x     Node region origin x
     * @param y     Node region origin y
     * @param z     Node region origin z
     * @param xwid  Node region width x
     * @param ywid  Node region width y
     * @param zwid  Node region width z
     * @param dim   Current split dimension (0=x, 1=y, 2=z)
     * @param depth Current depth
     * @return The resulting node after insertion
     */
    BintreeNode insert(AirObject obj, int x, int y, int z,
        int xwid, int ywid, int zwid, int dim, int depth);

    /**
     * Removes an object from this node.
     *
     * @param obj   The object to remove
     * @param x     Node region origin x
     * @param y     Node region origin y
     * @param z     Node region origin z
     * @param xwid  Node region width x
     * @param ywid  Node region width y
     * @param zwid  Node region width z
     * @param dim   Current split dimension
     * @param depth Current depth
     * @return The resulting node after removal
     */
    BintreeNode remove(AirObject obj, int x, int y, int z,
        int xwid, int ywid, int zwid, int dim, int depth);

    /**
     * Dumps this node to the string builder.
     *
     * @param sb    StringBuilder to append to
     * @param x     Node region origin x
     * @param y     Node region origin y
     * @param z     Node region origin z
     * @param xwid  Node region width x
     * @param ywid  Node region width y
     * @param zwid  Node region width z
     * @param depth Current depth
     * @param count Node counter array
     */
    void dump(StringBuilder sb, int x, int y, int z,
        int xwid, int ywid, int zwid, int depth, int[] count);

    /**
     * Finds objects intersecting the query box.
     *
     * @param sb      StringBuilder to append to
     * @param qx      Query box origin x
     * @param qy      Query box origin y
     * @param qz      Query box origin z
     * @param qxwid   Query box width x
     * @param qywid   Query box width y
     * @param qzwid   Query box width z
     * @param x       Node region origin x
     * @param y       Node region origin y
     * @param z       Node region origin z
     * @param xwid    Node region width x
     * @param ywid    Node region width y
     * @param zwid    Node region width z
     * @param dim     Current split dimension
     * @param depth   Current depth
     * @param count   Node counter array
     */
    void intersect(StringBuilder sb, int qx, int qy, int qz,
        int qxwid, int qywid, int qzwid,
        int x, int y, int z, int xwid, int ywid, int zwid,
        int dim, int depth, int[] count);

    /**
     * Finds collisions within this node.
     *
     * @param sb    StringBuilder to append to
     * @param x     Node region origin x
     * @param y     Node region origin y
     * @param z     Node region origin z
     * @param xwid  Node region width x
     * @param ywid  Node region width y
     * @param zwid  Node region width z
     * @param depth Current depth
     */
    void collisions(StringBuilder sb, int x, int y, int z,
        int xwid, int ywid, int zwid, int depth);

    /**
     * Checks if this node is empty.
     *
     * @return true if empty
     */
    boolean isEmpty();

    /**
     * Gets all objects in this subtree.
     *
     * @param objects Array to fill
     * @param index   Current index array
     */
    void getObjects(AirObject[] objects, int[] index);

    /**
     * Counts objects in this subtree.
     *
     * @return Object count
     */
    int countObjects();
}
