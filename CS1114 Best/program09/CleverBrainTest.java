// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- kalendaco

import student.micro.*;
import static org.assertj.core.api.Assertions.*;
import student.tetris.*;
// -------------------------------------------------------------------------
/**
 *  A test class for the CleverBrain class
 *
 *  @author kalendaco
 *  @version 2023.10.31
 */
public class CleverBrainTest
    extends TestCase
{
    //~ Fields ................................................................
    private Board board;
    private Brain brain;

    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new CleverBrainTest test object.
     */
    public CleverBrainTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }


    //~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    public void setUp()
    {
        board = new Board(10, 5,
            "          ",
            "          ",
            "          ",
            "       #  ",
            "####  ### "
        );
        
        brain = new CleverBrain();
    }


    // ----------------------------------------------------------
    /**
     * Tests the BestMove Method
     */
    public void testBestMove1()
    {     
        Piece piece = Piece.getPiece(Piece.T, 0);
        Move move = brain.bestMove(board, piece, 5);
    
        assertThat(move.getPiece()).isEqualTo(Piece.getPiece(Piece.T, 2));

    
        assertThat(move.getLocation()).isEqualTo(new Point(5, 0));
    }
}
