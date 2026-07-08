/**
 * Represents a balloon in the ATC system.
 * Extends AirObject with type and ascent rate.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class Balloon extends AirObject {

    /** The type of balloon (e.g., "hot_air"). */
    private String type;

    /** The ascent rate of the balloon. */
    private int ascentRate;

    /**
     * Constructor for Balloon.
     *
     * @param name       The name of this balloon
     * @param x          The x-coordinate of the origin
     * @param y          The y-coordinate of the origin
     * @param z          The z-coordinate of the origin
     * @param xwid       The width in the x dimension
     * @param ywid       The width in the y dimension
     * @param zwid       The width in the z dimension
     * @param type       The type of balloon
     * @param ascentRate The ascent rate
     */
    public Balloon(String name, int x, int y, int z,
                   int xwid, int ywid, int zwid,
                   String type, int ascentRate) {
        super(name, x, y, z, xwid, ywid, zwid);
        this.type = type;
        this.ascentRate = ascentRate;
    }

    /**
     * Gets the balloon type.
     *
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the ascent rate.
     *
     * @return The ascent rate
     */
    public int getAscentRate() {
        return ascentRate;
    }

    /**
     * Returns the type name for this air object.
     *
     * @return "Balloon"
     */
    @Override
    public String getTypeName() {
        return "Balloon";
    }

    /**
     * Returns the type-specific fields as a string.
     *
     * @return String containing type and ascent rate
     */
    @Override
    protected String getTypeSpecificString() {
        return " " + type + " " + ascentRate;
    }

    /**
     * Checks if this balloon is valid.
     * Type must not be null, ascent rate must be >= 0.
     *
     * @return true if valid
     */
    @Override
    public boolean isValid() {
        return isValidBoundingBox() && type != null && ascentRate >= 0;
    }
}
