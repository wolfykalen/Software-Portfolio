/**
 * Represents an airplane in the ATC system.
 * Extends AirObject with carrier, flight number, and engine count.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class AirPlane extends AirObject {

    /** The airline carrier name. */
    private String carrier;

    /** The flight number. */
    private int flightNum;

    /** The number of engines. */
    private int numEngines;

    /**
     * Constructor for AirPlane.
     *
     * @param name       The name of this airplane
     * @param x          The x-coordinate of the origin
     * @param y          The y-coordinate of the origin
     * @param z          The z-coordinate of the origin
     * @param xwid       The width in the x dimension
     * @param ywid       The width in the y dimension
     * @param zwid       The width in the z dimension
     * @param carrier    The airline carrier name
     * @param flightNum  The flight number
     * @param numEngines The number of engines
     */
    public AirPlane(String name, int x, int y, int z,
                    int xwid, int ywid, int zwid,
                    String carrier, int flightNum, int numEngines) {
        super(name, x, y, z, xwid, ywid, zwid);
        this.carrier = carrier;
        this.flightNum = flightNum;
        this.numEngines = numEngines;
    }

    /**
     * Gets the carrier name.
     *
     * @return The carrier name
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Gets the flight number.
     *
     * @return The flight number
     */
    public int getFlightNum() {
        return flightNum;
    }

    /**
     * Gets the number of engines.
     *
     * @return The number of engines
     */
    public int getNumEngines() {
        return numEngines;
    }

    /**
     * Returns the type name for this air object.
     *
     * @return "Airplane"
     */
    @Override
    public String getTypeName() {
        return "Airplane";
    }

    /**
     * Returns the type-specific fields as a string.
     *
     * @return String containing carrier, flight number, and engine count
     */
    @Override
    protected String getTypeSpecificString() {
        return " " + carrier + " " + flightNum + " " + numEngines;
    }

    /**
     * Checks if this airplane is valid.
     * Carrier must not be null, flight number and engine count must be > 0.
     *
     * @return true if valid
     */
    @Override
    public boolean isValid() {
        return isValidBoundingBox() && carrier != null
            && flightNum > 0 && numEngines > 0;
    }
}
