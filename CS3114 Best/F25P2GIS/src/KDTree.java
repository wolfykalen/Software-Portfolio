import java.util.ArrayList;
import java.util.List;

/**
 * A 2D k-d tree implementation 
 * for storing and querying 
 * City objects by their (x,y) coordinates.
 * The tree alternates between splitting 
 * on x and y coordinates at each level.
 * 
 * @author Kalendaco
 * @version 1.0
 */
public class KDTree {
    /**
     * Represents a node in the k-d tree.
     */
    private static class Node {
        // The city stored in this node 
        City val;
        // Reference to the left child node 
        Node left;
        // Reference to the right child node 
        Node right;

        /**
         * Creates a new node with the specified city.
         * 
         * @param v the city to store in this node
         */
        Node(City v) { 
            this.val = v; 
        }
    }

    private Node root;

    /**
     * Checks if the k-d tree is empty.
     * 
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }
    /**
     * Finds a city at the specified coordinates.
     * 
     * @param x the x-coordinate to search for
     * @param y the y-coordinate to search for
     * @return the City at (x,y) if found, null otherwise
     */
    public City find(int x, int y) {
        return findRec(root, x, y, 0);
    }

    /**
     * Recursively searches for a city at
     * specified coordinates in the k-d tree.
     * 
     * @param n the current node being examined
     * @param x the x-coordinate to search for
     * @param y the y-coordinate to search for
     * @param depth the current depth in the tree (used to determine split axis)
     * @return the City object if found, null otherwise
     */
    private City findRec(Node n, int x, int y, int depth) {
        if (n == null) return null;
        if (n.val.sameLocation(x, y)) return n.val;
        boolean splitX = (depth % 2 == 0);
        if (splitX) {
            if (x < n.val.getX()) return findRec(n.left, x, y, depth + 1);
            else return findRec(n.right, x, y, depth + 1);
        } 
        else 
        {
            if (y < n.val.getY()) return findRec(n.left, x, y, depth + 1);
            else return findRec(n.right, x, y, depth + 1);
        }
    }

    /**
     * Inserts a city into the k-d tree.
     * 
     * @param c the city to insert
     * @return true if inserted, false if a city with same coordinates exists
     * @throws IllegalArgumentException if the city parameter is null

     */
    public boolean insert(City c) {
        if (c == null) 
        {
            throw new IllegalArgumentException("Cannot insert null city");
        }
        if (find(c.getX(), c.getY()) != null) return false;
        root = insertRec(root, c, 0);
        return true;
    }

    /**
     * Recursively inserts a city into the k-d tree.
     * 
     * @param n the current node being examined during insertion
     * @param c the city to insert
     * @param depth the current depth in the tree (used to determine split axis)
     * @return the updated node after insertion
     */
    private Node insertRec(Node n, City c, int depth) {
        if (n == null) return new Node(c);
        boolean splitX = (depth % 2 == 0);
        if (splitX) {
            if (c.getX() < n.val.getX()) n.left = 
                insertRec(n.left, c, depth + 1);
            else n.right = 
                insertRec(n.right, c, depth + 1);
        } 
        else 
        {
            if (c.getY() < n.val.getY()) n.left = 
                insertRec(n.left, c, depth + 1);
            else n.right = 
                insertRec(n.right, c, depth + 1);
        }
        return n;
    }

    /**
     * Represents the result of a delete operation.
     */
    public static class DeleteResult {
        /** The deleted city, or null if not found */
        public final City deleted;
        /** Number of nodes visited during delete */
        public final int visited;

        /**
         * Creates a new DeleteResult.
         * 
         * @param d the deleted city
         * @param v number of nodes visited
         */
        DeleteResult(City d, int v) { 
            this.deleted = d; 
            this.visited = v; 
        }
    }

    /**
     * Deletes a city with the specified coordinates 
     * from the k-d tree.
     * 
     * @param x the x-coordinate of the city to delete
     * @param y the y-coordinate of the city to delete
     * @return a DeleteResult object containing the deleted city 
     *         and the number of nodes visited during deletion
     */
    public DeleteResult delete(int x, int y) {
        Counter cnt = new Counter();
        Holder hold = new Holder();
        root = deleteRec(root, x, y, 0, cnt, hold);
        return new DeleteResult(hold.city, cnt.count);
    }

    /**
     * Counter class used to track the number of nodes visited during deletion.
     */
    private static class Counter { int count; }

    /**
     * Holder class used to store the deleted city during deletion.
     */
    private static class Holder { City city; }

    /**
     * Recursively deletes a city from the k-d tree.
     * 
     * @param n the current node being examined during deletion
     * @param x the x-coordinate of the city to delete
     * @param y the y-coordinate of the city to delete
     * @param depth the current depth in the tree (used to determine split axis)
     * @param cnt a counter to track the number of nodes visited during deletion
     * @param hold a holder to store the deleted city
     * @return the updated node after deletion
     */
    private Node deleteRec(Node n, int x, int y, int depth, 
        Counter cnt, Holder hold) {
        if (n == null) return null;
        cnt.count++;
        if (n.val.sameLocation(x, y)) {
            hold.city = n.val;
            if (n.right != null) {
                Node min = findMin(n.right, depth % 2, depth + 1, 
                    new Counter());
                n.val = min.val;
                n.right = deleteRec(n.right, min.val.getX(), 
                    min.val.getY(), depth + 1, cnt, new Holder());
            } 
            else if (n.left != null) 
            {
                Node min = findMin(n.left, depth % 2, depth + 1, 
                    new Counter());
                n.val = min.val;
                n.right = deleteRec(n.left, min.val.getX(), 
                    min.val.getY(), depth + 1, cnt, new Holder());
                n.left = null;
            } 
            else 
            {
                return null;
            }
            return n;
        }
        boolean splitX = (depth % 2 == 0);
        if (splitX) {
            if (x < n.val.getX()) n.left = 
                deleteRec(n.left, x, y, depth + 1, cnt, hold);
            else n.right = 
                deleteRec(n.right, x, y, depth + 1, cnt, hold);
        } 
        else 
        {
            if (y < n.val.getY()) n.left = 
                deleteRec(n.left, x, y, depth + 1, cnt, hold);
            else n.right = 
                deleteRec(n.right, x, y, depth + 1, cnt, hold);
        }
        return n;
    }

    /**
     * Recursively finds the minimum node in the k-d tree.
     * 
     * @param n the current node being examined
     * @param axis the axis to split on (0 for x, 1 for y)
     * @param depth the current depth in the tree (used to determine split axis)
     * @param cnt a counter to track the number of nodes visited during search
     * @return the minimum node in the k-d tree
     */
    private Node findMin(Node n, int axis, int depth, Counter cnt) {
        if (n == null) return null;
        cnt.count++;
        boolean splitX = (depth % 2 == 0);
        if ((axis == 0 && splitX) || (axis == 1 && !splitX)) {
            if (n.left == null) return n;
            return findMin(n.left, axis, depth + 1, cnt);
        }
        Node leftMin = findMin(n.left, axis, depth + 1, cnt);
        Node rightMin = findMin(n.right, axis, depth + 1, cnt);
        Node min = n;
        if (axis == 0) {
            if (leftMin != null && leftMin.val.getX() 
                < min.val.getX()) min = leftMin;
            if (rightMin != null && rightMin.val.getX() 
                < min.val.getX()) min = rightMin;
        } 
        else 
        {
            if (leftMin != null && leftMin.val.getY() 
                < min.val.getY()) min = leftMin;
            if (rightMin != null && rightMin.val.getY() 
                < min.val.getY()) min = rightMin;
        }
        return min;
    }

    /**
     * Performs an inorder traversal of the k-d tree and returns 
     * a formatted string
     *
     * @return a formatted string showing the tree structure with 
     * indentation showing the depth of each node in the tree
     */
    public String inorderFormatted() {
        StringBuilder sb = new StringBuilder();
        inorderRec(root, 0, sb);
        return sb.toString();
    }

    /**
     * Recursively performs an inorder traversal of the k-d tree.
     * 
     * @param n the current node being examined
     * @param level the current level of the tree (used for indentation)
     * @param sb the StringBuilder to append the traversal to
     */
    private void inorderRec(Node n, int level, StringBuilder sb) {
        if (n == null) return;
        inorderRec(n.left, level + 1, sb);
        sb.append(level)
        .append(repeatSpaces(level * 2))
        .append(n.val.toString())
            .append('\n');
        inorderRec(n.right, level + 1, sb);
    }

    /**
     * Returns a string of spaces with the specified length.
     * 
     * @param n the number of spaces to return
     * @return a string of spaces with the specified length
     */
    private String repeatSpaces(int n) {
        if (n <= 0) return "";
        char[] arr = new char[n];
        for (int i = 0; i < n; i++) arr[i] = ' ';
        return new String(arr);
    }

    /**
     * Represents the result of a range search operation in the k-d tree.
     * Contains the list of cities found within the search radius 
     * and the number of nodes visited.
     */
    public static class SearchResult {
        /** The list of cities found within the search radius */
        public final List<City> cities;
        
        /** The number of nodes visited during the search operation */
        public final int visited;
        
        /**
         * Creates a new SearchResult with the specified 
         * cities and visited node count.
         *
         * @param cs the list of cities found in the search
         * @param v the number of nodes visited during the search
         */
        SearchResult(List<City> cs, int v) { 
            this.cities = cs; 
            this.visited = v; 
        }
    }

    /**
     * Performs a range search within the specified radius from the given point.
     * 
     * @param x the x-coordinate of the point to search from
     * @param y the y-coordinate of the point to search from
     * @param radius the radius to search within
     * @return a SearchResult object containing the list of cities within the 
     * radius and the number of visited nodes
     */
    public SearchResult rangeSearch(int x, int y, int radius) {
        List<City> out = new ArrayList<>();
        Counter cnt = new Counter();
        int r2 = radius * radius;
        rangeRec(root, x, y, r2, 0, cnt, out);
        return new SearchResult(out, cnt.count);
    }

    /**
     * Recursively performs a range search within the 
     * specified radius from the given point.
     * 
     * @param n the current node being examined
     * @param x the x-coordinate of the point to search from
     * @param y the y-coordinate of the point to search from
     * @param r2 the square of the radius (to avoid repeated calculations)
     * @param depth the current depth in the tree (used to determine split axis)
     * @param cnt a counter to track the number of nodes visited during search
     * @param out a list to store the cities within the radius
     */
    private void rangeRec(Node n, int x, int y, int r2, int depth, 
            Counter cnt, List<City> out) {
        if (n == null) {
            return;
        }
        cnt.count++;
        int dx = n.val.getX() - x;
        int dy = n.val.getY() - y;
        int distSq = dx * dx + dy * dy;
        
        if (distSq <= r2) {
            if (r2 > 0 || (dx == 0 && dy == 0)) {
                out.add(n.val);
            }
        }
        
        boolean splitX = (depth % 2 == 0);
        if (splitX) {
            if (x - radiusForAxis(r2) <= n.val.getX()) {
                rangeRec(n.left, x, y, r2, depth + 1, cnt, out);
            }
            if (x + radiusForAxis(r2) >= n.val.getX() || r2 == 0) {
                rangeRec(n.right, x, y, r2, depth + 1, cnt, out);
            }
        } 
        else 
        {
            if (y - radiusForAxis(r2) <= n.val.getY()) {
                rangeRec(n.left, x, y, r2, depth + 1, cnt, out);
            }
            if (y + radiusForAxis(r2) >= n.val.getY() || r2 == 0) {
                rangeRec(n.right, x, y, r2, depth + 1, cnt, out);
            }
        }
    }

    /**
     * Calculates the radius for the specified axis 
     * based on the square of the radius.
     * 
     * @param r2 the square of the radius
     * @return the radius for the specified axis
     */
    private int radiusForAxis(int r2) {
        int r = (int)Math.ceil(Math.sqrt(r2));
        return r;
    }
}
