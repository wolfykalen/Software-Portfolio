/**
 * Represents a drone in the ATC system.
 * Extends AirObject with brand and engine count.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class Drone extends AirObject {

    /** The brand of the drone. */
    private String brand;

    /** The number of engines/motors. */
    private int numEngines;

    /**
     * Constructor for Drone.
     *
     * @param name       The name of this drone
     * @param x          The x-coordinate of the origin
     * @param y          The y-coordinate of the origin
     * @param z          The z-coordinate of the origin
     * @param xwid       The width in the x dimension
     * @param ywid       The width in the y dimension
     * @param zwid       The width in the z dimension
     * @param brand      The brand of the drone
     * @param numEngines The number of engines
     */
    public Drone(String name, int x, int y, int z,
                 int xwid, int ywid, int zwid,
                 String brand, int numEngines) {
        super(name, x, y, z, xwid, ywid, zwid);
        this.brand = brand;
        this.numEngines = numEngines;
    }

    /**
     * Gets the brand.
     *
     * @return The brand
     */
    public String getBrand() {
        return brand;
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
     * @return "Drone"
     */
    @Override
    public String getTypeName() {
        return "Drone";
    }

    /**
     * Returns the type specific fields as a string.
     *
     * @return String containing brand and engine count
     */
    @Override
    protected String getTypeSpecificString() {
        return " " + brand + " " + numEngines;
    }

    /**
     * Checks if this drone is valid.
     * Brand must not be null, engine count must be > 0.
     *
     * @return true if valid
     */
    @Override
    public boolean isValid() {
        return isValidBoundingBox() && brand != null && numEngines > 0;
    }
}
