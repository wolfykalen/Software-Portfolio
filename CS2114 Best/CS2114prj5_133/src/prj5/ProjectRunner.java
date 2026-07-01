// Project 5 2025 Spring

// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Mason Ramboyong 906665787

package prj5;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;

// -------------------------------------------------------------------------
/**
 * Instantiates the classes to run the entirety of the project
 * 
 * @author masonrambo
 * @version Apr 24, 2025
 */
public class ProjectRunner
{

// ~Public Methods ........................................................
    /**
     * main method of running entire project
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException
    {
        InputFileReader reader;
        if (args.length > 0)
        {
            reader = new InputFileReader(args[0]);
        }
        else
        {
            reader = new InputFileReader("SampleInput1_2023.csv");
        }

        boolean showConsole = true;
        boolean showGUI = true;

        if (showConsole)
        {
            DoublyLinkedList<Data> data = reader.readFile();
            // Process Q1 data
            DoublyLinkedList<Data> q1Data = processQ1Data(data);

            // Print traditional engagement
            printTraditionalEngagement(q1Data);

            // Print separator
            System.out.println("**********\n**********");

            // Print reach engagement
            printReachEngagement(q1Data);
        }

        if (showGUI)
        {
            GUI gui = new GUI(reader);
        }
    }


    private static DoublyLinkedList<Data> processQ1Data(
        DoublyLinkedList<Data> data)
    {
        java.util.HashMap<String, Data> channelData = new java.util.HashMap<>();
        java.util.HashMap<String, String> originalNameMap =
            new java.util.HashMap<>();
        for (Data d : data)
        {
            String month = d.getMonth().toLowerCase();
            if (!month.equals("january") && !month.equals("february")
                && !month.equals("march"))
            {
                continue;
            }
            String channelKey = d.getChannelName().toLowerCase();
            if (!originalNameMap.containsKey(channelKey))
            {
                originalNameMap.put(channelKey, d.getChannelName());
            }
            if (channelData.containsKey(channelKey))
            {
                Data agg = channelData.get(channelKey);
                agg.setLikes(agg.getLikes() + d.getLikes());
                agg.setPosts(agg.getPosts() + d.getPosts());
                agg.setComments(agg.getComments() + d.getComments());
                agg.setViews(agg.getViews() + d.getViews());
                // Use March followers only
                if (month.equals("march"))
                {
                    agg.setFollowers(d.getFollowers());
                }
            }
            else
            {
                Data agg = new Data(
                    "Q1",
                    d.getUsername(),
                    d.getChannelName(),
                    d.getCountry(),
                    d.getGenre(),
                    d.getLikes(),
                    d.getPosts(),
                    month.equals("march") ? d.getFollowers() : 0,
                    d.getComments(),
                    d.getViews());
                channelData.put(channelKey, agg);
            }
        }
        // Ensure March followers are set for all channels
        for (Data d : data)
        {
            String channelKey = d.getChannelName().toLowerCase();
            String month = d.getMonth().toLowerCase();
            if (month.equals("march") && channelData.containsKey(channelKey))
            {
                channelData.get(channelKey).setFollowers(d.getFollowers());
            }
        }
        DoublyLinkedList<Data> result = new DoublyLinkedList<>();
        for (String channelKey : channelData.keySet())
        {
            Data d = channelData.get(channelKey);
            Data out = new Data(
                "Q1",
                d.getUsername(),
                originalNameMap.get(channelKey),
                d.getCountry(),
                d.getGenre(),
                d.getLikes(),
                d.getPosts(),
                d.getFollowers(),
                d.getComments(),
                d.getViews());
            result.add(out);
        }
        return result;
    }


    private static void printTraditionalEngagement(DoublyLinkedList<Data> data)
    {
        data.sort(new ComparatorName());
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
        for (Data d : data)
        {
            // Traditional engagement rate is calculated directly within Data
            double rate = d.getTraditionalEngRate();
            System.out.println(d.getChannelName());
            if (rate == Double.NEGATIVE_INFINITY)
            {
                System.out.println("traditional: N/A");
            }
            else
            {
                System.out.println("traditional: " + df.format(rate));
            }
            System.out.println("==========");
        }
    }


    private static void printReachEngagement(DoublyLinkedList<Data> data)
    {
        data.sort(new ComparatorReachEngRate());
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
        for (Data d : data)
        {
            // Reach engagement rate is calculated directly within Data
            double rate = d.getReachEngRate();
            String rateStr = (rate < 0) ? "N/A" : df.format(rate);
            System.out.println(d.getChannelName());
            System.out.println("reach: " + rateStr);
            System.out.println("==========");
        }
    }
}
