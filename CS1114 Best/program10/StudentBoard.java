// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- kalendaco

import student.tetris2.*;
//-------------------------------------------------------------------------
/**
 *  The StudentBoard class implements the Board interface. It records
 *  and places the pieces on the board.
 *
 *  @author kalendaco
 *  @version 2023.11.07
 */
public class StudentBoard
    implements Board
{
    //~ Fields ................................................................
    private int width;
    private int height;
    private int[] columnHeights;
    private int[] blocksInAllRows;
    private boolean[][] grid;


    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the StudentBoard object.
     * @param myWidth the width the board
     * @param myHeight the height of the board
     */
    public StudentBoard(int myWidth, int myHeight)
    {
        super();
        /*# Do any work to initialize your class here. */
        this.width = myWidth;
        this.height = myHeight;
        this.columnHeights = new int[width];
        this.blocksInAllRows = new int [height];
        this.grid = new boolean[height][width];
    }


    //~ Methods ...............................................................

    /**
     * Getter for the width
     * @return width of the board
     */
    public int getWidth()
    {
        return this.width;
    }
    
    /**
     * getter for the height
     * @return height of the board
     */
    public int getHeight()
    {
        return this.height;
    }
    
    /**
     * will return an array containing all heights in 
     * the columns 
     * @return the necessary array
     */
    public int[] getColumnHeights()
    {
        return this.columnHeights;
    }
    
    /**
     * will check the columns height after placing pieces
     */
    public void checkColumnHeight()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int column = 0; column < grid[row].length; column++)
            {
                if (grid[row][column])
                {
                    columnHeights[column] = row + 1;
                }
            }
        }
    }
    
    /**
     * returns an array showing filled blocks in a row
     * @return an array representing the filled blocks in a row
     */
    public int[] getBlocksInAllRows()
    {
        return this.blocksInAllRows;
    }
    
    /**
     * checks if a piece can fit in a row
     * @return int is a constant defined in Board
     */
    public int checkBlocksInAllRows()
    {
        for (int i = 0; i < this.getBlocksInAllRows().length; i++)
        {
            if (this.blocksInAllRows[i] == this.width)
            {
                return Board.PLACE_ROW_FILLED;
            }
        }
        return Board.PLACE_OK;
    }
    
    /**
     * returns a 2D array depicting the current board 
     * @return a 2d array representing all locations on the board
     */
    public boolean[][] getGrid()
    {
        return this.grid;
    }
    
    /**
     * returns true if given space is filled 
     * otherwise returns false .
     * @param point the location being checked
     * @return True if there is a block at the point being checked
     */
    public boolean hasBlockAt(Point point)
    {
        int row = point.getY();
        int column = point.getX();
        return this.getGrid()[row][column];
    }
    
    /**
     * places a piece on the board
     * @param piece the piece being placed
     * @param location the location of the piece being placed
     * @return int indicates wheter the piece was succesfully placed or not
     */
    public int place(Piece piece, Point location)
    {
        for (int i = 0; i < piece.getBody().length; i++)
        {
            Point[] nPoints = piece.getBody();
            Point point = nPoints[i];
            
            int locationX = location.getX();
            int locationY = location.getY();
            
            int destX = locationX + point.getX();
            int destY = locationY + point.getY();
            
            if (destX > this.getWidth() || destX < 0 ||
                destY > this.getHeight() || destY < 0)
            {
                return Board.PLACE_OUT_BOUNDS;
            }
            else if (destX > this.getWidth() - 1 ||
                destY > this.getHeight() - 1)
            {
                return Board.PLACE_OUT_BOUNDS;
            }
            else if (grid[destY][destX])
            {
                return Board.PLACE_BAD;
            }
            else
            {
                grid[destY][destX] = true;
                this.blocksInAllRows[destY]++;
                this.checkColumnHeight();
            }
        }
        return this.checkBlocksInAllRows();
    }
    
    /**
     * Deletes blocks in a row when they've become full 
     * blocks above drop a row below  
     * @return boolean true if a row becomes cleared
     */
    public boolean clearRows()
    {
        for (int i = 0; i < this.height; i++)
        {
            if (blocksInAllRows[i] == this.width)
            {
                for (int j = i + 1; j < this.height; j++)
                {
                    this.grid[i] = this.grid[j];
                }
                boolean[] r = new boolean[width];
                grid[this.height - 1] = r;
                return true;
            }
        }
        return false;
    }
}
