/**
 * Base class for all air objects in the ATC system.
 * Stores common 3D bounding box information and name.
 * Implements Comparable for use in SkipList (compared by name).
 *
 * @author {Kalendaco}
 * @version {1}
 */
public abstract class AirObject implements Comparable<AirObject> {

    /** The name identifier for this air object. */
    private String name;

    /** The x-coordinate of the origin. */
    private int xOrig;

    /** The y-coordinate of the origin. */
    private int yOrig;

    /** The z-coordinate of the origin. */
    private int zOrig;

    /** The width in the x dimension. */
    private int xWidth;

    /** The width in the y dimension. */
    private int yWidth;

    /** The width in the z dimension. */
    private int zWidth;

    /**
     * Constructor for AirObject.
     *
     * @param name  The name of this air object
     * @param x     The x-coordinate of the origin
     * @param y     The y-coordinate of the origin
     * @param z     The z-coordinate of the origin
     * @param xwid  The width in the x dimension
     * @param ywid  The width in the y dimension
     * @param zwid  The width in the z dimension
     */
    public AirObject(String name, int x, int y, int z,
                     int xwid, int ywid, int zwid) {
        this.name = name;
        this.xOrig = x;
        this.yOrig = y;
        this.zOrig = z;
        this.xWidth = xwid;
        this.yWidth = ywid;
        this.zWidth = zwid;
    }

    /**
     * Gets the x-coordinate of the origin.
     *
     * @return The x-coordinate
     */
    public int getXorig() {
        return xOrig;
    }

    /**
     * Gets the y-coordinate of the origin.
     *
     * @return The y-coordinate
     */
    public int getYorig() {
        return yOrig;
    }

    /**
     * Gets the z-coordinate of the origin.
     *
     * @return The z-coordinate
     */
    public int getZorig() {
        return zOrig;
    }

    /**
     * Gets the width in the x dimension.
     *
     * @return The x width
     */
    public int getXwidth() {
        return xWidth;
    }

    /**
     * Gets the width in the y dimension.
     *
     * @return The y width
     */
    public int getYwidth() {
        return yWidth;
    }

    /**
     * Gets the width in the z dimension.
     *
     * @return The z width
     */
    public int getZwidth() {
        return zWidth;
    }

    /**
     * Gets the name of this air object.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Compares this AirObject to another based on name.
     * Used for SkipList ordering.
     *
     * @param other The other AirObject to compare to
     * @return Negative if this < other, 0 if equal, positive if this > other
     */
    @Override
    public int compareTo(AirObject other) {
        return this.name.compareTo(other.name);
    }

    /**
     * Checks if this air object's bounding box is valid.
     * Coordinates must be in range [0, 1023].
     * Widths must be in range [1, 1024].
     * The box must fit within the world.
     *
     * @return true if the bounding box is valid
     */
    public boolean isValidBoundingBox() {
        return name != null && !name.isEmpty()
            && xOrig >= 0 && xOrig <= 1023
            && yOrig >= 0 && yOrig <= 1023
            && zOrig >= 0 && zOrig <= 1023
            && xWidth >= 1 && xWidth <= 1024
            && yWidth >= 1 && yWidth <= 1024
            && zWidth >= 1 && zWidth <= 1024
            && xOrig + xWidth <= 1024
            && yOrig + yWidth <= 1024
            && zOrig + zWidth <= 1024;
    }

    /**
     * Checks if this air object is valid.
     * Subclasses should override to add type specific validation.
     *
     * @return true if the air object is valid
     */
    public abstract boolean isValid();

    /**
     * Returns the type name of this air object.
     *
     * @return The type name
     */
    public abstract String getTypeName();

    /**
     * Returns the type-specific fields as a string for toString().
     *
     * @return The type-specific fields
     */
    protected abstract String getTypeSpecificString();

    /**
     * Returns a string representation of this air object.
     *
     * @return The string representation
     */
    @Override
    public String toString() {
        return getTypeName() + " " + name + " " +
               xOrig + " " + yOrig + " " + zOrig + " " +
               xWidth + " " + yWidth + " " + zWidth +
               getTypeSpecificString();
    }
}
