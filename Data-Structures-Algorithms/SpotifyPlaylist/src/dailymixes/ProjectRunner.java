// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of 
// those who do.
// -- kalendaco
package dailymixes;

import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * Main entry point for the Spotify playlist application.
 * 
 * @author kalendaco
 * @version Apr 16, 2025
 */
public class ProjectRunner 
{
    /**
     * Main method that initializes the playlist system.
     * Reads input files and starts the playlist management system.
     * 
     * @param args Command-line arguments: [0] songs file path, 
     * [1] playlists file path
     * @throws ParseException If there's an error parsing input files
     * @throws DailyMixDataException If there's invalid data in the input files
     * @throws FileNotFoundException If input files cannot be found
     */
    public static void main(String[] args) 
            throws ParseException, DailyMixDataException, FileNotFoundException 
    {
        String songsFile = "input.txt";
        String playlistsFile = "playlists.txt";
        
        if (args.length == 2) 
        {
            songsFile = args[0];
            playlistsFile = args[1];
        }
        
        new PlaylistReader(songsFile, playlistsFile);
    }
}