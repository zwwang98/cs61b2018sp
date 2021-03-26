package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;


public class MapVisualTest {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final long SEED = 8378982;
    private static final Random RANDOM = new Random(SEED);

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

        MapGenerator mg = new MapGenerator();

        // test drawOneLine
        /*
        Position s = new Position(0, 5);
        Position e = new Position(3, 5);
        mg.drawOneLine(world, s, e, Tileset.FLOWER);
         */

        // test drwaOneRoom
        // s = new Position(0 ,0);
        // mg.drawOneRoom(world, s, 8, 8);

        // test drawOneRandomRoom
        mg.drawRandomRooms(world, RANDOM, 4500000);



        // draws the world to the screen
        ter.renderFrame(world);
    }
}
