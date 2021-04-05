package byog.Core;

import byog.TileEngine.TETile;

public class TestTwice {
    public static void main(String[] args) {
        Game g = new Game();

        TETile[][] world1 = g.playWithInputString("N123123SWWDDWW");
        System.out.println(TETile.toString(world1));
        TETile[][] world2 = g.playWithInputString("N123123SWWDDWW");
        System.out.println(TETile.toString(world2));
        System.out.println(world1.equals(world2));
    }

}
