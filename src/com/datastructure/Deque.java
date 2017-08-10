package com.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by 林润隆 on 2017/7/27.
 */

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private int N;
    private Node first;
    private Node last;

    public boolean isEmpty() {
        return N == 0;
    }

    public Deque() {
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
        first.next = oldfirst;
        if (isEmpty())
            last = first;

        N++;
        if (N != 1)//假如是1，则oldfirst此前为空，不拥有next
            oldfirst.previous = first;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }


        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldlast;
        if (isEmpty())
            first = last;

        N++;
        if (N != 1)//假如是1，则oldlast此前为空，不拥有next
            oldlast.next = last;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item;
        item = first.item;
        first = first.next;
        if (N != 1)
            first.previous = null;
        N--;
        if (isEmpty())
            last = first = null;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if (N != 1)
            last.next = null;
        N--;
        if (isEmpty())
            last = first = null;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Listlterater(first);
    }

    private class Listlterater implements Iterator<Item> {
        private Node current;

        public Listlterater(Node first) {
            current = first;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }


        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
