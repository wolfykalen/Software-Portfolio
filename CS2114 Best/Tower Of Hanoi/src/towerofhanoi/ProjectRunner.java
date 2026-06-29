package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco

// -------------------------------------------------------------------------
/**
 * This initializes the game with a specified number of disks and
 * creates the GUI window for displaying the game.
 * 
 *  @author kalendaco
 *  @version Mar 25, 2025
 */
public class ProjectRunner
{

    // ----------------------------------------------------------
    /**
     * Main method that initializes and runs the Tower of Hanoi game.
     * 
     * @param 
     *      args command line arguments 
     *            )
     */
    public static void main(String[] args)
    {
        int disks = 6;

        if (args.length == 1)
        {
            disks = Integer.parseInt(args[0]);
        }

        HanoiSolver solver = new HanoiSolver(disks);

        PuzzleWindow puzzleWindow = new PuzzleWindow(solver);
    }
}
