package game;

// -------------------------------------------------------------------------
/**
 * This simply just is just where you run the game from this is what takes in
 * all the classes and lets you run them\ to display the game
 * 
 * @author kalendaco
 * @version Feb 20, 2024
 */
public class ProjectRunner
{

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length > 0)
        {
            WhackAShape game = new WhackAShape(args);
        }
        else
        {
            WhackAShape game = new WhackAShape();
        }
    }
}
