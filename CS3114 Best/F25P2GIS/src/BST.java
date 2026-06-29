import java.util.ArrayList;
import java.util.List;

/**
 * A binary search tree implementation that stores City objects.
 * The tree is ordered by city names, 
 * with equal names stored in the left subtree.
 * 
 * @author Kalendaco
 * @version 1.0
 */
public class BST {
    /**
     * Represents a node in the binary search tree.
     */
    private static class Node {
        //The city stored in this node 
        City val;
        // Reference to the left child  
        Node left;
        // Reference to the right child 
        Node right;

        /**
         * Creates a new node with the specified city.
         * 
         * @param v The city to store in this node
         */
        Node(City v) { 
            this.val = v; 
        }
    }

    // The root node of the BST.
    private Node root;

    /**
     * Inserts a city into the BST. Cities with names less than or equal to 
     * the current node's city name are inserted into the left subtree.
     * 
     * @param c The city to insert
     * @throws IllegalArgumentException if the city parameter is null
     */
    public void insert(City c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot insert null city");
        }
        root = insertRec(root, c);
    }

    /**
     * Recursively inserts a city into the BST.
     * 
     * @param n The current node being examined during insertion
     * @param c The city to insert
     * @return The updated node after insertion
     */
    private Node insertRec(Node n, City c) {
        if (n == null) return new Node(c);
        int cmp = c.getName().compareTo(n.val.getName());
        if (cmp <= 0) {
            n.left = insertRec(n.left, c);
        } 
        else 
        {
            n.right = insertRec(n.right, c);
        }
        return n;
    }

    /**
     * Deletes a specific city from the BST. 
     * The city must match both name and coordinates.
     * 
     * @param target The city to delete
     * @return true if the city was found and deleted, false otherwise
     * @throws IllegalArgumentException if the target city is null
     */
    public boolean deleteCity(City target) {
        if (target == null) {
            throw new IllegalArgumentException("Target city cannot be null");
        }
        int[] changed = new int[1];
        root = deleteCityRec(root, target, changed);
        return changed[0] == 1;
    }

    /**
     * Recursively searches for and deletes a city from the BST.
     * 
     * @param n The current node being examined
     * @param target The city to delete
     * @param changed array tracks if deletion was successful 
     * @return The updated node after deletion
     */
    private Node deleteCityRec(Node n, City target, int[] changed) {
        if (n == null) return null;
        int cmp = target.getName().compareTo(n.val.getName());
        if (cmp < 0 || cmp == 0) {
            n.left = deleteCityRec(n.left, target, changed);
            if (changed[0] == 1) return n;
            if (cmp == 0 && sameCity(n.val, target)) {
                changed[0] = 1;
                return deleteNode(n);
            }
            if (cmp > 0) {
                n.right = deleteCityRec(n.right, target, changed);
            }
            return n;
        } 
        else 
        {
            n.right = deleteCityRec(n.right, target, changed);
            return n;
        }
    }

    /**
     * Compares two cities for equality based on name and coordinates.
     * 
     * @param a First city to compare
     * @param b Second city to compare
     * @return true if the cities have the same name and coordinates
     */
    private boolean sameCity(City a, City b) {
        return a.getName().equals(b.getName()) && 
            a.getX() == b.getX() && 
            a.getY() == b.getY();
    }

    /**
     * Deletes a node from the BST.
     * 
     * @param n The node to delete
     * @return The node that replaces the deleted node in the tree
     */
    private Node deleteNode(Node n) {
        if (n.left == null) return n.right;
        if (n.right == null) return n.left;
        Node[] res = extractMax(n.left);
        n.left = res[0];
        n.val = res[1].val;
        return n;
    }

    /**
     * Extracts the maximum node from a subtree.
     * 
     * @param n The root of the subtree
     * @return An array where the first element is the updated subtree root 
     *         and the second is the maximum node
     */
    private Node[] extractMax(Node n) {
        if (n.right == null) {
            return new Node[] { n.left, n };
        }
        Node[] res = extractMax(n.right);
        n.right = res[0];
        res[0] = n;
        return res;
    }

    /**
     * Deletes all cities with the given name from the BST.
     * 
     * @param name The name of the cities to delete
     * @return A list of all deleted cities in preorder deletion order
     * @throws IllegalArgumentException if the name parameter is null
     */
    public List<City> deleteAllByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        List<City> out = new ArrayList<>();
        root = deleteAllByNameRec(root, name, out);
        return out;
    }

    /**
     * Recursively deletes all nodes with the given name.
     * 
     * @param n The current node being examined
     * @param name The name of the cities to delete
     * @param out List to collect deleted cities
     * @return The updated node after deletions
     */
    private Node deleteAllByNameRec(Node n, String name, List<City> out) {
        if (n == null) return null;

        int cmp = name.compareTo(n.val.getName());

        if (cmp < 0) {
            n.left = deleteAllByNameRec(n.left, name, out);
            return n;
        } 
        else if (cmp > 0) {
            n.right = deleteAllByNameRec(n.right, name, out);
            return n;
        }
        else 
        {
            out.add(n.val);
            Node newRoot = deleteNode(n);
            return deleteAllByNameRec(newRoot, name, out);
        }
    }
    /**
     * Performs a preorder traversal of the BST and collects 
     * all cities with the specified name
     *
     * @param name the name of the cities to collect 
     * @return a list of cities with the specified name, 
     * in preorder traversal order.
     * Returns an empty list if no cities with 
     * the specified name are found.
     */
    public List<City> collectPreorderByName(String name) {
        List<City> out = new ArrayList<>();
        collectPreRec(root, name, out);
        return out;
    }

    /**
     * Recursively collects cities with the given name in preorder.
     * 
     * @param n The current node being examined
     * @param name The name of the cities to collect
     * @param out List to collect cities with the given name
     */
    private void collectPreRec(Node n, String name, List<City> out) {
        if (n == null) return;
        if (n.val.getName().equals(name)) out.add(n.val);
        collectPreRec(n.left, name, out);
        collectPreRec(n.right, name, out);
    }

    /**
     * Returns a string representation of the BST using in-order traversal.
     * Each line contains the city's name and coordinates
     * 
     * @return A formatted string representation of the BST
     */
    public String inorderFormatted() {
        StringBuilder sb = new StringBuilder();
        inorderRec(root, 0, sb);
        return sb.toString();
    }

    /**
     * Helper method for in-order traversal with formatting.
     * 
     * @param n The current node being visited
     * @param level The current depth in the tree
     * @param sb The string builder to append the output to
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
     * Creates an indentation string based on the node's level.
     * 
     * @param n The number of spaces to generate
     * @return A string of spaces for indentation
     */
    private String repeatSpaces(int n) {
        if (n <= 0) return "";
        char[] arr = new char[n];
        for (int i = 0; i < n; i++) arr[i] = ' ';
        return new String(arr);
    }
}
