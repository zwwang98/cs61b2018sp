package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class World {
    int width;
    int height;
    TETile[][] map;

    public World(int w, int h) {
        this.width = w;
        this.height = h;
        map = new TETile[width][height];
    }

    Position DOOR;
    Position PLAYER;
    Position oldPlayer;

    /**
     * When the PLAYER and the DOOR are updated,
     * update the world, especially the TETile[][] map
     * */
    public World updateTheWorld(World world) {
        world.map[oldPlayer.x][oldPlayer.y] = Tileset.FLOOR;
        world.map[PLAYER.x][PLAYER.y] = Tileset.PLAYER;
        oldPlayer = PLAYER;
        // if the PLAYER has reached the DOOR
        if(PLAYER.equalsTo(DOOR)) {
            world.map[DOOR.x][DOOR.y] = Tileset.UNLOCKED_DOOR;
        }
        return world;
    }

}
