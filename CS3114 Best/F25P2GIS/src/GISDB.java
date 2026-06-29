//-------------------------------------------------------------------------
/**
 * Implementation of the GIS interface. This is what calls the BST and the
 * kd tree to do the work.
 *
 * @author {Kalendaco}
 * @version {1}
 *
 */
public class GISDB implements GIS {

    /**
     * The maximum allowable value for a coordinate
     */
    public static final int MAXCOORD = 32767;

    /**
     * Dimension of the points stored in the tree
     */
    public static final int DIMENSION = 2;

    // Indices
    private BST nameIndex;
    private KDTree coordIndex;

    // ----------------------------------------------------------
    /**
     * Create a new GISDB object.
     */
    GISDB() {
        nameIndex = new BST();
        coordIndex = new KDTree();
    }


    // ----------------------------------------------------------
    /**
     * Reinitialize the database
     * @return True if the database has been cleared
     */
    public boolean clear() {
        nameIndex = new BST();
        coordIndex = new KDTree();
        return true;
    }

    // ----------------------------------------------------------
    /**
     * A city at coordinate (x, y) with name name is entered into the database.
     * It is an error to insert two cities with identical coordinates,
     * but not an error to insert two cities with identical names.
     * @param name City name.
     * @param x City x-coordinate. Integer in the range 0 to 2^{15} − 1.
     * @param y City y-coordinate. Integer in the range 0 to 2^{15} − 1.
     * @return True iff the city is successfully entered into the database
     */
    public boolean insert(String name, int x, int y) {
        if (!validCoord(x) || !validCoord(y)) return false;
        City c = new City(name, x, y);
        // Insert into KDTree first to enforce unique coordinates
        if (!coordIndex.insert(c)) return false;
        // Then into BST
        nameIndex.insert(c);
        return true;
    }


    // ----------------------------------------------------------
    /**
     * The city with these coordinates is deleted from the database
     * (if it exists).
     * Print the name of the city if it exists.
     * If no city at this location exists, print the empty string.
     * @param x City x-coordinate.
     * @param y City y-coordinate.
     * @return A string with the number of nodes visited during the deletion
     *          followed by the name of the city (this is blank if nothing
     *          was deleted).
     */
    public String delete(int x, int y) {
        if (coordIndex.isEmpty()) return "";
        KDTree.DeleteResult res = coordIndex.delete(x, y);
        if (res.deleted == null) {
            return Integer.toString(res.visited) + "\n";
        }
        // Remove from BST as well
        nameIndex.deleteCity(res.deleted);
        return Integer.toString(res.visited) + "\n" + res.deleted.getName();
    }


    // ----------------------------------------------------------
    /**
     * The city with this name is deleted from the database (if it exists).
     * If two or more cities have this name, then ALL such cities must be
     * removed.
     * Print the coordinates of each city that is deleted.
     * If no city with this name exists, print the empty string.
     * @param name City name.
     * @return A string with the coordinates of each city that is deleted
     *          (listed in preorder as they are deleted).
     *          Print the empty string if no cites match.
     */
    public String delete(String name) {
        // Delete from BST first to get preorder deletion order
        // and then remove matching coords from KDTree
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        /**
         *  iterate by repeatedly deleting matches 
         *  to preserve preorder deletion order
         */
        City next;
        while (true) {
            /**
             *  Get next in preorder by collecting one 
             *  at a time via BST helper
             *  We'll use collectPreorderByName to 
             *  find if any remain
             *  But to avoid arrays, we attempt to 
             *  delete one by name via 
             *  deleteAllByName working as loop
             * Use a temporary delete-one method: 
             * leverage deleteAllByName which deletes 
             * all; we need one-at-a-time
             * Simpler approach: collect then iterate
             */


            String collected = nameIndex.inorderFormatted(); // not useful
            // Fallback approach: we will use a helper: 
            //deleteAllByName once and process
            break;
        }
        // Since we cannot easily stream one-by-one with 
        // current BST API, use deleteAllByName
        // and build outputs, then remove from KDTree
        java.util.List<City> removed = nameIndex.deleteAllByName(name);
        if (removed.isEmpty()) return "";
        for (int i = 0; i < removed.size(); i++) {
            City c = removed.get(i);
            coordIndex.delete(c.getX(), c.getY()); // discard counts
            if (i > 0) sb.append('\n');
            sb.append(c.toString());
        }
        return sb.toString();
    }


    // ----------------------------------------------------------
    /**
     * Display the name of the city at coordinate (x, y) if it exists.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return The city name if there is such a city, empty otherwise
     */
    public String info(int x, int y) {
        City c = coordIndex.find(x, y);
        return c == null ? "" : c.getName();
    }


    // ----------------------------------------------------------
    /**
     * Display the coordinates of all cities with this name, if any exist.
     * @param name The city name.
     * @return String representing the list of cities and coordinates,
     *          empty if there are none.
     */
    public String info(String name) {
        java.util.List<City> matches = nameIndex.collectPreorderByName(name);
        if (matches.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matches.size(); i++) {
            if (i > 0) sb.append('\n');
            sb.append(matches.get(i).toString());
        }
        return sb.toString();
    }


    // ----------------------------------------------------------
    /**
     * All cities within radius distance from location (x, y) are listed.
     * A city that is exactly radius distance from the query point should be
     * listed.
     * This operation should be implemented so that as few nodes as possible in
     * the k-d tree are visited.
     * @param x Search circle center: X coordinate. May be negative.
     * @param y Search circle center: X coordinate. May be negative.
     * @param radius Search radius, must be non-negative.
     * @return String listing the cities found (if any) , followed by the count
     *          of the number of k-d tree nodes looked at during the
     *          search process. If the radius is bad, return an empty string.
     *          If k-d tree is empty, the number of nodes visited is zero.
     */
    public String search(int x, int y, int radius) {
        if (radius < 0) return "";
        if (coordIndex.isEmpty()) return "0";
        KDTree.SearchResult res = coordIndex.rangeSearch(x, y, radius);
        if (res.cities.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.cities.size(); i++) {
            if (i > 0) sb.append('\n');
            sb.append(res.cities.get(i).toString());
        }
        sb.append('\n').append(res.visited);
        return sb.toString();
    }


    // ----------------------------------------------------------
    /**
     * Print a listing of the database as an inorder traversal of the k-d tree.
     * Each city should be printed on a separate line. Each line should start
     * with the level of the current node, then be indented by 2 * level spaces
     * for a node at a given level, counting the root as level 0.
     * @return String listing the cities as specified.
     */
    public String debug() {
        return coordIndex.inorderFormatted();
    }


    // ----------------------------------------------------------
    /**
    /**
     * Print a listing of the BST in alphabetical order (inorder traversal)
     * on the names.
     * Each city should be printed on a separate line. Each line should start
     * with the level of the current node, then be indented by 2 * level spaces
     * for a node at a given level, counting the root as level 0.
     * @return String listing the cities as specified.
     */
    public String print() {
        return nameIndex.inorderFormatted();
    }

    private boolean validCoord(int v) {
        return v >= 0 && v <= MAXCOORD;
    }
}
