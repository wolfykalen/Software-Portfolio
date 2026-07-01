// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Andrew Nguyen 9066689185
package prj5;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * tests all methods in ComparatorName class
 * 
 * @author anguyen17
 * @version Apr 24, 2025
 */
public class ComparatorNameTest
    extends TestCase
{
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    /**
     * tests compare method with channel names different first characters
     */
    public void testCompareFirstCharDifferent()
    {
        ComparatorName comparator = new ComparatorName();
        Data data1 = new Data(
            "January",
            "user1",
            "Apple",
            "USA",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "January",
            "user2",
            "Banana",
            "USA",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        assertEquals(-1, comparator.compare(data1, data2));
        assertEquals(1, comparator.compare(data2, data1));
    }


    /**
     * tests compare method channel names that have common prefix but different
     * lengths
     */
    public void testCompareDiffLengths()
    {
        ComparatorName comparator = new ComparatorName();
        Data data1 = new Data(
            "January",
            "user1",
            "Mr",
            "USA",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "January",
            "user2",
            "MrBeast",
            "USA",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        assertEquals(-1, comparator.compare(data1, data2));
        assertEquals(1, comparator.compare(data2, data1));
    }


    /**
     * tests compare method identical channel names
     */
    public void testCompareSame()
    {
        ComparatorName comparator = new ComparatorName();
        Data data1 = new Data(
            "January",
            "user1",
            "NFL",
            "USA",
            "Music",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "February",
            "user2",
            "NFL",
            "Canada",
            "Entertainment",
            200,
            20,
            2000,
            100,
            10000);
        assertEquals(0, comparator.compare(data1, data2));
        assertEquals(0, comparator.compare(data2, data1));
    }


    /**
     * tests compare method different characters in middle
     */
    public void testCompareMiddleCharDifferent()
    {
        ComparatorName comparator = new ComparatorName();
        Data data1 = new Data(
            "January",
            "user1",
            "Gaming",
            "USA",
            "Tech",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "January",
            "user2",
            "Gating",
            "USA",
            "Tech",
            100,
            10,
            1000,
            50,
            5000);
        assertEquals(-1, comparator.compare(data1, data2));
        assertEquals(1, comparator.compare(data2, data1));
    }


    /**
     * tests equals(Object) method identical objects
     */
    public void testEqualsIdenticalObjects()
    {
        ComparatorName comparator = new ComparatorName();
        assertTrue(comparator.equals(comparator));
    }


    /**
     * tests equals(Object) method with null
     */
    public void testEqualsWithNull()
    {
        ComparatorName comparator = new ComparatorName();
        assertFalse(comparator.equals(null));
    }


    /**
     * tests equals(Object) method different class
     */
    public void testEqualsWithDifferentClass()
    {
        ComparatorName comparator = new ComparatorName();
        assertFalse(comparator.equals("String"));
    }


    /**
     * tests equals(Object) method different ComparatorName instances
     */
    public void testEqualsWithDifferentInstances()
    {
        ComparatorName comparator1 = new ComparatorName();
        ComparatorName comparator2 = new ComparatorName();
        assertTrue(comparator1.equals(comparator2));
    }


    /**
     * tests equalTo(Data) method with null
     */
    public void testEqualToWithNull()
    {
        ComparatorName comparator = new ComparatorName();
        assertFalse(comparator.equalTo(null));
    }


    /**
     * tests equalTo(Data) method valid Data objects
     */
    public void testEqualToWithData()
    {
        ComparatorName comparator = new ComparatorName();
        Data data1 = new Data(
            "January",
            "user1",
            "Apple",
            "USA",
            "Tech",
            100,
            10,
            1000,
            50,
            5000);
        Data data2 = new Data(
            "February",
            "user2",
            "Apple",
            "Canada",
            "Entertainment",
            200,
            20,
            2000,
            100,
            10000);
        Data data3 = new Data(
            "March",
            "user3",
            "Banana",
            "Mexico",
            "Food",
            300,
            30,
            3000,
            150,
            15000);
        assertTrue(comparator.equalTo(data1));
        assertTrue(comparator.equalTo(data2));
        assertTrue(comparator.equalTo(data3));
    }
}
