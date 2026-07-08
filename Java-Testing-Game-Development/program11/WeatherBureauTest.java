import student.micro.*;
import static org.assertj.core.api.Assertions.*;

// -------------------------------------------------------------------------
/**
 *  A test class for the WeatherBureau class.
 *
 *  @author kalendaco
 *  @version 2023.11.28
 */
public class WeatherBureauTest
extends TestCase
{
    //~ Fields ................................................................
    private WeatherBureau test;
    //~ Constructor ...........................................................
    /**
     * Creates a new WeatherBureauTest test object.
     */
    public WeatherBureauTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }
    //~ Methods ...............................................................
    /**
     * Sets up the test fixture.
     * Called before every test case 
     */
    public void setUp()
    {
        test = new WeatherBureau();
    }
    // ----------------------------------------------------------
    /**
     * A test method for the recordDailySummary() 
     */
    public void testRecordDailySummary()
    {

        String line = "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73";
        test.recordDailySummary(line);
        assertThat(test.getStation("KE000063612").getId()).isEqualTo("KE000063612");
    }

    /**
     * A test method for the recordDailySummaries() 
     */
    public void testRecordDailySummaries()
    {
        setIn(
            "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73",
            "KE000063820 -4.033 39.617 55 4/25/16 0 88 101 75");
        test.recordDailySummaries(in());
        assertThat(test.getStation("KE000063612").
            getId()).isEqualTo("KE000063612");
        assertThat(test.getStation("KE000063820").
            getId()).isEqualTo("KE000063820");
    }

    /**
     * A test method for the getStation() 
     */
    public void testGetStation() 
{
    WeatherStation station = test.getStation("KE000063612");
    if (station != null) 
    {
        assertThat(station.getId()).isEqualTo("KE000063612");
    } 
    else 
    {
        fail("Station with ID KE000063612 not found");
    }
}



    /**
     * A test method for the lowestStation() 
     */
    public void testLowestStation()
    {
        setIn(
            "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73",
            "KE000063820 -4.033 39.617 55 4/25/16 0 88 101 75");
        test.recordDailySummaries(in());
        WeatherStation station = test.lowestStation(2);
        assertThat(station.getId()).isEqualTo("KE000063612");
    }

    /**
     * A test method for the lowestStaion() 
     */
    public void testLowestStation2() 
    {
        setIn(
            "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73",
            "KE000063820 -4.033 39.617 55 4/25/16 0 88 101 75");
        test.recordDailySummaries(in());

        WeatherStation station = test.lowestStation();
        assertThat(station).isNotNull(); 
        assertThat(station.getId()).isEqualTo("KE000063612");
    }
}
