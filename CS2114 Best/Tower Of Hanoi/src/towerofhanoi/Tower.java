package towerofhanoi;
//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco

// -------------------------------------------------------------------------
/**
 * Represents a tower in the Tower of Hanoi game.
 * Each tower has a specific position (LEFT, CENTER, or RIGHT) in the game.
 * ensures that disks are always stacked correctly
 * prevents larger disks from being placed on smaller ones.
 * 
 * @author kalendaco
 * @version Mar 25, 2025
 */
public class Tower 
    extends LinkedStack<Disk> 
{
    private Position position;

    // ----------------------------------------------------------
    /**
     * Create a new Tower object with a specified position.
     * 
     * @param position the position of the tower (LEFT, CENTER, or RIGHT)
     */
    public Tower(Position position) 
    {
        super();
        this.position = position;
    }

    // ----------------------------------------------------------
    /**
     * Returns the position of the tower.
     * 
     * @return 
     *      the tower's position
     */
    public Position position() 
    {
        return position;
    }

    /**
     * Pushes a disk onto the tower.
     * makes sure only smaller disks can be placed on top of larger ones.
     * 
     * @param 
     *      disk the disk to push onto the tower
     * @throws 
     *      IllegalArgumentException if the disk is null
     * @throws 
     *      IllegalStateException if attempting to place a larger disk on a 
     *      smaller one
     */
    @Override
    public void push(Disk disk) 
    {
        if (disk == null) 
        {
            throw new IllegalArgumentException("Disk cannot be null.");
        }
        if (this.isEmpty() || this.peek().compareTo(disk) > 0) 
        {
            super.push(disk);
        } 
        else 
        {
            throw new IllegalStateException("Cannot place a larger disk on a "
                + "smaller one.");
        }
    }
}  

