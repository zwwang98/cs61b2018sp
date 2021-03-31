package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.List;
import java.util.Random;


public class MapVisualTest {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final long SEED = 5197882313569031643L;
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

        MapGenerator mg = new MapGenerator(5197880843569031643L);

        // test drawOneLine
        /*
        Position s = new Position(0, 5);
        Position e = new Position(3, 5);
        mg.drawOneLine(world, s, e, Tileset.FLOWER);
         */

        // test drwaOneRoom
        // s = new Position(0 ,0);
        // mg.drawOneRoom(world, s, 8, 8);

        // test drawOneRandomRooms
        List<Room> rooms = mg.drawRandomRooms(world, RANDOM, 1000);
        Room r = new Room(1, 1, null);
        r.connectRooms(rooms, world);
        r.drawWalls(world);
        mg.addTheDoor(world);
        mg.addThePlayer(world);


        // test draw two rooms and connect them
        // case 1: two rooms' start points share the same x coordinate
        //Room r1 = new Room(4, 4, new Position(3, 0));
        //Room r2 = new Room(4, 4, new Position(3, 8));
        // @TODO
        //  it should be able to pass in a Room object and draw it
        //  drawing a room like below is a little abstract and not easy to read
        //  I think the signature of 'drawOneRoom' should be something like
        //  drawOneRoom(TETile[][] world, Room r)
        //mg.drawOneRoom(world, r1);
        //mg.drawOneRoom(world, r2);
        //Room.connectX(r1.s, r2.s, world);

        // @TODO 从这里能看出来，我的设计还是有问题的
        // 1. 上面的connectX，假如r1、r2是x=0，那就会indexOutOfBound，因为hallway的x=0，
        //    这样的话，hallway的左侧的wall就超出了界限
        // 2. 选择WALL时，可能会遇到不合适的情况，比如
        //    (1) 这里的，连接了两个room的角落，但是实际上两个room没有连接
        //    (2) hallway穿过了其中的一个room，造成了奇怪的情况，并且失去了连通性，因为下面的room
        //        因为hallway被隔出来了一个room

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
