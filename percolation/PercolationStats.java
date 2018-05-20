import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] t;
    private int trials;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Parameters must be greater than 0.");

        this.trials = trials;
        t = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            t[i] = (double) p.numberOfOpenSites() / (double) (n * n);
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(t);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(t);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args)        // test client (described below)
    {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean = %f\n", p.mean());
        StdOut.printf("stddev = %f\n", p.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", p.confidenceLo(), p.confidenceHi());
    }
}
