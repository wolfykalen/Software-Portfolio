package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco
import static org.junit.Assert.*;
import org.junit.Test;

//-------------------------------------------------------------------------
/**
 * tests to verify the functionality of the Tower
 * 
 * @author kalendaco
 * @version Mar 25, 2025
 */
public class TowerTest 
{

    // ----------------------------------------------------------
    /**
     * Tests pushing a disk onto an empty tower.
     */
    @Test
    public void testPushDisk() 
    {
        Tower tower = new Tower(Position.LEFT);
        Disk disk = new Disk(30);
        tower.push(disk);
        assertEquals(disk, tower.peek());
    }

    // ----------------------------------------------------------
    /**
     * Tests pushing a larger disk onto a tower with a smaller disk.
     */
    @Test
    public void testPushLargerDisk() 
    {
        Tower tower = new Tower(Position.LEFT);
        Disk disk1 = new Disk(30); 
        Disk disk2 = new Disk(40); 
        tower.push(disk1); 

        try 
        {
            tower.push(disk2); 
            fail("Expected IllegalStateException to be thrown");
        } 
        catch (IllegalStateException e) 
        {
            assertEquals("Cannot place a larger disk on a smaller one.", 
                e.getMessage());
        }
    }
    
 // ----------------------------------------------------------
    /**
     * Tests pushing a null disk onto a tower.
     */
    @Test
    public void testPushNullDisk() 
    {
        Tower tower = new Tower(Position.LEFT);
        try 
        {
            tower.push(null);
            fail("Expected IllegalArgumentException");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals("Disk cannot be null.", e.getMessage());
        }
    }
}