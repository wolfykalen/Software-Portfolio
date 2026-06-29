/**
 * Represents a rocket in the ATC system.
 * Extends AirObject with ascent rate and trajectory.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class Rocket extends AirObject {

    /** The ascent rate of the rocket. */
    private int ascentRate;

    /** The trajectory angle of the rocket. */
    private double trajectory;

    /**
     * Constructor for Rocket.
     *
     * @param name       The name of this rocket
     * @param x          The x-coordinate of the origin
     * @param y          The y-coordinate of the origin
     * @param z          The z-coordinate of the origin
     * @param xwid       The width in the x dimension
     * @param ywid       The width in the y dimension
     * @param zwid       The width in the z dimension
     * @param ascentRate The ascent rate
     * @param trajectory The trajectory angle
     */
    public Rocket(String name, int x, int y, int z,
                  int xwid, int ywid, int zwid,
                  int ascentRate, double trajectory) {
        super(name, x, y, z, xwid, ywid, zwid);
        this.ascentRate = ascentRate;
        this.trajectory = trajectory;
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
     * Gets the trajectory.
     *
     * @return The trajectory
     */
    public double getTrajectory() {
        return trajectory;
    }

    /**
     * Returns the type name for this air object.
     *
     * @return "Rocket"
     */
    @Override
    public String getTypeName() {
        return "Rocket";
    }

    /**
     * Returns the type specific fields as a string.
     *
     * @return String containing ascent rate and trajectory
     */
    @Override
    protected String getTypeSpecificString() {
        return " " + ascentRate + " " + trajectory;
    }

    /**
     * Checks if this rocket is valid.
     *
     * @return true if valid
     */
    @Override
    public boolean isValid() {
        return isValidBoundingBox() && ascentRate >= 0 && trajectory >= 0;
    }
}
