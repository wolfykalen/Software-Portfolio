/**
 * Represents a bird (or flock of birds) in the ATC system.
 * Extends AirObject with type and number.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class Bird extends AirObject {

    /** The type/species of bird. */
    private String type;

    /** The number of birds in the flock. */
    private int number;

    /**
     * Constructor for Bird.
     *
     * @param name   The name of this bird/flock
     * @param x      The x-coordinate of the origin
     * @param y      The y-coordinate of the origin
     * @param z      The z-coordinate of the origin
     * @param xwid   The width in the x dimension
     * @param ywid   The width in the y dimension
     * @param zwid   The width in the z dimension
     * @param type   The type/species of bird
     * @param number The number of birds
     */
    public Bird(String name, int x, int y, int z,
                int xwid, int ywid, int zwid,
                String type, int number) {
        super(name, x, y, z, xwid, ywid, zwid);
        this.type = type;
        this.number = number;
    }

    /**
     * Gets the bird type.
     *
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the number of birds.
     *
     * @return The number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the type name for this air object.
     *
     * @return "Bird"
     */
    @Override
    public String getTypeName() {
        return "Bird";
    }

    /**
     * Returns the type-specific fields as a string.
     *
     * @return String containing type and number
     */
    @Override
    protected String getTypeSpecificString() {
        return " " + type + " " + number;
    }

    /**
     * Checks if this bird is valid.
     * Type must not be null, number must be > 0.
     *
     * @return true if valid
     */
    @Override
    public boolean isValid() {
        return isValidBoundingBox() && type != null && number > 0;
    }
}
