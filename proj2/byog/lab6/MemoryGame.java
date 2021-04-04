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


        //TODO: If game is not over, display relevant game information at the top of the screen
        // sets the font to be large and bold (size 30 is appropriate),
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.1, 0.97, "Round: " + s.charAt(s.length() - 1));
        StdDraw.text(0.5, 0.97, s.substring(0, s.length() - 1));
        StdDraw.text(0.8, 0.97, "You're a star!");
        StdDraw.setPenRadius(0.01);
        StdDraw.line(0, 0.94, 1, 0.94);
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        int round  = letters.length();
        char[] chars = letters.toCharArray();
        for (char c : chars) {
            // clears the canvas
            StdDraw.clear();
            // sets the font to be large and bold (size 30 is appropriate),
            Font font = new Font("Arial", Font.BOLD, 30);
            StdDraw.setFont(font);
            // draws the input string so that it is centered on the canvas,
            StdDraw.text(0.5, 0.5, "" + c);

            drawFrame("WATCH!" + round);
            // and then shows the canvas on the screen.
            StdDraw.show();
            // the description says that pause(int t) = pauses for t milliseconds
            // https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html#pause-int-
            StdDraw.pause(1000);

            // blank the screen between two characters
            StdDraw.clear();
            StdDraw.show();
            drawFrame("WATCH!" + round);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        StdDraw.clear();
        drawFrame("TYPE!" + n);
        //TODO: Read n letters of player input
        String s = "";
        while (s.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                StdDraw.clear();
                Font font = new Font("Arial", Font.BOLD, 30);
                StdDraw.setFont(font);
                StdDraw.text(0.5, 0.5, s);
                StdDraw.show();
            }
        }
        // pause for 1000 milliseconds when user has finished the input
        StdDraw.pause(1000);
        return s;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Game loop
        // 	1. Start the game at round 1
        StdDraw.setCanvasSize(800, 800);
        for (int i = 1; i < 100; i ++) {
            //	2. Display the message “Round: “ followed by
            //     the round number in the center of the screen
            StdDraw.clear();
            // sets the font to be large and bold (size 30 is appropriate),
            Font font = new Font("Arial", Font.BOLD, 30);
            StdDraw.setFont(font);
            // draws the input string so that it is centered on the canvas,
            StdDraw.text(0.5, 0.5, "Round" + i);
            // and then shows the canvas on the screen.
            StdDraw.pause(2000);
            //	3. Generate a random string of length equal to the current round number
            String s = generateRandomString(i);
            //	4. Display the random string one letter at a time
            flashSequence(s);
            //	5. Wait for the player to type in a string the same length as the target string
            String userInput = solicitNCharsInput(s.length());
            //	6. Check to see if the player got it correct
            //	   • If they got it correct, repeat from step 2 after increasing the round by 1
            //     • If they got it wrong, end the game and display the message
            //       “Game Over! You made it to round:”
            //       followed by the round number they failed in the center of the screen
            if (!s.equals(userInput)) {
                StdDraw.text(0.5, 0.5 ,"Game Over! You made it to round:" + i);
                break;
            }

        }






    }

}
