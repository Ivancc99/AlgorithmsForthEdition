package Assignment3;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Board { // 从一个n*n的数组中创建一个板
    private int manhattan;
    private int[] cBlocks;
    private int dimension;
    private int hamming;
    private int location;

    private void exch(int[] a, int i, int j) {
        int swap;
        swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private Integer abs(Integer a) {
        return a > 0 ? a : -a;
    }


    public Board(int[][] blocks) {
        int n = blocks.length;
        dimension = n;
        cBlocks = new int[dimension * dimension];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cBlocks[i * n + j] = blocks[i][j];
            }
        }
        for (int i = 0; i < n * n; i++) {
            int x1, y1; //当前点的位置
            int x, y; //应该存放的位置
            if (cBlocks[i] != 0) {
                y = (cBlocks[i] - 1) % n;
                x = (cBlocks[i] - 1) / n;
                y1 = i % n;
                x1 = i / n;
                manhattan += (abs(y - y1) + abs(x - x1));

            }
            if (cBlocks[i] != i + 1 && i != n * n - 1)
                hamming++;
            if (cBlocks[i] == 0)
                location = i;
        }
    }

    // (where cBlocks[i][j] = block in row i, column j)
    public int dimension() {    // 板的尺寸 n
        return dimension;
    }

    public int hamming() {  // 有多少滑块不在指定位置上
        return hamming;
    }

    public int manhattan() { // 所有滑块的曼哈顿距离之和
        return manhattan;
    }

    public boolean isGoal() { //当前板是否为目标板
        return hamming == 0;
    }

    public Board twin() { // 被转换了任意一对滑块的板
        int[] twinBlocks = new int[dimension * dimension];
        for (int i = 0; i < dimension * dimension; i++) {
            twinBlocks[i] = cBlocks[i];
        }
        if (cBlocks[0] != 0 && cBlocks[1] != 0) {
            exch(twinBlocks, 0, 1);
        } else {
            exch(twinBlocks, 2, 3);
        }
        int[][] blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                blocks[i][j] = twinBlocks[i * dimension + j];
            }
        }
        Board twinBoard = new Board(blocks);
        return twinBoard;
    }

    public boolean equals(Object y) { //是否跟其他板一致？
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension!=that.dimension) return false;
        for (int i = 0; i < dimension * dimension; i++) {
            if (this.cBlocks[i] != that.cBlocks[i])
                return false;
        }
        return true;
    }

    public Iterable<Board> neighbors() {// 所有可能会出现的相邻板
        if(cBlocks==null)
            return null;
        int[][] matrix = new int[dimension][dimension];
        int posX = location / dimension;
        int posY = location % dimension;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = cBlocks[i * dimension + j];
            }
        }

        Queue<Board> queue = new Queue<Board>();
        int[] dx = {0, 0, -1, 1};
        int[] dy = {1, -1, 0, 0};
        for (int i = 0; i < 4; i++) {
            int x = posX + dx[i];
            int y = posY + dy[i];

            if (x < dimension && x >= 0 && y < dimension && y >= 0) {
                int tmp = matrix[posX][posY];
                matrix[posX][posY] = matrix[x][y];
                matrix[x][y] = tmp;
                queue.enqueue(new Board(matrix));
                tmp = matrix[posX][posY];
                matrix[posX][posY] = matrix[x][y];
                matrix[x][y] = tmp;
            }
        }

        return queue;
    }

    public String toString() {         // 字符串表示形式
        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = cBlocks[i * dimension + j];
            }
        }
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", matrix[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {// u单元测试(不参与评分)
        int[][] mat = {
                {1, 2, 3},
                {4, 6, 0},
                {7, 8, 5}
        };
        //hamming
        //manhattan
        Board b = new Board(mat);
        Board c = b.twin();
        StdOut.println(b.equals(c));
        StdOut.print(b.toString());
        StdOut.print(c.toString());
        for (Board it : b.neighbors()) {
            StdOut.print(it.toString());
            StdOut.println("hamming: " + it.hamming());
            StdOut.println("manhattan: " + it.manhattan());
        }
    }

}

