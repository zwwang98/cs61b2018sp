package byog.lab6;

import edu.princeton.cs.algs4.StdDraw;

public class TriFunc {

    public void drawUnitCircle() {
        StdDraw.setScale(-2, +2);
        StdDraw.enableDoubleBuffering();

        for (double t = 0.0; true; t += 0.02) {
            double x = Math.sin(t);
            double y = Math.cos(t);
            StdDraw.clear();

            // draw the x-axis and y-axis
            StdDraw.line(-2, 0, 2, 0);
            StdDraw.line(0, -2, 0, 2);
            StdDraw.line(0, 1, 0.12, 1);
            StdDraw.line(0, -1, 0.12, -1);
            StdDraw.line(1, 0, 1, 0.12);
            StdDraw.line(-1, 0, -1, 0.12);
            StdDraw.text(0.2, 0.98, "1");
            StdDraw.text(0.2, -0.98, "-1");
            StdDraw.text(0.98, -0.1, "1");
            StdDraw.text(-0.98, -0.1, "-1");
            // draw a circle


            StdDraw.filledCircle(x, y, 0.05);
            StdDraw.filledCircle(-x, -y, 0.05);
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}
