// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Mason Ramboyong 906665787

package prj5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Reads the excel files and instantiates objects
 * 
 * @author masonrambo
 * @version Apr 21, 2025
 */
public class InputFileReader
{
    // ~ Fields ................................................................
    private String fileName;
    private final int DATA_TOKENS = 10;
    private Scanner scanner;

    // ~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new InputFileReader object.
     * 
     * @param name
     * @throws IOException
     */
    public InputFileReader(String name)
        throws IOException
    {
        this.fileName = name;
    }


    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Reads the entire file and returns a list of Data objects.
     * 
     * @return a list of Data objects read from the file
     * @throws IOException
     *             if there is an error reading the file
     */
    public DoublyLinkedList<Data> readFile()
        throws IOException
    {
        if (fileName == null || fileName.isEmpty())
        {
            throw new IOException("File name not set");
        }

        DoublyLinkedList<Data> list = new DoublyLinkedList<>();
        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            scanner.nextLine();
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty())
                {
                    try
                    {
                        Data data = readLine(line);
                        if (data != null)
                        {
                            list.add(data);
                        }
                    }
                    catch (ParseException e)
                    {
                        throw new IOException("Error parsing line: " + line, e);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            throw new IOException("File not found: " + fileName, e);
        }
        return list;
    }


    /**
     * Reads a line of data and returns it as a Data object.
     * 
     * @param line
     *            the line of data to parse
     * @return a Data object representing the parsed data
     * @throws ParseException
     *             if the data format is invalid
     */
    public Data readLine(String line)
        throws ParseException
    {
        try
        {
            String[] tokens = line.split(",");
            if (tokens.length != DATA_TOKENS)
            {
                throw new ParseException("Invalid data format", 0);
            }

            String month = tokens[0];
            String username = tokens[1];
            String channelName = tokens[2];
            String country = tokens[3];
            String genre = tokens[4];
            int likes = Integer.parseInt(tokens[5]);
            int posts = Integer.parseInt(tokens[6]);
            int followers = Integer.parseInt(tokens[7]);
            int comments = Integer.parseInt(tokens[8]);
            int views = Integer.parseInt(tokens[9]);

            return new Data(
                month,
                username,
                channelName,
                country,
                genre,
                likes,
                posts,
                followers,
                comments,
                views);
        }
        catch (NumberFormatException e)
        {
            throw new ParseException("Invalid number format", 0);
        }
    }


    /**
     * Reads the next line from the file and returns it as a Data object.
     * 
     * @return the next Data object from the file, or null if no more data
     * @throws IOException
     *             if there's an error reading the file
     */
    public Data readLine()
        throws IOException
    {
        if (scanner == null)
        {
            scanner = new Scanner(new File(fileName));
        }

        if (!scanner.hasNextLine())
        {
            return null;
        }

        String line = scanner.nextLine();
        if (line.trim().isEmpty())
        {
            return readLine();
        }

        try
        {
            return readLine(line);
        }
        catch (ParseException e)
        {
            throw new IOException("Error parsing line: " + line, e);
        }
    }
}
