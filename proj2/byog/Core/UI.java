package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;

import java.io.IOException;


/**
 * Generate users' interface
 * */
public class UI {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static void drawMainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(StdDraw.BLACK);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 60));
        StdDraw.text(WIDTH / 2, HEIGHT * 0.7, "The Maze");
        StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
        StdDraw.text(WIDTH / 2, HEIGHT * 0.5, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.4, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.3, "Quit (Q)");

        StdDraw.show();
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UI ui = new UI();
        ui.drawMainMenu();
    }


}
