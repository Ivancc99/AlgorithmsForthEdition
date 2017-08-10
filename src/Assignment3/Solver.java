package Assignment3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private BoardNode theLastNode; //最后一个搜索节点
    private boolean isSovable;


    private class BoardNode implements Comparable<BoardNode> {
        public BoardNode previous;
        public int prioty;
        public int manhattan;
        public int move;
        public Board board;
        public boolean isTwin;

        public BoardNode(Board b) {
            board = b;
            manhattan = b.manhattan();
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        @Override
        public int compareTo(BoardNode that) {
            if (this.prioty > that.prioty)
                return 1;
            if (this.prioty < that.prioty)
                return -1;
            if (this.move > that.move)
                return 1;
            if (this.move < that.move)
                return -1;
            return 0;
        }
    }


    public Solver(Board initial) {    // 找出该难题的解（使用A*算法）
        MinPQ<BoardNode> pq, qp;
        if (initial == null)
            throw new IllegalArgumentException();

        pq = new MinPQ<BoardNode>();

        BoardNode initialNode=new BoardNode(initial);
        BoardNode twinNode=new BoardNode(initial.twin());

        initialNode.move=0;
        initialNode.isTwin=false;
        twinNode.move=0;
        twinNode.isTwin=true;

        pq.insert(initialNode);
        pq.insert(twinNode);

        BoardNode x = pq.delMin();

        while (!x.isGoal() ) {
            for (Board b : x.board.neighbors()) {
                BoardNode nei = new BoardNode(b);
                nei.previous = x;
                nei.move=x.move+1;
                nei.prioty=nei.move+nei.manhattan;
                nei.isTwin=x.isTwin;
                if (x.previous == null || !x.previous.board.equals(nei.board))
                    pq.insert(nei);
            }
            x = pq.delMin();
        }
        isSovable = !x.isTwin;
        if (x.isGoal()&&!x.isTwin) {
            isSovable = true;
            theLastNode = x;
        }
    }

    public boolean isSolvable() { //初始板是否有解？
        return isSovable;
    }

    public int moves() {  // 解出初始板的最小次数，-1表示无解。
        if(theLastNode==null)
            return -1;
        return theLastNode.move;
    }

    public Iterable<Board> solution() {  //解题的一系列步骤
        if(theLastNode==null)
            return null;
        Stack<Board> stack = new Stack<Board>();
        BoardNode pre = theLastNode;
        while (pre != null) {
            stack.push(pre.board);
            pre = pre.previous;
        }

        return stack;
    }

    public static void main(String[] args) {

        // 读取文件，创建一个新板
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        //解决问题
        Solver solver = new Solver(initial);

        //打印
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}