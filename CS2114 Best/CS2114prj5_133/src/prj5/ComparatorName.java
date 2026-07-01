// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Andrew Nguyen 9066689185

package prj5;

import java.util.Comparator;

// -------------------------------------------------------------------------
/**
 * implements Comparator to compare Names with each other
 * 
 * @author anguyen17
 * @version Apr 23, 2025
 */
public class ComparatorName
    implements Comparator<Data>
{
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    /**
     * Compares two Data objects based on channel names.
     * 
     * @param data1
     *            first Data object to compare
     * @param data2
     *            second Data object to compare
     * @return a negative integer, zero, or a positive integer as the first
     *             argument is less than, equal to, or greater than the second
     */
    @Override
    public int compare(Data data1, Data data2)
    {
        String first = data1.getChannelName();
        String second = data2.getChannelName();
        int minLength = Math.min(first.length(), second.length());
        for (int i = 0; i < minLength; i++)
        {
            char a = Character.toLowerCase(first.charAt(i));
            char b = Character.toLowerCase(second.charAt(i));

            if (a < b)
            {
                return -1;
            }
            if (a > b)
            {
                return 1;
            }
        }
        if (first.length() > second.length())
        {
            return 1;
        }
        if (first.length() < second.length())
        {
            return -1;
        }

        return 0;
    }


    @Override
    public boolean equals(Object obj)
    {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }


    /**
     * compares two Data objects for equality based on channel names
     *
     * @param other
     *            the Data object to compare with
     * @return true if the channel names are equal, false otherwise
     */
    public boolean equalTo(Data other)
    {
        return other != null;
    }

}
