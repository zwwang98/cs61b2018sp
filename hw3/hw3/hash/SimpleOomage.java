package hw3.hash;
import java.awt.Color;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;


public class SimpleOomage implements Oomage {
    /* red, green, and blue, and each may have any value
    *  that is a multiple of 5 between 0 and 255 */
    protected int red;
    protected int green;
    protected int blue;

    private static final double WIDTH = 0.01;
    private static final boolean USE_PERFECT_HASH = true;

    @Override
    public boolean equals(Object o) {
        // @source https://algs4.cs.princeton.edu/12oop/Date.java.html
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        SimpleOomage that = (SimpleOomage) o;
        return (this.red == that.red) && (this.green == that.green) && (this.blue == that.blue);
    }

    /* Uncomment this method after you've written
       equals and failed the testHashCodeAndEqualsConsistency
       test. */
    @Override
    public int hashCode() {
        if (!USE_PERFECT_HASH) {
            return red + green + blue;
        } else {
            /* Since the values of red, green and blue are multiples of 5,
            *  so when we have a hashtable who also has a multiple of 5 buckets, say 10,
            *  then our Oomage object will only go to two buckets, 0 and 5.
            *  So here, we divide each instance variable by 5. */
            /* the value is between 0-255, 255 / 5 = 51, and it is said that
            *  prime number is better in hashCode function, so I multiple it by 53
            * */
            return red / 5 * 53 * 53 + green / 5 * 53 + blue / 5;
        }
    }

    public SimpleOomage(int r, int g, int b) {
        // r, g and b should be in the range of 0-255 (not inclusive)1
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
        // r, g and b should be a multiple of 5
        if ((r % 5 != 0) || (g % 5 != 0) || (b % 5 != 0)) {
            throw new IllegalArgumentException("red/green/blue values must all be multiples of 5!");
        }
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public void draw(double x, double y, double scalingFactor) {
        StdDraw.setPenColor(new Color(red, green, blue));
        StdDraw.filledSquare(x, y, WIDTH * scalingFactor);
    }

    public static SimpleOomage randomSimpleOomage() {
        int red = StdRandom.uniform(0, 51) * 5;
        int green = StdRandom.uniform(0, 51) * 5;
        int blue = StdRandom.uniform(0, 51) * 5;
        return new SimpleOomage(red, green, blue);
    }

    public static void main(String[] args) {
        System.out.println("Drawing 4 random simple Oomages.");
        randomSimpleOomage().draw(0.25, 0.25, 1);
        randomSimpleOomage().draw(0.75, 0.75, 1);
        randomSimpleOomage().draw(0.25, 0.75, 1);
        randomSimpleOomage().draw(0.75, 0.25, 1);
    }

    public String toString() {
        return "R: " + red + ", G: " + green + ", B: " + blue;
    }
} 
