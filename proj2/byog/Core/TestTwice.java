package byog.Core;

import byog.TileEngine.TETile;
import java.util.Arrays;

public class TestTwice {
    public static void main(String[] args) {
        Game g = new Game();

        TETile[][] world1 = g.playWithInputString("n5017254678959904157swdssswwawsdds");
        System.out.println(TETile.toString(world1));
        TETile[][] world2 = g.playWithInputString("n5017254678959904157swdssswwawsdds");
        System.out.println(TETile.toString(world2));
        System.out.println(Arrays.deepEquals(world1, world2));
    }

}
