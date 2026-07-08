
//-------------------------------------------------------------------------
/**
 *  depicts a Weather Station that reads rainfall
 *  finds the avg rainfall per month 
 *  and the month with the least rainfall
 *
 *  @author kalendaco
 *  @version 2023.11.28
 */
public class WeatherStation
{
    //~ Fields ................................................................
    private String id;
    private int[] monthlyRainfall;
    private int[] monthlyDailyRainCounts;
    //~ Constructor ...........................................................
    /**
     * Initializes a newly created WeatherStation object.
     * 
     * @param identifier (String) depicts a Station ID for 
     * a weather station.
     */
    public WeatherStation(String identifier)
    {
        super();
        id = identifier;
        monthlyRainfall = new int[12];
        monthlyDailyRainCounts = new int[12];
    }
    //~ Methods ...............................................................
    /**
     * A getter method returns an ID of the weather station.
     * 
     * @return (String) depicts an ID of the weather station.
     */
    public String getId()
    {
        return id;
    }

    /**
     * records daily summarys by amount of rainfall
     *  and increasing amount of rain in that month.
     * 
     * @param month (int) represents the month the rainfall occurred in.
     * @param rainfall (double) represents the amount of rainfall that day.
     */
    public void recordDailyRain(int month, double rainfall)
    {
        monthlyRainfall[month] += rainfall;
        monthlyDailyRainCounts[month]++;
    }

    /**
     *  getter method returns the amount of days it rained in a month 
     * 
     * @param month (int) depicts the amount of days rained in a month
     * @return (int) depicts a months rain  couint
     */
    public int getCountForMonth(int month)
    {
        return monthlyDailyRainCounts[month];
    }

    /**
     * returns the average rainfall per day in a given month 
     * @param month (int) depicts the month you want to see the avg rainfall
     * 
     * @return (double) depicts the average rainfall per day over the month
     */
    public double getAvgForMonth(int month)
    {
        if (monthlyDailyRainCounts[month] == 0)
        {
            return -1;
        }
        return (double) monthlyRainfall[month] / 
        monthlyDailyRainCounts[month];
    }

    /**
     * returns the month with the lowest rain per day
     * 
     * @return (int) depicts the month with the lowest avg rain fall (1-12)
     */
    public int getLowestMonth()
    {
        int lowestMonth = 1;
        for (int i = 1; i < monthlyRainfall.length; i++)
        {
            if (this.getAvgForMonth(i) < this.getAvgForMonth(lowestMonth))
            {
                lowestMonth = i;
            }
        }
        return lowestMonth;
    }
}
