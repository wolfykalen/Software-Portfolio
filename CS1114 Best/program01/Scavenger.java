
import student.micro.jeroo.*;
//-------------------------------------------------------------------------
/**
 *  This will navigate jeroo around the map picking up flowers
 *  jeroo will start with the top of the map then bottom ending in the middle 
 *  @author kalendaco
 *  @version 2023.09.05
 */
public class Scavenger
    extends Jeroo
{
    //~ Fields ................................................................

    //~ Constructor ...........................................................
    // ----------------------------------------------------------
    /**
     * Initializes a newly created Scavenger object.
     */
    public Scavenger()
    {
        super();
        /*# Do any work to initialize your class here. */
    }

    //~ Methods ...............................................................
    /**
     * this takes in all other methods to complete the task 
     */
    public void collectFlowers()
    {
        this.getFlowerNorth();
        this.pickNorthFlowers();
        this.goSouth();
        this.pickSouthFlowers();
        this.middle();
    }

    /**
     * this brings jeroo to the top of the map  
     */
    public void getFlowerNorth()
    {
        this.hop(2);
        this.turn(RIGHT);
        this.hop(2);
        this.turn(LEFT);
        this.hop(2);
        this.turn(LEFT);
        this.hop(3);
        this.turn(RIGHT);
    }

    /**
     * this allows jeroo to pick the flower up top
     */
    public void pickNorthFlowers()
    {
        this.pick();
        this.hop(3);
        this.pick();
        this.hop(4);
        this.pick();
    }
    /**
     * navigates jeroo to the bottom of the map
     */
    public void goSouth()
    {
        this.turn(RIGHT);
        this.hop();
        this.turn(LEFT);
        this.hop();
        this.turn(RIGHT);
        this.hop(7);

    }
    /**
     * allows jerooo to pick flowers at the bottom of the map
     */
    public void pickSouthFlowers()
    {
        this.pick();
        this.turn(RIGHT);
        this.hop(2);
        this.pick();
        this.hop(3);
        this.pick();
        this.turn(RIGHT);
        this.hop();
        this.turn(LEFT);
        this.hop(2);
        this.turn(LEFT);
        this.hop();
        this.pick();
    }
    /**
     * send jeroo to pick the middle flower 
     */
    public void middle()
    {
        this.turn(LEFT);
        this.turn(LEFT);
        this.hop(6);
        this.turn(RIGHT);
        this.hop(3);
        this.turn(RIGHT);
        this.hop(2);
        this.pick();

    }
}
