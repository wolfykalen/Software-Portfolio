import java.io.IOException;
import student.TestCase;

/**
 * @author CS3114/5040 Staff
 * @version Summer 2025
 */
public class MovieRaterTest extends TestCase {

    private MovieRaterDB it;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new MovieRaterDB();
    }

    /**
     * Test clearing on initial
     * @throws IOException
     */
    public void testClearInit()
        throws IOException
    {
        assertTrue(it.clear());
    }

    /**
     * Test empty print movie or reviewer
     * @throws IOException
     */
    public void testRefMissing()
        throws IOException
    {
        assertNull(it.listMovie(2));
        assertNull(it.listReviewer(3));
        assertFalse(it.deleteScore(5, 1));
        assertFalse(it.deleteReviewer(2));
        assertFalse(it.deleteMovie(2));
    }


    /**
     * Test insert two items and print
     * @throws IOException
     */
    public void testRefinsertTwo()
        throws IOException
    {
        assertTrue(it.addReview(2, 3, 7));
        assertTrue(it.addReview(2, 5, 5));
        assertFuzzyEquals(it.printRatings(), "2: (3, 7) (5, 5)");
        assertFuzzyEquals(it.listMovie(3), "3: 7");
        assertFuzzyEquals(it.listReviewer(2), "2: 7 5");
    }

    /**
     * Test bad review values
     * @throws IOException
     */
    public void testRefBadRatings()
        throws IOException
    {
        assertFalse(it.addReview(2, 3, -1));
        assertFalse(it.addReview(2, 4, 0));
        assertFalse(it.addReview(2, 5, 20));
        assertFuzzyEquals(it.printRatings(), "");
    }


    /**
     * Test insert 5 items and print
     * @throws IOException
     */
    public void testRefinsertFive()
        throws IOException
    {
        assertTrue(it.addReview(7, 3, 10));
        assertTrue(it.addReview(2, 3, 7));
        assertTrue(it.addReview(3, 5, 8));
        assertTrue(it.addReview(5, 7, 9));
        assertTrue(it.addReview(7, 7, 1));
        assertFuzzyEquals(
            multiline(
                "2: (3, 7)",
                "3: (5, 8)",
                "5: (7, 9)",
                "7: (3, 10) (7, 1)"),
            it.printRatings());
    }

    /**
     * Updating an existing review should overwrite the score and reflect
     * in print and list outputs.
     */
    public void testUpdateReview() {
        assertTrue(it.addReview(1, 1, 4));
        assertTrue(it.addReview(1, 2, 6));
        // Update the (1,1) score from 4 to 9
        assertTrue(it.addReview(1, 1, 9));
        assertFuzzyEquals("1: (1, 9) (2, 6)", it.printRatings());
        assertFuzzyEquals("1: 9 6", it.listReviewer(1));
        assertFuzzyEquals("1: 9", it.listMovie(1));
    }

    /**
     * Deleting a score removes it deleting reviewer/movie removes all attached.
     */
    public void testDeleteScoreRowCol() {
        // Build matrix
        assertTrue(it.addReview(2, 1, 5));
        assertTrue(it.addReview(2, 3, 7));
        assertTrue(it.addReview(4, 3, 8));
        assertTrue(it.addReview(5, 2, 6));
        assertTrue(it.addReview(6, 2, 9));

        // Delete one score
        assertTrue(it.deleteScore(2, 3));
        assertFalse(it.deleteScore(2, 3)); 
        assertFuzzyEquals("3: 8", it.listMovie(3));
        assertFuzzyEquals("2: 5", it.listReviewer(2));

        // Delete a reviewer (4)
        assertTrue(it.deleteReviewer(4));
        assertFalse(it.deleteReviewer(4));
        assertNull(it.listReviewer(4));
        assertNull(it.listMovie(3));
        // Delete a movie (2)
        assertTrue(it.deleteMovie(2));
        assertFalse(it.deleteMovie(2));
        assertNull(it.listMovie(2));
        assertNull(it.listReviewer(5));
        assertNull(it.listReviewer(6));
        assertFuzzyEquals("2: (1, 5)", it.printRatings());
    }

    /**
     * Deep clear over many indices to check both clear() loops.
     * Seeds high reviewer/movie indices to force long iterations clears
     * and verifies no remnants  uses high indices to ensure no
     * links remain.
     */
    public void testClearDeep() {
        // Seed many spread-out reviewers/movies to enlarge headers
        int[] reviewers = {1, 2, 7, 64, 129, 257};
        int[] movies = {3, 5, 8, 33, 65, 131};
        int score = 5;
        for (int r : reviewers) {
            for (int m : movies) {
                if (((r + m) & 1) == 0) {
                    assertTrue(it.addReview(r, m, score));
                    score = (score % 10) + 1; 
                }
            }
        }
        assertFalse(it.printRatings().isEmpty());

        // Clear must remove all
        assertTrue(it.clear());
        assertEquals("", it.printRatings());
        for (int r : reviewers) {
            assertNull(it.listReviewer(r));
        }
        for (int m : movies) {
            assertNull(it.listMovie(m));
        }

        // Reuse high indices after clear without issues
        assertTrue(it.addReview(257, 131, 9));
        assertFuzzyEquals("257: 9", it.listReviewer(257));
        assertFuzzyEquals("131: 9", it.listMovie(131));
    }

    /**
     * Similar reviewer compute by average absolute 
     * difference across shared movies.
     */
    public void testSimilarReviewer() {
        // Base reviewer 2 overlaps with 1 and 3
        assertTrue(it.addReview(1, 1, 5)); 
        assertTrue(it.addReview(1, 2, 10)); 
        assertTrue(it.addReview(2, 1, 7));
        assertTrue(it.addReview(2, 3, 9));
        assertTrue(it.addReview(3, 1, 8)); 
        assertTrue(it.addReview(3, 3, 6)); 
        assertEquals(1, it.similarReviewer(2));

        // Reviewer 4 exists but has no overlap with others  no suitable match
        assertTrue(it.addReview(4, 4, 3));
        assertEquals(-1, it.similarReviewer(4));

        // Non-existent reviewer
        assertEquals(-1, it.similarReviewer(100));
    }

    /**
     * Similar movie compute by average absolute 
     * difference across shared reviewers.
     */
    public void testSimilarMovie() {
        // Base movie 10 overlaps with 12 and 13
        assertTrue(it.addReview(1, 10, 4));
        assertTrue(it.addReview(2, 10, 6));
        assertTrue(it.addReview(1, 12, 5)); 
        assertTrue(it.addReview(2, 12, 8)); 
        assertTrue(it.addReview(1, 13, 6)); 
        assertTrue(it.addReview(2, 13, 5)); 
        assertEquals(12, it.similarMovie(10));

        // Movie with no overlap
        assertTrue(it.addReview(3, 20, 9));
        assertEquals(-1, it.similarMovie(20));

        // Non-existent movie
        assertEquals(-1, it.similarMovie(999));
    }

    /**
     * Invalid indices and boundary checks.
     */
    public void testInvalidInputsAndBounds() {
        // Invalid reviewer/movie indices
        assertFalse(it.addReview(0, 1, 5));
        assertFalse(it.addReview(1, 0, 5));
        assertFalse(it.addReview(-1, 2, 5));
        assertFalse(it.addReview(2, -2, 5));
        // Invalid deleteScore indices
        assertFalse(it.deleteScore(0, 1));
        assertFalse(it.deleteScore(1, 0));
        // Listing out-of-range
        assertNull(it.listReviewer(0));
        assertNull(it.listMovie(0));
        // After adding valid ones, ensure order
        assertTrue(it.addReview(10, 2, 3));
        assertTrue(it.addReview(10, 5, 8));
        assertTrue(it.addReview(3, 4, 7));
        assertFuzzyEquals(
            multiline(
                "3: (4, 7)",
                "10: (2, 3) (5, 8)"),
            it.printRatings());
        assertFuzzyEquals("10: 3 8", it.listReviewer(10));
        assertFuzzyEquals("4: 7", it.listMovie(4));
    }

    /**
     * Similar reviewer with 3 shared movies and a clear non-tie winner.
     */
    public void testSimilarReviewerArithmeticNonTie() {
        assertTrue(it.addReview(10, 1, 6));
        assertTrue(it.addReview(10, 2, 5));
        assertTrue(it.addReview(10, 3, 7));

        assertTrue(it.addReview(11, 1, 4)); 
        assertTrue(it.addReview(11, 2, 7)); 
        assertTrue(it.addReview(11, 3, 5)); 

        assertTrue(it.addReview(12, 1, 5)); 
        assertTrue(it.addReview(12, 2, 7)); 
        assertTrue(it.addReview(12, 3, 6)); 

        assertEquals(12, it.similarReviewer(10));
    }

    /**
     * Similar movie with 3 shared reviewers and a clear non-tie winner.
     */
    public void testSimilarMovieArithmeticNonTie() {
        assertTrue(it.addReview(1, 300, 5));
        assertTrue(it.addReview(2, 300, 7));
        assertTrue(it.addReview(3, 300, 6));

        assertTrue(it.addReview(1, 301, 3)); 
        assertTrue(it.addReview(2, 301, 10)); 
        assertTrue(it.addReview(3, 301, 7)); 

        assertTrue(it.addReview(1, 302, 6)); 
        assertTrue(it.addReview(2, 302, 8)); 
        assertTrue(it.addReview(3, 302, 8)); 

        assertEquals(302, it.similarMovie(300));
    }

    /**
     * Attempting to delete a score for a movie index less than the first
     * entry in the row should return false and leave data intact
     */
    public void testDeleteScoreBeforeFirstColumn() {
        assertTrue(it.addReview(20, 5, 7));
        assertFalse(it.deleteScore(20, 1));
        assertFuzzyEquals("20: 7", it.listReviewer(20));
        assertFuzzyEquals("5: 7", it.listMovie(5));
    }

    /**
     * DeleteReviewer unlink variants ensure when removing a reviewer
     * column heads and internal links update correctly no matter
     * where the node is 
     */
    public void testDeleteReviewerUnlinkVariants() {
        assertTrue(it.addReview(30, 40, 5)); 
        assertTrue(it.addReview(35, 40, 7)); 
        assertTrue(it.addReview(32, 41, 6)); 
        assertTrue(it.addReview(34, 41, 8)); 
        assertTrue(it.addReview(36, 41, 9)); 
        assertTrue(it.deleteReviewer(30));
        assertFuzzyEquals("40: 7", it.listMovie(40));
        assertTrue(it.deleteReviewer(34));
        assertFuzzyEquals("41: 6 9", it.listMovie(41));

        // Delete non-existent reviewer returns false
        assertFalse(it.deleteReviewer(34));
    }

    /**
     * similarMovie skip paths where other column is empty 
     * and where there is no overlap Ensure the correct movie is
     * chosen among valid candidates.
     */
    public void testSimilarMovieSkipsAndSelection() {
        assertTrue(it.addReview(1, 400, 5));
        assertTrue(it.addReview(3, 400, 8));
        assertTrue(it.addReview(2, 403, 7));
        assertTrue(it.addReview(1, 404, 6));
        assertTrue(it.addReview(3, 405, 10));
        assertEquals(404, it.similarMovie(400));
    }

    /**
     * similarReviewer  skip paths where another row is empty and
     * another has no overlap ensure valid overlapping reviewer is chosen.
     */
    public void testSimilarReviewerSkipsAndSelection() {
        assertTrue(it.addReview(50, 10, 4));
        assertTrue(it.addReview(50, 12, 9));
        assertTrue(it.addReview(52, 11, 7));
        assertTrue(it.addReview(53, 10, 6));
        assertTrue(it.addReview(54, 12, 8));
        assertEquals(54, it.similarReviewer(50));
    }

    /**
     * deleteReviewer invalid bounds deleting reviewer 0 and a large index
     * beyond header capacity should return false.
     */
    public void testDeleteReviewerInvalidBounds() {
        assertFalse(it.deleteReviewer(0));
        assertFalse(it.deleteReviewer(100000));
    }

    /**
     * deleteScore tail deletion remove the last entry in a row to 
     * unlink when prevInRow != null and tail removal on column side.
     */
    public void testDeleteScoreTail() {
        assertTrue(it.addReview(60, 2, 3));
        assertTrue(it.addReview(60, 4, 5));
        assertTrue(it.addReview(60, 6, 7));
        assertTrue(it.addReview(61, 6, 9));
        assertTrue(it.deleteScore(60, 6));
        assertFuzzyEquals("60: 3 5", it.listReviewer(60));
        assertFuzzyEquals("6: 9", it.listMovie(6));
        // Deleting again should be false
        assertFalse(it.deleteScore(60, 6));
    }

    /**
     * ensureCapacity large doubling force multiple doublings 
     */
    public void testEnsureCapacityLargeDoubling() {
        assertTrue(it.addReview(1, 1024, 5));
        assertFuzzyEquals("1024: 5", it.listMovie(1024));
        assertTrue(it.addReview(2048, 2, 6));
        assertFuzzyEquals("2048: 6", it.listReviewer(2048));
        assertFuzzyEquals(
            multiline(
                "1: (1024, 5)",
                "2048: (2, 6)"),
            it.printRatings());
    }

    /**
     * ensureCapacity triggers when indices are exactly equal to current
     * header lengths 
     */
    public void testEnsureCapacityAtBoundaryEqualsInitialLength() {
   
        assertTrue(it.addReview(8, 8, 7));
        assertFuzzyEquals("8: 7", it.listReviewer(8));
        assertFuzzyEquals("8: 7", it.listMovie(8));
        assertFuzzyEquals("8: (8, 7)", it.printRatings());
    }

    /**
     * Resizing must preserve previously inserted data 
     * and must not overrun arrays
     */
    public void testResizePreservesDataAndNoOverrun() {
        assertTrue(it.addReview(1, 1, 4));
        assertTrue(it.addReview(2, 2, 5));
        assertTrue(it.addReview(1, 64, 9)); 
        assertTrue(it.addReview(64, 2, 6));
        assertFuzzyEquals("1: 4 9", it.listReviewer(1));
        assertFuzzyEquals("2: 5 6", it.listMovie(2));
        assertFuzzyEquals("64: 6", it.listReviewer(64));
        assertFuzzyEquals("64: 9", it.listMovie(64));
    }

    /**
     * ensureCapacity must update maxColIndex when c increases verify by
     *  behavior in similarMovie 
     */
    public void testMaxColIndexUpdatedForSimilarMovie() {
        assertTrue(it.addReview(1, 1, 5));
        assertTrue(it.addReview(2, 1, 7));
        assertTrue(it.addReview(1, 33, 6));
        assertTrue(it.addReview(2, 33, 8));
        assertEquals(33, it.similarMovie(1));
    }

    /**
     * ensureCapacity must update maxRowIndex when r increases verify by
     * behavior in similarReviewer 
     */
    public void testMaxRowIndexUpdatedForSimilarReviewer() {
        assertTrue(it.addReview(1, 5, 4));
        assertTrue(it.addReview(1, 6, 9));
        assertTrue(it.addReview(35, 5, 5));
        assertTrue(it.addReview(35, 6, 8));
        assertEquals(35, it.similarReviewer(1));
    }

    /**
     * growLen when current == 0 and targetIndex is small use
     * initial capacity (8) 
     */
    public void testGrowLenCurrentZeroTargetSmall() {
        int n = SparseRatings.computeGrowLenForTest(0, 0);
        assertEquals(8, n);
    }

    /**
     * growLen when current > targetIndex return current unchanged.
     */
    public void testGrowLenNoLoopWhenCurrentGreaterThanTarget() {
        int n = SparseRatings.computeGrowLenForTest(16, 7);
        assertEquals(16, n);
    }

    /**
     * growLen while condition true at equality boundary (n == targetIndex)
     * 
     */
    public void testGrowLenWhileEqualityBoundary() {
        int n = SparseRatings.computeGrowLenForTest(8, 8);
        assertEquals(16, n);
    }

    /**
     * growLen normal doubling over multiple iterations until passing target
     */
    public void testGrowLenMultipleDoublings() {
        int n = SparseRatings.computeGrowLenForTest(8, 20);
        assertEquals(32, n);
    }

    /**
     * growLen overflow guard starts near the int limit the
     * left shift produces a non positive value
     */
    public void testGrowLenOverflowSetsTargetPlusOne() {
        int start = 1 << 30;          
        int target = 1 << 30;         
        int n = SparseRatings.computeGrowLenForTest(start, target);
        assertEquals(target + 1, n);  
    }

    /**
     * growLen current not 0 and n already greater than target loop not entered
     * 
     */
    public void testGrowLenCurrentNonZeroAndNoLoop() {
        int n = SparseRatings.computeGrowLenForTest(1, 0);
        assertEquals(1, n);
    }
}
