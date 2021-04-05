package byog.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Locale;

public class TestAnimation {
    public static void main(String[] args) {
        String input = "N123112323sWASADFAD";
        int separatePoint = input.toLowerCase(Locale.ROOT).indexOf('s');
        String seed = input.substring(1, separatePoint);
        String movements = input.substring(separatePoint + 1, input.length()).toLowerCase(Locale.ROOT);

        System.out.println(seed);
        System.out.println(movements);
    }
}
