import student.micro.*;
import static org.assertj.core.api.Assertions.*;
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
 *  Unit tests for the Schelling Simulation class/
 *
 *  @author kalendaco
 *  @version 2023.10.03
 */
public class SchellingSimulationTest
    extends TestCase
{
    //~ Fields ................................................................
    private SchellingSimulation image;
    private Pixel a;
    private Pixel b;
    private Pixel c;
    private Pixel d;
    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new SchellingSimulationTest test object.
     */
    public SchellingSimulationTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }

    //~ Methods ...............................................................
    // ----------------------------------------------------------
    /**
     * Sets up the test fixture
     */
    public void setUp()
    {
        image = new SchellingSimulation(2, 2);
        image.setSatisfactionThreshold(0.5);
        a = image.getPixel(0, 0);
        b = image.getPixel(1, 0);
        c = image.getPixel(0, 1);
        d = image.getPixel(1, 1);

        a.setColor(Color.BLUE);
        b.setColor(Color.ORANGE);
        c.setColor(Color.ORANGE);
        d.setColor(Color.WHITE);
    }

    /**
     * Tests the getSatifcationThreshold method  makes sure
     *  the satisfaction threshold is returned.
     */
    public void testGetSatisfactionThreshold()
    {
        image.setSatisfactionThreshold(0.7);
        double threshold = image.getSatisfactionThreshold();

        assertThat(threshold).isEqualTo(0.7);
    }

    /**
     * Tests the setSatisfactionThreshold method when setting the
     * threshold to 0.7 
     */
    public void testSetSatisfactionThreshold()
    {
        image.setSatisfactionThreshold(0.7);

        assertThat(image.getSatisfactionThreshold()).isEqualTo(0.7);
    }

    /**
     * Tests the getRedLine method to make sure that it is 0.
     */
    public void testGetRedLine()
    {
        int redline = image.getRedLine();

        assertThat(redline).isEqualTo(0);
    }

    /**
     * Tests the setRedLine method by setting the red line to 1.
     */
    public void testSetRedLine()
    {
        image.setRedLine(1);

        assertThat(image.getRedLine()).isEqualTo(1);
    }

    /**
     * Tests the populate method when blue is 50%
     * and orange is 50%.
     */
    public void testPopulate5050()
    {
        Random.setNextDoubles(0.2, 0.4, 0.7, 0.9);
        SchellingSimulation sim = new SchellingSimulation(2, 2);
        Pixel p1 = sim.getPixel(0, 0);
        Pixel p2 = sim.getPixel(0, 1);
        Pixel p3 = sim.getPixel(1, 0);
        Pixel p4 = sim.getPixel(1, 1);
        sim.populate(0.5, 0.5);

        assertThat(sim.isEmpty(p1)).isEqualTo(false);
        assertThat(sim.isEmpty(p2)).isEqualTo(false);
        assertThat(sim.isEmpty(p3)).isEqualTo(false);
        assertThat(sim.isEmpty(p4)).isEqualTo(false);
    }

    /**
     * Tests the populate method when blue is 50%
     * and orange is 35% and the red line is 2.
     */
    public void testPopulate5050RedLineOf2()
    {
        Random.setNextDoubles(0.2, 0.3, 0.7, 0.9);
        SchellingSimulation simu = new SchellingSimulation(2, 2);
        simu.setRedLine(2);
        Pixel p1 = simu.getPixel(0, 0);
        Pixel p2 = simu.getPixel(0, 1);
        Pixel p3 = simu.getPixel(1, 0);
        Pixel p4 = simu.getPixel(1, 1);
        simu.populate(0.5, 0.35);

        assertThat(simu.isEmpty(p1)).isEqualTo(false);
        assertThat(simu.isEmpty(p2)).isEqualTo(true);
        assertThat(simu.isEmpty(p3)).isEqualTo(false);
        assertThat(simu.isEmpty(p4)).isEqualTo(true);
    }

    /**
     * Tests the areSameColor method when the two
     * pixels are the same color.
     */
    public void testAreSameColorSame()
    {
        boolean samecolor = image.areSameColor(b, c);

        assertThat(samecolor).isEqualTo(true);
    }

    /**
     * Tests the areSameColor method when the two
     * pixels are not the same color.
     */
    public void testAreSameColorNotSame()
    {
        boolean samecolor = image.areSameColor(a, c);

        assertThat(samecolor).isEqualTo(false);
    }

    /**
     * Tests the isEmpty method when the pixel is empty.
     */
    public void testIsEmptyEmpty()
    {
        assertThat(image.isEmpty(d)).isEqualTo(true);
    }

    /**
     * Tests the isEmpty method when the pixel is not empty.
     */
    public void testIsEmptyNotEmpty()
    { 
        assertThat(image.isEmpty(c)).isEqualTo(false);
    }

    /**
     * Tests the isSatisfied method when the agent is satisfied.
     * Checks to see how many orange neighbors the pixel at 
     * 0,0 has and checks to see if that value wil satisfy 
     * the satisfaction threshold. 
     */
    public void testIsSatisfiedYes()
    {
        image.setSatisfactionThreshold(0.3);
        boolean satisfied = image.isSatisfied(a, Color.ORANGE);

        assertThat(satisfied).isEqualTo(true);
    }

    /**
     * Tests the isSatisfied method when the agent is not satisfied.
     * Checks to see how many blue neighbors the pixel at 0,0 
     * has and it does not have any. 
     */
    public void testIsSatisfiedNo()
    {
        image.setSatisfactionThreshold(0.3);
        boolean notSatisfied = image.isSatisfied(a, Color.BLUE);

        assertThat(notSatisfied).isEqualTo(false);
    }

    /**
     * Tests the isSatisfied method when the agent is satisfied.
     * Checks to see how many blue neighbors the pixel at 0,0 
     * has and it has one blue.
     */
    public void testIsSatisfiedYesOneNeighbor()
    {
        b.setColor(Color.BLUE);
        c.setColor(Color.WHITE);
        d.setColor(Color.WHITE);
        image.setSatisfactionThreshold(0.5);
        boolean notSatisfied = image.isSatisfied(a, Color.BLUE);

        assertThat(notSatisfied).isEqualTo(true);
    }

    /**
     * Tests the isSatisfied method when the agent has no 
     * neighbors. The agent is satisfied. 
     */
    public void testIsSatisfiedYesNoNeighbors()
    {
        SchellingSimulation sim = new SchellingSimulation(3, 3);
        sim.setSatisfactionThreshold(0.5);
        sim.getPixel(0, 0).setColor(Color.BLUE);
        sim.getPixel(1, 0).setColor(Color.WHITE);
        sim.getPixel(2, 0).setColor(Color.WHITE);
        sim.getPixel(0, 1).setColor(Color.WHITE);
        sim.getPixel(1, 1).setColor(Color.WHITE);
        sim.getPixel(2, 1).setColor(Color.WHITE);
        sim.getPixel(0, 2).setColor(Color.WHITE);
        sim.getPixel(1, 2).setColor(Color.WHITE);
        sim.getPixel(2, 2).setColor(Color.WHITE);
        Pixel aa = sim.getPixel(0, 0);
        boolean notSatisfied = image.isSatisfied(aa, Color.BLUE);

        assertThat(notSatisfied).isEqualTo(true);
    }

    /**
     * Tests the maybeRelocate method when the agent is moved.
     */
    public void testMaybeRelocateMoved()
    {
        a.setColor(Color.ORANGE);
        Random.setNextInts(1, 1);

        boolean moved = image.maybeRelocate(a);

        assertThat(moved).isEqualTo(true);
    }

    /**
     * Tests the maybeRelocate method when the agent is not 
     * moved because the new pixel is not empty.
     */
    public void testMaybeRelocateNotMovedCellOccupied()
    {
        a.setColor(Color.ORANGE);
        d.setColor(Color.ORANGE);
        Random.setNextInts(1, 1);

        boolean moved = image.maybeRelocate(a);

        assertThat(moved).isEqualTo(false);
    }

    /**
     * Tests the maybeRelocate method when the agent is not moved
     * because the agent would not be satisfied at the new
     * location and the agent is orange.
     */
    public void testMaybeRelocateNotMovedNotSatisfiedOrange()
    {
        a.setColor(Color.ORANGE);
        b.setColor(Color.BLUE);
        c.setColor(Color.BLUE);
        Random.setNextInts(0, 1, 1);

        boolean moved = image.maybeRelocate(a);

        assertThat(moved).isEqualTo(false);
    }

    /**
     * Tests the maybeRelocate method when the agent is not moved
     * because the agent would not be satisfied at the new
     * location and the new location is not empty and the agent 
     * is orange.
     */
    public void testMaybeRelocateNotEmptyAndNotSatisfied()
    {
        a.setColor(Color.ORANGE);
        b.setColor(Color.BLUE);
        c.setColor(Color.BLUE);
        d.setColor(Color.BLUE);
        Random.setNextInts(0, 1, 1);

        boolean moved = image.maybeRelocate(a);

        assertThat(moved).isEqualTo(false);
    }

    /**
     * Tests the maybeRelocate method when the agent is not moved
     * because the agent would not be satisfied at the new
     * location.
     */
    public void testMaybeRelocateNotMovedNotSatisfiedBlue()
    {
        a.setColor(Color.BLUE);
        Random.setNextInts(0, 1, 1);

        boolean moved = image.maybeRelocate(a);

        assertThat(moved).isEqualTo(false);
    }

    /**
     * Tests the maybeRelocate method when the agent is not moved
     * because the agent would not be satisfied at the new
     * location and the agent is white.
     */
    public void testMaybeRelocateNotMovedNotSatisfiedWhite()
    {
        a.setColor(Color.WHITE);
        Random.setNextInts(0, 1, 1);

        boolean moved = image.maybeRelocate(a);

        assertThat(moved).isEqualTo(false);
    }

    /**
     * Tests the maybeRelocate method when the agent is not
     * moved because the new location is above 
     * the red line and the pixel is ORANGE.
     */
    public void testMaybeRelocateAboveRedLine()
    {
        image.setRedLine(1);
        a.setColor(Color.ORANGE);
        Random.setNextInts(1, 1);

        boolean moved = image.maybeRelocate(a);

        assertThat(moved).isEqualTo(false);
    }

    /**
     * Tests the cycleAgents method when there is 1 move
     * in a 3x3 image. The agent at 0,0 is not satisfied
     * and is moved to 2,2 where it is satisfied because 
     * it has 2 blue neighbors.
     */
    public void testCycleAgents1Moves()
    {
        SchellingSimulation sim = new SchellingSimulation(3, 3);
        sim.setSatisfactionThreshold(0.5);
        sim.getPixel(0, 0).setColor(Color.BLUE);
        sim.getPixel(1, 0).setColor(Color.ORANGE);
        sim.getPixel(2, 0).setColor(Color.ORANGE);
        sim.getPixel(0, 1).setColor(Color.ORANGE);
        sim.getPixel(1, 1).setColor(Color.ORANGE);
        sim.getPixel(2, 1).setColor(Color.BLUE);
        sim.getPixel(0, 2).setColor(Color.ORANGE);
        sim.getPixel(1, 2).setColor(Color.BLUE);
        sim.getPixel(2, 2).setColor(Color.WHITE);
        Random.setNextInts(2, 2);

        int moves = sim.cycleAgents();

        assertThat(moves).isEqualTo(1);
    }

    /**
     * Tests the cycleAgents method when there are no moves
     * in a 3x3 image.
     */
    public void testCycleAgentsNoMoves()
    {
        SchellingSimulation sim = new SchellingSimulation(3, 3);
        sim.setSatisfactionThreshold(0.5);
        sim.getPixel(0, 0).setColor(Color.BLUE);
        sim.getPixel(1, 0).setColor(Color.ORANGE);
        sim.getPixel(2, 0).setColor(Color.ORANGE);
        sim.getPixel(0, 1).setColor(Color.ORANGE);
        sim.getPixel(1, 1).setColor(Color.ORANGE);
        sim.getPixel(2, 1).setColor(Color.ORANGE);
        sim.getPixel(0, 2).setColor(Color.ORANGE);
        sim.getPixel(1, 2).setColor(Color.BLUE);
        sim.getPixel(2, 2).setColor(Color.WHITE);
        Random.setNextInts(2, 2);

        int moves = sim.cycleAgents();

        assertThat(moves).isEqualTo(0);
    }
}
