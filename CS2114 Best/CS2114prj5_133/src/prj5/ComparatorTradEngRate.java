// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Anh Truong (anht)

package prj5;

import java.util.Comparator;

// -------------------------------------------------------------------------
/**
 * comparator class for traditional engagement rate
 * 
 * @author ANH
 * @version Apr 22, 2025
 */
public class ComparatorTradEngRate
    implements Comparator<Data>
{
    private DataCalculator calculator = new DataCalculator();

    @Override
    public int compare(Data d1, Data d2)
    {
        if (calculator.calculateTraditionalEngRate(d1) < calculator
            .calculateTraditionalEngRate(d2))
        {
            return 1;
        }
        else if (calculator.calculateTraditionalEngRate(d1) > calculator
            .calculateTraditionalEngRate(d2))
        {
            return -1;
        }
        return 0;
    }
}
