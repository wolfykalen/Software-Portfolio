package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco
import cs2.Button;
import cs2.Shape;
import cs2.Window;
import cs2.WindowSide;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

// -------------------------------------------------------------------------
/**
 * Represents the GUI for the Tower of Hanoi
 * implements the Observer pattern to update the display
 * whenever the game state changes. It manages the visual representation
 * of the towers and disks as well as the user interface elements.
 * 
 * @author kalendaco
 * @version Mar 25, 2025
 */
public class PuzzleWindow 
    implements Observer 
{

    private HanoiSolver game;
    private Shape left;
    private Shape center;
    private Shape right;
    private Window window;

    /**
     * Width factor used to determine disk sizes
     */
    public static final int WIDTH_FACTOR = 15;
    /**
     * Gap between disks when stacked
     */
    public static final int DISK_GAP = 0;
    /**
     * Height of each disk.
     */
    public static final int DISK_HEIGHT = 15;
    /**
     * Gap between poles.
     */
    public static final int POLE_GAP = 100; 

    // ----------------------------------------------------------
    /**
     * Create a new PuzzleWindow object.
     * Initializes the game window, poles, bases, and disks,
     * and sets up the user interface.
     * @param g
     *      the HanoiSolver instance to observe
     */
    public PuzzleWindow(HanoiSolver g) 
    {
        this.game = g;
        game.addObserver(this);

        window = new Window("Tower of Hanoi");

        int poleHeight = 200;
        int poleY = (window.getGraphPanelHeight() / 2) - (poleHeight / 2);

        int leftX = 200 - 15 / 2; 
        left = new Shape(leftX, poleY, 15, poleHeight, new Color(50, 50, 50));

        center = new Shape(leftX + 15 + POLE_GAP, poleY, 15, poleHeight, 
            new Color(50, 50, 50));
        right = new Shape(center.getX() + 15 + POLE_GAP, poleY, 15, poleHeight, 
            new Color(50, 50, 50));

        int baseWidth = 90; 
        int baseHeight = 10; 
        addBase(left, poleY + poleHeight, baseWidth, baseHeight);
        addBase(center, poleY + poleHeight, baseWidth, baseHeight);
        addBase(right, poleY + poleHeight, baseWidth, baseHeight);

        for (int width = (game.disks() + 1) 
            * WIDTH_FACTOR; width > WIDTH_FACTOR; width -= WIDTH_FACTOR) 
        {
            Disk disk = new Disk(width);
            game.getTower(Position.LEFT).push(disk);
            window.addShape(disk);
            moveDisk(Position.LEFT);
        }

        window.addShape(left);
        window.addShape(center);
        window.addShape(right);

        Button solveButton = new Button("Solve");
        window.addButton(solveButton, WindowSide.SOUTH);
        solveButton.onClick(this, "clickedSolve");
    }

    /**
     * Adds a base shape under a pole.
     * 
     * @param 
     *      pole the pole to add a base to
     * @param 
     *      y the y-coordinate for the base
     * @param 
     *      baseWidth the width of the base
     * @param 
     *      baseHeight the height of the base
     */
    private void addBase(Shape pole, int y, int baseWidth, int baseHeight) 
    {
        window.addShape(new Shape(pole.getX() - (baseWidth - pole.getWidth()) 
            / 2, y, baseWidth, baseHeight, new Color(50, 50, 50)));
    }

    /**
     * Moves a disk to its correct position on a pole.
     * 
     * @param 
     *      position the position of the pole to move the disk to
     */
    private void moveDisk(Position position) 
    {
        Disk currentDisk = game.getTower(position).peek();
        Shape currentPole;

        switch (position) 
        {
            case LEFT: currentPole = left; break;        
            case CENTER: currentPole = center; break;
            case RIGHT: currentPole = right; break;
            default: currentPole = center; break;
        }

        int diskCount = game.getTower(position).size();
        int y = currentPole.getY() + currentPole.getHeight() - 
            (diskCount * DISK_HEIGHT);
        int x = currentPole.getX() + (currentPole.getWidth() / 2) - 
            (currentDisk.getWidth() / 2);

        currentDisk.moveTo(x, y);
    }

    /**
     * Updates the display when the game state changes.
     * 
     * @param 
     *      o the observable object (HanoiSolver)
     * @param 
     *      arg the position of the moved disk
     */
    @Override
    public void update(Observable o, Object arg) 
    {
        if (arg instanceof Position) 
        {
            Position position = (Position) arg;
            moveDisk(position);
            sleep();
        }
    }

    // ----------------------------------------------------------
    /**
     * Handles the Solve button.
     * Starts a new thread to solve the puzzle.
     * 
     * @param 
     *      button the button that was clicked
     */
    public void clickedSolve(Button button) 
    {
        button.disable();
        new Thread(() -> game.solve()).start();
    }

    /**
     * Pauses the execution for a short duration.
     * .
     */
    private void sleep() 
    {
        try 
        {
            Thread.sleep(500);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}