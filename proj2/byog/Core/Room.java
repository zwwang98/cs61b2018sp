package byog.Core;

import java.util.ArrayList;
import java.util.Random;

/**
 * We assume that all rooms are rectangular.
 * So three variables below is enough to decide the room.
 * */
public class Room {
    /* Deal with randomness. */
    private static final long SEED = 0;
    private static final Random RANDOM = new Random(SEED);

    int w; // the width of the room
    int h; // the height of the room
    Position s; // represent the lower left point's position

    public Room(int w, int h, Position p) {
        this.w = w;
        this.h = h;
        this.s = p;
    }

    public boolean isOverlapped(ArrayList<Room> rooms) {
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
     *         but the Room r doesn't contain any corner of the calling room in it
     * r → * * * * * * ⬇this
     *     *   * * * * * * *      in this situation, Room r and this are overlapped,
     *     *   *     *     *      but this doesn't have any corner of r,
     *     *   *     *     *      so the examination above is not correct
     *     *   * * * * * * *
     *     * * * * * *
     *    solution: check this.isOverlapped(Room r) and r.isOverlapped(this)
     *    ???: is there any other simpler way to test whether two rooms are overlapped?
     *    bug2: the solution is not correct, because there is a situation
     *        r → * * * * * *       this
     *      * * * * * * * * * * * * ⬇      in this situation, Room r and this are overlapped,
     *      *     *         *     *        but this doesn't have any corner of r,
     *      *     *         *     *        and r doesn't have any corner of this, too
     *      * * * * * * * * * * * *        so the examination above is not correct again
     *            * * * * * *
     *
     * @param r calculate its four corners' positions
     * */
    public boolean isOverlapped(Room r) {
        int x = r.s.x;
        int y = r.s.y;
        int w = r.w;
        int h = r.h;
        // go through every single point in Room r
        // check if any point is in the Room this
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
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
     * */
    public boolean isInTheRoom(Position p, Room r) {
        int x = r.s.x;
        int y = r.s.y;
        int w = r.w;
        int h = r.h;
        return (r.s.x <= p.x && p.x < x + w) &&
                (y <= p.y && p.y < y + h);
    }


    /**
     * Connect two rooms with hallway following steps below:
     * 1. 
     * */
    public void connect(Room r) {

    }

    /**
     * Pick up a FLOOR point in a room randomly.
     * */
    public Position randomPoint(Room r) {
        // minimum x coordinate of FLOOR tile in the Room r
        int xMin = r.s.x + 1;
        // minimum y coordinate of FLOOR tile in the Room r
        int yMin = r.s.y + 1;
        // maximum x coordinate (including) of FLOOR tile in the Room r
        int xMax = r.s.x + r.w - 2;
        // maximum y coordinate (including) of FLOOR tile in the Room r
        int yMax = r.s.y + r.h - 2;
        // RandomUtils.uniform(Random random, int a, int b) return a random integer uniformly in [a, b)
        // so to include xMax and yMax, the endpoint should be 1 larger than xMax and yMax
        int xRandom = RandomUtils.uniform(RANDOM, xMin, xMax + 1);
        int yRandom = RandomUtils.uniform(RANDOM, yMin, yMax + 1);
        // generate the random point in the room
        return new Position(xRandom, yRandom);
    }
}
