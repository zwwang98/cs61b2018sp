/** Class that determines whether or not a year is a leap year.
 *  @author YOUR NAME HERE
 */
public class LeapYear {

    /** Calls isLeapYear to print correct statement.
     *  @param  year to be analyzed
     */
    private static void checkLeapYear(int year) {
        if (isLeapYear(year)) {
            System.out.printf("%d is a leap year.\n", year);
        } else {
            System.out.printf("%d is not a leap year.\n", year);
        }
    }

    /** Must be provided an integer as a command line argument ARGS. */
    public static void main(String[] args) {
        // test isLeapYear(year)
        // 2000 and 2004 are leap years. 1900, 2003, and 2100 are not leap years.
        /*assert (isLeapYear(2000));
        assert (isLeapYear(2004));
        assert (!isLeapYear(1900));
        assert (!isLeapYear(2003));
        assert (!isLeapYear(2100));*/

        if (args.length < 1) {
            System.out.println("Please enter command line arguments.");
            System.out.println("e.g. java Year 2000");
        }
        for (int i = 0; i < args.length; i++) {
            try {
                int year = Integer.parseInt(args[i]);
                checkLeapYear(year);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
    }

    /**
     * To check if the input year is a leap year. A leap year is either:
     * 1. divisible by 400 or
     * 2. divisible by 4 but not by 100.
     *
     * @param year the year to be tested
     * */
    public static boolean isLeapYear(int year) {
        // a year who is divisible by 400 is a leap year
        if (year % 400 == 0) return true;
        // a year who is divisible by 100 is not a leap year
        if (year % 100 == 0) return false;
        // a year who is divisible by 4 but not by 100 is a leap year
        return year % 4 == 0;
    }

}

