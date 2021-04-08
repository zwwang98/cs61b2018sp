package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class World implements Serializable {
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
        if (PLAYER.equalsTo(DOOR)) {
            world.map[DOOR.x][DOOR.y] = Tileset.UNLOCKED_DOOR;
        }
        return world;
    }

    public static void renderWorld(World world) {
        TERenderer ter = new TERenderer();
        ter.initialize(world.width, world.height);
        ter.renderFrame(world.map);
    }

    public boolean equals(World world) {
        int w = world.width;
        int h = world.height;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (!this.map[i][j].description().equals(world.map[i][j].description())) {
                    return false;
                }
            }
        }
        return true;
    }
}
