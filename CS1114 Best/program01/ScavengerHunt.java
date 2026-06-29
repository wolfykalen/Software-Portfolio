// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Kalen Dacosta (kalendaco)
import student.micro.jeroo.*;
//-------------------------------------------------------------------------
/**
 *  Spawns the jeroo in the map and performs the collectFlower Method
 *
 *  @author kalendaco
 *  @version 2023.09.05
 */
public class ScavengerHunt 
    extends LongIsland
{
    //~ Fields ................................................................

    //~ Constructor ...........................................................
    // ----------------------------------------------------------
    /**
     * Initializes a newly created ScavengerHunt object.
     */
    public ScavengerHunt()
    {
        super();
        /*# Do any work to initialize your class here. */
    }

    //~ Methods ...............................................................
    /**
     * spawns the jeroo craig at 1 , 2
     */
    public void myProgram()
    {
        Scavenger craig = new Scavenger();
        this.addObject(craig , 1 , 2 );
        craig.collectFlowers();
    }

}
