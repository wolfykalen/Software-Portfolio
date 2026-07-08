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
 *  Tests for the Disk class 
 *  tests to verify the functionality of the Disk class
 * 
 *  @author kalendaco
 *  @version Mar 25, 2025
 */
public class DiskTest 
{

    // ----------------------------------------------------------
    /**
     * Tests the creation of a Disk object.
     */
    @Test
    public void testDiskCreation() 
    {
        Disk disk = new Disk(30);
        assertEquals(30, disk.getWidth());
    }

    // ----------------------------------------------------------
    /**
     * Tests the Disk constructor with a negative width.
     */
    @Test
    public void testDiskWidthNegative() 
    {
        try 
        {
            new Disk(-10);
            fail("Expected IllegalArgumentException");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals("Width must be positive.", e.getMessage());
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the compareTo method.
     */
    @Test
    public void testCompareTo() 
    {
        Disk disk1 = new Disk(30);
        Disk disk2 = new Disk(20);
        assertTrue(disk1.compareTo(disk2) > 0);
    }

    // ----------------------------------------------------------
    /**
     * Tests the Equals method.
     */
    @Test
    public void testEquals() 
    {
        Disk disk1 = new Disk(30);
        Disk disk2 = new Disk(30);
        assertTrue(disk1.equals(disk2));
    }
    
 // ----------------------------------------------------------
    /**
     * Tests the compareTo method with a null disk.
     */
    @Test
    public void testCompareToNull() 
    {
        Disk disk = new Disk(30);
        try 
        {
            disk.compareTo(null);
            fail("Expected IllegalArgumentException");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals("Cannot compare to null.", e.getMessage());
        }
    }
    
 // ----------------------------------------------------------
    /**
     * Tests the equals method with different width disks.
     */
    @Test
    public void testEqualsDifferentWidth() 
    {
        Disk disk1 = new Disk(30);
        Disk disk2 = new Disk(20);
        assertFalse(disk1.equals(disk2));
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method with a different object type.
     */
    @Test
    public void testEqualsDifferentObject() 
    {
        Disk disk = new Disk(30);
        assertFalse(disk.equals(new Object()));
    }

    // ----------------------------------------------------------
    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() 
    {
        Disk disk = new Disk(30);
        assertEquals("30", disk.toString());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests the equals method with a different class.
     */
    @Test
    public void testEqualsDifferentClass() 
    {
        Disk disk = new Disk(30);
        assertFalse(disk.equals(new Object())); 
    }
}
