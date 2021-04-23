package hw3.hash;

/**
 * Output will be like:
 * The powers of 256 in Java are:
 * 0th power: 1
 * 1th power: 256
 * 2th power: 65536
 * 3th power: 16777216
 * 4th power: 0
 * 5th power: 0
 * 6th power: 0
 * 7th power: 0
 * 8th power: 0
 * 9th power: 0
 *
 * 256^3 = 16777216
 * 256^4 = 4294967296
 * int range: -2,147,483,648 - 2,147,483,647
 * 256^4 is beyond the range and thus it prints out 0
 *
 * Remember, in ComplexOomage.java, our hashCode() method multiples by 256 too,
 * so we could set our params' size larger than 4 (say 10), then if two ComplexOomage object
 * have the same values in the first four of params, no matter what value follows, they will
 * have same hash code. For example,
 *
 * */
public class Hint {
    public static void main(String[] args) {
        System.out.println("The powers of 256 in Java are:");
        int x = 1;
        for (int i = 0; i < 10; i += 1) {
            System.out.println(i + "th power: " + x);
            x = x * 256;
        }
    }
} 
