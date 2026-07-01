// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Andrew Nguyen 9066689185

package prj5;

import static org.junit.Assert.*;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * tests all methods in DataCalculator class
 * 
 * @author anguyen17
 * @version Apr 21, 2025
 */
public class DataCalculatorTest
    extends TestCase
{
// ~ Fields ................................................................

    private DataCalculator calculator;
    private Data[] testData;
    private Data normalData;

    /**
     * setUp method
     */
    public void setUp()
    {
        calculator = new DataCalculator();

        normalData = new Data(
            "January",
            "user1",
            "MrBeast",
            "USA",
            "Entertainment",
            1000,
            10,
            5000,
            500,
            10000);

        testData = new Data[] { normalData,
            new Data(
                "February",
                "user2",
                "NFL",
                "USA",
                "Sports",
                500,
                5,
                2000,
                200,
                0),
            new Data(
                "March",
                "user3",
                "GothamChess",
                "USA",
                "Chess",
                300,
                3,
                0,
                100,
                5000),
            new Data(
                "January",
                "user4",
                "National Geographic",
                "USA",
                "Documentary",
                200,
                2,
                1000,
                50,
                2000) };
    }


    /**
     * tests getUsersInMonth method if month has no data
     */
    public void testGetUsersInMonthNoData()
    {
        DoublyLinkedList<Data> result =
            calculator.getUsersInMonth("April", testData);
        assertEquals(0, result.size());
    }


    /**
     * tests getUsersInMonth method with case sensitivity
     */
    public void testGetUsersInMonthCaseSensitive()
    {
        DoublyLinkedList<Data> result =
            calculator.getUsersInMonth("january", testData);
        assertEquals(0, result.size());
    }


    /**
     * tests calculateReachEngRate method normal data
     */
    public void testCalculateReachEngRateNormal()
    {
        double result = calculator.calculateReachEngRate(normalData);
        assertEquals(15.0, result, 0.001);
    }


    /**
     * tests calculateReachEngRate method zero views
     */
    public void testCalculateReachEngRateZeroViews()
    {
        Data zeroViewsData =
            new Data("February", "user2", "", "", "", 500, 5, 2000, 200, 0);
        double result = calculator.calculateReachEngRate(zeroViewsData);
        assertEquals(Double.NEGATIVE_INFINITY, result, 0.0);
    }


    /**
     * tests calculateTraditionalEngRate method normal data
     */
    public void testCalculateTraditionalEngRateNormal()
    {
        double result = calculator.calculateTraditionalEngRate(normalData);
        assertEquals(30.0, result, 0.001);
    }


    /**
     * tests calculateTraditionalEngRate method zero followers
     */
    public void testCalculateTraditionalEngRateZeroFollowers()
    {
        Data zeroFollowersData =
            new Data("March", "user3", "", "", "", 300, 3, 0, 100, 5000);
        double result =
            calculator.calculateTraditionalEngRate(zeroFollowersData);
        assertEquals(Double.NEGATIVE_INFINITY, result, 0.0);
    }


    /**
     * tests getUsersInMonth filter
     */
    public void testMonthFilter()
    {
        Data janData = new Data("January", "user1", "", "", "", 0, 0, 0, 0, 0);
        Data febData = new Data("February", "user2", "", "", "", 0, 0, 0, 0, 0);
        Data[] dataArray = new Data[] { janData, febData };
        DoublyLinkedList<Data> result =
            calculator.getUsersInMonth("January", dataArray);
        assertEquals(1, result.size());
        assertTrue(result.toString().contains("user1"));
        result = calculator.getUsersInMonth("February", dataArray);
        assertEquals(1, result.size());
        assertTrue(result.toString().contains("user2"));
        result = calculator.getUsersInMonth("March", dataArray);
        assertEquals(0, result.size());
    }
}
