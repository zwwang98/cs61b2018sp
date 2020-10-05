package byog.lab5;
import javafx.geometry.Pos;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final boolean UP = true;
    private static final boolean DOWN = false;

    public static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Adds a hexagon of side length s to a given position in the world.
     * 1.Position is a very simple class with two variables p.x and p.y and no methods.
     * 2.p specifies the lower left corner of the hexagon.
     * 3.The coordinate for TETile[][] world is a little tricky here.
     *
     *   world[0][0] is the left bottom corner tile in the generating world interface
     * */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        /* add a hexagon following steps below:
        * 1.add a hexagon by add two halves(upper half and bottom half):
        * (1) use the Position p to calculate the two middle rows' start points' Position
        * (2) draw each half from the two middle rows's start points' Position
        * 2. */
        HexWorld h = new HexWorld();
        Position[] startPoints = h.calMidStartPoint(p.x, p.y, s);
        // lo is the lower middle row's start point
        Position lo = startPoints[0];
        // hi is the lower middle row's start point
        Position hi = startPoints[1];
        // draw the upper half of the hexagon
        h.addHalfHexagon(world, hi, s, t, UP);
        // draw the lower half of the hexagon
        h.addHalfHexagon(world, lo, s, t, DOWN);

    }

    /**
     * Calculate the two start points of the two middle rows of a hexagon to be added.
     * The hexagon's lower left point's Position is passing in as p.
     * */
    public Position[] calMidStartPoint(int x, int y, int n) {
        Position[] startPoints = new Position[2];
        // As we all know, there are two longest middle rows
        // p1 is the lower middle row's start point, its index will be 0
        // p2 is the upper middle row's start point, its index will be 1
        Position p1 = new Position(x - (n - 1), y + (n - 1));
        Position p2 = new Position(x - (n - 1),y + n);
        startPoints[0] = p1;
        startPoints[1] = p2;
        return startPoints;
    }

    /**
     * A private helper method to help addHexagon complete its work.
     * Add half of a hexagon to TETile[][] world.
     * 1.p is the left most point of one of the two middle rows
     * 2.boolean upOrDown decides the direction,
     *   true for draw up and false for draw down
     * */
    private void addHalfHexagon(TETile[][] world, Position p, int s, TETile t, boolean up) {
        if (up) {
            // draw up
            for (int i = 0; i < s; i++) {
                // curStartP is the current start point of the row to be drawn in this loop
                Position curStartP = new Position(p.x + i, p.y + i);
                // l is the length of current row to be drawn
                // (s + 2 * (s - 1)) is the length of the longest row
                // we could figure out this rule from the instructions' example hexagons
                //      aa        aaa         aaaa             aaaaa
                //    aaaa      aaaaa       aaaaaa           aaaaaaa
                //    aaaa     aaaaaaa     aaaaaaaa         aaaaaaaaa
                //     aa      aaaaaaa    aaaaaaaaaa       aaaaaaaaaaa
                //              aaaaa     aaaaaaaaaa      aaaaaaaaaaaaa
                //               aaa       aaaaaaaa       aaaaaaaaaaaaa
                //                          aaaaaa         aaaaaaaaaaa
                //                           aaaa           aaaaaaaaa
                //                                           aaaaaaa
                //                                            aaaaa
                int l = (s + 2 * (s - 1)) - 2 * i;
                // draw one row in the for loop below
                addRow(world, curStartP, l, t);
            }
        } else {
            // draw down
            for (int i = 0; i < s; i++) {
                // curStartP is the current start point of the row to be drawn in this loop
                Position curStartP = new Position(p.x + i, p.y - i);
                int l = (s + 2 * (s - 1)) - 2 * i;
                // draw one row in the for loop below
                addRow(world, curStartP, l, t);
            }
        }
    }

    /** Add a row in TETile[][] world.
     * 1.the row starts at Position start
     * 2.the row's length is passing in as length
     * 3.fill the row with TETile t*/
    private void addRow(TETile[][] world, Position start, int length, TETile t) {
        for (int j = 0; j < length; j++) {
            world[start.x][start.y] = t;
            start.x++;
        }
    }

    /** @source BoringWorldDemo.java */
    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p = new Position(2, 0);
        HexWorld.addHexagon(world, p, 3, Tileset.WATER);
        // fills in a block 14 tiles wide by 4 tiles tall

        // draws the world to the screen
        ter.renderFrame(world);
    }

}
