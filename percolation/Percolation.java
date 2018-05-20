import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[] isSiteOpen;
    private int openSitesCount = 0;
    private WeightedQuickUnionUF qu;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than 0");

        this.n = n;
        qu = new WeightedQuickUnionUF(n * n + 2); // plus two virtual sites: [n*n] - top, [n*n+1] - bottom
        isSiteOpen = new boolean[n * n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == 0) qu.union(i * n + j, n * n); // connect first row to virtual top site
                if (i == n - 1) qu.union(i * n + j, n * n + 1); // connect last row to virtual bottom site
                isSiteOpen[i * n + j] = false;
            }
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (isOpen(row, col))
            return;

        int current = current(row, col);
        int right = current + 1;
        int left = current - 1;
        int up = current - n;
        int down = current + n;
        openSitesCount++;
        isSiteOpen[current] = true;

        if (right % n != 0 && isSiteOpen[right]) qu.union(current, right);
        if (current % n != 0 && isSiteOpen[left]) qu.union(current, left);
        if (down < n * n && isSiteOpen[down]) qu.union(current, down);
        if (up >= 0 && isSiteOpen[up]) qu.union(current, up);
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        validateArgs(row, col);
        return isSiteOpen[current(row, col)];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        return isOpen(row, col) && qu.connected(current(row, col), n * n);
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return openSitesCount;
    }

    public boolean percolates()              // does the system percolate?
    {
        return qu.connected(n * n, n * n + 1);
    }

    public static void main(String[] args)   // test client (optional)
    {

    }

    private int current(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void validateArgs(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException(String.format("Argument value must be between 1 and %d", n));
    }
}
