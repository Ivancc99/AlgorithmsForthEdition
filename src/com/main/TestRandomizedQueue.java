package com.main;

import com.datastructure.RandomizedQueue;

public class TestRandomizedQueue {


    public static void main(String[] args) {
        RandomizedQueue<Integer> rq=new RandomizedQueue<Integer>();
        rq.enqueue(47);
        rq.dequeue();
        rq.enqueue(25);
        rq.dequeue();
    }
}
