package hw2;

import edu.princeton.cs.introcs.StdRandom; // 可以生成随机数，用于生成随机坐标(row, col)
import edu.princeton.cs.introcs.StdStats;


/**
 * 1.先列出hw2中交代的API:
 * public class PercolationStats {
 *    public PercolationStats(int N, int T, PercolationFactory pf)
 *    // perform T independent experiments on an N-by-N grid
 *
 *    public double mean()
 *    // sample mean of percolation threshold
 *
 *    public double stddev()
 *    // sample standard deviation of percolation threshold
 *
 *    public double confidenceLow()
 *    // low endpoint of 95% confidence interval
 *
 *    public double confidenceHigh()
 *    // high endpoint of 95% confidence interval
 * }
 *
 * 2.再列出来hw2中对于PercolationStats class提出的要求：
 *   (1) The constructor should throw a java.lang.IllegalArgumentException
 *       if either N ≤ 0 or T ≤ 0. 【N，grid边长，T，实验次数。】
 *   (2) The constructor should take three arguments N, T, and pf, and perform T
 *       independent computational experiments (discussed above)
 *       on an N-by-N grid. Using this experimental data, it should calculate the mean,
 *       standard deviation, and the 95% confidence interval
 *       for the percolation threshold.
 *   (3) For this portion of the HW assignment, you must use the PercolationFactory
 *       object pf to create new Percolation objects.
 *       a. You should never call new Percolation(N)  directly in  PercolationStats,
 *       for this will mess up autograder testing.
 *       b. Using the PercolationFactory object will allow us to use your
 *       PercolationStats implementation with the solution
 *          Percolation implementation to avoid cascading errors.
 *   (4) In addition, you must use edu.princeton.cs.introcs.StdRandom
 *       (http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdRandom.html)
 *       to generate random numbers because our testing suite(测试套件) relies on this library.
 *   (5) You should use edu.princeton.cs.introcs.StdStats
 *       (http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdStats.html)
 *       to compute the sample mean and standard deviation, but if you’d prefer,
 *       you’re welcome to write your own functions to compute statistics.
 *
 * 3.再写出API中各个methods可能的实现方法：
 *    public PercolationStats(int N, int T, PercolationFactory pf)
 *    // perform T independent experiments on an N-by-N grid
 *      该操作要完成的工作比较多，包括【计算均值】、【计算标准偏差】、【计算置信区间两端值】，
 *      而这些计算都是通过重复进行N次独立实验来实现的。那么，现在问题
 *      变成了，怎么实现N次独立实验？实现N次独立实验，就是把一个随机实验实现N次。
 *      那我们先把一次实验的实现给写出来，再让这个实现跑N次，我想这样就可以了。
 *
 *      一次实验的方法或者说步骤在hw2中已经给出来了：
 *        a. Initialize all sites to be blocked.
 *        b. Repeat the following until the system percolates:
 *           I. Choose a site uniformly(@???啥意思) at random among all blocked sites.
 *          II. Open the site.
 *         III. The fraction of sites that are opened(即open sites的数量除以sites总数)
 *         when the system percolates
 *              provides an estimate of the percolation threshold.
 *
 *      那接下来我们就尝试把上面这些步骤的实现方法写出来：
 *        a. Initialize all sites to be blocked.
 *           按照hw2要求中第3点用PercolationFactory新建一个N*N的Percolation grid
 *           代码：pf.make(N); pf表示constructor中传入的PercolationFactory
 *        b. Repeat the following until the system percolates:
 *           I. Choose a site uniformly(@???啥意思) at random among all blocked sites.
 *              按照hw2要求中第4点，用edu.princeton.cs.introcs.StdRandom生成随机数。
 *              我暂时想用2D Array的坐标，那就生成两个0~(N-1)的随机数分别表示row、col即可。
 *          II. Open the site.
 *              先用constructor新建一个Percolation，并且把这个Percolation
 *              保存在一个reference variable中，比如叫做p。
 *              当我们需要open上一步得到的随机坐标对应的格子时，
 *              我们就调用p来open，代码如下：
 *              p.open(row, col);
 *
 *              然后我们应该需要重复进行I、II两个步骤，直到达到III中的“when the system percolates”，
 *              这个时候我们再进行III。
 *         III. The fraction of sites that are opened(即open sites的数量除以sites总数)
 *              when the system percolates
 *              provides an estimate of the percolation threshold.
 *              这一步我们可以该次独立实验的阈值，我们可以把所有T次独立实验的结果都保存下来，
 *              最后进行一次计算，或者有更好的、更节省空间的方法，比如说
 *              在每次独立实验后更新我们的计算结果。
 *
 *    public double mean()
 *    // sample mean of percolation threshold
 *      在constructor处理之后得到一个结果存储在int mean里，调用mean()的时候直接返回这个变量即可。
 *
 *    public double stddev()
 *    // sample standard deviation of percolation threshold
 *      在constructor处理之后得到一个结果存储在int stddev里，调用stddev()的时候直接返回这个变量即可。
 *
 *    public double confidenceLow()
 *    // low endpoint of 95% confidence interval
 *      95%置信区间的计算公式在hw2中已经列出，用到的参数为【样品均值】、【样品标准偏差】、【实验总次数】，三者都可以获取。
 *
 *    public double confidenceHigh()
 *    // high endpoint of 95% confidence interval
 *      95%置信区间的计算公式在hw2中已经列出，用到的参数为【样品均值】、【样品标准偏差】、【实验总次数】，三者都可以获取。
 * */
public class PercolationStats {
    /** 进行独立实验的总次数。 */
    private int T;
    /** 通过该reference variable保存constructor中通过PercolationFactory构造的Percolation类object。 */
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
     * 1. 进行T次独立实验，保存所有结果。也许有更好地方法，比如每次独立实验结尾都把该次实验结果用于更新均值、标准偏差等。
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
        // 任务一，进行T次独立实验并保存所有结果。
        double[] fractions = new double[T]; // 用array来保存T次独立实验获得的fraction值
        for (int i = 0; i < T; i++) {
            fractions[i] = independentExperiment(N, pf, i);
        }
        // 任务二，对T次独立实验的结果进行数据处理，计算得到样本均值、样本标准偏差和置信区间两端的值
        //        按照hw2要求第5点(@???这里可不可以做一个超链接指向我上面列出来的hw2要求？)，
        //        我们需要用edu.princeton.cs.introcs.StdStats来处理数据。
        //        http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdStats.html
        // 我看了上面的网址之后，发现有两个method很适合我需要的计算：
        // 1. public static double mean(double[] a)
        //    Parameters: a - the array,
        //    Returns: the average value in the array a[]; Double.NaN if no such value
        // 2. public static double stddev(double[] a)
        //    Parameters: a - the array,
        //    Returns: the sample standard deviation in the array a[]; Double.NaN if no such value
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
     *           先用constructor新建一个Percolation，并且把这个Percolation
     *           保存在一个reference variable中，比如叫做p。
     *           当我们需要open上一步得到的随机坐标对应的格子时，我们就调用p来open，代码如下：
     *           p.open(row, col);
     *           然后我们应该需要重复进行I、II两个步骤，直到达到III中的“when the system percolates”，
     *           这个时候我们再进行III。
     *      III. The fraction of sites that are opened(即open sites的数量除以sites总数)
     *      when the system percolates
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
