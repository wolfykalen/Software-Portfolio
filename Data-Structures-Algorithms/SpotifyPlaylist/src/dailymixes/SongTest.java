// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those 
//who do.
// -- Your name kalendaco)
package dailymixes;

import static org.junit.Assert.*;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 * A test class for the Song class
 * tests the correctness of operations in the Song class
 * 
 * @author kalendaco
 * @version Apr 3, 2025
 */
public class SongTest 
{
    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws an IllegalArgumentException when
     * provided with a null song name.
     */
    @Test
    public void testConstructorNullName() 
    {
        try 
        {
            new Song(null, 30, 40, 30, "Daily Mix 1");
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            // Expected exception
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws an IllegalArgumentException when
     * provided with an empty song name.
     */
    @Test
    public void testConstructorEmptyName() 
    {
        try 
        {
            new Song("", 30, 40, 30, "Daily Mix 1");
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            // Expected exception
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws an IllegalArgumentException when
     * provided with invalid genre percentages 
     */
    @Test
    public void testConstructorInvalidPercentages() 
    {
        try 
        {
            new Song("Test Song", -1, 40, 30, "Daily Mix 1");
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            // Expected exception
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the constructor with valid parameters verifies all
     * song properties are correctly initialized 
     */
    @Test
    public void testConstructorValid() 
    {
        Song song = new Song(" Test Song ", 30, 40, 30, " Daily Mix 1 ");
        assertEquals("Test Song", song.getName());
        GenreSet genreSet = song.getGenreSet();
        assertEquals(30, genreSet.getPop());
        assertEquals(40, genreSet.getRock());
        assertEquals(30, genreSet.getCountry());
        assertEquals("Daily Mix 1", song.getSuggestedPlaylist());
    }


    // ----------------------------------------------------------
    /**
     * Tests the toString method verifying that it correctly formats
     * the song information 
     */
    @Test
    public void testToString() 
    {
        Song song = new Song("Test Song", 30, 40, 30, "Daily Mix 1");
        assertEquals("Test Song Pop:30 Rock:40 Country:30 Suggested: "
            + "Daily Mix 1", song.toString());

        Song songNoPlaylist = new Song("Test Song", 30, 40, 30, "");
        assertEquals("No-Playlist Test Song Pop:30 Rock:40 Country:30", 
            songNoPlaylist.toString());
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method verifying that it correctly compares songs
     */
    @Test
    public void testEquals() 
    {
        Song song1 = new Song("Test Song", 30, 40, 30, "Daily Mix 1");
        Song song2 = new Song("Test Song", 30, 40, 30, "Daily Mix 1");
        Song song3 = new Song("Different Song", 30, 40, 30, "Daily Mix 1");
        Song song4 = new Song("Test Song", 40, 30, 30, "Daily Mix 1");
        Song song5 = new Song("Test Song", 30, 40, 30, "Daily Mix 2");

        assertTrue(song1.equals(song2));
        assertFalse(song1.equals(song3)); 
        assertFalse(song1.equals(song4)); 
        assertFalse(song1.equals(song5)); 
        assertFalse(song1.equals(null));
        assertFalse(song1.equals(new Object()));
        assertTrue(song1.equals(song1)); 
    }

    // ----------------------------------------------------------
    /**
     * Tests the getGenreSet method verifies that it correctly returns
     * the song's genre composition.
     */
    @Test
    public void testGetGenreSet() 
    {
        Song song = new Song("Test Song", 30, 40, 30, "Daily Mix 1");
        GenreSet genreSet = song.getGenreSet();
        assertEquals(30, genreSet.getPop());
        assertEquals(40, genreSet.getRock());
        assertEquals(30, genreSet.getCountry());
    }

    // ----------------------------------------------------------
    /**
     * Tests the getName method verifies it correctly returns
     * the songs name
     */
    @Test
    public void testGetName() 
    {
        Song song = new Song(" Test Song ", 30, 40, 30, "Daily Mix 1");
        assertEquals("Test Song", song.getName());
    }

    // ----------------------------------------------------------
    /**
     * Tests the getSuggestedPlaylist method verifies that it correctly
     * returns the suggested playlist name 
     */
    @Test
    public void testGetSuggestedPlaylist() 
    {
        Song song = new Song("Test Song", 30, 40, 30, "Daily Mix 1");
        assertEquals("Daily Mix 1", song.getSuggestedPlaylist());

        Song songNoPlaylist = new Song("Test Song", 30, 40, 30, "");
        assertEquals("", songNoPlaylist.getSuggestedPlaylist());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests that suggestedPlaylist is set to empty string when null is passed.
     */
    @Test
    public void testSuggestedPlaylistNull() 
    {
        Song song = new Song("Test Song", 10, 20, 30, null);
        assertEquals("", song.getSuggestedPlaylist());
    }

    // ----------------------------------------------------------
    /**
     * Tests that suggestedPlaylist is trimmed when a non-null value is passed.
     */
    @Test
    public void testSuggestedPlaylistTrimmed() 
    {
        Song song = new Song("Test Song", 10, 20, 30, "  My Playlist  ");
        assertEquals("My Playlist", song.getSuggestedPlaylist());
    }
}
