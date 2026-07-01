// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Anh Truong (anht)
package prj5;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * test class for ComparatorReachEngRate
 * 
 * @author ANH
 * @version Apr 24, 2025
 */
public class ComparatorReachEngRateTest
    extends TestCase
{
    // ~ Fields ................................................................
    private ComparatorReachEngRate comparator;
    private Data data1;
    private Data data2;
    private Data data3;

    // ~ Set up ..........................................................
    /**
     * setUp method
     */
    public void setUp()
    {
        comparator = new ComparatorReachEngRate();
        // formula: ( (comments + likes)/ views) x 100
        data1 = new Data(
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

        data2 = new Data(
            "February",
            "user1",
            "Apple",
            "USA",
            "Music",
            500,
            10,
            1000,
            500,
            5000);

        data3 = new Data(
            "March",
            "user1",
            "Apple",
            "USA",
            "Music",
            50,
            10,
            1000,
            100,
            5000);

    }


    // ~Public Methods ........................................................
    /**
     * tests compare method
     */
    public void testCompare()
    {
        assertEquals(0, comparator.compare(data1, data3));
        assertEquals(-1, comparator.compare(data2, data1));
        assertEquals(1, comparator.compare(data1, data2));
    }

}
