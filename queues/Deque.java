import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return size;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new IllegalArgumentException("item == null");
        size++;

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (oldFirst != null) oldFirst.prev = first;
        first.next = oldFirst;
        first.prev = null;
        if (last == null) last = first;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new IllegalArgumentException("item == null");
        size++;

        if (last == null) {
            last = new Node();
            last.item = item;
            first = last;
        } else {
            last.next = new Node();
            last.next.prev = last;
            last.next.item = item;
            last = last.next;
        }
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException();
        size--;

        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException();
        size--;
        Item item = last.item;
        last = last.prev;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<String>();
        dq.addFirst("5");
        for (String i : dq) StdOut.printf(i);
        StdOut.println();
        dq.addLast("6");
        dq.addLast("7");
        dq.addFirst("4");
        dq.addFirst("3");
        for (String i : dq) StdOut.printf(i);
        StdOut.println();
        dq.removeLast();
        dq.removeLast();
        dq.removeLast();
        dq.removeFirst();
        for (String i : dq) StdOut.printf(i);
        dq.removeLast();
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}