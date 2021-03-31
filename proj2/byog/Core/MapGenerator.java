package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  Generate a random map following steps below:
 *  1. create random rooms
 *      (1) a room is determined by three variables: its position and its size(width and height)
 *      (2) the position and the size of a room should be random
 *      (3)
 *  2. connect random rooms using hallway
 *  3. add the door tile and the player tile
 *  */
public class MapGenerator {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /* Deal with randomness. */
    private static long SEED;
    private static Random RANDOM;

    public MapGenerator() {

    }

    public MapGenerator(long seed) {
        SEED = seed;
        RANDOM = new Random(seed);
    }

    /**
     * 我想直接把Hallway都当作L型的。这样一来：
     * 1. 生成两个相邻room之间的hallway时，我们只需要在两个room中各选一个点，
     *    再随机决定生成L型hallway的方向，就可以将两个room连接起来了。
     * 2. hallway所谓的方向，就是 “正 L” 和 “倒 L”
     *    *            *   |   * * *     * * *
     *    *            *   |   *             *
     *    * * *    * * *   |   *             *
     *    正 L  ==>  true  |   倒 L  ==>  false
     * 3. 不过仔细想一下，只有两个点x、y都不相同，才能形成L型hallway，否则就是straight hallway
     *    所以可能加一个if-else简单处理一下这种情况先
     * */
    public static class Hallway {
        // the length of the Hallway object
        int l;
        // the direction of the Hallway object, true for horizontal and false for vertical
        boolean d;

        public Hallway(int l, boolean d) {

        }
    }

    /**
     * Draw one room with given Room object.
     * This is just a simplified version of the drawOneRoom below.
     * */
    public void drawOneRoom(TETile[][] world, Room r) {
        drawOneRoom(world, r.s, r.w, r.h);
    }

    /**
     * Draw one room with given start point, its width and its height.
     * Now we simply draw the FLOOR tiles, WALL tiles are not included.
     * */
    public void drawOneRoom(TETile[][] world, Position s, int w, int h) {
        // check if the room is valid
        if ((s.x + w - 1) >= world.length) {
            return;
        }
        if ((s.y + h - 1) >= world[0].length) {
            return;
        }
        // calculate the four corners of the room
        Position leftTop = new Position(s.x, s.y + h - 1);
        Position rightBottom = new Position(s.x + w - 1, s.y);
        Position rightTop = new Position(s.x + w - 1, s.y + h - 1);
        // draw the floor
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                world[s.x + i][s.y + j] = Tileset.FLOOR;
            }
        }
    }

    /**
     * A helper method for drawOneRoom to draw the wall.
     * */
    public void drawOneLine(TETile[][] world, Position start, Position end, TETile tile) {
        // check the line to draw is horizontal or vertical
        if (start.x == end.x) { // draw a vertical line
            int x = start.x;
            int y = start.y;
            // we draw a line from bottom to top/from left to right
            int length = end.y - start.y + 1;
            for (int i = 0; i < length; i++) {
                world[x][y++] = tile;
            }
        } else { // draw a horizontal line
            int x = start.x;
            int y = start.y;
            // we draw a line from bottom to top/from left to right
            int length = end.x - start.x + 1;
            for (int i = 0; i < length; i++) {
                world[x++][y] = tile;
            }
        }
    }

    /**
     * Generate one random room following steps below:
     * 1. a room is determined by three variables: Position p, width w and height h
     *    so to generate a random room, we need to generate these variables randomly
     *    (1) Position p represents the bottom left point of the room
     *    (2) I assume that the size of the room includes
     *        not only the blank Tile but also the wall tile, so
     *        a room's width >= 4
     *        a room's height >= 4
     *    (3) therefore, the Position p should leave enough space
     *        for generating a room at least 4×4, so
     *        0 <= p.x <= world.width - 4
     *        0 <= p.y <= world.height - 4
     * */
    public void drawOneRandomRoom(TETile[][] world, Random random) {
        int x = RandomUtils.uniform(random, 2, world.length - 4);
        int y = RandomUtils.uniform(random, 2, world[0].length - 4);
        Position s = new Position(x, y);
        int w = RandomUtils.uniform(random, 4, 8);
        int h = RandomUtils.uniform(random, 4, 8);
        drawOneRoom(world, s, w, h);
    }


    /**
     * Draw random rooms.
     * Each time we generate a new randomly-located room and
     * check if it is overlapped with any existing room,
     *     if not, we add it to the TETile[][] world
     *     if so, we abandon this new room and try one more time
     * In total, we try this procedure for "tries" times.
     *
     * @param world TETile[][], represents the world
     * @param random Random
     * @param tries the number for trying to draw a random room
     * */
    public List<Room> drawRandomRooms(TETile[][] world, Random random, int tries) {
        List<Room> rooms = new ArrayList();
        for (int i = 0; i < tries; i++) {
            // generate a random start point for a new room
            // since now we only count FLOOR tiles, so we need to leave 1 tile for WALL
            int x = RandomUtils.uniform(random, 1, world.length - 4);
            int y = RandomUtils.uniform(random, 1, world[0].length - 4);
            Position s = new Position(x, y);
            // decide the new room's size with randomness
            int w = RandomUtils.uniform(random, 3, 6);
            int h = RandomUtils.uniform(random, 3, 6);
            // check if the new room is beyond the boundary
            // also, leave 1 tile empty for WALL
            if (((x + w) >= world.length) || ((y + h) >= world[0].length)) {
                continue;
            }
            // check if the new room is overlapped with existing rooms
            Room newRoom = new Room(w, h, s);
            if (newRoom.isOverlapped(rooms)) {
                continue;
            }
            // make the rooms more distributed
            if (Room.isNearFloorN(world, x, y, 3)) {
                continue;
            }


            // 1. add the new room to the Room[] rooms
            rooms.add(newRoom);
            // 2. draw it
            drawOneRoom(world, s, w, h);
        }
        return rooms;
    }

    /**
     * Choose a WALL tile to be the DOOR.
     * The tile above this WALL should be Tileset.FLOOR and
     * the tile below this WALL should be Tileset.NOTHING.
     * Besides, I hope the DOOR can be around the middle,
     * so let's limit the x between like 30-50 or 35-45
     * */
    public void addTheDoor(TETile[][] world) {
        outerLoop:
        for (int i = 35; i < 45; i++) {
            for (int j = 1; j < world[0].length - 1; j++) {
                if (world[i][j] == Tileset.WALL) {
                    if ((world[i][j + 1] == Tileset.FLOOR)
                            && (world[i][j - 1] == Tileset.NOTHING)) {
                        world[i][j] = Tileset.LOCKED_DOOR;
                        // once we find an appropriate place, we don't need to find another one
                        break outerLoop;
                    }
                }
            }
        }
    }

    public TETile[][] drawARandomWorld() {
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

        // test drawOneRandomRooms
        List<Room> rooms = mg.drawRandomRooms(world, RANDOM, 1000);
        Room r = new Room(1, 1, null, SEED);
        r.connectRooms(rooms, world);
        r.drawWalls(world);
        mg.addTheDoor(world);
        return world;
    }

    /**
     * */
    public void addThePlayer(TETile[][] world) {
        outerLoop:
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == Tileset.FLOOR) {
                    world[i][j] = Tileset.PLAYER;
                    break outerLoop;
                }
            }
        }
    }



}
