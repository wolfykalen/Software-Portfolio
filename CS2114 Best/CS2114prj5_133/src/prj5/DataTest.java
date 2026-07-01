// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Mason Ramboyong 906665787

package prj5;

import java.io.*;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Data method to ensure everything is running correctly
 * 
 * @author masonrambo
 * @version Apr 20, 2025
 */
public class DataTest
    extends TestCase
{
    // ~ Fields ................................................................
    private Data dataset;

    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Create a new Data object.
     */
    public void setUp()
    {
        dataset = new Data(
            "January",
            "aafootball",
            "allaboutfootball",
            "ES",
            "Sports",
            22876452,
            333,
            4650272,
            518,
            170095);
    }


    // ----------------------------------------------------------
    /**
     * tests the getUsername method
     */
    public void testGetUsername()
    {
        assertEquals("aafootball", dataset.getUsername());
    }


    // ----------------------------------------------------------
    /**
     * test the getChannelName method
     */
    public void testGetChannelName()
    {
        assertEquals("allaboutfootball", dataset.getChannelName());
    }


    // ----------------------------------------------------------
    /**
     * tests the getCountry method
     */
    public void testGetCountry()
    {
        assertEquals("ES", dataset.getCountry());
    }


    // ----------------------------------------------------------
    /**
     * tests the getMonth method
     */
    public void testGetMonth()
    {
        assertEquals("January", dataset.getMonth());
    }


    // ----------------------------------------------------------
    /**
     * tests the getComments method
     */
    public void testGetComments()
    {
        assertEquals(518, dataset.getComments());
    }


    // ----------------------------------------------------------
    /**
     * tests the getLikes method
     */
    public void testGetLikes()
    {
        assertEquals(22876452, dataset.getLikes());
    }


    // ----------------------------------------------------------
    /**
     * tests the getViews method
     */
    public void testGetViews()
    {
        assertEquals(170095, dataset.getViews());
    }


    // ----------------------------------------------------------
    /**
     * tests the getFollowers method
     */
    public void testGetFollowers()
    {
        assertEquals(4650272, dataset.getFollowers());
    }


    // ----------------------------------------------------------
    /**
     * tests the getTraditionalEngRate method
     */
    public void testGetTraditionalEngRate()
    {
        assertEquals(491.95, dataset.getTraditionalEngRate(), 0.01);
        dataset.setFollowers(0);
        assertEquals(
            Double.NEGATIVE_INFINITY,
            dataset.getTraditionalEngRate(),
            0.01);
    }


    // ----------------------------------------------------------
    /**
     * tests the getReachEngRate method
     */
    public void testGetReachEngRate()
    {
        assertEquals(13449.53, dataset.getReachEngRate(), 0.01);
    }


    // ----------------------------------------------------------
    /**
     * tests the toString meethod
     */
    public void testToString()
    {
        assertEquals(
            "Month:January Username:aafootball Channel Name:allaboutfootball"
                + " Country:ES Main Topic: Sports Likes:22876452 Posts:333 "
                + "Followers:4650272 Comments:518 Views:170095",
            dataset.toString());
    }


    // ----------------------------------------------------------
    /**
     * tests the main method
     * 
     * @throws IOException
     */
    public void testMain() throws IOException 
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try 
        {
            // Test SampleInput1_2023.csv
            ProjectRunner.main(new String[] { "SampleInput1_2023.csv" });
            String actualOutput1 = outContent.toString()
                .replace("\r\n", "\n")
                .trim();
            String expectedOutput1 = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("SampleOutput1_2023.txt")
                )
            ).replace("\r\n", "\n").trim();
            assertEquals(expectedOutput1, actualOutput1);
            outContent.reset();

            // Test SampleInput2_2023.csv
            ProjectRunner.main(new String[] { "SampleInput2_2023.csv" });
            String actualOutput2 = outContent.toString()
                .replace("\r\n", "\n")
                .trim();
            String expectedOutput2 = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("SampleOutput2_2023.txt")
                )
            ).replace("\r\n", "\n").trim();
            assertEquals(expectedOutput2, actualOutput2);
            outContent.reset();

            // Test SampleInput3_2023.csv
            ProjectRunner.main(new String[] { "SampleInput3_2023.csv" });
            String actualOutput3 = outContent.toString()
                .replace("\r\n", "\n")
                .trim();
            String expectedOutput3 = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("SampleOutput3_2023.txt")
                )
            ).replace("\r\n", "\n").trim();
            assertEquals(expectedOutput3, actualOutput3);
        } 
        finally 
        {
            System.setOut(originalOut);
        }
    }

}
