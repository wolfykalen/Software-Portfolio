// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who 
//  do.
// -- Your name kalendaco))
package dailymixes;

import static org.junit.Assert.*;
import org.junit.Test;
import queue.EmptyQueueException;

// -------------------------------------------------------------------------
/**
 * A test class for the ArrayQueue 
 * tests for operations in the ArrayQueue class
 * 
 * @author kalendaco
 * @version Apr 3, 2025
 */
public class ArrayQueueTest 
{
    private static final int DEFAULT_CAPACITY = 20;

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws an IllegalArgumentException when
     * a negative capacity value is given.
     */
    @Test
    public void testConstructorNegativeCapacity() 
    {
        try 
        {
            new ArrayQueue<>(-1);
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            // Expected exception
        }
    }

    // ----------------------------------------------------------
    /**
     * verifies that the constructor creates
     * a queue with the default capacity and initializes it as empty.
     */
    @Test
    public void testConstructorDefault() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        assertEquals(DEFAULT_CAPACITY + 1, queue.getLengthOfUnderlyingArray());
        assertEquals(0, queue.getSize());
        assertTrue(queue.isEmpty());
    }

    // ----------------------------------------------------------
    /**
     * Tests constructor verifies that it creates
     * a queue with the specified capacity and initializes it as empty.
     */
    @Test
    public void testConstructorCustomCapacity() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>(10);
        assertEquals(11, queue.getLengthOfUnderlyingArray());
        assertEquals(0, queue.getSize());
        assertTrue(queue.isEmpty());
    }

    // ----------------------------------------------------------
    /**
     * Tests enqueue and dequeue verifies that elements
     * are added and removed in the correct order and that size tracking
     * works correctly.
     */
    @Test
    public void testEnqueueDequeue() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>(3);
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals(2, queue.getSize());
        assertFalse(queue.isEmpty());
        assertFalse(queue.isFull());

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());

        assertEquals(0, queue.getSize());
        assertTrue(queue.isEmpty());
    }

    // ----------------------------------------------------------
    /**
     * Tests the circular behavior of the queue verifies that elements
     * wrap around correctly when the queue reaches capacity.
     */
    @Test
    public void testCircularBehavior() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>(3);
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        queue.enqueue("D");

        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());
    }

    // ----------------------------------------------------------
    /**
     * Tests the capacity expansion verifies that the
     * queue grows when it becomes full and that elements stay in 
     * order after expansion.
     */
    @Test
    public void testEnsureCapacity() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>(3);
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertTrue(queue.isFull());
        queue.enqueue("D"); 

        assertEquals(4, queue.getSize());
        assertEquals(9, queue.getLengthOfUnderlyingArray());

        String[] expected = {"A", "B", "C", "D"};
        assertArrayEquals(expected, queue.toArray());
    }

    // ----------------------------------------------------------
    /**
     * Tests clear verifies that it empties the
     * queue while maintaining the underlying array capacity.
     */
    @Test
    public void testClear() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>(3);
        queue.enqueue("A");
        queue.enqueue("B");

        queue.clear();

        assertEquals(0, queue.getSize());
        assertEquals(DEFAULT_CAPACITY + 1, queue.getLengthOfUnderlyingArray());
        assertTrue(queue.isEmpty());
    }

    // ----------------------------------------------------------
    /**
     * Tests the toString method verifies that it correctly represents
     * the queue's contents
     */
    @Test
    public void testToString() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>(3);
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("[A, B]", queue.toString());

        queue.clear();
        assertEquals("[]", queue.toString());
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method verifies that it correctly compares
     * queues based on their contents and order.
     */
    @Test
    public void testEquals() 
    {
        ArrayQueue<String> queue1 = new ArrayQueue<>(3);
        queue1.enqueue("A");
        queue1.enqueue("B");

        ArrayQueue<String> queue2 = new ArrayQueue<>(3);
        queue2.enqueue("A");
        queue2.enqueue("B");

        assertTrue(queue1.equals(queue2));

        ArrayQueue<String> queue3 = new ArrayQueue<>(3);
        queue3.enqueue("B");
        queue3.enqueue("A");

        assertFalse(queue1.equals(queue3));
    }

    // ----------------------------------------------------------
    /**
     * Tests that dequeue throws EmptyQueueException when called on an
     * empty queue.
     */
    @Test
    public void testDequeueEmpty() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        try 
        {
            queue.dequeue();
            fail("Expected EmptyQueueException to be thrown");
        } 
        catch (EmptyQueueException e) 
        {
            assertNotNull(e);
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that getFront throws EmptyQueueException when called on an
     * empty queue.
     */
    @Test
    public void testGetFrontEmpty() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        try 
        {
            queue.getFront();
            fail("Expected EmptyQueueException to be thrown");
        } 
        catch (EmptyQueueException e) 
        {
            assertNotNull(e);
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that toArray throws EmptyQueueException when called on an
     * empty queue.
     */
    @Test
    public void testToArrayEmpty() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        try 
        {
            queue.toArray();
            fail("Expected EmptyQueueException to be thrown");
        } 
        catch (EmptyQueueException e) 
        {
            assertNotNull(e);
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that enqueue throws IllegalArgumentException when attempting
     * to add a null element to the queue.
     */
    @Test
    public void testEnqueueNull() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        try 
        {
            queue.enqueue(null);
            fail("Expected IllegalArgumentException to be thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            assertNotNull(e);
            assertEquals("Cannot enqueue null element", e.getMessage());
        }
    }
    
    // ----------------------------------------------------------
    /**
     * Tests that equals returns true when comparing the queue to itself.
     */
    @Test
    public void testEqualsItself() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        assertTrue(queue.equals(queue)); 
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false when comparing the queue to null.
     */
    @Test
    public void testEqualsNull() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        assertFalse(queue.equals(null)); // should be false
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false when comparing the queue to a 
     * different class.
     */
    @Test
    public void testEqualsDifferentClass() 
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        String notAQueue = "Not a queue";
        assertFalse(queue.equals(notAQueue)); 

    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false when queues have different sizes.
     */
    @Test
    public void testEqualsDifferentSizes() 
    {
        ArrayQueue<String> queue1 = new ArrayQueue<>();
        queue1.enqueue("A");

        ArrayQueue<String> queue2 = new ArrayQueue<>();
        assertFalse(queue1.equals(queue2)); 
    }
}
