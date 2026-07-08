package game;

import bag.Node;
import java.util.Random;

// -------------------------------------------------------------------------
/**
 * Shows the implementation of bag data structure using a linked list, functions
 * allowing the addition removing and picking random elements from the bag were
 * added
 * 
 * @author kalendaco
 * @version Feb 20, 2024
 * @param <T>
 */
public class SimpleLinkedBag<T>
{
    private Node<T> firstNode;
    private int numberOfEntries;

    // ----------------------------------------------------------
    /**
     * Initializes the linked bag and sets the first node to null and # of
     * entries to 0
     */
    public SimpleLinkedBag()
    {
        firstNode = null;
        numberOfEntries = 0;
    }


    // ----------------------------------------------------------
    /**
     * returns the current number of entries the the bag
     * 
     * @return # of entries in the bag
     */
    public int getCurrentSize()
    {
        return numberOfEntries;
    }


    // ----------------------------------------------------------
    /**
     * Checks if the bag is empty
     * 
     * @return # of entries in the bag
     */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }


    // ----------------------------------------------------------
    /**
     * adds an entry to the bag if not null
     * 
     * @param anEntry
     *            the entry added to the bag
     * @return true if the entry was added
     */
    public boolean add(T anEntry)
    {
        if (anEntry == null)
        {
            return false;
        }

        Node<T> newNode = new Node<>(anEntry);
        newNode.setNext(firstNode);
        firstNode = newNode;
        numberOfEntries++;
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Returns a random element from the bag
     * 
     * @return random element
     */
    public T pick() {
        if (isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(numberOfEntries); 

        Node<T> currentNode = firstNode;
        for (int i = 0; i < index; i++) {
            if (currentNode.getNext() == null) {
                
                currentNode = firstNode;
                break;
            }
            currentNode = currentNode.getNext();
        }
        return currentNode.getData();
    }



    // A helper method that gets the reference for a specific entry
    private Node<T> getReferenceTo(T anEntry)
    {
        boolean found = false;
        Node<T> currentNode = firstNode;

        while (!found && currentNode != null)
        {
            if (anEntry.equals(currentNode.getData()))
            {
                found = true;
            }
            else
            {
                currentNode = currentNode.getNext();
            }
        }
        return currentNode;
    }


    // ----------------------------------------------------------
    /**
     * Removes a specific entry from the bag
     * 
     * @param anEntry
     *            a shape from the bag
     * @return true if node is removed false if null
     */
    public boolean remove(T anEntry)
    {
        Node<T> targetNode = getReferenceTo(anEntry);
        if (targetNode == null)
        {
            return false;
        }

        targetNode.setData(firstNode.getData());
        firstNode = firstNode.getNext();
        numberOfEntries--;
        return true;
    }
}
