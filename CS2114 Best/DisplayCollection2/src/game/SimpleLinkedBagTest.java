package game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 * This will test all the methods in SimpleLinkedBag to assure that the methods
 * are working as intended
 * 
 * @author kalendaco
 * @version Feb 20, 2024
 */
public class SimpleLinkedBagTest
{

    private SimpleLinkedBag<Integer> bag;

    // ----------------------------------------------------------
    /**
     * set up for the tests all other methods will refer here before running
     */
    @Before
    public void setUp()
    {
        bag = new SimpleLinkedBag<>();
    }


    // ----------------------------------------------------------
    /**
     * Checks to see if the correct size of bag is returned after adding
     * elements
     */
    @Test
    public void testGetCurrentSize()
    {
        assertEquals(0, bag.getCurrentSize());
        bag.add(1);
        assertEquals(1, bag.getCurrentSize());
        bag.add(2);
        assertEquals(2, bag.getCurrentSize());
    }


    // ----------------------------------------------------------
    /**
     * checks if the bag is actually empty
     */
    @Test
    public void testIsEmpty()
    {
        assertTrue(bag.isEmpty());
        bag.add(1);
        assertFalse(bag.isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * checks if the addition of elements is working
     */
    @Test
    public void testAdd()
    {
        assertTrue(bag.add(1));
        assertTrue(bag.add(2));
        assertEquals(2, bag.getCurrentSize());
    }


    // ----------------------------------------------------------
    /**
     * checks the behavior of pick when the bag is full or empty
     */
    @Test
    public void testPick()
    {
        assertNull(bag.pick());

        bag.add(1);
        bag.add(2);
        bag.add(3);

        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;

        for (int i = 0; i < 1000; i++)
        {
            Integer picked = bag.pick();
            if (picked != null)
            {
                if (picked == 1)
                {
                    found1 = true;
                }
                else if (picked == 2)
                {
                    found2 = true;
                }
                else if (picked == 3)
                {
                    found3 = true;
                }
            }
        }

        assertTrue(found1);
        assertTrue(found2);
        assertTrue(found3);
    }


    // ----------------------------------------------------------
    /**
     * checks if elements are being removed when they need to
     */
    @Test
    public void testRemove()
    {
        assertFalse(bag.remove(1));
        bag.add(1);
        assertTrue(bag.remove(1));
        assertTrue(bag.isEmpty());
    }
}
