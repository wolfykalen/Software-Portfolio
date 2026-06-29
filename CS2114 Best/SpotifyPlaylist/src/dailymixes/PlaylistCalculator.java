// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of 
//those who do.
// -- Your name kalendaco)
package dailymixes;

import list.AList;
import java.util.Arrays;

/**
 * The PlaylistCalculator class manages the distribution of songs into playlists
 * based on genre constraints and playlist capacities. It maintains a queue of
 * songs a list of playlists and tracks rejected tracks that don't fit into
 * playlist's 
 * 
 * @author kalendaco
 * @version Apr 16, 2025
 */
public class PlaylistCalculator 
{

    /**
     * Constant defining the number of playlists in the system.
     */
    public static final int NUM_PLAYLISTS = 3;
    
    /**
     * Minimum percentage value for genre constraints (0%).
     */
    public static final int MIN_PERCENT = 0;
    
    /**
     * Maximum percentage value for genre constraints (100%).
     */
    public static final int MAX_PERCENT = 100;

    private Playlist[] playlists;
    private AList<Song> rejectedTracks;
    private ArrayQueue<Song> songQueue;

    /**
     * Creates a new PlaylistCalculator to manage song distribution.
     * 
     * @param songQueue The queue of songs to be distributed
     * @param playlists The array of playlists to distribute songs into
     * @throws IllegalArgumentException if songQueue is null
     */
    public PlaylistCalculator(ArrayQueue<Song> songQueue, Playlist[] playlists) 
    {
        if (songQueue == null) 
        {
            throw new IllegalArgumentException("Song queue cannot be null.");
        }
        this.songQueue = songQueue;
        this.playlists = playlists;
        this.rejectedTracks = new AList<>();
    }

    /**
     * Removes the front song from the queue adds it to the rejected track list
     * 
     * @throws IllegalStateException if the song queue is empty
     */
    public void reject() 
    {
        if (!songQueue.isEmpty()) 
        {
            Song song = songQueue.dequeue();
            rejectedTracks.add(song);
        }
    }

    /**
     * Finds the playlist with maximum capacity that can accept a given song.
     * 
     * @param aSong The song to find a playlist for
     * @return The playlist with maximum capacity that can accept the song
     */
    private Playlist getPlaylistWithMaximumCapacity(Song aSong) 
    {
        if (aSong == null) 
        {
            return null;
        }
        Playlist[] sorted = Arrays.copyOf(playlists, playlists.length);
        Arrays.sort(sorted); 
        for (int i = sorted.length - 1; i >= 0; i--) 
        { 
            if (canAccept(sorted[i], aSong)) 
            {
                return sorted[i];
            }
        }
        return null;
    }

    /**
     * Attempts to add the next song in the queue to an appropriate playlist.
     * 
     * @return true if a song was successfully added to a playlist
     */
    public boolean addSongToPlaylist() 
    {
        if (songQueue.isEmpty()) 
        {
            return false;
        }
        Song nextSong = songQueue.getFront();
        Playlist playlist = getPlaylistForSong(nextSong);
        if (playlist != null && playlist.addSong(nextSong)) 
        {
            songQueue.dequeue();
            return true;
        }
        return false;
    }

    /**
     * Finds the most appropriate playlist for a given song
     * 
     * @param nextSong The song to find a playlist for
     * @return The appropriate playlist for the song or null 
     */
    public Playlist getPlaylistForSong(Song nextSong) 
    {
        if (nextSong == null) 
        {
            return null;
        }
        String suggestedName = nextSong.getSuggestedPlaylist();
        int suggestedIndex = getPlaylistIndex(suggestedName);

        if (suggestedIndex >= 0 && suggestedIndex < playlists.length) 
        {
            Playlist suggested = playlists[suggestedIndex];
            if (canAccept(suggested, nextSong)) 
            {
                return suggested;
            }
        }
        return getPlaylistWithMaximumCapacity(nextSong);
    }

    /**
     * Returns the queue of songs waiting to be distributed.
     * 
     * @return The song queue
     */
    public ArrayQueue<Song> getQueue() 
    {
        return songQueue;
    }

    /**
     * Checks if a playlist can accept a song based on its capacity 
     * and genre constraints.
     * 
     * @param playlist The playlist to check
     * @param song The song to check
     * @return true if the playlist can accept the song false otherwise
     */
    private boolean canAccept(Playlist playlist, Song song) 
    {
        if (playlist == null || song == null) 
        {
            return false;
        }
        return !playlist.isFull() && playlist.isQualified(song);
    }

    /**
     * Finds the index of a playlist by its name.
     * 
     * @param playlistName The name of the playlist to find
     * @return The index of the playlist, or -1 if not found
     */
    public int getPlaylistIndex(String playlistName) 
    {
        if (playlistName == null) 
        {
            return -1;
        }
        for (int i = 0; i < playlists.length; i++) 
        {
            if (playlistName.equals(playlists[i].getName())) 
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the array of playlists managed by this calculator.
     * 
     * @return The array of playlists
     */
    public Playlist[] getPlaylists() 
    {
        return playlists;
    }

    /**
     * Returns the list of songs that were rejected 
     * 
     * @return The list of rejected songs
     */
    public AList<Song> getRejectedTracks() 
    {
        return rejectedTracks;
    }
}