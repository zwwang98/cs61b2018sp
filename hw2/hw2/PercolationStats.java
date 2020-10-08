package hw2;

import edu.princeton.cs.introcs.StdRandom; // 可以生成随机数，用于生成随机坐标(row, col)
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    /** 进行独立实验的总次数。 */
    private int T;
    /** 通过该reference variable保存constructor中通过P
     * ercolationFactory构造的Percolation类object。 */
    private Percolation p;
    /** 保存着T次独立实验过程中现有结果的均值。 */
    private double mean;
    /** 保存着T次独立实验过程中现有结果的标准偏差。 */
    private double stddev;
    /** 保存着T次独立实验过程中现有结果计算得到的95%置信区间的前端值。 */
    private double confidenceLow;
    /** 保存着T次独立实验过程中现有结果计算得到的95%置信区间的末端值。 */
    private double confidenceHigh;


    /** perform T independent experiments on an N-by-N grid
     * 我们在这里需要做的工作由：
     * 1. 进行T次独立实验，保存所有结果。也许有更好地方法，
     * 比如每次独立实验结尾都把该次实验结果用于更新均值、标准偏差等。
     * 2. 将T次独立实验得到的结果用于计算目标值，即均值、标准偏差、置信区间两端值。
     *
     * @param N the grid is N-by-N
     * @param T the number of independent experiments
     * @param pf make a Percolation through pf
     * @throws IllegalArgumentException if N or T is less than zero
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T should not be less than 0!");
        }

        double[] fractions = new double[T];
        for (int i = 0; i < T; i++) {
            fractions[i] = independentExperiment(N, pf, i);
        }

        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
        confidenceLow = mean - (1.96 * stddev / Math.sqrt(T));
        confidenceHigh = mean + (1.96 * stddev / Math.sqrt(T));
    }

    /** 单次独立实验的内容。
     *     a. Initialize all sites to be blocked.
     *        按照hw2要求中第3点用PercolationFactory新建一个N*N的Percolation grid
     *        代码：Percolation p = pf.make(N); pf表示constructor中传入的PercolationFactory
     *     b. Repeat the following until the system percolates:
     *        I. Choose a site uniformly(@???啥意思) at random among all blocked sites.
     *           按照hw2要求中第4点，用edu.princeton.cs.introcs.StdRandom生成随机数。
     *           我暂时想用2D Array的坐标，那就生成两个0~(N-1)的随机数分别表示row、col即可。
     *       II. Open the site.
     *           先用constructor新建一个Percolation，并且把这个Percolation保存
     *           在一个reference variable中，比如叫做p。
     *           当我们需要open上一步得到的随机坐标对应的格子时，我们就调用p来open，代码如下：
     *           p.open(row, col);
     *           然后我们应该需要重复进行I、II两个步骤，直到达到III中的“when the system percolates”，
     *           这个时候我们再进行III。
     *      III. The fraction of sites that are opened(即open sites的数量除以sites总数)
     *           when the system percolates
     *           provides an estimate of the percolation threshold.
     *           这一步我们可以该次独立实验的阈值，我们可以把所有T次独立实验的结果都保存下来，
     *           最后进行一次计算，或者有更好的、更节省空间的方法，比如说
     *           在每次独立实验后更新我们的计算结果。
     * */
    private double independentExperiment(int N, PercolationFactory pf, int i) {
        // 步骤1，Initialize all sites to be blocked.
        p = pf.make(N); // create a new N-by-N grid
        // 步骤2，重复步骤2.1和步骤2.2直到Percolation system变成percolate。
        while (!p.percolates()) {
            // 步骤2.1，Choose a site uniformly(@???啥意思) at random among all blocked sites.
            //         按照hw2要求中第4点，用edu.princeton.cs.introcs.StdRandom生成随机数。
            //http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdRandom.html
            //         我暂时想用2D Array的坐标，那就生成两个0~(N-1)的随机数分别表示row、col即可。
            // 在看了网址里的文档之后，我选择了uniform(int n)作为生成随机数的method。
            // public static int uniform(int n), Returns a random integer uniformly in [0, n).
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            // 步骤2.2，Open the site.
            //         先用constructor新建一个Percolation，
            //         并且把这个Percolation保存在一个reference variable中，比如叫做p。
            //         当我们需要open上一步得到的随机坐标对应的格子时，
            //         我们就调用p来open，代码如下：
            //         p.open(row, col);
            p.open(row, col);
        }
        // 步骤3，得到该次独立实验的结果，即系统percolate时open格子占总数的比例，并返回该结果。
        return 1.0 * p.numberOfOpenSites() / (N * N);
        // 之前通过了置信区间的测试，却没有通过mean、stddev，很奇怪，现在我怀疑是这里的return type弄错了
    }

    /** sample mean of percolation threshold
     *
     * */
    public double mean() {
        return mean;
    }

    /** sample standard deviation of percolation threshold
     *
     */
    public double stddev() {
        return stddev;
    }

    /** low endpoint of 95% confidence interval
     *
     */
    public double confidenceLow() {
        return confidenceLow;
    }

    /** high endpoint of 95% confidence interval
     *
     */
    public double confidenceHigh() {
        return confidenceHigh;
    }
}

