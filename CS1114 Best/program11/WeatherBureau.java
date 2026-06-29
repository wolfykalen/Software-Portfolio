import java.util.*;

//-------------------------------------------------------------------------
/**
 *  depicts a WeatherBureau object that records daily
 *  summaries and returns a station that has the 
 *  lowest amount of rain per month.
 *
 *  @author kalendaco
 *  @version 2023.11.28
 */
public class WeatherBureau
{
    //~ Fields ................................................................
    private Map<String, WeatherStation> weatherStations;

    //~ Constructor ...........................................................
    /**
     * Initializes a newly created WeatherBureau object.
     */
    public WeatherBureau()
    {
        weatherStations = new HashMap<>();
    }

    //~ Methods ...............................................................

    /**
     * Records a daily summary statement.
     * 
     * @param input (String) represents the daily summary statement.
     */
    public void recordDailySummary(String input)
    {
        String[] components = input.split("\\s+");

        String stationID = components[0];
        int month = Integer.parseInt(components[4].split("/")[0]);
        double rainfall = Double.parseDouble(components[5]);

        if (rainfall != -1) {
            WeatherStation station = weatherStations.computeIfAbsent(stationID, WeatherStation::new);

            station.recordDailyRain(month, rainfall);
        }
    }

    /**
     * Creates a list of summary statements.
     * 
     * @param input (Scanner) represents the input that contains the daily
     * summary statements.
     */
    public void recordDailySummaries(Scanner input)
    {
        while (input.hasNextLine()) {
            recordDailySummary(input.nextLine());
        }
    }

    /**
     * Returns a weather station that matches the weather
     * station ID.
     * 
     * @param iD (String) ID of a weather station.
     * @return (WeatherStation) depicts a weather station with a
     * matching ID.
     */
    public WeatherStation getStation(String iD)
{
    if (weatherStations.containsKey(iD)) {
        return weatherStations.get(iD);
    } else {
        return null; 
    }
}


    /**
     * Returns a weather station with the lowest average rainfall
     * per specific month, returns null if there's no rainfall.
     * 
     * @param month (int) depicts the month that was recorded.
     * @return (WeatherStation) depicts a weather station that has the
     * lowest average rainfall.
     */
    public WeatherStation lowestStation(int month)
    {
        WeatherStation lowestStation = null;
        double lowestAvg = Double.MAX_VALUE;

        for (WeatherStation station : weatherStations.values()) {
            double avg = station.getAvgForMonth(month);
            if (avg != -1 && avg < lowestAvg) {
                lowestAvg = avg;
                lowestStation = station;
            }
        }

        return lowestStation;
    }

    /**
     * Returns a weather station with the lowest average rainfall
     * no matter the month, returns null when no rainfall was recorded.
     * 
     * @return (WeatherStation) depicts a weather station with the
     * lowest average rainfall.
     */
    public WeatherStation lowestStation()
    {
        WeatherStation lowestStation = null;
        double lowestAvg = Double.MAX_VALUE;

        for (WeatherStation station : weatherStations.values()) {
            double avg = station.getAvgForMonth(station.getLowestMonth());
            if (avg != -1 && avg < lowestAvg) {
                lowestAvg = avg;
                lowestStation = station;
            }
        }

        return lowestStation;
    }
}
