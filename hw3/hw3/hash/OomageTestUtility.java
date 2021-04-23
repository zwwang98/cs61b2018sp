package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] bucketNums = new int[M];
        for (Oomage o : oomages) {
            // instructions said that we must convert hashcode into bucket number like this
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            bucketNums[bucketNum] += 1;
        }
        double hi = oomages.size() / 2.5;
        double lo = oomages.size() / 50.0;
        for (int i : bucketNums) {
            if (i > hi || i < lo) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
