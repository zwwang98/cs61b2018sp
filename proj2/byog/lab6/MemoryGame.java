package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    /**
     * Empty constructor. Convenient for test.
     * */
    public MemoryGame(int seed) {
        rand = new Random(seed);
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String randomStringN = "";
        for (int i = 0; i < n; i++) {
            int randomIndex = RandomUtils.uniform(rand, 0, 26);
            Character c = CHARACTERS[randomIndex];
            randomStringN += c;
        }
        return randomStringN;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        // clears the canvas
        StdDraw.clear();
        // sets the font to be large and bold (size 30 is appropriate),
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        // draws the input string so that it is centered on the canvas,
        StdDraw.text(0.5, 0.5, s);
        // and then shows the canvas on the screen.
        StdDraw.show();

        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] chars = letters.toCharArray();
        for (char c : chars) {
            // clears the canvas
            StdDraw.clear();
            // sets the font to be large and bold (size 30 is appropriate),
            Font font = new Font("Arial", Font.BOLD, 30);
            StdDraw.setFont(font);
            // draws the input string so that it is centered on the canvas,
            StdDraw.text(0.5, 0.5, "" + c);
            // and then shows the canvas on the screen.
            StdDraw.show();
            // the description says that pause(int t) = pauses for t milliseconds
            // https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html#pause-int-
            StdDraw.pause(1000);

            // blank the screen between two characters
            StdDraw.clear();
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        return null;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Game loop
    }

}
