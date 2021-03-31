package byog.Core;

import byog.TileEngine.TETile;

public class TestTwice {
    public static void main(String[] args) {
        Game g = new Game();

        TETile[][] world1 = g.playWithInputString("N5197880843569031643S");
        System.out.println(TETile.toString(world1));
        TETile[][] world2 = g.playWithInputString("N5197880843569031643S");
        System.out.println(TETile.toString(world2));
        System.out.println(world1.equals(world2));
    }

}
