package Assinment2;

import edu.princeton.cs.algs4.*;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private int numberOfSegments;

    private LineSegment[] theSegments;

    public FastCollinearPoints(Point[] points) {// finds all line segments containing 4 or more points
/*
* 如果输入数组为空值，数组中的点存在空值，存在重复的点
* 则抛出异常
* */
        if (points == null)//
            throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }
        int n = points.length;

        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
/*
* 初始化线段数组
* */
        int num = 32767;
        theSegments = new LineSegment[num];


        Point[] auxPoints = new Point[n];

        for (int m = 0; m < n; m++) { //对每一个点都采用快速排序，找出以它在其上的线段
            Point p = points[m];
            for (int i = 0; i < n - 1; i++) { //初始化将要进行排序的辅助数组，其中p不在排序范围内之内
                if (i < m)
                    auxPoints[i] = points[i];
                if (i >= m)
                    auxPoints[i] = points[i + 1];
            }
            auxPoints[n - 1] = p; //在辅助数组后面加上本身，防止continue语句直接将下面的for循环跑完
            int count = 1; //作用是为了记录与p斜率相同的点的数目
            sort(auxPoints, 0, n - 2, p.slopeOrder()); //对其进行快速排序
            for (int i = 0; i < n - 1; i++) {
                if (p.slopeOrder().compare(auxPoints[i], auxPoints[i + 1]) == 0) {
                    count++;
                    continue;
                } else {
                    if (count >= 3) { //如果有三点及三点以上共斜率
                        Point[] lineSeg = new Point[count + 1]; //再建立一个辅助数组用于存放待成为线段的点
                        lineSeg[0] = p;
                        int theNum = i - count + 1;
                        for (int k = 1; k < count + 1; k++) {
                            lineSeg[k] = auxPoints[theNum++];
                        }
                        Arrays.sort(lineSeg); //将数组中的点按照其大小顺序排好已确定其端点
                       // Insertion.sort(lineSeg);
                        LineSegment line = new LineSegment(lineSeg[0], lineSeg[count]); //建立线段
                        theSegments[numberOfSegments++] = line; //先将线段添加进数组中
                        if (theSegments[1] != null) //如果已经拥有了两个及两个以上的线段，则可以用于比较其是否重复
                            for (int jk = 0; jk < numberOfSegments - 1; jk++) {
                            //通过判断其字符串是否相等来判断线段是否相同。  注意：将通过不了其中一个测试！
                                LineSegment seg = theSegments[jk];
                                String ss = seg.toString();
                                String sl = line.toString();
                                if (ss.equals(sl)) { //如果发现线段相等，回退该操作
                                    line = null;
                                    theSegments[numberOfSegments--] = line;
                                    break;
                                }
                            }

                    }
                    count = 1;
                }
            }
        }
    }

    public int numberOfSegments() {// the number of line segments
        return numberOfSegments;
    }

    public LineSegment[] segments() {// the line segments
        LineSegment[] segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = theSegments[i];
        }
        return segments;
    }

    private void sort(Point[] a, int lo, int hi, Comparator<Point> b) {
        if (hi <= lo) return;
        StdRandom.shuffle(a, lo, hi);
        int lt = lo, gt = hi;
        Point v = a[lo];
        int i = lo;
        while (i <= gt) {
            int x = b.compare(a[i], v);
            if (x < 0) exch(a, lt++, i++);
            else if (x > 0) exch(a, i, gt--);
            else {
                i++;
            }
        }

        sort(a, lo, lt - 1, b);
        sort(a, gt + 1, hi, b);
    }


    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}