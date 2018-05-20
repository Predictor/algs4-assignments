import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (k == 0) continue;
            if (queue.size() == k) queue.dequeue();
            queue.enqueue(s);
        }
        if (k == 0) return;
        for (String s : queue)
            StdOut.println(s);
    }
}
