
/**
 * Represents an immutable city with a name and 2D integer coordinates.
 * This class is used to store and retrieve city information including
 * the city's name and its position in a 2D coordinate system.
 * 
 * @author Kalendaco
 * @version 1.0
 */
public class City {
    // The name of the city 
    private final String name;
    
    // The x-coordinate of the city's position 
    private final int x;
    
    // The y-coordinate of the city's position 
    private final int y;

    /**
     * Constructs a new City with the specified name and coordinates.
     * 
     * @param name the name of the city (must not be null or empty)
     * @param x    the x-coordinate of the city's position
     * @param y    the y-coordinate of the city's position
     * @throws IllegalArgumentException if name is null or empty
     */
    public City(String name, int x, int y) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "City name cannot be null or empty");
        }
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the name of the city.
     * 
     * @return the city's name, never null or empty
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the x-coordinate of the city's position.
     * 
     * @return the x-coordinate value
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the city's position.
     * 
     * @return the y-coordinate value
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if two cities have the same location.
     * 
     * @param ox the x-coordinate of the other city
     * @param oy the y-coordinate of the other city
     * @return true if both cities have the same coordinates, false otherwise
     */
    public boolean sameLocation(int ox, int oy) {
        return this.x == ox && this.y == oy;
    }

    /**
     * Returns a string representation of the city formatted as "Name (x, y)".
     * 
     * @return a string representation of the city
     */
    @Override
    public String toString() {
        return name + " (" + x + ", " + y + ")";
    }
}
