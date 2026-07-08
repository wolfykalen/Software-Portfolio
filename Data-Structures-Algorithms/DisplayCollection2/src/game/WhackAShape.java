package game;

import bag.BagInterface;
import cs2.*;

import java.awt.Color;

/**
 * Whack A shape is a window game that when run, shows an intial shape of a
 * random size color and shape (Circle or Square) and lets the user click the
 * shape to display a new one, after all the shapes have been quit the player
 * will be awarded with a message saying you win,
 * 
 * @author kalendaco
 * @version Feb 20, 2024
 */
public class WhackAShape
{
    private SimpleArrayBag<Shape> bag;
    private Window window;
    private Button quitButton;

    private static final String[] STRINGS =
        { "red circle", "blue circle", "red square", "blue square" };

    // ----------------------------------------------------------
    /**
     * Initializes the game creates a window, a quit button generates random
     * shapes and shows the first shape
     */
    public WhackAShape()
    {
        bag = new SimpleArrayBag<Shape>();
        window = new Window();
        quitButton = new Button("Quit");
        quitButton.onClick(this, "clickedQuit");
        window.addButton(quitButton, WindowSide.EAST);

        // Initialize bag with shapes
        int size = 6 + (int)(Math.random() * 9); // Random size between 6 and 14
        for (int i = 0; i < size; i++)
        {
            String randomShape = STRINGS[(int)(Math.random() * STRINGS.length)];
            try
            {
                Shape shape = buildShape(randomShape);
                bag.add(shape);
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }

        Shape firstShape = bag.pick();
        window.addShape(firstShape);
    }


    // ----------------------------------------------------------
    /**
     * Does the same thing as the method above and Initialized the game with
     * specific shapes as an String input
     * 
     * @param inputs
     */
    public WhackAShape(String[] inputs)
    {
        bag = new SimpleArrayBag<>();
        window = new Window();
        quitButton = new Button("Quit");
        quitButton.onClick(this, "clickedQuit");
        window.addButton(quitButton, WindowSide.EAST);

        // Build shapes from
        for (String input : inputs)
        {
            try
            {
                Shape shape = buildShape(input);
                bag.add(shape);
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }

        Shape firstShape = bag.pick();
        window.addShape(firstShape);
    }


    // ----------------------------------------------------------
    /**
     * This builds the shape, decides what color the type of shape and the size
     * 
     * @param input
     * @return
     * @throws IllegalArgumentException
     */
    private Shape buildShape(String input)
        throws IllegalArgumentException
    {
        String[] tokens = input.split("\\s+");
        if (tokens.length != 2)
        {
            throw new IllegalArgumentException(
                "Invalid input format: " + input);
        }

        String colorString = tokens[0].toLowerCase();
        String shapeString = tokens[1].toLowerCase();

        int size = 100 + (int)(Math.random() * 101);
        int x = (int)(Math.random() * (window.getGraphPanelWidth() - size));
        int y = (int)(Math.random() * (window.getGraphPanelHeight() - size));

        Color color;
        if (colorString.equals("red"))
        {
            color = Color.RED;
        }
        else if (colorString.equals("blue"))
        {
            color = Color.BLUE;
        }
        else
        {
            throw new IllegalArgumentException("Invalid color: " + colorString);
        }

        Shape shape;
        if (shapeString.equals("circle"))
        {
            shape = new CircleShape(x, y, size, color);
        }
        else if (shapeString.equals("square"))
        {
            shape = new SquareShape(x, y, size, color);
        }
        else
        {
            throw new IllegalArgumentException("Invalid shape: " + shapeString);
        }

        shape.onClick(this, "clickedShape");
        return shape;
    }


    // ----------------------------------------------------------
    /**
     * Starts when a shape is clicked, removes the clicked shape from the window
     * and the bag, selects the next shape. and displays the message you win
     * 
     * @param shape
     */
    public void clickedShape(Shape shape)
    {
        window.removeShape(shape);
        bag.remove(shape);

        Shape nextShape = bag.pick();
        if (nextShape == null)
        {
            TextShape text = new TextShape(
                window.getGraphPanelWidth() / 2,
                window.getGraphPanelHeight() / 2,
                "You Win!");
            window.addShape(text);
        }
        else
        {
            window.addShape(nextShape);
        }
    }


    // ----------------------------------------------------------
    /**
     * the function of the quit button exits out the window when clicked
     * 
     * @param button
     */
    public void clickedQuit(Button button)
    {
        System.exit(0);
    }


    // ----------------------------------------------------------
    /**
     * getter method access the window for the game
     * 
     * @return the window
     */
    public Window getWindow()
    {
        return window;
    }


    // ----------------------------------------------------------
    /**
     * Getter method access the bag of shapes
     * 
     * @return the bag
     */
    public SimpleArrayBag<Shape> getBag()
    {
        return bag;
    }

}
