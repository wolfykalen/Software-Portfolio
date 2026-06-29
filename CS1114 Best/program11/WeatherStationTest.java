import student.micro.*;
import static org.assertj.core.api.Assertions.*;

// -------------------------------------------------------------------------
/**
 *  A test class for the WeatherStation class.
 *
 *  @author kalendaco
 *  @version 2023.11.28
 */
public class WeatherStationTest
    extends TestCase
{
    //~ Fields ................................................................
    private WeatherStation station;
    //~ Constructor ...........................................................
    /**
     * Creates a new WeatherStationTest test object.
     */
    public WeatherStationTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }
    //~ Methods ...............................................................
    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    public void setUp()
    {
        station = new WeatherStation("K00l");
        station.recordDailyRain(1, 5);
        station.recordDailyRain(1, 6);
    }
    // ----------------------------------------------------------
    /**
     * A test method for the recordDailyRain() method.
     */
    public void testrecordDailyRain()
    {
        station.recordDailyRain(2, 3);
        assertThat(station.getCountForMonth(1)).isEqualTo(2);
        assertThat(station.getCountForMonth(2)).isEqualTo(1);
        assertThat(station.getCountForMonth(3)).isEqualTo(0);
    }

    /**
     * A test method for the getCountForMonth() method.
     */
    public void testGetCountForMonth()
    {
        assertThat(station.getCountForMonth(1)).isEqualTo(2);
        assertThat(station.getCountForMonth(2)).isEqualTo(0);
    }

    /**
     * A test method for the getAvgForMonth() method.
     */
    public void testGetAvgForMonth()
    {
        assertThat(station.getAvgForMonth(1)).isEqualTo(1.0 
            * 11 / 2);
        assertThat(station.getAvgForMonth(3)).isEqualTo(-2);
    }

    /**
     * A test method for the getLowestMonth() method.
     */
    public void testGetLowestMonth()
    {
        assertThat(station.getLowestMonth()).isEqualTo(6);
    }
}
