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
 * comparator class for reach engagement rate
 * 
 * @author ANH
 * @version Apr 22, 2025
 */
public class ComparatorReachEngRate
    implements Comparator<Data>
{
    private DataCalculator calculator = new DataCalculator();

    @Override
    public int compare(Data d1, Data d2)
    {
        double rate1 = calculator.calculateReachEngRate(d1);
        double rate2 = calculator.calculateReachEngRate(d2);
        return Double.compare(rate2, rate1);
    }
}
