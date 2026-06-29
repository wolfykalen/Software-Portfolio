// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of 
//those who do.
// -- Your name kalendaco)
package dailymixes;

//-------------------------------------------------------------------------
/**
 * exception class for handling invalid data
 * exception is thrown if playlist or song data doesn't follow the criteria
 * invalid genre percentages or incorrect file formats.
 * 
 * @author kalendaco
 * @version Apr 16, 2025
 */
public class DailyMixDataException 
    extends Exception 
{
    // ----------------------------------------------------------
    /**
     * Creates a new DailyMixDataException with an error message
     * used to show that playlist or song data is invalid
     * 
     * @param message A description of why the data is invalid
     */
    public DailyMixDataException(String message) 
    {
        super(message);
    }
}