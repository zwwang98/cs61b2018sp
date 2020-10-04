import static org.junit.Assert.*;
import org.junit.Test;

import java.io.FileReader;
import java.util.ArrayList;

public class TestArrayDequeGold {

    /**
     * Task 1
     *
     * Steps
     * 1.create a JUnit test file called TestArrayDequeGold.java
     * 2.Start your file with the needed import statements:
     *   import static org.junit.Assert.*;
     *   import org.junit.Test;
     * 3.Write a single test to randomly call StudentArrayDeque
     *   and ArrayDequeSolution methods until they disagree on an output.
     *   You can generate random numbers using the StdRandom library
     *   (Documentation: http://introcs.cs.princeton.edu/java/stdlib/javadoc/StdRandom.html).
     *
     * Other Tips
     * 1.For this project, you must use Integer as your type for the Deque
     * 2.You should be able to find an error using only the addFirst, addLast,
     *   removeFirst, and removeLast methods, though you’re welcome to
     *   try out the other methods as well.
     * 3.Your test should NOT cause a NullPointerException.
     *   Make sure that you never try to remove from an empty ArrayDeque,
     *   since Integer x = ad.removeFirst() will cause a NullPointerException.
     * 4.Additionally, for this project always use Integer instead of int
     *   when you are retrieving values from the deques, i.e. do not do int x = ad.removeFirst().
     *   For an explanation of why this causes problems, please read the “Frequently Asked Questions” below.
     * */
    @Test
    public void test1() {
        // track the failure method calling sequence
        ArrayList<String> failure = new ArrayList<>();

        // create and instantiate a StudentArrayDeque and a ArrayDequeSolution
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();

        // call methods randomized from stu and sol and check if their outputs match each other
        for (int i = 0; i < 1000; i += 1) {
            // in each loop, generate a random number in [0, 1)
            // using the random number to decide which method to call
            // we only call addFirst, addLast, removeFirst and removeLast here
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.25) {
                stu.addFirst(i);
                sol.addFirst(i);
                failure.add("addFirst(" + i + ")");
            } else if (numberBetweenZeroAndOne < 0.5) {
                stu.addLast(i);
                sol.addLast(i);
                failure.add("addFirst(" + i + ")");
            } else if (numberBetweenZeroAndOne < 0.75) {
                if (!stu.isEmpty() && !sol.isEmpty()) {
                    Integer expected = sol.removeFirst();
                    Integer actual = stu.removeFirst();
                    failure.add("addFirst(" + i + ")");
                    
                    assertEquals(failure.toString() + "\n   Random number " + actual
                                    + " not equal to " + expected + "!",
                            expected, actual);
                } else {
                    stu.addFirst(i);
                    sol.addFirst(i);
                    failure.add("addFirst(" + i + ")");
                }
            } else {
                if (!stu.isEmpty() && !sol.isEmpty()) {
                    Integer expected = sol.removeLast();
                    Integer actual = stu.removeLast();
                    failure.add("addFirst(" + i + ")");
                    assertEquals(failure.toString() + "\n   Random number " + actual
                                    + " not equal to " + expected + "!",
                            expected, actual);
                } else {
                    stu.addLast(i);
                    sol.addLast(i);
                    failure.add("addFirst(" + i + ")");
                }
            }
        }

    }
}
