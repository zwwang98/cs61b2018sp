package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /* Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 20, 10);
        SimpleOomage ooA3 = new SimpleOomage(10, 5, 20);
        SimpleOomage ooA4 = new SimpleOomage(10, 20, 5);
        SimpleOomage ooA5 = new SimpleOomage(20, 10, 5);
        SimpleOomage ooA6 = new SimpleOomage(20, 5, 10);
        SimpleOomage[] o = {ooA, ooA2, ooA3, ooA4, ooA5, ooA6};
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                assertNotEquals(o[i], o[j]);
            }
        }

        // add a cast according to autograder
        SimpleOomage ooA7 = new SimpleOomage(0, 0, 5);
        SimpleOomage ooA8 = new SimpleOomage(0, 5, 0);
        assertNotEquals(ooA7, ooA8);
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
