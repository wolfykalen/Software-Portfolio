// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of 
//those who do.
// -- Your name kalendaco)
package dailymixes;

import static org.junit.Assert.*;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 * A test class for the GenreSet class
 * verifies operations in the GenreSet class 
 * 
 * @author kalendaco
 * @version Apr 3, 2025
 */
public class GenreSetTest 
{
    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws an IllegalArgumentException when
     * provided with a negative percentage value.
     */
    @Test
    public void testConstructorInvalidPercentages() 
    {
        try 
        {
            new GenreSet(-1, 50, 50); 
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            // Expected exception
        }
    }


    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws an IllegalArgumentException when
     * provided with a percentage value greater than 100.
     */
    @Test
    public void testConstructorInvalidPercentages2() 
    {
        try 
        {
            new GenreSet(101, 50, 50); 
            fail("Expected IllegalArgumentException was not thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            // Expected exception
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the constructor with valid percentage values verifying that
     * the genre percentages are correctly set and the toString representation
     * is accurate.
     */
    @Test
    public void testConstructorValid() 
    {
        GenreSet genreSet = new GenreSet(30, 40, 30);
        assertEquals(30, genreSet.getPop());
        assertEquals(40, genreSet.getRock());
        assertEquals(30, genreSet.getCountry());
        assertEquals("Pop:30 Rock:40 Country:30", genreSet.toString());
    }

    // ----------------------------------------------------------
    /**
     * Tests the getter methods for each genre percentage
     */
    @Test
    public void testGetters() 
    {
        GenreSet genreSet = new GenreSet(25, 75, 0);
        assertEquals(25, genreSet.getPop());
        assertEquals(75, genreSet.getRock());
        assertEquals(0, genreSet.getCountry());
    }

    // ----------------------------------------------------------
    /**
     * Tests the isLessThanOrEqualTo method verifies
     * that it correctly compares genre percentages 
     */
    @Test
    public void testIsLessThanOrEqualTo() 
    {
        GenreSet g1 = new GenreSet(30, 40, 30);
        GenreSet g2 = new GenreSet(40, 50, 60);
        GenreSet g3 = new GenreSet(30, 40, 30);
        GenreSet g4 = new GenreSet(20, 30, 40);
        GenreSet g5 = new GenreSet(30, 30, 30);
        
        assertTrue(g1.isLessThanOrEqualTo(g2)); 
        assertTrue(g1.isLessThanOrEqualTo(g3)); 
        assertFalse(g2.isLessThanOrEqualTo(g1)); 
        assertFalse(g4.isLessThanOrEqualTo(g1)); 
        assertTrue(g5.isLessThanOrEqualTo(g1)); 
        assertFalse(g1.isLessThanOrEqualTo(g5)); 
    }

    // ----------------------------------------------------------
    /**
     * Tests that isLessThanOrEqualTo throws an IllegalArgumentException when
     * called with a null parameter.
     */
    @Test
    public void testIsLessThanOrEqualToNull() 
    {
        GenreSet g1 = new GenreSet(30, 40, 30);
        try 
        {
            g1.isLessThanOrEqualTo(null);
            fail("Expected IllegalArgumentException to be thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            assertNotNull(e);
            assertEquals("Other GenreSet cannot be null", e.getMessage());
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the isWithinRange method  verifying
     * that it correctly checks if a genre set falls within range.
     */
    @Test
    public void testIsWithinRange() 
    {
        GenreSet min = new GenreSet(20, 30, 40);
        GenreSet max = new GenreSet(40, 50, 60);

        // Valid cases
        assertTrue(new GenreSet(30, 40, 50).isWithinRange(min, max));
        assertTrue(new GenreSet(20, 30, 40).isWithinRange(min, max)); 
        assertTrue(new GenreSet(40, 50, 60).isWithinRange(min, max)); 

        // Invalid cases
        assertFalse(new GenreSet(10, 20, 30).isWithinRange(min, max));
        assertFalse(new GenreSet(50, 60, 70).isWithinRange(min, max));
    }

    // ----------------------------------------------------------
    /**
     * Tests that isWithinRange throws an IllegalArgumentException when
     * called with a null minimum genre set.
     */
    @Test
    public void testIsWithinRangeNullMin() 
    {
        GenreSet max = new GenreSet(40, 50, 60);
        try 
        {
            new GenreSet(30, 40, 50).isWithinRange(null, max);
            fail("Expected IllegalArgumentException to be thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            assertNotNull(e);
            assertEquals("GenreSet cant be null", e.getMessage());
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that isWithinRange throws an IllegalArgumentException when
     * called with a null maximum genre set.
     */
    @Test
    public void testIsWithinRangeNullMax() 
    {
        GenreSet min = new GenreSet(20, 30, 40);
        try 
        {
            new GenreSet(30, 40, 50).isWithinRange(min, null);
            fail("Expected IllegalArgumentException to be thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            assertNotNull(e);
            assertEquals("GenreSet cant be null", e.getMessage());
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the equals method verifying it correctly compares
     * genre sets based on their genre percentages and handles null cases.
     */
    @Test
    public void testEquals() 
    {
        GenreSet g1 = new GenreSet(30, 40, 30);
        GenreSet g2 = new GenreSet(30, 40, 30);
        GenreSet g3 = new GenreSet(40, 50, 60);

        assertTrue(g1.equals(g2));
        assertFalse(g1.equals(g3));
        assertFalse(g1.equals(null));
        assertFalse(g1.equals(new Object()));
        assertTrue(g1.equals(g1)); 
    }

    // ----------------------------------------------------------
    /**
     * Tests the compareTo method verifying  it correctly compares
     * genre sets based on the sum of their genre percentages.
     */
    @Test
    public void testCompareTo() 
    {
        GenreSet g1 = new GenreSet(30, 40, 30);
        GenreSet g2 = new GenreSet(40, 50, 60);
        GenreSet g3 = new GenreSet(30, 40, 30);
        GenreSet g4 = new GenreSet(40, 30, 30);
        GenreSet g5 = new GenreSet(30, 30, 40);
        
        assertTrue(g1.compareTo(g2) < 0); 
        assertTrue(g2.compareTo(g1) > 0); 
        assertEquals(0, g1.compareTo(g3));
        assertTrue(g1.compareTo(g4) < 0);
        assertTrue(g1.compareTo(g5) > 0); 
    }

    // ----------------------------------------------------------
    /**
     * Tests that compareTo throws an IllegalArgumentException when
     * called with a null argument.
     */
    @Test
    public void testCompareToNull() 
    {
        GenreSet g1 = new GenreSet(30, 40, 30);
        try 
        {
            g1.compareTo(null);
            fail("Expected IllegalArgumentException to be thrown");
        } 
        catch (IllegalArgumentException e) 
        {
            assertNotNull(e);
            assertEquals("Other GenreSet cannot be null", e.getMessage());
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the toString method verifying that it correctly formats
     * the genre set as a string.
     */
    @Test
    public void testToString() 
    {
        GenreSet genreSet = new GenreSet(30, 40, 30);
        assertEquals("Pop:30 Rock:40 Country:30", genreSet.toString());
    }
    
    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws IllegalArgumentException 
     * for invalid pop values.
     */
    @Test
    public void testConstructorInvalidPop() 
    {
        try 
        {
            new GenreSet(-1, 50, 50);
            fail("Expected IllegalArgumentException for pop < 0");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Genre percentage must be between 0 and 100",
                e.getMessage()
            );
        }
        try 
        {
            new GenreSet(101, 50, 50);
            fail("Expected IllegalArgumentException for pop > 100");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Genre percentage must be between 0 and 100",
                e.getMessage()
            );
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws IllegalArgumentException
     * for invalid rock values.
     */
    @Test
    public void testConstructorInvalidRock() 
    {
        try 
        {
            new GenreSet(50, -1, 50);
            fail("Expected IllegalArgumentException for rock < 0");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Genre percentage must be between 0 and 100",
                e.getMessage()
            );
        }
        try 
        {
            new GenreSet(50, 101, 50);
            fail("Expected IllegalArgumentException for rock > 100");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Genre percentage must be between 0 and 100",
                e.getMessage()
            );
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests that the constructor throws IllegalArgumentException 
     * for invalid country values.
     */
    @Test
    public void testConstructorInvalidCountry() 
    {
        try 
        {
            new GenreSet(50, 50, -1);
            fail("Expected IllegalArgumentException for country < 0");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Genre percentage must be between 0 and 100",
                e.getMessage()
            );
        }
        try 
        {
            new GenreSet(50, 50, 101);
            fail("Expected IllegalArgumentException for country > 100");
        } 
        catch (IllegalArgumentException e) 
        {
            assertEquals(
                "Genre percentage must be between 0 and 100",
                e.getMessage()
            );
        }
    }
    
    // ----------------------------------------------------------
    /**
     * Tests equals returns true when comparing identical GenreSet objects.
     */
    @Test
    public void testEqualsIdentical() 
    {
        GenreSet g1 = new GenreSet(10, 20, 30);
        GenreSet g2 = new GenreSet(10, 20, 30);
        assertTrue(g1.equals(g2));
    }

    // ----------------------------------------------------------
    /**
     * Tests equals returns false when GenreSet objects differ in pop.
     */
    @Test
    public void testEqualsDifferentPop() 
    {
        GenreSet g1 = new GenreSet(10, 20, 30);
        GenreSet g2 = new GenreSet(11, 20, 30);
        assertFalse(g1.equals(g2));
    }

    // ----------------------------------------------------------
    /**
     * Tests equals returns false when GenreSet objects differ in rock.
     */
    @Test
    public void testEqualsDifferentRock() 
    {
        GenreSet g1 = new GenreSet(10, 20, 30);
        GenreSet g2 = new GenreSet(10, 21, 30);
        assertFalse(g1.equals(g2));
    }

    // ----------------------------------------------------------
    /**
     * Tests equals returns false when GenreSet objects differ in country.
     */
    @Test
    public void testEqualsDifferentCountry() 
    {
        GenreSet g1 = new GenreSet(10, 20, 30);
        GenreSet g2 = new GenreSet(10, 20, 31);
        assertFalse(g1.equals(g2));
    }

    // ----------------------------------------------------------
    /**
     * Tests equals returns true when comparing a GenreSet to itself.
     */
    @Test
    public void testEqualsItself() 
    {
        GenreSet g = new GenreSet(10, 20, 30);
        assertTrue(g.equals(g));
    }

    // ----------------------------------------------------------
    /**
     * Tests equals returns false when comparing to null.
     */
    @Test
    public void testEqualsNull() 
    {
        GenreSet g = new GenreSet(10, 20, 30);
        assertFalse(g.equals(null));
    }

    // ----------------------------------------------------------
    /**
     * Tests equals returns false when comparing to a different class.
     */
    @Test
    public void testEqualsDifferentClass() 
    {
        GenreSet g = new GenreSet(10, 20, 30);
        String notAGenreSet = "Not a GenreSet";
        assertFalse(g.equals(notAGenreSet));
    }
}
