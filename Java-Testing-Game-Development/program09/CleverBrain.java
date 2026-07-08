// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- kalendaco
import student.tetris.*;
//-------------------------------------------------------------------------
/**
 *  A class that creates a new and smarter brain to complete a game of
 *  tetris to the best of its ability by running through for loops
 *
 *  @author kalendaco
 *  @version 2023.10.31
 */
public class CleverBrain
    implements Brain
{
    //~ Fields ................................................................

    //~ Constructor ...........................................................
    // ----------------------------------------------------------
    /**
     * Initializes a newly created CleverBrain object.
     */
    public CleverBrain()
    {
        super();
        /*# Do any work to initialize your class here. */
    }

    //~ Methods ...............................................................

    /**
     * utilizing the rateBoard method the best possible move is 
     * determined and executed 
     * @param board the board being used
     * @param piece the piece being placed
     * @param heightLimit shows the limit the individual columns can 
     * reach before the game is ends
     * @return returns the move of the best possible action
     */
    public Move bestMove(Board board, Piece piece, int heightLimit)
    {
        Move bestMove = null;
        double bestScore = Double.MAX_VALUE;
        for (Piece p : piece.getRotations())
        {
            for (int i = 0; i < board.getWidth() - p.getWidth() + 1; i++)
            {
                Move m = new Move(p);
                movePiece(p, board, i, m);
                if ((i < heightLimit && rateBoard(board) < bestScore)
                || i == heightLimit)
                {
                    bestScore = rateBoard(board);
                    bestMove = m;
                }

                board.undo();
            }
        }
        bestMove.setScore(bestScore);
        return bestMove;
    }

    /**
     * takes the average height of all the columns then takes the 
     * total number of blocks * the highest column 
     * @param board the board that is used
     * @return returns the rating for all possible moves to be used in 
     * bestMove method
     */
    public double rateBoard(Board board)
    {
        int highest = 0;
        for (int i = 0; i < board.getColumnHeights().length; i++)
        {
            if (board.getColumnHeights()[i] > highest)
            {
                highest = board.getColumnHeights()[i];
            }
        }

        int totalblocks = 0;
        for (int i = 0; i < board.getColumnHeights().length; i++)
        {
            totalblocks += board.getColumnHeights()[i];
        }

        int avg = totalblocks / board.getColumnHeights().length;

        return highest * totalblocks * avg;
    }

    /**
     * makes a move on the board then assings that spot to a value
     * sets the location of a  move to that point and clear the rows.
     * @param piece the piece being placed
     * @param board the board being used
     * @param column the column that is being assigned to the 
     * point on the board for the piece
     * @param newMove the move that ends up being executed
     */
    public void movePiece(Piece piece, Board board, int column, Move newMove)
    {
        int y = board.rowAfterDrop(piece, column);
        Point point = new Point(column, y);
        newMove.setLocation(point);
        board.place(piece, point);
        board.clearRows();
    }
}