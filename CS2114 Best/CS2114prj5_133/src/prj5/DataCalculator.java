// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Andrew Nguyen 9066689185

package prj5;

// -------------------------------------------------------------------------
/**
 * Completes the logic and math behind the scenes to organize our data. As well
 * as updates the newly sorted data to a new DoublyLinkedList
 * 
 * @author anguyen17
 * @version Apr 21, 2025
 */
public class DataCalculator
{
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    /**
     * returns users in a specific month
     * 
     * @param month
     *            the month to search for
     * @param data
     *            the data array to search through
     * @return DoublyLinkedList of data for the specified month
     */
    public DoublyLinkedList<Data> getUsersInMonth(String month, Data[] data)
    {
        DoublyLinkedList<Data> result = new DoublyLinkedList<Data>();
        for (Data entry : data)
        {
            if (entry.getMonth().equals(month))
            {
                result.add(entry);
            }
        }
        return result;
    }


    /**
     * calculates engagement rate for data
     * 
     * @param data
     *            the data to calculate rate
     * @return calculated reach engagement rate or -1.0 if views are 0
     */
    public double calculateReachEngRate(Data data)
    {
        if (data.getViews() == 0)
        {
            return Double.NEGATIVE_INFINITY;
        }
        return ((double)(data.getComments() + data.getLikes())
            / data.getViews()) * 100;
    }


    /**
     * calculate traditional engagement for data
     * 
     * @param data
     *            the data to calculate rate
     * @return calculated traditional engagement rate or -1.0 if followers are 0
     */
    public double calculateTraditionalEngRate(Data data)
    {
        if (data.getFollowers() == 0)
        {
            return Double.NEGATIVE_INFINITY;
        }
        return ((double)(data.getComments() + data.getLikes())
            / data.getFollowers()) * 100;
    }

}
