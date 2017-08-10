package com.main;

import com.company.Main;
import com.datastructure.Deque;
import com.datastructure.RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 林润隆 on 2017/7/27.
 */

public class TestStack {
/*    public static void main(String[] args) throws IOException {
        Dequeue<String> stack = new Dequeue<String>();
        String docName=args[0];
        FileInputStream is = new FileInputStream(new File(docName));
        System.setIn(is);
        Main.main(args);
        Dequeue<String> q = new Dequeue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-") && !item.equals("#")){
                //q.addLast(item);
                 q.addFirst(item);
            }
            else if (item.equals("-")){
                StdOut.print(q.removeFirst() + " ");
            }
            else if(item.equals("#")){
                StdOut.print(q.removeLast() + " ");
            }
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }*/

    public static void main(String[] args){
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String docName=args[0];
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File(docName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setIn(is);
        Main.main(args);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else StdOut.print(q.dequeue() + " ");
            //       else StdOut.print(q.sample() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }

}

