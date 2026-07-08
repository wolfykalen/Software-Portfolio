// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those 
//who do.
// -- Your name kalendaco)
package dailymixes;

/**
 * Represents a set of genre percentages for a song or playlist.
 * Each genre percentage is represented as an integer between 0 and 100.
 * 
 * @author kalendaco
 * @version Apr 3, 2025
 */
public class GenreSet 
    implements Comparable<GenreSet> 
{
    private final int pop;
    private final int rock;
    private final int country;

    /**
     * Creates a new GenreSet with the specified genre percentages.
     * 
     * @param pop the percentage of pop composition 
     * @param rock the percentage of rock composition 
     * @param country the percentage of country composition 
     */
    public GenreSet(int pop, int rock, int country) 
    {
        if (pop < 0 || pop > 100 || rock < 0 || rock > 100 || 
            country < 0 || country > 100) 
        {
            throw new IllegalArgumentException("Genre percentage "
                + "must be between 0 and 100");
        }
        this.pop = pop;
        this.rock = rock;
        this.country = country;
    }

    /**
     * Checks if this GenreSets values are less than or equal to another.
     * 
     * @param other the GenreSet to compare against
     * @return true if all genre percentages are <= to the others
     */
    public boolean isLessThanOrEqualTo(GenreSet other) 
    {
        if (other == null) 
        {
            throw new IllegalArgumentException("Other GenreSet cannot be null");
        }
        return this.pop <= other.pop && 
            this.rock <= other.rock && 
            this.country <= other.country;
    }

    /**
     * Checks if this GenreSet's values are within the specified range.
     * 
     * @param minGenreSet the minimum allowed GenreSet
     * @param maxGenreSet the maximum allowed GenreSet
     * @return true if all genre percentages are within the specified range
     */
    public boolean isWithinRange(GenreSet minGenreSet, GenreSet maxGenreSet) {
        if (minGenreSet == null || maxGenreSet == null) {
            throw new IllegalArgumentException("GenreSet cant be null");
        }
        return isLessThanOrEqualTo(maxGenreSet) && 
            minGenreSet.isLessThanOrEqualTo(this);
    }

    /**
     * Returns the percentage of pop composition.
     * 
     * @return the percentage of pop composition
     */
    public int getPop() 
    {
        return pop;
    }

    /**
     * Returns the percentage of rock composition.
     * 
     * @return the percentage of rock composition
     */
    public int getRock() 
    {
        return rock;
    }

    /**
     * Returns the percentage of country composition.
     * 
     * @return the percentage of country composition
     */
    public int getCountry() 
    {
        return country;
    }

    /**
     * Compares this GenreSet with another for equality.
     * based on each genre percentages.
     * 
     * @param obj the object to compare with
     * @return true if the object is a GenreSet with the same genre percentages
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) 
        {
            return false;
        }
        GenreSet other = (GenreSet) obj;
        return pop == other.pop && rock == other.rock && 
            country == other.country;
    }

    /**
     * Compares this GenreSet with another based on genre percentages 
     * 
     * @param other the GenreSet to compare with
     * @return a negative integer, zero, or a positive integer 
     * @throws IllegalArgumentException if the other GenreSet is null
     */
    @Override
    public int compareTo(GenreSet other) 
    {
        if (other == null) 
        {
            throw new IllegalArgumentException("Other GenreSet cannot be null");
        }
        
        int popCompare = Integer.compare(this.pop, other.pop);
        if (popCompare != 0) 
        {
            return popCompare;
        }
        
        int rockCompare = Integer.compare(this.rock, other.rock);
        if (rockCompare != 0) 
        {
            return rockCompare;
        }
        
        return Integer.compare(this.country, other.country);
    }

    /**
     * Returns a string representation of this GenreSet.
     * 
     * @return a string representation of this GenreSet
     */
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Pop:").append(pop)
        .append(" Rock:").append(rock)
        .append(" Country:").append(country);
        return sb.toString();
    }
}