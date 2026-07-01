package prj5;

import cs2.*;
import java.awt.Color;
import java.io.IOException;

// Virginia Tech Honor Code Pledge:
//
// Project 3 Spring 2025
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I
// accept the actions of those who do.
// -- Anh Truong (anht)

// -------------------------------------------------------------------------
/**
 * The Social Media dataset vizualization window that displays the data from a
 * given dataset. The user can sort different channels based on name,
 * traditional engagement rate, and reach engagement rate
 * 
 * @author ANH
 * @version Apr 29, 2025
 */
public class GUI
{
    // ~ Fields ................................................................
    private InputFileReader reader;
    private DoublyLinkedList<Data> data;
    private DoublyLinkedList<Data> currMonthData;

    private Window window;
    private Button quit;
    private Button sortByChannelName;
    private Button sortByEngagementRate;
    private Button tradEngRate;
    private Button reachEngRate;
    private Button january;
    private Button february;
    private Button march;
    private Button quarter;

    private static final double DISPLAY_FACTOR = 1.5;

    private static final int TEXT_MARGINS = 3; // Y spacing between all text

    private Shape[] channelRectangles = new Shape[4];
    private int[] channelHeights = new int[4];
    private String[][] channelStrings = new String[4][2]; // represents
                                                          // channelName and
                                                          // engRate
    private TextShape[][] channelTexts = new TextShape[4][2];

    private static final int CHANNEL_HEIGHT_FACTOR = 30;
    private static Color[] CHANNEL_COLORS = new Color[4];
    private static final int CHANNEL_PADDING = 100;
    private static final int CHANNEL_TEXT_MARGINS = 30; // how far text is from
                                                        // the channel bar
    private static final int CHANNEL_WIDTH = 50;
    private static final int CHANNEL_X = 120;
    private static final int CHANNEL_Y = 620;

    // enums to keep track of the current sort mode and engagement rate type
    // selected
    private enum SortMode
    {
        BY_NAME,
        BY_ENG_RATE
    }


    private enum EngRateType
    {
        REACH,
        TRAD
    }

    private SortMode currentSort = SortMode.BY_ENG_RATE;
    private EngRateType currentEngType = EngRateType.REACH;
    
    private String currMonth;

    // ~ Constructors ..........................................................
    /**
     * @throws IOException
     */
    public GUI(InputFileReader reader)
        throws IOException
    {
        window = new Window("anht masonrambo anguyen17 kalendaco");
        window
            .setSize((int)(500 * DISPLAY_FACTOR), (int)(500 * DISPLAY_FACTOR));

        this.reader = reader;
        data = reader.readFile();
        currMonthData = processQ1Data(data); // default data is Q1
        currMonth = "First Quarter (Jan - March)";

        // initialize channel colors
        CHANNEL_COLORS[0] = new Color(167, 101, 69); // brown
        CHANNEL_COLORS[1] = new Color(255, 165, 93); // orange
        CHANNEL_COLORS[2] = new Color(255, 223, 136); // yellow
        CHANNEL_COLORS[3] = new Color(172, 197, 114); // green

        // add buttons
        sortByChannelName = new Button("Sort by Channel Name");
        window.addButton(sortByChannelName, WindowSide.NORTH);
        sortByChannelName.onClick(this, "clickedSortByChannelName");

        sortByEngagementRate = new Button("Sort by Engagement Rate");
        window.addButton(sortByEngagementRate, WindowSide.NORTH);
        sortByEngagementRate.onClick(this, "clickedSortByEngRate");

        quit = new Button("Quit");
        window.addButton(quit, WindowSide.NORTH);
        quit.onClick(this, "clickedQuit");

        tradEngRate = new Button("Traditional Engagement Rate");
        window.addButton(tradEngRate, WindowSide.WEST);
        tradEngRate.onClick(this, "clickedTradEngRate");

        reachEngRate = new Button("Reach Engagement Rate");
        window.addButton(reachEngRate, WindowSide.WEST);
        reachEngRate.onClick(this, "clickedReachEngRate");

        january = new Button("January");
        window.addButton(january, WindowSide.SOUTH);
        january.onClick(this, "clickedJan");

        february = new Button("February");
        window.addButton(february, WindowSide.SOUTH);
        february.onClick(this, "clickedFeb");

        march = new Button("March");
        window.addButton(march, WindowSide.SOUTH);
        march.onClick(this, "clickedMar");

        quarter = new Button("First Quarter (Jan - March");
        window.addButton(quarter, WindowSide.SOUTH);
        quarter.onClick(this, "clickedQuarter");

        update();

    }


    // ~Public Methods ........................................................
    /**
     * 
     */
    private DoublyLinkedList<Data> processQ1Data(DoublyLinkedList<Data> data)
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


    // ----------------------------------------------------------
    /**
     * 
     */
    private DoublyLinkedList<Data> processJanData(DoublyLinkedList<Data> data)
    {
        DoublyLinkedList<Data> result = new DoublyLinkedList<>();

        for (Data d : data)
        {
            String month = d.getMonth().toLowerCase();
            if (month.equals("january"))
            {
                result.add(d);
            }
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private DoublyLinkedList<Data> processFebData(DoublyLinkedList<Data> data)
    {
        DoublyLinkedList<Data> result = new DoublyLinkedList<>();

        for (Data d : data)
        {
            String month = d.getMonth().toLowerCase();
            if (month.equals("february"))
            {
                result.add(d);
            }
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private DoublyLinkedList<Data> processMarData(DoublyLinkedList<Data> data)
    {
        DoublyLinkedList<Data> result = new DoublyLinkedList<>();

        for (Data d : data)
        {
            String month = d.getMonth().toLowerCase();
            if (month.equals("march"))
            {
                result.add(d);
            }
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private void applyCurrentSort()
    {
        switch (currentSort)
        {
            case BY_NAME:
                currMonthData.sort(
                    (a, b) -> a.getChannelName()
                        .compareToIgnoreCase(b.getChannelName()));
                break;

            case BY_ENG_RATE:
                switch (currentEngType)
                {
                    case TRAD:
                        currMonthData.sort(new ComparatorTradEngRate());
                        break;
                    case REACH:
                        currMonthData.sort(new ComparatorReachEngRate());
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedSortByChannelName(Button button)
    {
        currentSort = SortMode.BY_NAME;
        update();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedSortByEngRate(Button button)
    {
        currentSort = SortMode.BY_ENG_RATE;
        update();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedTradEngRate(Button button)
    {
        currentEngType = EngRateType.TRAD;
        update();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedReachEngRate(Button button)
    {
        currentEngType = EngRateType.REACH;
        update();
    }


    // ----------------------------------------------------------
    /**
     * exits out of the window when the quit button is clicked
     * 
     * @param button
     *            is the quit button
     */

    public void clickedQuit(Button button)
    {
        System.exit(0);
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedJan(Button button)
    {
        currMonthData = processJanData(data);
        currMonth = "January";
        update();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedFeb(Button button)
    {
        currMonthData = processFebData(data);
        currMonth = "February";
        update();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedMar(Button button)
    {
        currMonthData = processMarData(data);
        currMonth = "March";
        update();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    public void clickedQuarter(Button button)
    {
        currMonthData = processQ1Data(data);
        currMonth = "First Quarter (Jan - March)";
        update();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private void update()
    {
        applyCurrentSort();
        window.removeAllShapes();
        drawChannels();
        initializeChannelText();
        initializeCornerText();
        updateShapes();
        updateText();
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private void updateShapes()
    {
        int shapeX = CHANNEL_X;
        int i = 0;
        String[][] newChannelStrings = new String[channelStrings.length][2];
        for (Data d : currMonthData)
        {
            int index = findChannelIndex(d.getChannelName());

            // if the channel name is never found. This will never happen but
            // the if statement is for formalities
            if (index != -1)
            {
                // swap rectangle shapes
                int height = channelHeights[index];
                int shapeY = CHANNEL_Y - height;
                channelRectangles[index].moveTo(shapeX, shapeY);
                shapeX += CHANNEL_WIDTH + CHANNEL_PADDING;

                // update text
                newChannelStrings[i][0] = channelStrings[index][0];
                newChannelStrings[i][1] = channelStrings[index][1];
                i++;
            }
        }
        channelStrings = newChannelStrings;
    }


    /**
     * 
     */
    private int findChannelIndex(String channelName)
    {
        for (int i = 0; i < channelStrings.length; i++)
        {
            if (channelStrings[i][0].equals(channelName))
            {
                return i;
            }
        }
        return -1;
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private void updateText()
    {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");

        for (int i = 0; i < channelTexts.length; i++)
        {
            channelTexts[i][0].setText(channelStrings[i][0]);
            channelTexts[i][1]
                .setText(df.format(Double.parseDouble(channelStrings[i][1])));
        }
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private void initializeCornerText() {
        String engType = "Reach Engagement Rate";
        String sortMode = "Sorting by Engagement Rate";
        if (currentSort == SortMode.BY_NAME) {
            sortMode = "Sorting by Channel Name";
        }
        else {
            sortMode = "Sorting by Engagement Rate";
        }
        
        if (currentEngType == EngRateType.REACH) {
            engType = "Reach Engagement Rate";
        }
        else {
            engType = "Traditional Engagement Rate";
        }

        TextShape month = addTextShape(currMonth, 20, 10);
        TextShape engRate = addTextShape(
            engType,
            20,
            TEXT_MARGINS + month.getY() + month.getHeight());
        addTextShape(
            sortMode,
            20,
            TEXT_MARGINS + engRate.getY() + engRate.getHeight());
    }
    
    // ----------------------------------------------------------
    /**
     * 
     */
    private void initializeChannelText()
    {
        // channel text
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");

        int i = 0;
        for (Shape channel : channelRectangles)
        {
            int x = channel.getX();
            TextShape channelName = addTextShape(
                channelStrings[i][0],
                x,
                CHANNEL_Y + CHANNEL_TEXT_MARGINS);
            TextShape channelData = addTextShape(
                df.format(Double.parseDouble(channelStrings[i][1])),
                x,
                CHANNEL_Y + channelName.getHeight() + CHANNEL_TEXT_MARGINS
                    + TEXT_MARGINS);

            channelTexts[i][0] = channelName;
            channelTexts[i][1] = channelData;

            i++;
        }
    }


    // ----------------------------------------------------------
    /**
     * Helper method to add a TextShape to the window
     */
    private TextShape addTextShape(String message, int x, int y)
    {
        if (message != null)
        {
            TextShape shape = new TextShape(x, y, message, Color.black);
            shape.setBackgroundColor(Color.white);
            window.addShape(shape);
            return shape;
        }
        return null;
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private void drawChannels()
    {
        int i = 0; // should only go 0 - 3. each index for 1 channel
        int shapeX = CHANNEL_X;
        
        for (Data d : currMonthData)
        {
            // initialize visuals based on engagement type
            double engRateValue = d.getReachEngRate();
            if (currentEngType == EngRateType.REACH) {
                engRateValue = d.getReachEngRate();
            }
            else {
                engRateValue = d.getTraditionalEngRate();
            }
            
            // initializes channel heights and texts
            channelHeights[i] = (int)(engRateValue 
                * CHANNEL_HEIGHT_FACTOR * DISPLAY_FACTOR);
            channelStrings[i][0] = d.getChannelName();
            channelStrings[i][1] = Double.toString(engRateValue);

            Color color = CHANNEL_COLORS[i];
            int shapeY = CHANNEL_Y - channelHeights[i];
            channelRectangles[i] =
                drawChannel(shapeX, shapeY, channelHeights[i], color);
            shapeX += CHANNEL_WIDTH + CHANNEL_PADDING;

            i++;
        }
    }


    // ----------------------------------------------------------
    /**
     * 
     */
    private Shape drawChannel(int x, int y, int height, Color color)
    {
        Shape channel = new Shape(x, y, CHANNEL_WIDTH, height, color);
        window.addShape(channel);
        return channel;
    }
}
