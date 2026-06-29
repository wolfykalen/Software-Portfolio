import student.media.*;
import student.util.Random;
import java.awt.Color; 
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- kalendaco
/**
 *  This will create a simulation of the Schelling model.
 *
 *  @author kalendaco
 *  @version 2023.10.03
 */
public class SchellingSimulation
    extends Picture
{
    private double satisfactionThreshold;
    private int redLine;

    /**
     * creates a SchellingSimulation object
     * 
     * @param w (int) sets up the object
     * @param h (int) sets up the object
     */
    public SchellingSimulation(int w, int h)
    {
        super(w, h);
        satisfactionThreshold = 0.3;
        redLine = 0;
    }

    /**
     *  a getter method designed to return the satisfation threshold
     * 
     * @return (double) shows the satisfaction value
     */
    public double getSatisfactionThreshold()
    {
        return satisfactionThreshold;
    }

    /**
     * A setter method designed to take  a double parameter while also changing
     * the satisfaction threshold to the specified value.
     * 
     * @param newSatisfactionThreshold (double) shows the new value of the 
     * satisfaction threshold
     */
    public void setSatisfactionThreshold(double newSatisfactionThreshold)
    {
        satisfactionThreshold = newSatisfactionThreshold;    
    }

    /**
     * A getter method designed to return the redline value
     * 
     * @return (int) shows the value of redline
     */
    public int getRedLine()
    {
        return redLine;
    }

    /**
     * A setter method designed to take an integer parameter while changing
     * the redline to the specified value.
     * 
     * @param newRedLine (int) takes in the new value of redline
     */
    public void setRedLine(int newRedLine)
    {
        redLine = newRedLine;    
    }

    /**
     *creates the image with a random assortment of blue and orange pixels.
     *
     *@param bluePercent (double)  randmomizes the colors of blue
     *@param orangePercent (double) randmomizes the colors of orange
     */
    public void populate(double bluePercent, double orangePercent)
    {
        for (Pixel pix : this.getPixels())
        {
            Random generator = Random.generator();
            double randomNum = generator.nextDouble();

            if (randomNum < bluePercent)
            {
                pix.setColor(Color.BLUE);
            }
            else if (randomNum < bluePercent + orangePercent 
                && pix.getY() >= redLine)
            {
                pix.setColor(Color.ORANGE);
            }
        }
    }

    /**
     * using two pixels  will return a boolean value showing wether or not
     *  two pixels have are same color.
     *  
     *  @return (boolean) shows if the 2 values are the same
     *  @param pix1 (boolean) is the first pixel
     *  @param pix2 (boolean) is the 2nd pixel
     */
    public boolean areSameColor(Pixel pix1, Pixel pix2)
    {
        return pix1.getColor().equals(pix2.getColor());
    }

    /**
     * using one pixel will return true if the color is
     * white.
     * 
     * @return (boolean) shows is the color white
     * @param pix (boolean) is checking this pixel
     */
    public boolean isEmpty(Pixel pix)
    {
        return pix.getColor().equals(Color.WHITE);
    }

    /**
     * using one pixel and a color value a boolean
     * value will be returned showing if the specific 
     * color would be true at the given pixel location
     * 
     * @return (boolean) is the value at this spot correct
     * @param pix (boolean) shows the pixel
     * @param c (boolean) shows the color
     */
    public boolean isSatisfied(Pixel pix, Color c)
    {
        double neighbors = 0.0;
        double satisfied = 0.0;
        for (Pixel neighbor : pix.getNeighborPixels())
        {
            if (!neighbor.getColor().equals(Color.WHITE))
            {
                if (neighbor.getColor().equals(c))
                {
                    satisfied++;       
                }
                neighbors = neighbors + 1;
            }        
        }
        if (neighbors > 0)
        {
            return satisfied / neighbors >= satisfactionThreshold;    
        }
        return true;
    }

    /**
     * will relocate the pixels with the goal of satisfying each color
     * 
     * @return (boolean) is the color satisfied
     * @param pix (boolean) is the pixel
     */
    public boolean maybeRelocate(Pixel pix)
    {
        Color c = pix.getColor();
        Random generator = Random.generator();
        int newX = generator.nextInt(this.getWidth());
        int newY = generator.nextInt(this.getHeight());
        Pixel newP = this.getPixel(newX, newY);
        if (c.equals(Color.ORANGE) && this.isSatisfied(newP, c) && 
            this.isEmpty(newP) && newY > redLine)
        {   
            newP.setColor(c);
            pix.setColor(Color.WHITE);
            return true;
        }
        else if (c.equals(Color.BLUE) && (this.isEmpty(newP) 
            && this.isSatisfied(newP, c)))
        {   
            newP.setColor(c);
            pix.setColor(Color.WHITE);
            return true;   
        }
        return false;
    }

    /**
     * Using a loop  every pixel scenario will be checked
     * if its empty it will do nothing.
     * If the pixel is occupied the agent will be checked 
     * to determine if its satisfied
     * If none are these true it will attempt to move the pixel 
     *finally the total number of successful moves will be returned
     *
     *@return (int) shows the number of correct moves
     */
    public int cycleAgents()
    {
        int counter = 0;
        for (Pixel pix : this.getPixels())
        {
            Color c  = pix.getColor();
            if (!this.isEmpty(pix) && !this.isSatisfied(pix, c) 
                && this.maybeRelocate(pix))
            {
                counter++;
            }
        }
        return counter;
    }

}
