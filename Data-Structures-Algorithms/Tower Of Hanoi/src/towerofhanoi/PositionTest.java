package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco
import static org.junit.Assert.*;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 * This class tests to verify the functionality of the position enum
 * 
 *  @author kalendaco
 *  @version Mar 25, 2025
 */
public class PositionTest 
{

    // ----------------------------------------------------------
    /**
     * Tests the enum constants by their string names.
     */
    @Test
    public void testPositionValues() 
    {
        assertEquals(Position.LEFT, Position.valueOf("LEFT"));
        assertEquals(Position.CENTER, Position.valueOf("CENTER"));
        assertEquals(Position.RIGHT, Position.valueOf("RIGHT"));
    }
}
