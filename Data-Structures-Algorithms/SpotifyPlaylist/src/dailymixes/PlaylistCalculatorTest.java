// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of 
// those who do.
// -- kalendaco
package dailymixes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;

/**
 * Test class for PlaylistCalculator ensures song distribution and 
 * playlist management
 * 
 * @author kalendaco
 * @version Apr 16, 2025
 */
public class PlaylistCalculatorTest {
    private Playlist[] playlists;
    private ArrayQueue<Song> queue;
    private PlaylistCalculator calculator;

    /**
     * Sets up test environment before each test method.
     * Creates three playlists each accepting up to 50% of each genre.
     */
    @Before
    public void setUp() 
    {
        playlists = new Playlist[] 
        {
            new Playlist("Daily Mix 1", 0, 0, 0, 50, 50, 50, 2),
            new Playlist("Daily Mix 2", 0, 0, 0, 50, 50, 50, 2),
            new Playlist("Daily Mix 3", 0, 0, 0, 50, 50, 50, 2)
        };
        
        queue = new ArrayQueue<>();
        
        calculator = new PlaylistCalculator(queue, playlists);
    }

    /**
     * Verifies that PlaylistCalculator constructor handles null song queues
     */
    @Test
    public void testConstructorWithNullQueue() 
    {
        try 
        {
            new PlaylistCalculator(null, playlists);
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals("Song queue cannot be null.", e.getMessage());
        }
    }

    /**
     * Tests the addition of a song to a playlist
     */
    @Test
    public void testAddSongToSuggestedPlaylist() 
    {
        Song song = new Song("Song1", 50, 0, 0, "Daily Mix 1");
        queue.enqueue(song);
        assertEquals(true, calculator.addSongToPlaylist());
        assertEquals(1, playlists[0].getNumberOfSongs());
        assertEquals(true, queue.isEmpty());
    }

    /**
     * Tests rejection of a song that doesn't fit any playlist's constraints
     */
    @Test
    public void testAddSongWithInvalidGenreConstraints() 
    {
        Song song = new Song("Song2", 60, 60, 60, "Invalid Mix");
        queue.enqueue(song);
        assertEquals(false, calculator.addSongToPlaylist());
        assertEquals(false, queue.isEmpty()); 

        calculator.reject();
        assertEquals(true, queue.isEmpty()); 
    }

    /**
     * Tests retrieval of queue and rejected tracks after processing songs
     */
    @Test
    public void testGetRejectedTracksAndQueueState() 
    {
        Song song1 = new Song("Song1", 40, 30, 20, "Daily Mix 1");
        Song song2 = new Song("Song2", 70, 70, 70, "Invalid Mix");

        queue.enqueue(song1);
        queue.enqueue(song2);

        assertEquals(true, calculator.addSongToPlaylist()); 
        assertEquals(false, calculator.addSongToPlaylist()); 

        calculator.reject();

        assertEquals(1, playlists[0].getNumberOfSongs());
        assertEquals(1, calculator.getRejectedTracks().getLength());
        assertEquals(true, queue.isEmpty());
    }

    // ----------------------------------------------------------
    // ----------------------------------------------------------
    /**
     * Tests that reject() removes a song from the queue and adds it to 
     * rejectedTracks when the queue is not empty.
     */
    @Test
    public void testRejectWhenQueueNotEmpty() 
    {
        ArrayQueue<Song> testQueue = new ArrayQueue<>();
        Playlist[] testPlaylists = new 
            Playlist[PlaylistCalculator.NUM_PLAYLISTS];
        Song song = new Song("Test Song", 10, 10, 10, "Mix");
        testQueue.enqueue(song);

        PlaylistCalculator calc = new 
            PlaylistCalculator(testQueue, testPlaylists);
        calc.reject();

        assertEquals(0, testQueue.getSize());
        assertEquals(1, calc.getRejectedTracks().getLength());
        assertEquals(song, calc.getRejectedTracks().getEntry(0));
    }

    // ----------------------------------------------------------
    /**
     * Tests that reject() does nothing when the queue is empty.
     */
    @Test
    public void testRejectWhenQueueEmpty() 
    {
        ArrayQueue<Song> testQueue = new ArrayQueue<>();
        Playlist[] testPlaylists = new 
            Playlist[PlaylistCalculator.NUM_PLAYLISTS];

        PlaylistCalculator calc = new 
            PlaylistCalculator(testQueue, testPlaylists);
        calc.reject();

        assertEquals(0, calc.getRejectedTracks().getLength());
    } 

    // ----------------------------------------------------------
    /**
     * Helper method to test private getPlaylistWithMaximumCapacity 
     * 
     */
    private Playlist testMaxCapacity(PlaylistCalculator calc, Song song) 
        throws Exception 
    {
        Method method = PlaylistCalculator.class.getDeclaredMethod("getPlaylist"
            + "WithMaximumCapacity", Song.class);
        method.setAccessible(true);
        return (Playlist) method.invoke(calc, song);
    }

    // ----------------------------------------------------------
    /**
     * Tests getPlaylistWithMaximumCapacity with null song
     * @throws Exception if reflections fails
     */
    @Test
    public void testGetPlaylistWithMaximumCapacityNullSong() 
        throws Exception 
    {
        Song nullSong = null;
        Playlist result = testMaxCapacity(calculator, nullSong);
        assertNull(result);
    }

    // ----------------------------------------------------------
    /**
     * Tests getPlaylistWithMaximumCapacity when no playlist can accept the song
     * @throws Exception if reflections fails
     */
    @Test
    public void testGetPlaylistWithMaximumCapacityNoAcceptingPlaylist() 
        throws Exception 
    {
        Song song = new Song("Song1", 60, 60, 60, "Invalid Mix");
        Playlist result = testMaxCapacity(calculator, song);
        assertNull(result);
    }

    // ----------------------------------------------------------
    /**
     * Tests getPlaylistWithMaximumCapacity when one playlist can 
     * accept the song
     * @throws Exception if reflections fails
     */
    @Test
    public void testGetPlaylistWithMaximumCapacityOneAcceptingPlaylist() 
        throws Exception 
    {
        Song song = new Song("Song1", 40, 0, 0, "Daily Mix 1");
        Playlist result = testMaxCapacity(calculator, song);
        assertNotNull(result);
        assertEquals("Daily Mix 3", result.getName());
    }

    // ----------------------------------------------------------
    /**
     * Tests getPlaylistWithMaximumCapacity when multiple playlists can accept 
     * the song
     * @throws Exception if reflections fails
     */
    @Test
    public void testGetPlaylistWithMaximumCapacityMultipleAcceptingPlaylists() 
        throws Exception 
    {
        Song song = new Song("Song1", 20, 20, 20, "Daily Mix 1");
        
        playlists = new Playlist[] 
            {
            new Playlist("Daily Mix 1", 0, 0, 0, 100, 100, 100, 5),
            new Playlist("Daily Mix 2", 0, 0, 0, 100, 100, 100, 10),
            new Playlist("Daily Mix 3", 0, 0, 0, 100, 100, 100, 3)
        };
        
        calculator = new PlaylistCalculator(queue, playlists);
        
        Playlist result = testMaxCapacity(calculator, song);
        assertNotNull(result);
        assertEquals("Daily Mix 2", result.getName());  
    }
    
 // ----------------------------------------------------------
    /**
     * Tests addSongToPlaylist when the queue is empty
     */
    @Test
    public void testAddSongToPlaylistEmptyQueue() 
    {
        assertEquals(false, calculator.addSongToPlaylist());
        assertEquals(true, queue.isEmpty());
    }

    // ----------------------------------------------------------
    /**
     * Tests addSongToPlaylist when a song can be added to a playlist
     */
    @Test
    public void testAddSongToPlaylistSuccessfulAdd() 
    {
        Song song = new Song("Song1", 40, 0, 0, "Daily Mix 1");
        queue.enqueue(song);
        
        assertEquals(1, queue.getSize());
        assertEquals(true, calculator.addSongToPlaylist());
        assertEquals(true, queue.isEmpty());
        assertEquals(1, playlists[0].getNumberOfSongs());
    }

    // ----------------------------------------------------------
    /**
     * Tests addSongToPlaylist when no playlist can accept the song
     */
    @Test
    public void testAddSongToPlaylistNoAcceptingPlaylist() 
    {
        Song song = new Song("Song1", 60, 60, 60, "Invalid Mix");
        queue.enqueue(song);
        
        assertEquals(1, queue.getSize());
        assertEquals(false, calculator.addSongToPlaylist());
        assertEquals(1, queue.getSize());
        assertEquals(0, playlists[0].getNumberOfSongs());
        assertEquals(0, playlists[1].getNumberOfSongs());
        assertEquals(0, playlists[2].getNumberOfSongs());
    }
}