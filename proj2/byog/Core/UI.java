package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import javax.annotation.processing.SupportedSourceVersion;
import java.awt.*;
import java.io.*;
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
        StdDraw.setCanvasSize();
        Font font = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.8, "CS61B: THE GAME");
        Font font2 = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.55, "New Game (N)");
        StdDraw.text(0.5, 0.5, "Load Game (L)");
        StdDraw.text(0.5, 0.45, "Quit (Q)");
        StdDraw.show();
    }

    /**
     * Solicit keyboard's input.
     * Return strings in lower case.
     * */
    public String solicitKeyboardInput() {
        String input = "";
        if (StdDraw.hasNextKeyTyped()) {
            input += StdDraw.nextKeyTyped();
        }
        return input.toLowerCase(Locale.ROOT);
    }

    public void solicitMainMenuOption() throws IOException, ClassNotFoundException {
        String s = "";
        while (true) {
            String option = solicitKeyboardInput().toLowerCase(Locale.ROOT);
            switch (option) {
                case "n":
                    s = solicitNewGameSeed();
                    MapGenerator mg = new MapGenerator(Long.parseLong(s));
                    World world = mg.generateARandomWorld();
                    playTheGame(world);
                    break;
                case "l":
                    World loadWorld = loadGame();
                    playTheGame(loadWorld);
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
     * With this method and the method below, now we could
     * display the description of the tile under the mouse pointer.
     *
     * @Source https://github.com/Joshmomel/CS61B/blob/master/proj2/byog/Core/Game.java line 149
     * */
    public char waitForControlKey(World world) {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
            mouseTile(world);
        }
        return StdDraw.nextKeyTyped();
    }

    /**
     * @Source https://github.com/Joshmomel/CS61B/blob/master/proj2/byog/Core/Game.java line 158
     * */
    public void mouseTile(World world) {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        int w = (int) Math.floorDiv((long) x, 1);
        int h = (int) Math.floorDiv((long) y, 1);
        if (h >= HEIGHT) {
            h = HEIGHT - 1;
        }
        if (w >= WIDTH) {
            w = WIDTH - 1;
        }
        TETile tile = world.map[w][h];
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH / 2, HEIGHT - 1, WIDTH / 2, 1);
        StdDraw.setPenColor(Color.PINK);
        StdDraw.textLeft(1, HEIGHT - 1, tile.description());
        StdDraw.show(10);
    }


    /**
     * After generating a random world,
     * user can use WASD to move the PLAYER tile
     * until it reach the DOOR tile
     * */
    public void playTheGame(World world) throws IOException, ClassNotFoundException {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world.map);

        // move the player until it reach the door
        while (true) {
            String direction = ("" + waitForControlKey(world)).toLowerCase(Locale.ROOT);
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
                    case "q":
                        saveGame(world);
                        drawMainMenu();
                        solicitMainMenuOption();
                }
                if (world.DOOR.equalsTo(world.PLAYER)) {
                    StdDraw.clear();
                    StdDraw.text(40, 15, "You win!");
                    StdDraw.show();
                    break;
                }
            }
    }

    /**
     * After pressing L for "load game",
     * */
    public World loadGame() throws IOException, ClassNotFoundException {
        World world = null;
        try {
            FileInputStream fileIn = new FileInputStream(
                    "./world.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            world = (World) in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException c) {
            System.out.println("World class not found");
            c.printStackTrace();
            return null;
        }
        return world;
    }

    public void quitGame() {

    }

    /**
     * After pressing Q for "quit game",
     * */
    public void quitGame(World world) throws IOException {
        saveGame(world);

    }

    public void saveGame(World world) throws IOException {
        File f = new File("./world.txt");
        try {
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UI ui = new UI();
        ui.drawMainMenu();
        ui.solicitMainMenuOption();

    }


}
