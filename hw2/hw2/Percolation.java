package hw2;

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

/** 思路整理
 * 1.先把 Percolation class的API列出来：
 * public class Percolation {
 *    public Percolation(int N) // create N-by-N grid, with all sites initially blocked
 *    public void open(int row, int col)       // open the site (row, col) if it is not open already
 *    public boolean isOpen(int row, int col)  // is the site (row, col) open?
 *    public boolean isFull(int row, int col)  // is the site (row, col) full?
 *    public int numberOfOpenSites()           // number of open sites
 *    public boolean percolates()              // does the system percolate?
 *    public static void main(String[] args)   // use for unit testing (not required)
 * }
 *
 * 2.再把相关背景和问题交代清楚
 *   2.1 基本术语
 *      site，表示Percolation System的最小单位，可以理解为一个小格子，下文中我们就称作“格子”。
 *      grid(N * N)，表示整个Percolation System，由N * N个格子组成。
 *      open/blocked，一个格子可能的两种状态。只和格子本身有关，具体含义看下一部分。
 *      full/empty，一个格子可能的两种状态。尽管reading book中没有明显辨析两者含义，
 *      但是在我的理解中，只有一个格子是open的，我们才能够根据其他条件来
 *                  讨论这个格子是不是full/empty的。具体含义看下一部分。
 *      percolate，整个Percolation System的一种状态，指该系统已经被渗透完全了。
 *   2.2 问题复述
 *      我们用grid来表示一个Percolation System，可以理解为被水掩盖着的土地，
 *      并且grid从最上面第一层到最下面一层相当于土地由浅到深。
 *      open/blocked，可以理解为格子对应的空间是被挖空了(open)还是被土填满了(blocked)，
 *      因为我们的体系比较简化，所以只有这两种极端状态。
 *      full/empty，可以理解为格子对应的空间是被水填满了(full)还是没有水填进来(empty)。
 *                  在我的理解中，只有一个格子是open的，才能够讨论full/empty。
 *                  即，如果一个格子是open且有水填满，才是full，如果一个格子是open但是没有水填满，
 *                  那么就是empty。至于如何判断一个格子是否被水填满，
 *                  我们可以看这个open格子是否有一条open的路径（只考虑上下左右相邻的格子）
 *                  连接到最上层接触水面的open格子，其实就是从水面挖了
 *                  一条通道到格子对应的空间。
 *      percolate，如果一个Percolation System的最底层格子都有full的，
 *      那我们就说这个系统是percolate的。即最底层格子和最上层格子之间有一条open的通道。
 *
 *      我们研究的问题是，对于给定的格子的open概率，整个系统变成percolate的概率是多少，原文如下：
 *      ==> "if sites are independently set to be open with probability p
 *           (and therefore blocked with probability 1 − p),
 *           what is the probability that the system percolates?"
 *
 * 3.再把数据结构交代清楚
 *   我们需要存储的数据和对应的数据结构
 *   a 单个格子的open/blocked，用一个2D Array存储
 *   b 格子之间的连通性，用disjoint-sets存储
 *   c 虚拟点（virtual site）。我们会用虚拟点来分别连接grid中的最上面的一层和最下面的一层。这样做的好处有：
 *     i.当我们需要检测一个格子是否full时，我们需要检测它是否和最上层的一个open格子是connected的，
 *   这里会遇到一个问题，2D Array是用(x, y)坐标来表示格子的，而disjoint-sets是一维坐标，我们需要在两者之间进行转换。

 * 4.最后来谈谈API中各个methods的实现的可能方法
 *    【方法一】
 *    public Percolation(int N)
 *    // create N-by-N grid, with all sites initially blocked
 *      1) boolean[][] open
 *         通过2D Array记录grid中各个site是open还是blocked
 *      2) WeightedQuickUnionUF VTSUF
 *         通过一个含有virtual top site的WeightedQuickUnionUF记录grid中各点和row0之间的连通性，
 *         这样可以避免在row0进行n次迭代
 *      3) WeightedQuickUnionUF VBSUF
 *         通过一个含有virtual bottom site的WeightedQuickUnionUF
 *         记录grid中各点和row(N-1)之间的连通性，这样也可以避免在row(N-1)进行n次迭代
 *      4) 通过一些instance variable记录grid中的一些属性
 *         a. int openNum，记录grid中open sites的数目
 *         b. boolean isPercolated，记录该percolation系统是否已经渗透
 *         c. int N，记录该grid的边长
 *         d. int BOTTOM，表示VBSUF中virtual bottom site的index
 *         e. int TOP，表示VTSUF中virtual top site的index
 *      在constructor中，对上面的内容进行相应的初始化，其中openNum和isPercolated可以直接使用默认值。
 *
 *    【方法二】
 *    public void open(int row, int col)       // open the site (row, col) if it is not open already
 *      应该是最难的地方了。按照我的理解，open要依次完成下列工作：
 *      1) 首先检测输入是否合适。row、col都应该在0~N-1范围内（inclusive）。
 *      2) 如果该格子之前已经open了，那就跳过。
 *         如果确定了该格子是blocked的，那就改变open[row][col]的值为true，表示该格子现在是open，并且openNum+1。
 *      3) 和上下左右四个neighbors中open的进行union。
 *         要在VTSUF和VBSUF两个union-find数据结构中都进行union操作。
 *         这里需要专门写一个2D Array坐标到union-find index的转换method，因为两者的坐标是不同但有一一对应关系的。
 *      4) 检测是否和最上面一层相连（可能值得专门写一个method），如果相连，那该格子就是full。
 *         *替代方法*
 *         如果open是int[][]，那我们可以用2来表示full，这样检测isFull时
 *         只需要返回open[row][col]检测是不是2即可，速度很快
 *      5) 检测格子(row, col)是否既和top row相连又和bottom row相连，
 *         如果是，那就表示该系统已经percolate，然后更新isPercolated为true
 *
 *    【方法三】
 *    public boolean isOpen(int row, int col)  // is the site (row, col) open?
 *      实现比较简单，直接通过boolean[][] open中对应格子上的boolean值即可，即open[row][col]。
 *      *替代方法*
 *      grid中各个site一共可以有三种状态，分别是blocked、open但empty、open并full，
 *      那这样我们完全可以考虑设计一个int[][]，
 *      用0、1、2三个数字来分别对应上述三种状态。
 *
 *    【方法四】
 *    public boolean isFull(int row, int col)  // is the site (row, col) full?
 *      如果一个site满足下列条件，那我们就可以认为它是full的：
 *      1) 该site本身是open的
 *      2) 该site和最上面一层是connected的。
 *         a. connected是只有open格子之间才存在的关系，blocked格子之间是不会connected的
 *         b. 根据Percolation的背景，最上面一层格子只要open，那就是full
 *         c. 所以只需要用connected with top就足够判断一个site是否full了，
 *         甚至都默认包含了该格子是open的条件，连第一点都可以省去
 *
 *    【方法五】
 *    public int numberOfOpenSites()           // number of open sites
 *      实现比较简单，我们有一个openNum记录了当前状况下的number of open sites，直接返回该值即可。
 *
 *    【方法六】
 *    public boolean percolates()              // does the system percolate?
 *      实现比较简单，我们有一个openNum记录了当前状况下的number of open sites，直接返回该值即可。
 *
 *    【方法七】
 *    public static void main(String[] args)   // use for unit testing (not required)
 * */

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
     *     如果确定了该格子是blocked的，那就改变open[row][col]的值为true，表示该格子现在是open，并且openNum+1。
     *  3) 和上下左右四个neighbors中open的进行union。
     *     要在VTSUF和VBSUF两个union-find数据结构中都进行union操作。
     *     这里需要专门写一个2D Array坐标到union-find index的转换method，因为两者的坐标是不同但有一一对应关系的。
     *  4) 检测是否和最上面一层相连（可能值得专门写一个method），如果相连，那该格子就是full。
     *     *替代方法*
     *     如果open是int[][]，那我们可以用2来表示full，这样检测isFull时
     *     只需要返回open[row][col]检测是不是2即可，速度很快
     *  5) 检测格子(row, col)是否既和top row相连又和bottom row相连，
     *     如果是，那就表示该系统已经percolate，然后更新isPercolated为true
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
        /*  2) 如果该格子之前已经open了，说明该格子在之前已经被open过并被进行过一系列操作了，
         *     所以不需要再进行后续操作了。
         *     如果确定了该格子是blocked的，那就改变open[row][col]的值为true，
         *     表示该格子现在是open，并且openNum+1。*/
        if (open[row][col]) {
            return;
        }
        open[row][col] = true;
        openNum += 1;
        /*  3) 和上下左右四个neighbors中open的进行union。这里可以考虑专门写一个
         *     connectNeighbors来表示连接上下左右四个相邻的格子。
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
              statusOfSite[row][col] = 2; // 如果用int[][]表示site的blocked、open、full，
              我们可以用数字2来表示full
        }*/
        /*  5) 检测格子(row, col)是否既和top row相连又和bottom row相连，
        如果是，那就表示该系统已经percolate，然后更新isPercolated为true*/
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
     * @return Return true if the site (row, col) is connected to the top row of the grid.
     * */
    private boolean isConnectedToTopRow(int row, int col) {
        int curIndex = coordinateToIndex(row, col);
        return VTSUF.find(TOP) == VTSUF.find(curIndex);
    }

    /**To test if the site (row, col) is connected to bottom row.
     *
     * @param row the index of the sites's row in 2D Array
     * @param col the index of the site's column in 2D Array
     * @return Return true if the site (row, col) is connected to the bottom row of the grid.
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
