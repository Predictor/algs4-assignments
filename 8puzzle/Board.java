import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    private final int sz;                                  // dimension of board
    private final int[] board;                         // board of interest

    public Board(int[][] blocks) {         // construct a board from an N-by-N array of blocks
        sz = blocks[0].length;
        board = new int[sz * sz];
        for (int i = 0; i < sz; i++)           // blocks[i][j]: block in row i, column j
            for (int j = 0; j < sz; j++)
                board[i * sz + j] = blocks[i][j];
    }

    private Board(int[] board) {            // private constructor useful in twin()
        sz = (int) Math.sqrt(board.length);
        this.board = new int[board.length];
        for (int i = 0; i < board.length; i++)
            this.board[i] = board[i];
    }

    public int dimension() {                 // board dimension N
        return sz;
    }

    public int hamming() {                  // number of blocks out of place
        int count = 0;
        for (int i = 0; i < sz * sz; i++)      // compare board[1] through board[N^2-1] with goal
            if (board[i] != i + 1 && board[i] != 0)                  // count for blocks in wrong place
                count++;
        return count;
    }

    public int manhattan() {               // sum of Manhattan distances between blocks and goal
        int sum = 0;
        for (int i = 0; i < sz * sz; i++)
            if (board[i] != i + 1 && board[i] != 0)
                sum += manhattan(board[i], i);
        return sum;
    }

    private int manhattan(int goal, int current) {  // return manhattan distance of a misplaced block
        int row, col;                                                // row and column distance from the goal
        row = Math.abs((goal - 1) / sz - current / sz);              // row difference
        col = Math.abs((goal - 1) % sz - current % sz);             // column difference
        return row + col;
    }

    public boolean isGoal() {              // is this board the goal board?
        for (int i = 0; i < sz * sz - 1; i++)
            if (board[i] != i + 1)
                return false;
        return true;
    }

    public Board twin() {                  // a board obtained by exchanging two adjacent blocks in the same row
        Board twin;
        if (sz == 1) return null;                        // check if twin board exists
        twin = new Board(board);

        if (board[0] != 0 && board[1] != 0)
            twin.exch(0, 1);                // if the first two blocks in first row is not empty, exchange them.
        else
            twin.exch(sz, sz + 1);  // otherwise, exchange the first two blocks on second row.
        return twin;
    }

    private Board exch(int i, int j) { // exchange two elements in the array
        int temp = board[j];
        board[j] = board[i];
        board[i] = temp;
        return this;
    }

    public boolean equals(Object y) {      // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        return Arrays.equals(this.board, that.board);
    }

    public Iterable<Board> neighbors() {    // all neighboring boards
        int index = 0;                               // record the position of empty block
        boolean found = false;                       // if empty block is found
        Board neighbor;
        LinkedList<Board> q = new LinkedList<Board>();

        for (int i = 0; i < board.length; i++)    // search for empty block
            if (board[i] == 0) {
                index = i;
                found = true;
                break;
            }
        if (!found) return null;

        if (index / sz != 0) {                   // if not first row
            neighbor = new Board(board);
            neighbor.exch(index, index - sz);  // exchange with upper block
            q.add(neighbor);
        }

        if (index / sz != (sz - 1)) {            // if not last row
            neighbor = new Board(board);
            neighbor.exch(index, index + sz);  // exchange with lower block
            q.add(neighbor);
        }

        if ((index % sz) != 0) {                // if not leftmost column
            neighbor = new Board(board);
            neighbor.exch(index, index - 1);  // exchange with left block
            q.add(neighbor);
        }

        if ((index % sz) != sz - 1) {           // if not rightmost column
            neighbor = new Board(board);
            neighbor.exch(index, index + 1);  // exchange with right block
            q.add(neighbor);
        }

        return q;
    }

    public String toString() {              // string representation of the board
        StringBuilder s = new StringBuilder();
        s.append(sz + "\n");
        for (int i = 0; i < board.length; i++) {
            s.append(String.format("%2d ", board[i]));
            if (i % sz == 0)
                s.append("\n");
        }
        return s.toString();
    }
}


