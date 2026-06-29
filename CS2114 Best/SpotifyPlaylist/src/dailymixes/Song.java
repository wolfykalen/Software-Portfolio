// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those 
//who do.
// -- Your name kalendaco)
package dailymixes;

/**
 * Represents a song with its name, genre composition, and suggested playlist.
 * Songs can be added to playlists based on their genre composition 
 * 
 * @author kalendaco
 * @version Apr 3, 2025
 */
public class Song 
{
    private final String name;
    private final GenreSet genreSet;
    private final String suggestedPlaylist;

    /**
     * Creates a new Song with the specified details.
     * 
     * @param name the name of the song
     * @param pop  the percentage of pop composition 
     * @param rock the percentage of rock composition 
     * @param country the percentage of country composition 
     * @param suggestedPlaylist the suggested playlist for the song
     * @throws IllegalArgumentException if any condition is met
     *         - song name is null or empty
     *         - any genre percentage is not between 0 and 100
     */
    public Song(String name, int pop, int rock, int country, 
        String suggestedPlaylist) 
    {
        if (name == null || name.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        
        this.name = name.trim();
        this.genreSet = new GenreSet(pop, rock, country);
        this.suggestedPlaylist = suggestedPlaylist != null ? 
            suggestedPlaylist.trim() : "";
    }

    /**
     * Gets the name of the song.
     * 
     * @return the songs name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Gets the genre composition of the song.
     * 
     * @return the GenreSet representing the songs genre composition
     */
    public GenreSet getGenreSet() 
    {
        return genreSet;
    }

    /**
     * Gets the suggested playlist for this song
     * 
     * @return the suggested playlist name or empty string if none
     */
    public String getSuggestedPlaylist() 
    {
        return suggestedPlaylist;
    }

    /**
     * Gets the name of the suggested playlist for this song
     * 
     * @return the suggested playlist name or empty string if none
     */
    public String getPlaylistName() 
    {
        return suggestedPlaylist;
    }
    
    /**
     * Returns a string representation of the song.
     * 
     * @return a string representation of the song
     */
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        
        if (suggestedPlaylist.isEmpty()) 
        {
            sb.append("No-Playlist ");
        }
        
        sb.append(name)
          .append(" Pop:")
          .append(genreSet.getPop())
          .append(" Rock:")
          .append(genreSet.getRock())
          .append(" Country:")
            .append(genreSet.getCountry());
        
        if (!suggestedPlaylist.isEmpty()) 
        {
            sb.append(" Suggested: ").append(suggestedPlaylist);
        }
        
        return sb.toString();
    }
    
    /**
     * Compares this song with another for equality.
     * 
     * @param obj the object to compare with
     * @return true if the object is a Song with the same properties
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
        Song other = (Song) obj;
        return name.equals(other.name) && 
               genreSet.equals(other.genreSet) && 
               suggestedPlaylist.equals(other.suggestedPlaylist);
    }
}