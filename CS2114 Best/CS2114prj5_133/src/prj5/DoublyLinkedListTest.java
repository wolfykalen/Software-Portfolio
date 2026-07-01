// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- KalenDaco _____

package prj5;

import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the DoublyLinkedList implementation.
 * 
 * @author kalendaco
 * @version Apr 22, 2025
 */
public class DoublyLinkedListTest
{
    private DoublyLinkedList<Integer> intList;
    private DoublyLinkedList<Data> dataList;

    /**
     * Sets up the test environment
     */
    @Before
    public void setUp()
    {
        intList = new DoublyLinkedList<>();
        dataList = new DoublyLinkedList<>();
    }


    /**
     * Tests the constructor of DoublyLinkedList.
     */
    @Test
    public void testConstructor()
    {
        assertNotNull(intList);
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
    }


    /**
     * Tests adding an element to an empty list.
     */
    @Test
    public void testAddEmptyList()
    {
        assertTrue(intList.add(5));
        assertEquals(1, intList.size());
        assertFalse(intList.isEmpty());
        assertEquals("5", intList.toString());
    }


    /**
     * Tests adding multiple elements to the list.
     */
    @Test
    public void testAddMultipleElements()
    {
        assertTrue(intList.add(5));
        assertTrue(intList.add(10));
        assertTrue(intList.add(15));
        assertEquals(3, intList.size());
        assertFalse(intList.isEmpty());
        assertEquals("5 -> 10 -> 15", intList.toString());
    }


    /**
     * Tests hasNext() method on an empty list.
     */
    @Test
    public void testHasNextEmptyList()
    {
        assertFalse(intList.hasNext());
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
    }


    /**
     * Tests hasNext() method on a list with a single element.
     */
    @Test
    public void testHasNextSingleElement()
    {
        intList.add(5);
        assertTrue(intList.hasNext());
        assertEquals(1, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests hasNext() method on a list with multiple elements.
     */
    @Test
    public void testHasNextMultipleElements()
    {
        intList.add(5);
        intList.add(10);
        assertTrue(intList.hasNext());
        assertEquals(2, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests clearing an empty list.
     */
    @Test
    public void testClearEmptyList()
    {
        intList.clear();
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
        assertEquals("", intList.toString());
    }


    /**
     * Tests clearing a list with a single element.
     */
    @Test
    public void testClearSingleElement()
    {
        intList.add(5);
        intList.clear();
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
        assertEquals("", intList.toString());
    }


    /**
     * Tests clearing a list with multiple elements.
     */
    @Test
    public void testClearMultipleElements()
    {
        intList.add(5);
        intList.add(10);
        intList.add(15);
        intList.clear();
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
        assertEquals("", intList.toString());
    }


    /**
     * Tests getting the size of an empty list.
     */
    @Test
    public void testSizeEmptyList()
    {
        assertEquals(0, intList.size());
        assertTrue(intList.isEmpty());
    }


    /**
     * Tests getting the size of a list with a single element.
     */
    @Test
    public void testSizeSingleElement()
    {
        intList.add(5);
        assertEquals(1, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests getting the size of a list with multiple elements.
     */
    @Test
    public void testSizeMultipleElements()
    {
        intList.add(5);
        intList.add(10);
        intList.add(15);
        assertEquals(3, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests checking if an empty list is empty.
     */
    @Test
    public void testIsEmptyEmptyList()
    {
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
    }


    /**
     * Tests checking if a list with one element is empty.
     */
    @Test
    public void testIsEmptySingleElement()
    {
        intList.add(5);
        assertFalse(intList.isEmpty());
        assertEquals(1, intList.size());
    }


    /**
     * Tests checking if a list with multiple elements is empty.
     */
    @Test
    public void testIsEmptyMultipleElements()
    {
        intList.add(5);
        intList.add(10);
        assertFalse(intList.isEmpty());
        assertEquals(2, intList.size());
    }


    /**
     * Tests removing from an empty list.
     */
    @Test
    public void testRemoveEmptyList()
    {
        assertNull(intList.remove());
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
    }


    /**
     * Tests removing a single element from the list.
     */
    @Test
    public void testRemoveSingleElement()
    {
        intList.add(5);
        assertEquals(Integer.valueOf(5), intList.remove());
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
        assertEquals("", intList.toString());
    }


    /**
     * Tests removing multiple elements from the list.
     */
    @Test
    public void testRemoveMultipleElements()
    {
        intList.add(5);
        intList.add(10);
        intList.add(15);
        assertEquals(Integer.valueOf(15), intList.remove());
        assertEquals(2, intList.size());
        assertEquals(Integer.valueOf(10), intList.remove());
        assertEquals(1, intList.size());
        assertEquals(Integer.valueOf(5), intList.remove());
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
        assertEquals("", intList.toString());
    }


    /**
     * Tests toString() method on an empty list.
     */
    @Test
    public void testToStringEmptyList()
    {
        assertEquals("", intList.toString());
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
    }


    /**
     * Tests toString() method on a list with a single element.
     */
    @Test
    public void testToStringSingleElement()
    {
        intList.add(5);
        assertEquals("5", intList.toString());
        assertEquals(1, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests toString() method on a list with multiple elements.
     */
    @Test
    public void testToStringMultipleElements()
    {
        intList.add(5);
        intList.add(10);
        intList.add(15);
        assertEquals("5 -> 10 -> 15", intList.toString());
        assertEquals(3, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests sorting an empty list.
     */
    @Test
    public void testSortEmptyList()
    {
        intList.sort((a, b) -> a - b);
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
        assertEquals("", intList.toString());
    }


    /**
     * Tests sorting a list with a single element.
     */
    @Test
    public void testSortSingleElement()
    {
        intList.add(5);
        intList.sort((a, b) -> a - b);
        assertEquals("5", intList.toString());
        assertEquals(1, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests sorting a list with multiple elements.
     */
    @Test
    public void testSortMultipleElements()
    {
        intList.add(5);
        intList.add(10);
        intList.add(3);
        intList.add(8);
        intList.sort((a, b) -> b - a);
        assertEquals("10 -> 8 -> 5 -> 3", intList.toString());
        assertEquals(4, intList.size());
        assertFalse(intList.isEmpty());
    }


    /**
     * Tests sorting an array of Data objects by reach engagement rate.
     */
    @Test
    public void testDataSortReachEngRate()
    {
        Data data1 = new Data(
            "Apr",
            "user1",
            "channel1",
            "US",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "Apr",
            "user2",
            "channel2",
            "US",
            "Music",
            200,
            10,
            1000,
            100,
            5000);
        Data data3 = new Data(
            "Apr",
            "user3",
            "channel3",
            "US",
            "Music",
            150,
            10,
            1000,
            75,
            5000);

        Data[] data = new Data[] { data1, data2, data3 };
        Data[] sorted = dataList.sortReachEngRate(data);

        assertTrue(sorted[0].getReachEngRate() >= sorted[1].getReachEngRate());
        assertTrue(sorted[1].getReachEngRate() >= sorted[2].getReachEngRate());
        assertEquals(3, sorted.length);
        assertNotNull(sorted[0]);
        assertNotNull(sorted[1]);
        assertNotNull(sorted[2]);
    }


    /**
     * Tests sorting an array of Data objects by traditional engagement rate.
     */
    @Test
    public void testDataSortTraditionalEngRate()
    {
        Data data1 = new Data(
            "Apr",
            "user1",
            "channel1",
            "US",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "Apr",
            "user2",
            "channel2",
            "US",
            "Music",
            200,
            10,
            1000,
            100,
            5000);
        Data data3 = new Data(
            "Apr",
            "user3",
            "channel3",
            "US",
            "Music",
            150,
            10,
            1000,
            75,
            5000);

        Data[] data = new Data[] { data1, data2, data3 };
        Data[] sorted = dataList.sortTraditionalEngRate(data);
        assertTrue(
            sorted[0].getTraditionalEngRate() >= sorted[1]
                .getTraditionalEngRate());
        assertTrue(
            sorted[1].getTraditionalEngRate() >= sorted[2]
                .getTraditionalEngRate());
        assertEquals(3, sorted.length);
        assertNotNull(sorted[0]);
        assertNotNull(sorted[1]);
        assertNotNull(sorted[2]);

        Data[] sorted2 = dataList.sortTraditionalEngRate(null);
        try
        {
            assertNull(
                sorted2[0].getTraditionalEngRate() >= sorted2[1]
                    .getTraditionalEngRate());
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }


    /**
     * Tests sorting an array of Data objects by channel name.
     */
    @Test
    public void testDataSortName()
    {
        Data data1 = new Data(
            "Apr",
            "user1",
            "Charlie",
            "US",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "Apr",
            "user2",
            "Alice",
            "US",
            "Music",
            200,
            10,
            1000,
            100,
            5000);
        Data data3 = new Data(
            "Apr",
            "user3",
            "Bob",
            "US",
            "Music",
            150,
            10,
            1000,
            75,
            5000);

        Data[] data = new Data[] { data1, data2, data3 };
        Data[] sorted = dataList.sortName(data);

        assertEquals("Alice", sorted[0].getChannelName());
        assertEquals("Bob", sorted[1].getChannelName());
        assertEquals("Charlie", sorted[2].getChannelName());
        assertEquals(3, sorted.length);
        assertNotNull(sorted[0]);
        assertNotNull(sorted[1]);
        assertNotNull(sorted[2]);
    }


    /**
     * Tests sorting with tail node swap .
     */
    @Test
    public void testSortWithTailSwap()
    {
        Data data1 = new Data(
            "Apr",
            "user1",
            "channel1",
            "US",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "Apr",
            "user2",
            "channel2",
            "US",
            "Music",
            200,
            10,
            1000,
            100,
            5000);
        Data data3 = new Data(
            "Apr",
            "user3",
            "channel3",
            "US",
            "Music",
            300,
            10,
            1000,
            150,
            5000);
        Data data4 = new Data(
            "Apr",
            "user4",
            "channel4",
            "US",
            "Music",
            250,
            10,
            1000,
            125,
            5000);

        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        dataList.add(data4);

        dataList.sort(new ComparatorReachEngRate());

        DLNode<Data> current = dataList.getHead();
        while (current.getNext() != null)
        {
            current = current.getNext();
        }
        assertEquals(data1, current.getData());

        current = dataList.getHead();
        while (current != null && current.getNext() != null)
        {
            assertTrue(
                current.getData().getReachEngRate() >= current.getNext()
                    .getData().getReachEngRate());
            current = current.getNext();
        }
    }


    /**
     * Tests sortName with null input.
     */
    @Test
    public void testSortNameNull()
    {
        Data[] result = dataList.sortName(null);
        assertNull(result);
    }


    /**
     * Tests sortName with empty array.
     */
    @Test
    public void testSortNameEmpty()
    {
        Data[] emptyArray = new Data[0];
        Data[] result = dataList.sortName(emptyArray);
        assertNotNull(result);
        assertEquals(0, result.length);
    }


    /**
     * Tests sortName with single element array.
     */
    @Test
    public void testSortNameSingleElement()
    {
        Data data = new Data(
            "January",
            "user1",
            "Test",
            "USA",
            "Entertainment",
            1,
            1,
            1,
            1,
            1);
        Data[] singleArray = new Data[] { data };
        Data[] result = dataList.sortName(singleArray);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(data, result[0]);
    }


    /**
     * Tests sortName with multiple elements array.
     */
    @Test
    public void testSortNameMultipleElements()
    {
        Data data1 = new Data(
            "January",
            "user1",
            "Apple",
            "USA",
            "Entertainment",
            1,
            1,
            1,
            1,
            1);
        Data data2 = new Data(
            "January",
            "user2",
            "Banana",
            "USA",
            "Entertainment",
            1,
            1,
            1,
            1,
            1);
        Data data3 = new Data(
            "January",
            "user3",
            "Cherry",
            "USA",
            "Entertainment",
            1,
            1,
            1,
            1,
            1);

        Data[] inputArray = new Data[] { data3, data1, data2 };
        Data[] result = dataList.sortName(inputArray);

        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(data1, result[0]);
        assertEquals(data2, result[1]);
        assertEquals(data3, result[2]);
    }


    /**
     * Tests the iterator on an empty list.
     */
    @Test
    public void testIteratorEmptyList()
    {
        Iterator<Integer> it = intList.iterator();
        assertFalse(it.hasNext());
        try
        {
            it.next();
            fail("Should have thrown NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }


    /**
     * Tests the iterator on a list with multiple elements.
     */
    @Test
    public void testIteratorMultipleElements()
    {
        intList.add(1);
        intList.add(2);
        intList.add(3);

        Iterator<Integer> it = intList.iterator();
        assertTrue(it.hasNext());
        assertEquals(Integer.valueOf(1), it.next());
        assertTrue(it.hasNext());
        assertEquals(Integer.valueOf(2), it.next());
        assertTrue(it.hasNext());
        assertEquals(Integer.valueOf(3), it.next());
        assertFalse(it.hasNext());

        try
        {
            it.next();
            fail("Should have thrown NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }


    /**
     * Tests the iterator on a list with a single element.
     */
    @Test
    public void testIteratorSingleElement()
    {
        intList.add(42);

        Iterator<Integer> it = intList.iterator();
        assertTrue(it.hasNext());
        assertEquals(Integer.valueOf(42), it.next());
        assertFalse(it.hasNext());

        try
        {
            it.next();
            fail("Should have thrown NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }

    /**
     * Tests getTail() method for empty and non-empty lists.
     */
    @Test
    public void testGetTail() 
    {
        assertNull(intList.getTail());
        intList.add(10);
        intList.add(20);
        assertNotNull(intList.getTail());
        assertEquals(Integer.valueOf(20), intList.getTail().getData());
    }

    /**
     * Tests sortReachEngRate edge cases
     */
    @Test
    public void testSortReachEngRateEdgeCases() 
    {
        // null input
        assertNull(dataList.sortReachEngRate(null));
        // empty array
        Data[] empty = new Data[0];
        assertSame(empty, dataList.sortReachEngRate(empty));
        // single element
        Data d = new Data("January", "user1", "Test", "USA", 
            "Entertainment", 1, 1, 1, 1, 1);
        Data[] single = new Data[] { d };
        assertSame(single, dataList.sortReachEngRate(single));
    }

    /**
     * Tests sortTraditionalEngRate edge cases 
     */
    @Test
    public void testSortTraditionalEngRateEdgeCases() 
    {
        // null input
        assertNull(dataList.sortTraditionalEngRate(null));
        // empty array
        Data[] empty = new Data[0];
        assertSame(empty, dataList.sortTraditionalEngRate(empty));
        // single element
        Data d = new Data("January", "user1", "Test", "USA", 
            "Entertainment", 1, 1, 1, 1, 1);
        Data[] single = new Data[] { d };
        assertSame(single, dataList.sortTraditionalEngRate(single));
    }
}

