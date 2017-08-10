package com.datastructure;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    private class Node {
        Item item;
        Node next;
    }

    public RandomizedQueue() {// construct an empty randomized queue
    }

    public boolean isEmpty() {// is the queue empty?
        return first == null;
    }

    public int size() {
        return n;
    }                  // return the number of items on the queue

    public void enqueue(Item item) {// add the item
        if (item == null)
            throw new IllegalArgumentException();
        //向表尾添加元素
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        n++;
    }

    public Item dequeue() { // remove and return a random item
        if (isEmpty())
            throw new NoSuchElementException();
        int random = StdRandom.uniform(n);
        Node temp = new Node();//新建一个中继节点
/*        Node sentinel = new Node(); //新建一个哨兵节点
        sentinel.next = first;
        temp.next = sentinel;
        for (int i = 0; i <= random; i++) {//定位到随机元素的前方节点
            temp = temp.next;
        }*/

        temp.next=first;
        while (random>0){
            temp=temp.next;
            random--;
        }


        Item item = temp.next.item;
        if (random == n - 1 && temp.next == first) {
            first = null;
            temp.next = null;
        } else if (random != n - 1 && temp.next == first) {
            first = temp.next.next;
            temp.next = temp.next.next;
        } else if (random == n - 1 && temp.next != first) {
            temp.next = null;
        } else if (random != n - 1 && temp.next != first) {
            temp.next = temp.next.next;
        }

        if (isEmpty())
            last = null;
        n--;
        return item;
    }

    public Item sample() {  // return (but do not remove) a random item
        if (isEmpty())
            throw new NoSuchElementException();
        int random = StdRandom.uniform(n);
        Node temp = new Node();//新建一个中继节点
        Node sentinel = new Node(); //新建一个哨兵节点
        sentinel.next = first;
        temp.next = sentinel;
        for (int i = 0; i <= random; i++) {//定位到随机元素的前方节点
            temp = temp.next;
        }
        Item item = temp.next.item;
        return item;
    }

    public Iterator<Item> iterator() { // return an independent iterator over items in random order
        return new ListIterator(first);
    }

    private class ListIterator implements Iterator<Item> {
        private Node first1;
        private Node last1;
        private int n1 = n;

        public ListIterator(Node first) {
            if (n1 == 0) return;
            Node newNode = new Node();
            newNode.item = first.item;
            newNode.next = null;

            last1 = newNode;
            first1 = last1;

            for (Node cur = first.next; cur != null; cur = cur.next) {
                newNode = new Node();
                newNode.item = cur.item;
                newNode.next = null;
                last1.next = newNode;
                last1 = newNode;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return first1 == null;
        }

        @Override
        public Item next() {
            if (isEmpty())
                throw new NoSuchElementException();
            int random = StdRandom.uniform(n1);
            Node temp = new Node();//新建一个中继节点
/*            Node sentinel = new Node(); //新建一个哨兵节点
            sentinel.next = first1;
            temp.next = sentinel;
            for (int i = 0; i <= random; i++) {//定位到随机元素的前方节点
                temp = temp.next;
            }*/
            temp.next=first;
            while (random>0){
                temp=temp.next;
                random--;
            }
            Item item = temp.next.item;
            if (random == n1 - 1 && temp.next == first) {
                first = null;
                temp.next = null;
            } else if (random != n1 - 1 && temp.next == first) {
                first = temp.next.next;
                temp.next = temp.next.next;
            } else if (random == n1 - 1 && temp.next != first) {
                temp.next = null;
            } else if (random != n1 - 1 && temp.next != first) {
                temp.next = temp.next.next;
            }

            if (isEmpty())
                last1 = null;
            n1--;
            return item;
        }
    }


}