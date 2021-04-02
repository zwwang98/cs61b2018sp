package byog.lab6;

import edu.princeton.cs.algs4.StdDraw;

public class KeyBoardInput {
    public static void main(String[] args) {
        char userInput = 0;
        double radius = 1 / 5000;
        StdDraw.setCanvasSize(600, 600);
        //as long as the user has not pressed 'q', keep drawing circles
        while (userInput != 'q') {
            if (StdDraw.hasNextKeyTyped()) { //check for keyboard input
                userInput = StdDraw.nextKeyTyped();
            }
            StdDraw.circle(0.5, 0.5, radius);
            radius = radius + 1 / 500.0;
            //display a circle every 10ms.
            StdDraw.show(10);
        }
    }
}
