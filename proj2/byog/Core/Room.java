package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.List;
import java.util.Comparator;


/**
 * We assume that all rooms are rectangular.
 * So three variables below is enough to decide the room.
 * */
public class Room {
    /* Deal with randomness. */
    private static long SEED;
    private static Random RANDOM;



    int w; // the width of the room
    int h; // the height of the room
    Position s; // represent the lower left point's position



    public Room(int w, int h, Position p, long s) {
        this.w = w;
        this.h = h;
        this.s = p;
        SEED = s;
        RANDOM = new Random(s);
    }

    public Room(int w, int h, Position p) {
        this.w = w;
        this.h = h;
        this.s = p;
    }

    public boolean isOverlapped(List<Room> rooms) {
        if (rooms.isEmpty()) {
            return false;
        }
        for (Room r : rooms) {
            if (this.isOverlapped(r)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if two rooms are overlapped following steps below:
     * 1. calculate the Room r's four corners' positions
     * 2. check if any of these four corners is in the room who is calling the method
     * 3. bug: there is a situation in which two rooms are overlapped
     * but the Room r doesn't contain any corner of the calling room in it
     * r → * * * * * * ⬇this
     * *   * * * * * * *      in this situation, Room r and this are overlapped,
     * *   *     *     *      but this doesn't have any corner of r,
     * *   *     *     *      so the examination above is not correct
     * *   * * * * * * *
     * * * * * * *
     * solution: check this.isOverlapped(Room r) and r.isOverlapped(this)
     * ???: is there any other simpler way to test whether two rooms are overlapped?
     * bug2: the solution is not correct, because there is a situation
     * r → * * * * * *       this
     * * * * * * * * * * * * * ⬇      in this situation, Room r and this are overlapped,
     * *     *         *     *        but this doesn't have any corner of r,
     * *     *         *     *        and r doesn't have any corner of this, too
     * * * * * * * * * * * * *        so the examination above is not correct again
     * * * * * * *
     *
     * @param r calculate its four corners' positions
     */
    public boolean isOverlapped(Room r) {
        int x = r.s.x;
        int y = r.s.y;
        // go through every single point in Room r
        // check if any point is in the Room this
        for (int i = 0; i < r.w; i++) {
            for (int j = 0; j < r.h; j++) {
                Position p = new Position(x + i, y + j);
                if (isInTheRoom(p, this)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the point p is in the Room r
     */
    public boolean isInTheRoom(Position p, Room r) {
        return (r.s.x <= p.x && p.x < r.s.x + r.w)
                && (r.s.y <= p.y && p.y < r.s.y + r.h);
    }


    /**
     * Connect all the rooms in the world folloing steps below:
     * 1. sort all the rooms from left to right with our own comparator
     * (1) First of all, since we do not allow overlap between rooms,
     * so it is impossible to have two rooms with the same start point
     * (left bottom point).
     * (2) sort them according to the room's start point's coordinate
     * a) smaller x, higher priority
     * b) when sharing same x, smaller y, high priority
     * c) that's it, since x and y cannot be the same at the same time
     * 2. connect all the rooms from left to right
     */
    public void connectRooms(List<Room> rooms, TETile[][] world) {
        // @TODO
        // sort rooms
        //rooms.sort(); // it seems that a comparator is needed.
        rooms.sort(Room.getCompByCoordinate());

        // connect two adjacent rooms
        for (int i = 0; i < rooms.size() - 1; i++) {
            rooms.get(i).connect(rooms.get(i + 1), world);
        }
    }

    public static Comparator<Room> getCompByCoordinate() {
        Comparator comp = new Comparator<Room>() {
            @Override
            public int compare(Room r1, Room r2) {
                return r1.compareTo(r2);
            }
        };
        return comp;
    }

    public int compareTo(Object o) {
        Room p = (Room) o;
        int x = p.s.x;
        int y = p.s.y;
        if (this.s.x == x) {
            return y - this.s.y;
        } else {
            return x - this.s.x;
        }
    }


    /**
     * Connect two rooms with hallway following steps below:
     * 1. in each room, pick up a WALL point randomly
     * 2. connect these two random points with hallway
     * (1) if these two random points are on the same horizontal or vertical line，
     * connect them with one line
     * (2) if these two random points are not on the same horizontal or vertical line，
     * connect them with a L hallway whose direction is randomly decided
     * 3. if the hallway is overlapped with any existing rooms or hallways, it should be abandoned
     * and then generate another random hallway and test it again
     */
    public void connect(Room r, TETile[][] world) {
        // generate one WALL point for each room
        Position tP = randomPoint(this);
        Position rP = randomPoint(r);
        // there three cases when connecting two points
        // case 1, two points are on the same horizontal line
        if (isSameX(tP, rP)) {
            connectX(tP, rP, world);
        }
        // case 2, two points are on the same vertical line
        if (isSameY(tP, rP)) {
            connectY(tP, rP, world);
            // case 3, two points doesn't share the same x or y
        } else {
            connectWithL(tP, rP, RANDOM, world);
        }
    }

    /**
     * Connect two points who are not on the same horizontal or vertical line
     */
    public void connectWithL(Position tP, Position rP, Random random, TETile[][] world) {
        // use random to decide L-typed hallway's direction
        if (random.nextInt() % 2 == 0) {
            // mid and tP are on the same vertical line, so connectX(tP, mid)
            // mid and rP are on the same horizontal line, so connectY(rP, mid)
            Position mid = new Position(tP.x, rP.y);
            connectX(tP, mid, world);
            connectY(rP, mid, world);
        } else {
            Position mid = new Position(rP.x, tP.y);
            // mid and tP are on the same horizontal line, so connectY(tP, mid)
            // mid and rP are on the same vertical line, so connectX(rP, mid)
            connectY(tP, mid, world);
            connectX(rP, mid, world);
        }
    }

    /**
     * Connect two points who are sharing the same x
     */
    public static void connectX(Position p1, Position p2, TETile[][] world) {
        int x = p1.x;
        int yMin = Math.min(p1.y, p2.y);
        int yMax = Math.max(p1.y, p2.y);
        for (int i = 0; i < yMax - yMin + 1; i++) {
            world[x][yMin + i] = Tileset.FLOOR;
        }
    }

    /**
     * Connect two points who are sharing the same y
     */
    public void connectY(Position p1, Position p2, TETile[][] world) {
        int y = p1.y;
        int xMin = Math.min(p1.x, p2.x);
        int xMax = Math.max(p1.x, p2.x);
        for (int i = 0; i < xMax - xMin + 1; i++) {
            world[xMin + i][y] = Tileset.FLOOR;
        }
    }


    public boolean isSameX(Position p1, Position p2) {
        return p1.x == p2.x;
    }

    public boolean isSameY(Position p1, Position p2) {
        return p1.y == p2.y;
    }

    /**
     * Pick up a WALL point of a room randomly.
     */
    public Position randomPoint(Room r) {
        // minimum x coordinate of WALL tile in the Room r
        int xMin = r.s.x;
        // minimum y coordinate of FLOOR tile in the Room r
        int yMin = r.s.y;
        // maximum x coordinate (including) of WALL tile in the Room r
        int xMax = r.s.x + r.w - 1;
        // maximum y coordinate (including) of WALL tile in the Room r
        int yMax = r.s.y + r.h - 1;
        // RandomUtils.uniform(Random random, int a, int b)
        // return a random integer uniformly in [a, b)
        // so to include xMax and yMax, the endpoint should be 1 larger than xMax and yMax
        int xRandom = RandomUtils.uniform(RANDOM, xMin, xMax + 1);
        int yRandom = RandomUtils.uniform(RANDOM, yMin, yMax + 1);
        // generate the random point in the room
        return new Position(xRandom, yRandom);
    }

    public void drawWalls(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] != Tileset.FLOOR && isNearFloor(world, i, j)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * To check if there is any FLOOR tile near the world[i][j].
     */
    public boolean isNearFloor(TETile[][] world, int x, int y) {
        // loop through the nearest 8 grids
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (isValidXY(x + i, y + j, world)) {
                    if (!(i == 0 && j == 0) && world[x + i][y + j] == Tileset.FLOOR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * To check if there is any FLOOR tile near the world[i][j].
     */
    public static boolean isNearFloorN(TETile[][] world, int x, int y, int n) {
        // loop through the nearest 8 grids
        for (int i = -n; i < n + 1; i++) {
            for (int j = -n; j < n + 1; j++) {
                if (isValidXY(x + i, y + j, world)) {
                    if (!(i == 0 && j == 0) && world[x + i][y + j] == Tileset.FLOOR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * To check if the coordinate (a, b) is in the TETile[][] world.
     * */
    public static boolean isValidXY(int a, int b, TETile[][] world) {
        return 0 <= a && a < world.length
                && 0 <= b && b < world[0].length;
    }
}


