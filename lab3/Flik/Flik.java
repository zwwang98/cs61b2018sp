/** An Integer tester created by Flik Enterprises. */
public class Flik {
    /**
     * I find the link below, it may help clear this problem.
     * https://stackoverflow.com/questions/1700081/why-is-128-128-false-but-127-127-is-true-when-comparing-integer-wrappers-in-ja
     * And this article explains the same thing in Chinese:
     * https://blog.csdn.net/tzs_1041218129/article/details/53574316
     * */
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }
}
