package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Locale;
import java.util.Map;

/**
 * Generate users' interface
 * */
public class UI {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public void drawMainMenu() {
        StdDraw.clear();
        Font font = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.8, "CS61B: THE GAME");
        Font font2 = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.55, "New Game (N)");
        StdDraw.text(0.5, 0.5, "Load Game (L)");
        StdDraw.text(0.5, 0.45, "Quit (Q)");
    }

    public void solicitMainMenuOption() {
        String s = "";
        while (true) {
            String option = "";
            if (StdDraw.hasNextKeyTyped()) {
                option += StdDraw.nextKeyTyped();
            }
            option = option.toLowerCase(Locale.ROOT);
            switch (option) {
                case "n":
                    s = solicitNewGameSeed();
                    MapGenerator mg = new MapGenerator(Long.parseLong(s));
                    World world = mg.generateARandomWorld();
                    playTheGame(world);
                    break;
                case "l":
                    loadGame();
                    break;
                case "q":
                    quitGame();
                    break;
            }
        }
    }

    /**
     * After pressing N for "new game",
     * we use StdDraw's key listening API to read in what the player typed.
     * */
    public String solicitNewGameSeed() {
        StdDraw.clear();
        String s = "";
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        while (s.length() < 10){
            if (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                if (Character.toLowerCase(s.charAt(s.length() - 1)) == 's') {
                    s = s.substring(0, s.length() - 1);
                    break;
                }
                StdDraw.clear();
                StdDraw.text(0.5, 0.5, s);
                StdDraw.show();
            }
        }
        StdDraw.pause(1);
        return s;
    }


    /**
     * After generating a random world,
     * user can use WASD to move the PLAYER tile
     * until it reach the DOOR tile
     * */
    public void playTheGame(World world) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world.map);

        // move the player until it reach the door
        Position door = world.DOOR;
        Position player = world.PLAYER;
        while (!world.DOOR.equalsTo(world.PLAYER)) {
            if (StdDraw.hasNextKeyTyped()) {
                String direction = "" + Character.toLowerCase(StdDraw.nextKeyTyped());
                // move the PLAYER according to user's input
                // W-UP, A-LEFT, S-DOWN, D-RIGHT
                switch (direction) {
                    case "w":
                        if (world.map[world.PLAYER.x][world.PLAYER.y + 1] == Tileset.FLOOR
                        || world.map[world.PLAYER.x][world.PLAYER.y + 1] == Tileset.LOCKED_DOOR) {
                            world.PLAYER = new Position(world.PLAYER.x, world.PLAYER.y + 1);
                            world.updateTheWorld(world);
                            StdDraw.clear();
                            ter.renderFrame(world.map);
                        }
                        break;
                    case "a":
                        if (world.map[world.PLAYER.x - 1][world.PLAYER.y] == Tileset.FLOOR
                        || world.map[world.PLAYER.x - 1][world.PLAYER.y] == Tileset.LOCKED_DOOR) {
                            world.PLAYER = new Position(world.PLAYER.x - 1, world.PLAYER.y);
                            world.updateTheWorld(world);
                            StdDraw.clear();
                            ter.renderFrame(world.map);
                        }
                        break;
                    case"s" :
                        if (world.map[world.PLAYER.x][world.PLAYER.y - 1] == Tileset.FLOOR
                        || world.map[world.PLAYER.x][world.PLAYER.y - 1] == Tileset.LOCKED_DOOR) {
                            world.PLAYER = new Position(world.PLAYER.x, world.PLAYER.y - 1);
                            world.updateTheWorld(world);
                            StdDraw.clear();
                            ter.renderFrame(world.map);
                        }
                        break;
                    case "d":
                        if (world.map[world.PLAYER.x + 1][world.PLAYER.y] == Tileset.FLOOR
                        || world.map[world.PLAYER.x + 1][world.PLAYER.y] == Tileset.LOCKED_DOOR) {
                            world.PLAYER = new Position(world.PLAYER.x + 1, world.PLAYER.y);
                            world.updateTheWorld(world);
                            StdDraw.clear();
                            ter.renderFrame(world.map);
                        }
                        break;
                }

            }
        }

    }

    /**
     * After pressing L for "load game",
     * */
    public void loadGame() {

    }

    /**
     * After pressing Q for "quit game",
     * */
    public void quitGame() {

    }

    public static void main(String[] args) {
        UI ui = new UI();
        ui.drawMainMenu();
        ui.solicitMainMenuOption();

    }


}
