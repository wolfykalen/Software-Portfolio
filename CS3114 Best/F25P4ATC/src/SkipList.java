import java.util.Random;

/**
 * A generic SkipList implementation.
 * Stores elements in sorted order based on their natural ordering.
 *
 * @param <K> The key type 
 * @param <V> The value type
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class SkipList<K extends Comparable<K>, V> {

    /**
     * Represents a node in the SkipList.
     * Each node has a key, value, and an array of forward pointers.
     */
    private class SkipNode {
        /** The key for this node. */
        private K key;

        /** The value stored in this node. */
        private V value;

        /** Array of forward pointers to next nodes at each level. */
        private Object[] forward;

        /** The level/depth of this node. */
        private int level;

        /**
         * Creates a new SkipNode with the given key, value, and level.
         *
         * @param key   The key for ordering
         * @param value The value to store
         * @param level The level of this node (number of forward pointers)
         */
        public SkipNode(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.level = level;
            this.forward = new Object[level + 1];
        }

        /**
         * Gets the forward pointer at the given level.
         *
         * @param lvl The level
         * @return The node at that level
         */
        @SuppressWarnings("unchecked")
        public SkipNode getForward(int lvl) {
            return (SkipNode) forward[lvl];
        }

        /**
         * Sets the forward pointer at the given level.
         *
         * @param lvl  The level
         * @param node The node to point to
         */
        public void setForward(int lvl, SkipNode node) {
            forward[lvl] = node;
        }

        /**
         * Gets the key of this node.
         *
         * @return The key
         */
        public K getKey() {
            return key;
        }

        /**
         * Gets the value of this node.
         *
         * @return The value
         */
        public V getValue() {
            return value;
        }

        /**
         * Gets the level/depth of this node.
         *
         * @return The level
         */
        public int getLevel() {
            return level;
        }
    }

    /** The maximum level for the skip list. */
    private static final int MAX_LEVEL = 24;

    /** The head node of the skip list. */
    private SkipNode head;

    /** The current maximum level of any node in the list. */
    private int level;

    /** The number of elements in the skip list. */
    private int size;

    /** Random number generator for determining node levels. */
    private Random rnd;

    /**
     * Creates a new empty SkipList.
     *
     * @param rnd The random number generator to use for level generation
     */
    public SkipList(Random rnd) {
        this.rnd = rnd;
        this.level = 0;
        this.size = 0;
        this.head = new SkipNode(null, null, MAX_LEVEL);
    }

    /**
     * Generates a random level for a new node.
     * Uses geometric distribution 
     *
     * @return The randomly generated level
     */
    private int randomLevel() {
        int lev = 0;
        while (lev < MAX_LEVEL && (rnd.nextInt() & 1) == 0) {
            lev++;
        }
        return lev;
    }

    /**
     * Inserts a key value pair into the skip list.
     * Does not allow duplicate keys.
     *
     * @param key   The key to insert
     * @param value The value to associate with the key
     * @return true if inserted successfully, false if key already exists
     */
    @SuppressWarnings("unchecked")
    public boolean insert(K key, V value) {
        // Array to track the path of nodes we traverse
        Object[] update = new Object[MAX_LEVEL + 1];
        SkipNode current = head;

        for (int i = level; i >= 0; i--) {
            while (current.getForward(i) != null &&
                   current.getForward(i).key.compareTo(key) < 0) {
                current = current.getForward(i);
            }
            update[i] = current;
        }

        current = current.getForward(0);

        // Check if key already exists
        if (current != null && current.key.compareTo(key) == 0) {
            return false; 
        }

        int newLevel = randomLevel();

        if (newLevel > level) {
            for (int i = level + 1; i <= newLevel; i++) {
                update[i] = head;
            }
            level = newLevel;
        }

        // Create and insert the new node
        SkipNode newNode = new SkipNode(key, value, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            SkipNode updateNode = (SkipNode) update[i];
            newNode.setForward(i, updateNode.getForward(i));
            updateNode.setForward(i, newNode);
        }

        size++;
        return true;
    }

    /**
     * Searches for a value by key.
     *
     * @param key The key to search for
     * @return The value associated with the key, or null if not found
     */
    public V find(K key) {
        SkipNode current = head;

        // Traverse from top level down
        for (int i = level; i >= 0; i--) {
            while (current.getForward(i) != null &&
                   current.getForward(i).key.compareTo(key) < 0) {
                current = current.getForward(i);
            }
        }

        current = current.getForward(0);

        // Check if we found the key
        if (current != null && current.key.compareTo(key) == 0) {
            return current.value;
        }
        return null;
    }

    /**
     * Removes a key value pair from the skip list.
     *
     * @param key The key to remove
     * @return The value that was removed, or null if key not found
     */
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        Object[] update = new Object[MAX_LEVEL + 1];
        SkipNode current = head;

        // Find the node to remove
        for (int i = level; i >= 0; i--) {
            while (current.getForward(i) != null &&
                   current.getForward(i).key.compareTo(key) < 0) {
                current = current.getForward(i);
            }
            update[i] = current;
        }

        current = current.getForward(0);

        // Check if we found the key
        if (current != null && current.key.compareTo(key) == 0) {
            V removedValue = current.value;

            // Remove node from all levels
            for (int i = 0; i <= level; i++) {
                SkipNode updateNode = (SkipNode) update[i];
                if (updateNode.getForward(i) != current) {
                    break;
                }
                updateNode.setForward(i, current.getForward(i));
            }

            while (level > 0 && head.getForward(level) == null) {
                level--;
            }

            size--;
            return removedValue;
        }
        return null;
    }

    /**
     * Performs a range search, finding all values with keys in [min, max].
     *
     * @param min The minimum key 
     * @param max The maximum key 
     * @return Array of values in the range, or null if min > max
     */
    @SuppressWarnings("unchecked")
    public V[] rangeSearch(K min, K max) {
        if (min.compareTo(max) > 0) {
            return null;
        }

        int count = 0;
        SkipNode current = head;

        // Find the first node >= min
        for (int i = level; i >= 0; i--) {
            while (current.getForward(i) != null &&
                   current.getForward(i).key.compareTo(min) < 0) {
                current = current.getForward(i);
            }
        }
        current = current.getForward(0);

        // Count elements in range
        SkipNode counter = current;
        while (counter != null && counter.key.compareTo(max) <= 0) {
            count++;
            counter = counter.getForward(0);
        }

        Object[] result = new Object[count];
        int index = 0;

        // Fill result array
        while (current != null && current.key.compareTo(max) <= 0) {
            result[index++] = current.value;
            current = current.getForward(0);
        }

        return (V[]) result;
    }

    /**
     * Returns the number of elements in the skip list.
     *
     * @return The size
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the skip list is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all elements from the skip list.
     */
    public void clear() {
        this.level = 0;
        this.size = 0;
        this.head = new SkipNode(null, null, MAX_LEVEL);
    }

    /**
     * Returns a string representation of the skip list.
     *
     * @return The string representation
     */
    public String dump() {
        if (size == 0) {
            return "SkipList is empty";
        }

        StringBuilder sb = new StringBuilder();
        SkipNode current = head;
        int nodeCount = 0;

        sb.append("Node has depth ");
        sb.append(level + 1);
        sb.append(", Value (null)\n");

        // Print all other nodes
        current = current.getForward(0);
        while (current != null) {
            sb.append("Node has depth ");
            sb.append(current.level + 1);
            sb.append(", Value (");
            sb.append(current.value.toString());
            sb.append(")\n");
            nodeCount++;
            current = current.getForward(0);
        }

        sb.append(nodeCount);
        sb.append(" skiplist nodes printed\n");

        return sb.toString();
    }
}
