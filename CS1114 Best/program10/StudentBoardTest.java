import student.micro.*;
import static org.assertj.core.api.Assertions.*;
import student.tetris2.*;
// -------------------------------------------------------------------------
/**
 *  A test class for StudentBoard
 *
 *  @author kalendaco
 *  @version 2023.11.07
 */
public class StudentBoardTest
    extends TestCase
{
    //~ Fields ................................................................


    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new StudentBoardTest test object.
     */
    public StudentBoardTest()
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
        /*# Insert your own setup code here */
    }


    // ----------------------------------------------------------
    /*# Insert your own test methods here */
    /**
     * test getters of height and width
     */
    public void testGetHeightWidth()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            10, 24,
            "#### #####"
            );
        assertThat(board.getWidth()).isEqualTo(10);
        assertThat(board.getHeight()).isEqualTo(24);
    }
    
    /**
     * test columnHeights method
     */
    public void testColumnHeights()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "#    ",
            "### #"
            );
        assertThat(board.getColumnHeights()[0]).isEqualTo(2);
    }
    
    /**
     * test columnHeights method
     */
    public void testColumnHeights1()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "     ",
            "     "
            );
        assertThat(board.getColumnHeights()[0]).isEqualTo(0);
    }
    
    /**
     * test getBlocksInAllRows
     */
    public void testGetBlocksInAllRows()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "# #  ",
            "#####"
            );
        assertThat(board.getBlocksInAllRows()[0]).isEqualTo(5);
        assertThat(board.getBlocksInAllRows()[1]).isEqualTo(2);
    }
    
    /**
     * test checkBlocksInAllRows
     */
    public void testCheckBlocksInAllRows()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "     ",
            "#####"
            );
        assertThat(board.checkBlocksInAllRows()).isEqualTo(1);
        
        StudentBoard board1 = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "     ",
            "## ##"
            );
        assertThat(board1.checkBlocksInAllRows()).isEqualTo(0);
    }
    
    /**
     * test hasBlockAt
     */
    public void testHasBlockAt()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "# #  ",
            "#####"
            );
        Point point = new Point(0, 0);
        assertThat(board.hasBlockAt(point)).isEqualTo(true);
        
        StudentBoard board1 = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "     ",
            " ####"
            );
        Point point1 = new Point(0, 0);
        assertThat(board1.hasBlockAt(point1)).isEqualTo(false);
    }
    
    /**
     * test place method with an L piece
     */
    public void testLOnRow0()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            10, 24,
            "#### #####"
            );
            
        Piece piece = Piece.getPiece(Piece.RIGHT_L, 3);
        
        int result = board.place(piece, new Point(4, 0));
        
        assertThat(result).isEqualTo(Board.PLACE_ROW_FILLED);
        
        int[] blockInRows = board.getBlocksInAllRows();
        assertThat(blockInRows[0]).isEqualTo(board.getWidth());
        
        assertThat(blockInRows[1]).isEqualTo(3);
        
        assertThat(board.getColumnHeights()).isEqualTo(new int[] {
            1, 1, 1, 1, 2, 2, 2, 1, 1, 1});
            
        StudentBoard board2 = BoardUtilities.newBoard(StudentBoard.class,
            10, 24,
            "#### #####"
            );
        Piece piece2 = Piece.getPiece(Piece.RIGHT_L, 3);
        int result2 = board2.place(piece2, new Point(0, 0));
        assertThat(result2).isEqualTo(3);
    }
    
    /**
     * test place method when out of bounds
     */
    public void testPlace1()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            10, 24,
            "#### #####"
            );
        Piece piece = Piece.getPiece(Piece.RIGHT_L, 3);
        int result = board.place(piece, new Point(11, 0));
        assertThat(result).isEqualTo(2);
        
        int result1 = board.place(piece, new Point(5, 25));
        assertThat(result1).isEqualTo(2);    
 
        int result2 = board.place(piece, new Point(11, 25));
        assertThat(result2).isEqualTo(2);
        
        int result3 = board.place(piece, new Point(-1, 0));
        assertThat(result3).isEqualTo(2);
        
        int result4 = board.place(piece, new Point(0, -1));
        assertThat(result4).isEqualTo(3);

        int result5 = board.place(piece, new Point(-1, -1));
        assertThat(result5).isEqualTo(2);
        
        int result6 = board.place(piece, new Point(0, 0));
        assertThat(result6).isEqualTo(3);
    }
    
    /**
     * test clearRows
     */
    public void testClearRows()
    {
        StudentBoard board = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "# # #",
            "# # #",
            "#####"
            );
        assertThat(board.clearRows()).isEqualTo(true);
        
        StudentBoard board1 = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "     ",
            "     ",
            "     ",
            "     "
            );
        assertThat(board1.clearRows()).isEqualTo(false);
        
        StudentBoard board2 = BoardUtilities.newBoard(StudentBoard.class,
            5, 5,
            "     ",
            "   ##",
            "###  ",
            "# ## ",
            "#### "
            );
        assertThat(board2.clearRows()).isEqualTo(false);
    }
}
