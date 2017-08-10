package Assinment2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] theSegments;
    private int numberOfSegments;

    public BruteCollinearPoints(Point[] points) { // 寻找所有包含了4个点的线段

        if (points == null)
            throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }
        int n = points.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i==j)
                    continue;
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }


        if (n < 4)
            return;
        int num = 32767;
        theSegments = new LineSegment[num];
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {

                        double k1 = points[i].slopeTo(points[j]);
                        double k2 = points[i].slopeTo(points[k]);
                        double k3 = points[i].slopeTo(points[l]);
                        if (k1 == k2 && k1 == k3) {
                            Point[] preSegment = new Point[4];
                            preSegment[0] = points[i];
                            preSegment[1] = points[j];
                            preSegment[2] = points[k];
                            preSegment[3] = points[l];
                            Insertion.sort(preSegment);
                            LineSegment line = new LineSegment(preSegment[0], preSegment[3]);
                            theSegments[numberOfSegments++] = line;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {// 线段的个数
        return numberOfSegments;
    }

    public LineSegment[] segments() {  // 线段数组
        LineSegment[] segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = theSegments[i];
        }
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line theSegments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
