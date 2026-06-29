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
 * A test class for the Playlist class.
 * tests for the correctness of operations in the Playlist class 
 * 
 * @author kalendaco
 * @version Apr 3, 2025
 */
public class PlaylistTest 
{
    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws an IllegalArgumentException when
     * provided with a null playlist name.
     */
    @Test
    public void testConstructorNullName() 
    {
        try 
        {
            new Playlist(null, 0, 0, 0, 100, 100, 100, 10);
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
     * provided with an empty playlist name.
     */
    @Test
    public void testConstructorEmptyName() 
    {
        try 
        {
            new Playlist("", 0, 0, 0, 100, 100, 100, 10);
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
     * provided with a negative playlist capacity.
     */
    @Test
    public void testConstructorNegativeCapacity() 
    {
        try 
        {
            new Playlist("Test", 0, 0, 0, 100, 100, 100, -1);
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
     * provided with invalid genre ranges
     */
    @Test
    public void testConstructorInvalidGenreRange() 
    {
        try 
        {

            new Playlist("Test", 60, 0, 0, 50, 100, 100, 10);
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            // Expected exception
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the constructor with valid parameters verifies that
     * playlist properties are correctly initialized 
     */
    @Test
    public void testConstructorValid() 
    {
        Playlist playlist = new Playlist(
            "Daily Mix 1", 20, 30, 40, 
            40, 50, 60, 5);
        assertEquals("Daily Mix 1", playlist.getName());
        assertEquals(20, playlist.getMinGenreSet().getPop());
        assertEquals(30, playlist.getMinGenreSet().getRock());
        assertEquals(40, playlist.getMinGenreSet().getCountry());
        assertEquals(40, playlist.getMaxGenreSet().getPop());
        assertEquals(50, playlist.getMaxGenreSet().getRock());
        assertEquals(60, playlist.getMaxGenreSet().getCountry());
        assertEquals(5, playlist.getCapacity());
        assertEquals(0, playlist.getNumberOfSongs());
        assertEquals(5, playlist.getSpacesLeft());
        assertFalse(playlist.isFull());
    }

    // ----------------------------------------------------------
    /**
     * Tests the toString method verifies it correctly formats
     * the playlist information 
     */
    @Test
    public void testToString() 
    {
        Playlist playlist = new Playlist(
            "Daily Mix 1", 20, 30, 40, 
            40, 50, 60, 5);
        assertEquals(
            "Playlist: Daily Mix 1 # of songs: 0 (cap: 5) " 
                + "Requires: Pop:20 Rock:30 "
                + "Country:40 - Pop:40 Rock:50 Country:60", 
                playlist.toString());
    }

    // ----------------------------------------------------------
    /**
     * Tests the compareTo method, verifying that it correctly compares
     * playlists 
     */
    @Test
    public void testCompareTo() 
    {
        Playlist playlist1 = new Playlist("A", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist2 = new Playlist("B", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist3 = new Playlist("A", 0, 0, 0, 100, 100, 100, 11);
        Playlist playlist4 = new Playlist("A", 0, 0, 0, 100, 100, 100, 10);

        assertTrue(playlist3.compareTo(playlist1) > 0); 
        assertTrue(playlist1.compareTo(playlist2) < 0); 
        assertEquals(0, playlist1.compareTo(playlist4)); 
    }

    // ----------------------------------------------------------
    /**
     * Tests the addSong method
     */
    @Test
    public void testAddSong() 
    {
        Playlist playlist = new Playlist("Test", 30, 40, 30, 50, 60, 50, 3);
        Song song1 = new Song("Song1", 40, 50, 40, "Test");
        Song song2 = new Song("Song2", 35, 55, 35, "Test");
        Song song3 = new Song("Song3", 25, 65, 45, "Test"); 

        assertTrue(playlist.addSong(song1));
        assertTrue(playlist.addSong(song2));
        assertFalse(playlist.addSong(song3)); 

        assertEquals(2, playlist.getNumberOfSongs());
        assertEquals(1, playlist.getSpacesLeft());
    }

    // ----------------------------------------------------------
    /**
     * Tests the isFull method verifying it correctly tracks
     * playlist capacity and prevents adding songs when full.
     */
    @Test
    public void testIsFull() 
    {
        Playlist playlist = new Playlist("Test", 0, 0, 0, 100, 100, 100, 2);
        Song song1 = new Song("Song1", 50, 50, 50, playlist.getName());
        Song song2 = new Song("Song2", 50, 50, 50, playlist.getName());

        assertFalse(playlist.isFull());
        assertTrue(playlist.addSong(song1));
        assertFalse(playlist.isFull());
        assertTrue(playlist.addSong(song2));
        assertTrue(playlist.isFull());
        assertFalse(playlist.addSong(new Song("Song3", 50, 50, 50, 
            playlist.getName())));
    }

    // ----------------------------------------------------------
    /**
     * Tests the getSongs method verifies it correctly returns
     * a copy of the playlist's songs array with the correct number
     * of songs.
     */
    @Test
    public void testGetSongs() 
    {
        Playlist playlist = new Playlist("Test", 0, 0, 0, 100, 100, 100, 3);
        Song song1 = new Song("Song1", 50, 50, 50, "Test");
        Song song2 = new Song("Song2", 50, 50, 50, "Test");

        assertTrue(playlist.addSong(song1));
        assertTrue(playlist.addSong(song2));

        Song[] songs = playlist.getSongs();
        assertEquals(2, songs.length);
        assertEquals(song1, songs[0]);
        assertEquals(song2, songs[1]);
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method, verifies that it correctly compares
     * playlists 
     */
    @Test
    public void testEquals() 
    {
        Playlist playlist1 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist2 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist3 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 11);

        assertTrue(playlist1.equals(playlist2));
        assertFalse(playlist1.equals(playlist3));
        assertFalse(playlist1.equals(null));
        assertFalse(playlist1.equals(new Object()));
    }

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws IllegalArgumentException 
     * when minPop > maxPop.
     */
    @Test
    public void testConstructorMinPopGreaterThanMaxPop() 
    {
        try 
        {
            new Playlist("My Playlist", 51, 40, 30, 50, 50, 50, 10);
            fail("Expected IllegalArgumentException for minPop > maxPop");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Min genre % cannot exceed max",
                e.getMessage()
            );
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws IllegalArgumentException 
     * when minRock > maxRock.
     */
    @Test
    public void testConstructorMinRockGreaterThanMaxRock() 
    {
        try 
        {
            new Playlist("My Playlist", 40, 51, 30, 50, 50, 50, 10);
            fail("Expected IllegalArgumentException for minRock > maxRock");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Min genre % cannot exceed max",
                e.getMessage()
            );
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws IllegalArgumentException 
     * when minCountry > maxCountry.
     */
    @Test
    public void testConstructorMinCountryGreaterThanMaxCountry() 
    {
        try 
        {
            new Playlist("My Playlist", 40, 40, 51, 50, 50, 50, 10);
            fail(
                "Expected IllegalArgumentException for minCountry > maxCountry"
            );
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Min genre % cannot exceed max",
                e.getMessage()
            );
        }
    }
    
    // ----------------------------------------------------------
    /**
     * Tests that setName successfully updates the name with a valid string.
     */
    @Test
    public void testSetNameValid() 
    {
        Playlist playlist = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10);
        playlist.setName("  New Name  ");
        assertEquals("New Name", playlist.getName());
    }

    // ----------------------------------------------------------
    /**
     * Tests that setName throws IllegalArgumentException when passed null.
     */
    @Test
    public void testSetNameNull() 
    {
        Playlist playlist = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10);
        try 
        {
            playlist.setName(null);
            fail("Expected IllegalArgumentException for null name");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "name cannot be null or empty",
                e.getMessage()
            );
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that setName throws IllegalArgumentException when 
     * passed an empty or whitespace string.
     */
    @Test
    public void testSetNameEmptyOrWhitespace() 
    {
        Playlist playlist = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10);
        try 
        {
            playlist.setName("   ");
            fail("Expected IllegalArgumentException for empty/whitespace name");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "name cannot be null or empty",
                e.getMessage()
            );
        }
    }
    
    // ----------------------------------------------------------
    /**
     * Tests that equals returns true when comparing a Playlist to itself.
     */
    @Test
    public void testEqualsItself() 
    {
        Playlist playlist = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        assertTrue(playlist.equals(playlist));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false when comparing to null.
     */
    @Test
    public void testEqualsNull() 
    {
        Playlist playlist = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        assertFalse(playlist.equals(null));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false when comparing to a different class.
     */
    @Test
    public void testEqualsDifferentClass() 
    {
        Playlist playlist = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        String notAPlaylist = "Not a Playlist";
        assertFalse(playlist.equals(notAPlaylist));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns true for two identical Playlists.
     */
    @Test
    public void testEqualsIdentical() 
    {
        Playlist p1 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        assertTrue(p1.equals(p2));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false if capacity differs.
     */
    @Test
    public void testEqualsDifferentCapacity() 
    {
        Playlist p1 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 11
        );
        assertFalse(p1.equals(p2));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false if name differs.
     */
    @Test
    public void testEqualsDifferentName() 
    {
        Playlist p1 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "Other Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        assertFalse(p1.equals(p2));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false if minGenreSet differs.
     */
    @Test
    public void testEqualsDifferentMinGenreSet() 
    {
        Playlist p1 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "My Playlist", 11, 10, 10, 50, 50, 50, 10
        );
        assertFalse(p1.equals(p2));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false if maxGenreSet differs.
     */
    @Test
    public void testEqualsDifferentMaxGenreSet() 
    {
        Playlist p1 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "My Playlist", 10, 10, 10, 51, 50, 50, 10
        );
        assertFalse(p1.equals(p2));
    }

    // ----------------------------------------------------------
    /**
     * Tests that equals returns false if songs array differs.
     */
    @Test
    public void testEqualsDifferentSongsArray() 
    {
        Playlist p1 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "My Playlist", 10, 10, 10, 50, 50, 50, 10
        );
        Song song = new Song("Test Song", 10, 10, 10, "Mix");
        p1.addSong(song);
        assertFalse(p1.equals(p2));
    }
    
    // ----------------------------------------------------------
    /**
     * Tests that compareTo returns a nonzero value when minGenreSet differs.
     */
    @Test
    public void testCompareToMinGenreSetDiffers() 
    {
        Playlist p1 = new Playlist(
            "Mix", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "Mix", 11, 10, 10, 50, 50, 50, 10
        );
        assertTrue(p1.compareTo(p2) < 0 || p1.compareTo(p2) > 0);
    }

    // ----------------------------------------------------------
    /**
     * Tests that compareTo returns a nonzero value when maxGenreSet differs.
     */
    @Test
    public void testCompareToMaxGenreSetDiffers() 
    {
        Playlist p1 = new Playlist(
            "Mix", 10, 10, 10, 50, 50, 50, 10
        );
        Playlist p2 = new Playlist(
            "Mix", 10, 10, 10, 51, 50, 50, 10
        );
        assertTrue(p1.compareTo(p2) < 0 || p1.compareTo(p2) > 0);
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method with playlists having null songs arrays
     */
    @Test
    public void testEqualsNullSongs() {
        Playlist playlist1 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist2 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        
        assertTrue(playlist1.equals(playlist2));
        
        // Add a song to one playlist
        playlist1.addSong(new Song("Song1", 50, 0, 0, "Test"));
        assertFalse(playlist1.equals(playlist2));
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method with playlists having different songs arrays
     */
    @Test
    public void testEqualsDifferentSongs() {
        Playlist playlist1 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist2 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        
        Song song1 = new Song("Song1", 50, 0, 0, "Test");
        Song song2 = new Song("Song2", 50, 0, 0, "Test");
        
        playlist1.addSong(song1);
        playlist2.addSong(song2);
        assertFalse(playlist1.equals(playlist2));
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method with playlists having different songs array 
     * lengths
     */
    @Test
    public void testEqualsDifferentLengths() {
        Playlist playlist1 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist2 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        
        Song song1 = new Song("Song1", 50, 0, 0, "Test");
        
        playlist1.addSong(song1);
        assertFalse(playlist1.equals(playlist2));
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method with playlists having different genre constraints
     */
    @Test
    public void testEqualsDifferentConstraints() {
        Playlist playlist1 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist2 = new Playlist("Test", 10, 0, 0, 100, 100, 100, 10);
        
        assertFalse(playlist1.equals(playlist2));
    }


    // ----------------------------------------------------------
    /**
     * Tests the equals method with playlists having the same songs in
     * the same order
     */
    @Test
    public void testEqualsSameSongsSameOrder() {
        Playlist playlist1 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        Playlist playlist2 = new Playlist("Test", 0, 0, 0, 100, 100, 100, 10);
        
        Song song1 = new Song("Song1", 50, 0, 0, "Test");
        Song song2 = new Song("Song2", 50, 0, 0, "Test");
        
        playlist1.addSong(song1);
        playlist1.addSong(song2);
        
        playlist2.addSong(song1);
        playlist2.addSong(song2);
        
        assertTrue(playlist1.equals(playlist2));
    }
}
