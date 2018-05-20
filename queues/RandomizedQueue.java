import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int size;

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return size;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new IllegalArgumentException("item == null");
        if (isEmpty()) q = (Item[]) new Object[1];
        size++;
        if (size >= q.length) resize(size * 2);
        q[size - 1] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(size);
        Item item = q[n];
        q[n] = q[size - 1];
        q[size - 1] = null;
        size--;
        if (size < q.length / 4) resize(q.length / 2);
        return item;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(size);
        return q[n];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = q[i];
        q = copy;
    }

    private class RandomIterator implements Iterator<Item> {
        private final int[] index = StdRandom.permutation(size);
        private int current;

        public boolean hasNext() {
            return current < index.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[index[current++]];
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        // no op
    }

}