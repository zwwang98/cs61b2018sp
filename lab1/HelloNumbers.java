/**
 * https://joshhug.gitbooks.io/hug61b/content/chap1/chap11.html, Exercise 1.1.2.
 * Modify HelloNumbers so that it prints out the cumulative sum of the integers from 0 to 9.
 * For example, your output should start with 0 1 3 6 10... and should end with 45.
 * */
public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int sum = 0;
        while (x < 10) {
            System.out.print(sum + " ");
            x = x + 1;
            sum = sum + x;
        }
    }
}