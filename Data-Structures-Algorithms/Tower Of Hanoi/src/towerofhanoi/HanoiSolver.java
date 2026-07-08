package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco

import java.util.Observable;

// -------------------------------------------------------------------------
/**
 *  Represents a solver for the Tower of hanoi 
 *  this manages the towers and implements the logic to solve the puzzle using
 *  recursion 
 * 
 *  @author kalendaco
 *  @version Mar 25, 2025
 */
public class HanoiSolver 
    extends Observable 
{
    private int numDisks;
    private Tower left;
    private Tower center;
    private Tower right;


    // ----------------------------------------------------------
    /**
     * Create a new HanoiSolver object.
     * 
     * @param numDisks
     *              the number of disks in the puzzle 
     */
    public HanoiSolver(int numDisks) 
    {
        this.numDisks = numDisks;
        left = new Tower(Position.LEFT);
        center = new Tower(Position.CENTER);
        right = new Tower(Position.RIGHT);
    }

    // ----------------------------------------------------------
    /**
     * Returns the number of disks in the puzzle.
     *  
     * @return 
     *      the number of disks
     */
    public int disks() 
    {
        return numDisks;
    }

    // ----------------------------------------------------------
    /**
     * Retrieves the tower associated with the specified position. 
     * 
     * @param pos the position of the tower (LEFT, CENTER, RIGHT)
     * @return 
     *      the corresponding Tower object
     */
    public Tower getTower(Position pos) 
    {
        switch (pos) 
        {
            case LEFT: return left;
            case CENTER: return center;
            case RIGHT: return right;
            default: return center;
        }
    }

    /**
     * Returns a string representation of the current state of the towers.
     * 
     * @return 
     *      a string representing the state of all towers
     */
    @Override
    public String toString() 
    {
        return left.toString() + center.toString() + right.toString();
    }

    // ----------------------------------------------------------
    /**
     * Moves a disk from the source tower to the destination tower. 
     * 
     * @param source the tower to move the disk from
     * @param destination the tower to move the disk to
     */
    public void move(Tower source, Tower destination) 
    {
        Disk disk = source.pop();
        destination.push(disk);
        setChanged();
        notifyObservers(destination.position());
    }

    // ----------------------------------------------------------
    /**
     * Solves the Tower of Hanoi puzzle recursively.
     * Moves disks from the start pole to the end pole
     * 
     * @param currentDisks the number of disks to move
     * @param startPole the tower to move disks from
     * @param tempPole the temporary tower used for moving disks
     * @param endPole the tower to move disks to
     */
    public void solveTowers(int currentDisks, Tower startPole, 
        Tower tempPole, Tower endPole) 
    {
        if (currentDisks == 1) 
        {
            move(startPole, endPole);
        } 
        else 
        {
            solveTowers(currentDisks - 1, startPole, endPole, tempPole);
            move(startPole, endPole);
            solveTowers(currentDisks - 1, tempPole, startPole, endPole);
        }
    }

    // ----------------------------------------------------------
    /**
     * Initiates the solving process for the Tower of Hanoi.
     * 
     */
    public void solve() 
    {
        solveTowers(numDisks, left, center, right);
    }
}

