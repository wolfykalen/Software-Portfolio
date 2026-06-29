//-------------------------------------------------------------------------
/**
 * SparseRatings implements a sparse matrix using orthogonal lists for storing
 * movie ratings by reviewer.
 *
 * Rows correspond to reviewers columns correspond to movies; and each
 * non-empty cell stores a rating from 1 to 10.
 *
 * This class supports the sparse matrix operations needed by MovieRaterDB
 * insert/update delete row/column/cell, row/column listing printing all
 * ratings ordered, and similarity computations across rows or columns.
 *
 * @author Kalendaco
 * @version Fall 2025
 */
public class SparseRatings {

    /** 
     * Initial header array capacity. 
     */
    private static final int INITIAL_CAPACITY = 8; // small, resizes as needed

    /** 
     * Node for orthogonal list. 
     */
    private static final class Node {
        /** 
         * Reviewer index (row). 
         */
        int r;
        /** 
         * Movie index (column). 
         */
        int c;
        /** 
         * Rating value (1-10). 
         */
        int score;
        /**
         *  Next node to the right in the same row (increasing col index). 
         **/
        Node right;
        /** 
         * Next node downward in the same column (increasing row index). 
         */
        Node down;

        Node(int rr, int cc, int sc) {
            r = rr;
            c = cc;
            score = sc;
        }
    }

    /** 
     * Row header array index i holds head of row i list or null 
     */
    private Node[] rowHeads;
    /** 
     * Column header array index j holds head of column j list or null
     */
    private Node[] colHeads;
    /** 
     * Largest reviewer index ever seen (for iteration bounds)
     */
    private int maxRowIndex;
    /** 
     * Largest movie index ever seen 
     */
    private int maxColIndex;
    /** 
     * Number of stored ratings  
     */
    private int nnz;

    /** 
     * Construct empty sparse ratings structure. 
     */
    public SparseRatings() {
        rowHeads = new Node[INITIAL_CAPACITY];
        colHeads = new Node[INITIAL_CAPACITY];
        maxRowIndex = 0;
        maxColIndex = 0;
        nnz = 0;
    }

    /** 
     * Remove all data. 
     */
    public void clear() {
        for (int i = 0; i < rowHeads.length; i++) {
            rowHeads[i] = null;
        }
        for (int j = 0; j < colHeads.length; j++) {
            colHeads[j] = null;
        }
        maxRowIndex = 0;
        maxColIndex = 0;
        nnz = 0;
    }

    /** 
     * Ensure row and column header arrays can hold the given indices
     */
    private void ensureCapacity(int r, int c) {
        if (r >= rowHeads.length) {
            int newLen = growLen(rowHeads.length, r);
            Node[] newArr = new Node[newLen];
            for (int i = 0; i < rowHeads.length; i++) {
                newArr[i] = rowHeads[i];
            }
            rowHeads = newArr;
        }
        if (c >= colHeads.length) {
            int newLen = growLen(colHeads.length, c);
            Node[] newArr = new Node[newLen];
            for (int j = 0; j < colHeads.length; j++) {
                newArr[j] = colHeads[j];
            }
            colHeads = newArr;
        }
        if (r > maxRowIndex) {
            maxRowIndex = r;
        }
        if (c > maxColIndex) {
            maxColIndex = c;
        }
    }

    /** 
     * Compute a new array length to fit at least up to index target.
     */
    private static int growLen(int current, int targetIndex) {
        int n = current;
        if (n == 0) {
            n = INITIAL_CAPACITY;
        }
        while (n <= targetIndex) {
            n = n << 1; 
            if (n <= 0) { 
                n = targetIndex + 1;
                break;
            }
        }
        return n;
    }

    /**
     * Validate inputs reviewer > 0, movie > 0, score within 1..10.
     *
     * @param reviewer the reviewer index (must be positive)
     * @param movie the movie index (must be positive)
     * @param score the rating score (1-10 inclusive)
     * @return true if all inputs are valid false otherwise
     */
    public static boolean validInputs(int reviewer, int movie, int score) {
        return reviewer > 0 && movie > 0 && score >= 1 && score <= 10;
    }

    /**
     * Check only indices (no score).
     *
     * @param reviewer the reviewer index (must be positive)
     * @param movie the movie index (must be positive)
     * @return true if both indices are valid false otherwise
     */
    public static boolean validIndices(int reviewer, int movie) {
        return reviewer > 0 && movie > 0;
    }

    /**
     * Insert or update a rating.
     * Returns true on success; false if inputs are invalid.
     *
     * @param reviewer the reviewer index (must be positive)
     * @param movie the movie index (must be positive)
     * @param score the rating score (1-10 inclusive)
     * @return true if inserted or updated successfully; false if inputs invalid
     */
    public boolean put(int reviewer, int movie, int score) {
        if (!validInputs(reviewer, movie, score)) {
            return false;
        }
        ensureCapacity(reviewer, movie);

        // Insert/update in row (ordered by column index)
        Node rowHead = rowHeads[reviewer];
        Node prevInRow = null;
        Node curInRow = rowHead;
        while (curInRow != null && curInRow.c < movie) {
            prevInRow = curInRow;
            curInRow = curInRow.right;
        }

        if (curInRow != null && curInRow.c == movie) {
            // Update existing
            curInRow.score = score;
            return true;
        }

        // Not found: create new node
        Node n = new Node(reviewer, movie, score);
        // link into row list
        if (prevInRow == null) {
            n.right = rowHead;
            rowHeads[reviewer] = n;
        }
        else {
            n.right = prevInRow.right;
            prevInRow.right = n;
        }

        // Insert into column (ordered by row index)
        Node colHead = colHeads[movie];
        Node prevInCol = null;
        Node curInCol = colHead;
        while (curInCol != null && curInCol.r < reviewer) {
            prevInCol = curInCol;
            curInCol = curInCol.down;
        }
        if (prevInCol == null) {
            n.down = colHead;
            colHeads[movie] = n;
        }
        else {
            n.down = prevInCol.down;
            prevInCol.down = n;
        }

        nnz++;
        return true;
    }

    /**
     * Delete a single score cell.
     *
     * @param reviewer the reviewer index of the score to delete
     * @param movie the movie index of the score to delete
     * @return true if deleted false if not present or indices invalid
     */
    public boolean deleteScore(int reviewer, int movie) {
        if (!validIndices(reviewer, movie)) {
            return false;
        }
        if (reviewer >= rowHeads.length || movie >= colHeads.length) {
            return false;
        }
        // unlink from row
        Node prevInRow = null;
        Node curInRow = rowHeads[reviewer];
        while (curInRow != null && curInRow.c < movie) {
            prevInRow = curInRow;
            curInRow = curInRow.right;
        }
        if (curInRow == null || curInRow.c != movie) {
            return false; 
        }
        // curInRow is the node to delete
        if (prevInRow == null) {
            rowHeads[reviewer] = curInRow.right;
        }
        else {
            prevInRow.right = curInRow.right;
        }
        // unlink from column
        Node prevInCol = null;
        Node curInCol = colHeads[movie];
        while (curInCol != null && curInCol != curInRow) {
            prevInCol = curInCol;
            curInCol = curInCol.down;
        }
        if (curInCol == curInRow) {
            if (prevInCol == null) {
                colHeads[movie] = curInRow.down;
            }
            else {
                prevInCol.down = curInRow.down;
            }
        }
        nnz--;
        return true;
    }

    /**
     * Delete entire reviewer row.
     *
     * @param reviewer the reviewer index to delete
     * @return true if any ratings were deleted for this reviewer false if the
     *         reviewer does not exist in the database
     */
    public boolean deleteReviewer(int reviewer) {
        if (reviewer <= 0 || reviewer >= rowHeads.length) {
            return false;
        }
        Node cur = rowHeads[reviewer];
        if (cur == null) {
            return false;
        }
        // For every node in row, unlink from column list
        while (cur != null) {
            Node toDelete = cur;
            cur = cur.right;
            // unlink from column
            int movie = toDelete.c;
            Node prevInCol = null;
            Node colCur = colHeads[movie];
            while (colCur != null && colCur != toDelete) {
                prevInCol = colCur;
                colCur = colCur.down;
            }
            if (colCur == toDelete) {
                if (prevInCol == null) {
                    colHeads[movie] = toDelete.down;
                }
                else {
                    prevInCol.down = toDelete.down;
                }
            }
            nnz--;
        }
        rowHeads[reviewer] = null;
        return true;
    }

    /**
     * Delete entire movie column.
     *
     * @param movie the movie index to delete
     * @return true if any ratings were deleted for this movie false if the
     *         movie does not exist in the database
     */
    public boolean deleteMovie(int movie) {
        if (movie <= 0 || movie >= colHeads.length) {
            return false;
        }
        Node cur = colHeads[movie];
        if (cur == null) {
            return false;
        }
        // For every node in column unlink from row list
        while (cur != null) {
            Node toDelete = cur;
            cur = cur.down;
            int reviewer = toDelete.r;
            Node prevInRow = null;
            Node rowCur = rowHeads[reviewer];
            while (rowCur != null && rowCur != toDelete) {
                prevInRow = rowCur;
                rowCur = rowCur.right;
            }
            if (rowCur == toDelete) {
                if (prevInRow == null) {
                    rowHeads[reviewer] = toDelete.right;
                }
                else {
                    prevInRow.right = toDelete.right;
                }
            }
            nnz--;
        }
        colHeads[movie] = null;
        return true;
    }

    /**
     * Return a string listing all ratings each reviewer on its own line in
     * ascending reviewer index, with movie/score pairs in ascending movie
     * index Returns the empty string if there are no ratings.
     *
     * @return formatted string of all ratings, or empty string if none
     */
    public String printAll() {
        if (nnz == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean anyRow = false;
        for (int r = 1; r <= maxRowIndex; r++) {
            Node head = (r < rowHeads.length) ? rowHeads[r] : null;
            if (head == null) {
                continue;
            }
            if (anyRow) {
                sb.append('\n');
            }
            anyRow = true;
            sb.append(r).append(":");
            Node cur = head;
            while (cur != null) {
                sb.append(' ')
                .append('(')
                .append(cur.c)
                .append(", ")
                .append(cur.score)
                    .append(')');
                cur = cur.right;
            }
        }
        return sb.toString();
    }

    /**
     * List all ratings for a given reviewer, with movies in ascending order.
     * Returns null if no such reviewer exists.
     *
     * @param reviewer the reviewer index to list
     * @return formatted string "r: s1 s2 ..." or null if no such reviewer
     */
    public String listReviewer(int reviewer) {
        if (reviewer <= 0 || reviewer >= rowHeads.length) {
            return null;
        }
        Node head = rowHeads[reviewer];
        if (head == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(reviewer).append(":");
        Node cur = head;
        while (cur != null) {
            sb.append(' ').append(cur.score);
            cur = cur.right;
        }
        return sb.toString();
    }

    /**
     * List all ratings for a given movie, with reviewers in ascending order.
     * Returns null if no such movie exists.
     *
     * @param movie the movie index to list
     * @return formatted string "m: s1 s2 ..." or null if no such movie
     */
    public String listMovie(int movie) {
        if (movie <= 0 || movie >= colHeads.length) {
            return null;
        }
        Node head = colHeads[movie];
        if (head == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(movie).append(":");
        Node cur = head;
        while (cur != null) {
            sb.append(' ').append(cur.score);
            cur = cur.down;
        }
        return sb.toString();
    }

    /**
     * Compute most similar reviewer to the given reviewer using average
     * absolute difference across shared movies.
     *
     * @param reviewer the reviewer to compare to others
     * @return the most similar reviewer index; -1 if none or reviewer missing
     */
    public int similarReviewer(int reviewer) {
        if (reviewer <= 0 || reviewer >= rowHeads.length) {
            return -1;
        }
        Node baseHead = rowHeads[reviewer];
        if (baseHead == null) {
            return -1;
        }
        int bestIdx = -1;
        double bestScore = Double.MAX_VALUE;
        // For all other reviewers that exist 
        for (int r = 1; r <= maxRowIndex; r++) {
            if (r == reviewer || r >= rowHeads.length) {
                continue;
            }
            Node other = rowHeads[r];
            if (other == null) {
                continue;
            }
            Node a = baseHead;
            Node b = other;
            int common = 0;
            int sumAbs = 0;
            while (a != null && b != null) {
                if (a.c == b.c) {
                    int diff = a.score - b.score;
                    sumAbs += (diff < 0) ? -diff : diff;
                    common++;
                    a = a.right;
                    b = b.right;
                }
                else if (a.c < b.c) {
                    a = a.right;
                }
                else {
                    b = b.right;
                }
            }
            if (common == 0) {
                continue; 
            }
            double score = ((double)sumAbs) / ((double)common);
            if (score < bestScore || (score == bestScore && r < bestIdx)) {
                bestScore = score;
                bestIdx = r;
            }
        }
        return bestIdx;
    }

    /**
     * Compute most similar movie to the given movie using average absolute
     * difference across shared reviewers.
     *
     * @param movie the movie to compare to others
     * @return the most similar movie index -1 if none or movie missing
     */
    public int similarMovie(int movie) {
        if (movie <= 0 || movie >= colHeads.length) {
            return -1;
        }
        Node baseHead = colHeads[movie];
        if (baseHead == null) {
            return -1;
        }
        int bestIdx = -1;
        double bestScore = Double.MAX_VALUE;
        for (int c = 1; c <= maxColIndex; c++) {
            if (c == movie || c >= colHeads.length) {
                continue;
            }
            Node other = colHeads[c];
            if (other == null) {
                continue;
            }
            Node a = baseHead;
            Node b = other;
            int common = 0;
            int sumAbs = 0;
            while (a != null && b != null) {
                if (a.r == b.r) {
                    int diff = a.score - b.score;
                    sumAbs += (diff < 0) ? -diff : diff;
                    common++;
                    a = a.down;
                    b = b.down;
                }
                else if (a.r < b.r) {
                    a = a.down;
                }
                else {
                    b = b.down;
                }
            }
            if (common == 0) {
                continue;
            }
            double score = ((double)sumAbs) / ((double)common);
            if (score < bestScore || (score == bestScore && c < bestIdx)) {
                bestScore = score;
                bestIdx = c;
            }
        }
        return bestIdx;
    }

    /**
     * Number of ratings stored.
     *
     * @return the count of non-empty cells (ratings)
     */
    public int size() {
        return nnz;
    }

    /**
     * Test helper expose growLen for unit tests without forcing array 
    * allocations
     *
     * @param current the current capacity value (may be 0)
     * @param targetIndex the target index that must be accommodated
     * @return a capacity value large enough to include targetIndex
     */
    static int computeGrowLenForTest(int current, int targetIndex) {
        return growLen(current, targetIndex);
    }
}
