// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those 
//who do.
// -- Your name kalendaco)
package dailymixes;

import java.util.Arrays;

/**
 * A playlist that maintains a collection of songs with genre constraints.
 * represents a playlist with defined minimum and maximum
 * genre percentages for pop, rock, and country music. 
 * 
 * @author kalendaco
 * @version Apr 3, 2025
 */
public class Playlist 
    implements Comparable<Playlist> {
    private GenreSet minGenreSet;
    private GenreSet maxGenreSet;
    private Song[] songs;
    private int capacity;
    private int numberOfSongs;
    private String name;

    /**
     * Creates a new playlist with specified genre constraints and capacity.
     * 
     * @param playlistName the name of the playlist
     * @param minPop minimum percentage of pop genre 
     * @param minRock minimum percentage of rock genre 
     * @param minCountry minimum percentage of country genre 
     * @param maxPop maximum percentage of pop genre 
     * @param maxRock maximum percentage of rock genre 
     * @param maxCountry maximum percentage of country genre 
     * @param playlistCap maximum capacity of the playlist
     * @throws IllegalArgumentException if
     *         - playlist capacity is not positive
     *         - minimum genre percentages exceed maximum genre percentages
     *         - playlist name is null or empty
     */

    public Playlist(String playlistName, int minPop, int minRock, 
        int minCountry, int maxPop, int maxRock, int maxCountry, 
        int playlistCap) 
    {
        if (playlistCap <= 0) 
        {
            throw new IllegalArgumentException("Playlist capacity must be + ");
        }
        if (minPop > maxPop || minRock > maxRock || minCountry > maxCountry) 
        {
            throw new IllegalArgumentException("Min genre % cannot exceed max");
        }
        if (playlistName == null || playlistName.trim().isEmpty()) 
        {
            throw new IllegalArgumentException(" name cant be null or empty");
        }

        name = playlistName.trim();
        capacity = playlistCap;
        numberOfSongs = 0;
        songs = new Song[capacity];
        minGenreSet = new GenreSet(minPop, minRock, minCountry);
        maxGenreSet = new GenreSet(maxPop, maxRock, maxCountry);
    }

    // ----------------------------------------------------------
    /**
     * Returns the minimum genre set  
     * 
     * @return the minimum genre set for this playlist
     */
    public GenreSet getMinGenreSet() 
    {
        return minGenreSet;
    }

    // ----------------------------------------------------------
    /**
     * Returns the maximum genre set 
     * 
     * @return the maximum genre set for this playlist
     */
    public GenreSet getMaxGenreSet() 
    {
        return maxGenreSet;
    }

    // ----------------------------------------------------------
    /**
     * Returns the name of this playlist.
     * 
     * @return the name of the playlist
     */
    public String getName() 
    {
        return name;
    }

    // ----------------------------------------------------------
    /**
     * Sets the name of this playlist.
     * 
     * @param newName the new name for the playlist
     * @throws IllegalArgumentException if newName is null or empty
     */
    public void setName(String newName) 
    {
        if (newName == null || newName.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        name = newName.trim();
    }

    // ----------------------------------------------------------
    /**
     * Returns a copy of the songs currently in the playlist.
     * 
     * @return an array containing the songs in the playlist
     */
    public Song[] getSongs() 
    {
        return Arrays.copyOf(songs, numberOfSongs);
    }

    // ----------------------------------------------------------
    /**
     * Returns the maximum capacity of this playlist.
     * 
     * @return the maximum number of songs this playlist can hold
     */
    public int getCapacity() 
    {
        return capacity;
    }

    // ----------------------------------------------------------
    /**
     * Returns the current number of songs in the playlist.
     * 
     * @return the number of songs currently in the playlist
     */
    public int getNumberOfSongs() 
    {
        return numberOfSongs;
    }

    // ----------------------------------------------------------
    /**
     * Returns the number of available spaces left in the playlist.
     * 
     * @return the number of empty slots available for adding new songs
     */
    public int getSpacesLeft() 
    {
        return capacity - numberOfSongs;
    }

    // ----------------------------------------------------------
    /**
     * Checks if the playlist is full.
     * 
     * @return true if the playlist has reached its maximum capacity
     */
    public boolean isFull() 
    {
        return numberOfSongs >= capacity;
    }

    /**
     * Checks if a song meets the playlists genre requirements.
     * 
     * @param song the song to be checked
     * @return true if the songs genre percentages are within the playlist's
     *         minimum and maximum genre constraints
     */
    public boolean isQualified(Song song) 
    {
        return song.getGenreSet().isWithinRange(minGenreSet, maxGenreSet);
    }

    // ----------------------------------------------------------
    /**
     * Checks if a song meets the playlists genre requirements.
     * 
     * @param newSong the new song to be added
     * @return true if the songs genre percentages are within the playlist's
     *         minimum and maximum genre constraints
     */
    public boolean addSong(Song newSong) 
    {
        if (isFull()) 
        {
            return false;
        }
        if (!isQualified(newSong)) 
        {
            return false;
        }
        songs[numberOfSongs] = newSong;
        numberOfSongs++;
        return true;
    }

    /**
     * Returns a string representation of the playlist.
     * 
     * @return a string representation of the playlist
     */
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist: ").append(name)
        .append(" # of songs: ").append(numberOfSongs)
        .append(" (cap: ").append(capacity)
        .append(") Requires: ").append(minGenreSet)
        .append(" - ").append(maxGenreSet);
        return sb.toString();
    }

    /**
     * Compares this playlist with another for equality.
     * 
     * @param obj the object to compare with
     * @return true if the object is a Playlist with the same properties
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
        Playlist other = (Playlist) obj;
        return capacity == other.capacity &&
            numberOfSongs == other.numberOfSongs &&
            name.equals(other.name) &&
            minGenreSet.equals(other.minGenreSet) &&
            maxGenreSet.equals(other.maxGenreSet) &&
            Arrays.equals(songs, other.songs);
    }

    /**
     * Compares this playlist with another playlist based on multiple criteria.
     * 
     * @param other the playlist to compare with
     * @return a negative integer, zero, or a positive integer 
     * depending on the specified playlist and the comparison criteria
     */
    @Override
    public int compareTo(Playlist other) 
    {
        // Compare by capacity
        if (capacity != other.capacity) 
        {
            return Integer.compare(capacity, other.capacity);
        }

        // Compare by spaces left
        int spacesLeft = getSpacesLeft();
        int otherSpacesLeft = other.getSpacesLeft();
        if (spacesLeft != otherSpacesLeft) 
        {
            return Integer.compare(spacesLeft, otherSpacesLeft);
        }

        // Compare by minGenreSet
        int minGenreCompare = minGenreSet.compareTo(other.minGenreSet);
        if (minGenreCompare != 0) 
        {
            return minGenreCompare;
        }

        // Compare by maxGenreSet
        int maxGenreCompare = maxGenreSet.compareTo(other.maxGenreSet);
        if (maxGenreCompare != 0) 
        {
            return maxGenreCompare;
        }

        // Compare by name
        return name.compareTo(other.name);
    }
}