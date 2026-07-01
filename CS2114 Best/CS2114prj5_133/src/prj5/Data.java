// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Mason Ramboyong 906665787

package prj5;

// -------------------------------------------------------------------------
/**
 * Creates a Data object containing all the information with methods to get
 * specific pieces of data as well as calculate engagement rates.
 * 
 * @author masonrambo
 * @version Apr 20, 2025
 */
public class Data
{
    // ~ Fields ................................................................
    private final String month;
    private final String username;
    private final String channelName;
    private final String country;
    private final String genre;
    private int likes;
    private int posts;
    private int followers;
    private int comments;
    private int views;

    // ~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new DataTest object.
     * 
     * @param month
     *            month of post
     * @param username
     *            username of who posted
     * @param channelName
     *            channelName of post
     * @param country
     *            coutry of origin of post
     * @param genre
     *            genre of post
     * @param likes
     *            number of likes
     * @param posts
     *            number of posts
     * @param followers
     *            number of followers
     * @param comments
     *            number of comments
     * @param views
     *            number of views
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public Data(
        String month,
        String username,
        String channelName,
        String country,
        String genre,
        int likes,
        int posts,
        int followers,
        int comments,
        int views)
    {
        this.month = month;
        this.username = username;
        this.channelName = channelName;
        this.country = country;
        this.genre = genre;
        this.likes = likes;
        this.posts = posts;
        this.followers = followers;
        this.comments = comments;
        this.views = views;
    }


    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * gets the username
     * 
     * @return username
     */
    public String getUsername()
    {
        return username;
    }


    // ----------------------------------------------------------
    /**
     * gets the channel Name
     * 
     * @return channelName
     */
    public String getChannelName()
    {
        return channelName;
    }


    // ----------------------------------------------------------
    /**
     * gets the country
     * 
     * @return country
     */
    public String getCountry()
    {
        return country;
    }


    // ----------------------------------------------------------
    /**
     * gets the month of the data
     * 
     * @return month
     */
    public String getMonth()
    {
        return month;
    }


    // ----------------------------------------------------------
    /**
     * gets the number of comments
     * 
     * @return comments
     */
    public int getComments()
    {
        return comments;
    }


    // ----------------------------------------------------------
    /**
     * gets the number of likes
     * 
     * @return likes
     */
    public int getLikes()
    {
        return likes;
    }


    // ----------------------------------------------------------
    /**
     * gets the number of views
     * 
     * @return views
     */
    public int getViews()
    {
        return views;
    }


    // ----------------------------------------------------------
    /**
     * gets the number of followers
     * 
     * @return followers
     */
    public int getFollowers()
    {
        return followers;
    }


    // ----------------------------------------------------------
    /**
     * gets the genre
     * 
     * @return genre
     */
    public String getGenre()
    {
        return genre;
    }


    // ----------------------------------------------------------
    /**
     * gets the number of posts
     * 
     * @return number of posts
     */
    public int getPosts()
    {
        return posts;
    }


    /**
     * Sets the number of likes
     * 
     * @param likes
     *            the number of likes
     */
    public void setLikes(int likes)
    {
        this.likes = likes;
    }


    /**
     * Sets the number of posts
     * 
     * @param posts
     *            the number of posts
     */
    public void setPosts(int posts)
    {
        this.posts = posts;
    }


    /**
     * Sets the number of comments
     * 
     * @param comments
     *            the number of comments
     */
    public void setComments(int comments)
    {
        this.comments = comments;
    }


    /**
     * Sets the number of views
     * 
     * @param views
     *            the number of views
     */
    public void setViews(int views)
    {
        this.views = views;
    }


    /**
     * Sets the number of followers
     * 
     * @param followers
     *            the number of followers
     */
    public void setFollowers(int followers)
    {
        this.followers = followers;
    }


    /**
     * Gets the traditional engagement rate
     * 
     * @return the engagement rate
     */
    public double getTraditionalEngRate()
    {
        if (followers == 0)
        {
            return Double.NEGATIVE_INFINITY;
        }
        return ((double)(comments + likes) / followers) * 100;
    }


    /**
     * Gets the reach engagement rate
     * 
     * @return the engagement rate
     */
    public double getReachEngRate()
    {
        if (views == 0)
        {
            return Double.NEGATIVE_INFINITY;
        }
        return ((double)(comments + likes) / views) * 100;
    }


    // ----------------------------------------------------------
    /**
     * Puts the data into a String
     * 
     * @return String of the data
     */
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        str.append("Month:");
        str.append(this.month);
        str.append(" Username:");
        str.append(this.username);
        str.append(" Channel Name:");
        str.append(this.channelName);
        str.append(" Country:");
        str.append(this.country);
        str.append(" Main Topic: ");
        str.append(this.genre);
        str.append(" Likes:");
        str.append(this.likes);
        str.append(" Posts:");
        str.append(this.posts);
        str.append(" Followers:");
        str.append(this.followers);
        str.append(" Comments:");
        str.append(this.comments);
        str.append(" Views:");
        str.append(this.views);
        return str.toString();
    }

}
