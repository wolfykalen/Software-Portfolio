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
 * Tests the HanoiSolver to verify its functionality 
 * 
 *  @author kalendaco
 *  @version Mar 25, 2025
 */
public class HanoiSolverTest
{

    // ----------------------------------------------------------
    /**
     * Tests the initialization of the HanoiSolver object.
     */
    @Test
    public void testHanoiSolverInitialization() 
    {
        HanoiSolver solver = new HanoiSolver(3);
        assertEquals(3, solver.disks());
    }

    // ----------------------------------------------------------
    /**
     * Tests the retrieval of towers by position.
     */
    @Test
    public void testGetTower() 
    {
        HanoiSolver solver = new HanoiSolver(3);
        assertNotNull(solver.getTower(Position.LEFT));
        assertNotNull(solver.getTower(Position.CENTER));
        assertNotNull(solver.getTower(Position.RIGHT));
    }

    // ----------------------------------------------------------
    /**
     * Tests the solving of the Tower of Hanoi puzzle.
     */
    @Test
    public void testSolve() 
    {
        HanoiSolver solver = new HanoiSolver(3); 
        Tower leftTower = solver.getTower(Position.LEFT);

        // Push disks onto the left tower in decreasing order
        leftTower.push(new Disk(3)); 
        leftTower.push(new Disk(2)); 
        leftTower.push(new Disk(1));     
        solver.solve();           
        assertEquals(3, solver.getTower(Position.RIGHT).size());            
        assertEquals(0, leftTower.size());
    }
    
 // ----------------------------------------------------------
    /**
     * Tests the tower with DEFAULT position.
     */
    @Test
    public void testGetTowerDefault() 
    {
        HanoiSolver solver = new HanoiSolver(3);
        assertNotNull(solver.getTower(Position.DEFAULT));
        assertEquals(solver.getTower(Position.CENTER), 
            solver.getTower(Position.DEFAULT));
    }
    
 // ----------------------------------------------------------
    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() 
    {
        HanoiSolver solver = new HanoiSolver(3);
        Tower leftTower = solver.getTower(Position.LEFT);
        Tower centerTower = solver.getTower(Position.CENTER);
        Tower rightTower = solver.getTower(Position.RIGHT);
        
        // Add disks to towers for testing
        leftTower.push(new Disk(3));
        centerTower.push(new Disk(2));
        rightTower.push(new Disk(1));
        
        String expected = leftTower.toString() + 
            centerTower.toString() + rightTower.toString();
        assertEquals(expected, solver.toString());
    }
}
