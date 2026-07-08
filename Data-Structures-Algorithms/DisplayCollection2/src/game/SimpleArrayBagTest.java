package game;

import org.junit.Before;
import org.junit.Test;
import student.TestableRandom;
import static org.junit.Assert.*;

// -------------------------------------------------------------------------
/**
 * This is a test class for the SimpleArrayBag class this is designed to make
 * sure the methods in SimpleArrayBag are working as they are intended
 * 
 * @author kalendaco
 * @version Feb 20, 2024
 */
public class SimpleArrayBagTest
{

    // ----------------------------------------------------------
    /**
     * this is the setup it will run before each test method
     */
    @Before
    public void setUp()
    {
        // Setting up the predictable random numbers
        TestableRandom.setNextInts(0, 42, 7);
    }


    // ----------------------------------------------------------
    /**
     * tests to make sure the current size of the bag is the correct size
     */
    @Test
    public void testGetCurrentSize()
    {
        SimpleArrayBag<String> bag = new SimpleArrayBag<>();
        assertEquals(0, bag.getCurrentSize());

        bag.add("Item 1");
        assertEquals(1, bag.getCurrentSize());

        bag.add("Item 2");
        assertEquals(2, bag.getCurrentSize());
    }


    // ----------------------------------------------------------
    /**
     * checks to make sure the bag is empty when said so
     */
    @Test
    public void testIsEmpty()
    {
        SimpleArrayBag<String> bag = new SimpleArrayBag<>();
        assertTrue(bag.isEmpty());

        bag.add("Item 1");
        assertFalse(bag.isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * makes sure elements can be added to the bag
     */
    @Test
    public void testAdd()
    {
        SimpleArrayBag<String> bag = new SimpleArrayBag<>();
        assertTrue(bag.add("Item 1"));
        assertEquals(1, bag.getCurrentSize());

        assertFalse(bag.add(null));
        assertEquals(1, bag.getCurrentSize());
    }


    // ----------------------------------------------------------
    /**
     * makes sure a random element is returned from the bag and that the items
     * are picked in order
     */

    @Test
    public void testPick()
    {
        SimpleArrayBag<String> bag = new SimpleArrayBag<>();
        bag.add("Item 1");
        bag.add("Item 2");
        bag.add("Item 3");

        boolean item1Picked = false;
        boolean item2Picked = false;
        boolean item3Picked = false;

        while (!item1Picked || !item2Picked || !item3Picked)
        {
            String pickedItem = bag.pick();
            if (pickedItem.equals("Item 1"))
            {
                item1Picked = true;
            }
            else if (pickedItem.equals("Item 2"))
            {
                item2Picked = true;
            }
            else if (pickedItem.equals("Item 3"))
            {
                item3Picked = true;
            }
        }

        assertTrue(item1Picked && item2Picked && item3Picked);
    }


    // ----------------------------------------------------------
    /**
     * checks to make sure an item is removed from the bag and if the item
     * doesnt exist return false
     */
    @Test
    public void testRemove()
    {
        SimpleArrayBag<String> bag = new SimpleArrayBag<>();
        bag.add("Item 1");
        bag.add("Item 2");
        bag.add("Item 3");

        assertTrue(bag.remove("Item 2"));
        assertEquals(2, bag.getCurrentSize());

        assertFalse(bag.remove("Item 4"));
        assertEquals(2, bag.getCurrentSize());
    }


    // ----------------------------------------------------------
    /**
     * checks to make sure the getIndexOf() in SimpleArrayBag find the index of
     * an item in the bag
     */
    @Test
    public void testGetIndexOf()
    {
        SimpleArrayBag<String> bag = new SimpleArrayBag<>();
        bag.add("Item 1");
        bag.add("Item 2");
        bag.add("Item 3");

        int index = bag.getIndexOf("Item 2");
        assertEquals(1, index);

        index = bag.getIndexOf("Item 4");
        assertEquals(-1, index);
    }
}
