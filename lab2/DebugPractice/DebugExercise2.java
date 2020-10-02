/**
 * Exercise to showcase the step over button.
 * Code adapted from https://stackoverflow.com/questions/4895173/bitwise-multiply-and-add-in-java and https://stackoverflow.com/questions/1533131/what-useful-bitwise-operator-code-tricks-should-a-developer-know-about
 *
 * The first bug was the method max(int a, int b), it should return the larger one however it actually returns the smaller one.
 * The second bug was the method add(int a, int b), it just doesn't work the way I think it should be.
 * And the instructions says that "If you find that one of these functions has a bug, you should completely rewrite it rather than trying to fix it."
 */
public class DebugExercise2 {
    /** Returns the max of a and b. Do not step into this function. 
      * This function may have a bug, but if it does, you should find it
      * by stepping over, not into. */
    public static int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }
    /* The code below is the skeleton's version
    public static int max(int a, int b) {
        int w = (b - a) >> 31;
        /* If you're stepping into this function, click the
           step out button because you're not going to learn anything. */
        /*int z = ~(b - a) >> 31;

        int max = b & w | a & z;
        return max;
    }*/



    /** Returns the sum of a and b. Do not step into this function. 
      * This function may have a bug, but if it does, you should find it
      * by stepping over, not into. */
    public static int add(int a, int b) {
        return a + b;
    }
    /* The code below is the skeleton's version.
    public static int add(int a, int b) {
        int x = a, y = b;
        /* If you're stepping into this function, click the
           step out button because you're not going to learn anything. */
        /*int xor, and, temp;
        and = x & y;
        xor = x ^ y;

        while (and != 0) {
            and <<= 1;
            temp = xor ^ and;
            and &= xor;
            xor = temp;
        }
        return xor;
    }*/

    /** Returns a new array where entry i is the max of
     * a[i] and b[i]. For example, if a = {1, -10, 3}
     * and b = {0, 20, 5}, this function will return {1, 20, 5}.
     * */
    public static int[] arrayMax(int[] a, int[] b) {
        if (a.length != b.length) {
            System.out.println("ERROR! Arrays don't match");
            return null;
        }
        int[] returnArray = new int[a.length];
        for (int i = 0; i < a.length; i += 1) {
            int biggerValue = max(a[i], b[i]);
            returnArray[i] = biggerValue;
        }

        return returnArray;
    }

    /** Returns the sum of all elements in x. */
    public static int arraySum(int[] x) {
        int i = 0;
        int sum = 0;
        while (i < x.length) {
            // sum = sum + add(sum, x[i]); this is the skeleton's version, obviously it's a bug
            sum = add(sum, x[i]);
            // or we could simply write like: sum += x[i]
            i = i + 1;
        }
        return sum;
    }

    /** Returns the sum of the element-wise max of a and b.
     *  For example if a = {2, 0, 10, 14} and b = {-5, 5, 20, 30},
     *  the result should be 57.
     * */
    public static int sumOfElementwiseMaxes(int[] a, int[] b) {
        int[] maxes = arrayMax(a, b);
        int sumofMaxes = arraySum(maxes);
        return sumofMaxes;
    }


    public static void main(String[] args) {
        int[] a = {1, 11, -1, -11};
        int[] b = {3, -3, 2, -1};
        // expected result {3, 11, 2, -1}, so the sumOfElementwiseMaxes should be 15

        int sumOfElementwiseMaxes = sumOfElementwiseMaxes(a, b);
        System.out.println(sumOfElementwiseMaxes);
    }
}
