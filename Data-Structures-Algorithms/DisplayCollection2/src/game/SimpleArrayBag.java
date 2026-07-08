package game;

import bag.SimpleBagInterface;
import java.util.Random;

// -------------------------------------------------------------------------
/**
 * Shows implementation of bag data structure using an array, this allows the
 * addition, removal and random picking of elements in the bag to be possible,
 * the bag also has a max capacity
 * 
 * @author kalendaco
 * @version Feb 20, 2024
 * @param <T>
 */
public class SimpleArrayBag<T>
    implements SimpleBagInterface<T>
{
    private static final int MAX = 18;
    private T[] bag;
    private int numberOfEntries;

    // ----------------------------------------------------------
    /**
     * Initializes a bag
     */
    public SimpleArrayBag()
    {
        @SuppressWarnings("unchecked")
        T[] tempbag = (T[])new Object[MAX];
        bag = tempbag;
        numberOfEntries = 0;
    }


    // getter method returns the number of entries in the bag
    @Override
    public int getCurrentSize()
    {
        return numberOfEntries;
    }


    // checks if the bag is empty
    @Override
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }


    // adds an entry to the bag if it has space and isnt null
    @Override
    public boolean add(T anEntry)
    {
        if (numberOfEntries >= MAX || anEntry == null)
        {
            return false;
        }
        bag[numberOfEntries] = anEntry;
        numberOfEntries++;
        return true;
    }


    // returns a random element from the bag
    @Override
    public T pick()
    {
        if (isEmpty())
        {
            return null;
        }
        Random generator = new Random();
        int index = generator.nextInt(numberOfEntries);
        // Return the entry at the randomly generated index
        return bag[index];
    }


    // ----------------------------------------------------------
    /**
     * method to get the index of an entry in the bag
     * 
     * @param anEntry
     *            a shape from the bag
     * @return index of an entry
     */
    int getIndexOf(T anEntry)
    {
        for (int i = 0; i < numberOfEntries; i++)
        {
            if (bag[i].equals(anEntry))
            {
                return i;
            }
        }
        return -1;
    }


    // removes a specific entry from the bag
    @Override
    public boolean remove(T anEntry)
    {
        int index = getIndexOf(anEntry);
        if (index == -1)
        {
            return false;
        }
        bag[index] = bag[numberOfEntries - 1];
        bag[numberOfEntries - 1] = null;
        numberOfEntries--;
        return true;
    }

}
