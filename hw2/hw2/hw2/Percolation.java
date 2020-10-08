
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 *  Name:    Wang
 *  NetID:   N/A
 *  Precept: N/A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Description:  Modeling Percolation using an N-by-N grid and Union-Find data
 *                structures to determine the threshold. woot. woot.
 ******************************************************************************/

public class Percolation {
    private int N; // N for a N-by-N grid
    private int openNum; // the number of open sites;
    private boolean isPercolated; // If the percolation system percolates, true.

    /* record the status of each site.
     * true ==> blocked
     * false ==> open */
    private boolean[][] open;

    // UF data structure with VTS(virtual top site), thus its size is (N * N + 1)
    private WeightedQuickUnionUF VTSUF;
    private int TOP; // virtual top site's index in VTSUF

    // UF data structure with VBS(virtual bottom site), thus its size is (N * N + 1)
    private WeightedQuickUnionUF VBSUF;
    private int BOTTOM; // virtual bottom sits's index in VBSUF

    /**
     * Create N-by-N grid representing a percolation system
     * with all sites initially blocked.
     *
     * @param  n the length of side of the grid
     * @throws IllegalArgumentException if {@code n < 0}
     */
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The argument n for constructor is less than 0.");
        }
        N = n;
        open = new boolean[n][n];
        VTSUF = new WeightedQuickUnionUF(N * N + 1);
        TOP = N * N;
        VBSUF = new WeightedQuickUnionUF(N * N + 1);
        BOTTOM = N * N;
        // connect all the sites in the first row(row = 0) with virtual top site
        for (int i = 0; i <= N - 1; i++) {
            VTSUF.union(i, TOP);
        }
        // connect all the sites in the last row(row = N - 1) with virtual bottom site
        for (int i = N * N - N; i <= N * N - 1; i++) {
            VBSUF.union(i, BOTTOM);
        }
    }

    /** opens the site (row, col) if it is not open already
     *  应该是最难的地方了。按照我的理解，open要依次完成下列工作：
     *  1) 首先检测输入是否合适。row、col都应该在0~N-1范围内（inclusive）。
     *  2) 如果该格子之前已经open了，那就跳过。
     *     如果确定了该格子是blocked的，那就改变open[row][col]的值为true，
     *     表示该格子现在是open，并且openNum+1。
     *  3) 和上下左右四个neighbors中open的进行union。
     *     要在VTSUF和VBSUF两个union-find数据结构中都进行union操作。
     *     这里需要专门写一个2D Array坐标到union-find index的转换method，
     *     因为两者的坐标是不同但有一一对应关系的。
     *  4) 检测是否和最上面一层相连（可能值得专门写一个method），如果相连，那该格子就是full。
     *     *替代方法*
     *     如果open是int[][]，那我们可以用2来表示full，这样检测isFull时
     *     只需要返回open[row][col]检测是不是2即可，速度很快
     *  5) 检测格子(row, col)是否既和top row相连又和bottom row相连，如果是，
     *     那就表示该系统已经percolate，然后更新isPercolated为true
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * @throws IndexOutOfBoundsException unless both {@code 0 <= p < n} and {@code 0 <= q < n}
     * */
    public void open(int row, int col) {
        /*  1) 首先检测输入是否合适。row、col都应该在0~N-1范围内（inclusive）。*/
        if (!validateCoordinate(row, col)) {
            throw new IndexOutOfBoundsException("The input (row, col) is not valid.");
        }
        /*  2) 如果该格子之前已经open了，说明该格子在之前已经被open过
         * 并被进行过一系列操作了，所以不需要再进行后续操作了。
         * 如果确定了该格子是blocked的，那就改变open[row][col]的值为true，
         * 表示该格子现在是open，并且openNum+1。*/
        if (open[row][col]) {
            return;
        }
        open[row][col] = true;
        openNum += 1;
        /*  3) 和上下左右四个neighbors中open的进行union。这里可以考虑专门
         *     写一个connectNeighbors来表示连接上下左右四个相邻的格子。
         *     要在VTSUF和VBSUF两个union-find数据结构中都进行union操作。
         *     这里需要专门写一个2D Array坐标到union-find index的转换method，
         *     因为两者的坐标是不同但有一一对应关系的。*/
        connectNeighbors(row, col);
        /*  4) 检测是否和最上面一层相连（可能值得专门写一个method），如果相连，那该格子就是full。
         *     *替代方法*
         *     如果open是int[][]，那我们可以用2来表示full，这样检测isFull时
         *     只需要返回open[row][col]检测是不是2即可，速度很快。
         *     但是这种整数2D Array似乎没有必要，因为我们只需要检测一个site
         *     是否和virtual top site相连就可以判断其是否full。 */
        /*if (isConnectedToTopRow(row, col)) {
              statusOfSite[row][col] = 2; // 如果用int[][]表示site的
              blocked、open、full，我们可以用数字2来表示full
        }*/
        /*  5) 检测格子(row, col)是否既和top row相连又和bottom row相连，
         *     如果是，那就表示该系统已经percolate，然后更新isPercolated为true*/
        if (isConnectedToBottomRow(row, col) && isConnectedToTopRow(row, col)) {
            isPercolated = true;
        }
    }

    /**Union the neighbors of the site (row, col) (four sites at most, up, down, left, right)
     * if they exists(if (row, col) sits in the corner or one side of the grid,
     * it may have less than 4 neighbors).
     * The judgement about whether the neighbors are open
     * are left for the private method connectNeighbor.
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * */
    private void connectNeighbors(int row, int col) {
        // 上方neighbor，(row - 1, col)
        if (validateCoordinate(row - 1, col)) {
            connectNeighbor(row, col, row - 1, col);
        }
        // 下方neighbor，(row + 1, col)
        if (validateCoordinate(row + 1, col)) {
            connectNeighbor(row, col, row + 1, col);
        }
        // 左方neighbor，(row, col - 1)
        if (validateCoordinate(row, col - 1)) {
            connectNeighbor(row, col, row, col - 1);
        }
        // 右方neighbor，(row, col + 1)
        if (validateCoordinate(row, col + 1)) {
            connectNeighbor(row, col, row, col + 1);
        }
    }

    /**Union the site (row, col) and one site of its open neighbors(if exists)
     * whose coordinate is (neighborRow, neighborCol).
     * We should examine whether the neighbor site is open here.
     * The task of union should be completed in both VTSUF and VBSUF.
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * @param neighborRow the index of the site's assigned neighbor
     *                    in 2D Array and the neighbor site must be open
     * @param neighborCol the index of the site's assigned neighbor
     *                    in 2D Array and the neighbor site must be open
     * */
    private void connectNeighbor(int row, int col, int neighborRow, int neighborCol) {
        // if the assigned neighbor itself is not open, then we don't try to union them
        if (!isOpen(neighborRow, neighborCol)) {
            return;
        }
        // after we make sure that the neighbor is open, union them in both VTSUF and VBSUF
        int currIndex = coordinateToIndex(row, col);
        int neighborIndex = coordinateToIndex(neighborRow, neighborCol);
        VTSUF.union(currIndex, neighborIndex);
        VBSUF.union(currIndex, neighborIndex);
    }

    /**Calculate the corresponding index in union-find data structure of the site (row, col).
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * */
    private int coordinateToIndex(int row, int col) {
        return row * N + col;
    }


    /**To test if the site (row, col) is connected to top row.
     * If the result is true, then the site (row, col) should be full.
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * @return Return true if the site (row, col)
     * is connected to the top row of the grid.
     * */
    private boolean isConnectedToTopRow(int row, int col) {
        int curIndex = coordinateToIndex(row, col);
        return VTSUF.find(TOP) == VTSUF.find(curIndex);
    }

    /**To test if the site (row, col) is connected to bottom row.
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * @return Return true if the site (row, col)
     * is connected to the bottom row of the grid.
     * */
    private boolean isConnectedToBottomRow(int row, int col) {
        int curIndex = coordinateToIndex(row, col);
        return VBSUF.find(BOTTOM) == VBSUF.find(curIndex);
    }

    /** Make sure the input coordinate (row, col) is between 0 and (N - 1).
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * @return Return true if the input coordinate (row, col) is valid
     * */
    private boolean validateCoordinate(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return open[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && isConnectedToTopRow(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }

    // test client (optional)
    public static void main(String[] args) {

    }

}
