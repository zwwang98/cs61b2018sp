package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Locale;
import java.util.Map;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() throws IOException, ClassNotFoundException {
        UI.drawMainMenu();
        String option = solicitKeyboardInput();
        while (true) {
            if (option.equals("n")) {
                String s = solicitNewGameSeed();
                MapGenerator mg = new MapGenerator(Long.parseLong(s));
                World world = mg.generateARandomWorld();
                World.renderWorld(world);
                playTheGame(world);
                break;
            }
            if (option.equals("l")) {
                World loadWorld = loadGame();
                World.renderWorld(loadWorld);
                playTheGame(loadWorld);
                break;
            }
            option = solicitKeyboardInput();
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * 这里的input有两种情况：
     * 1. 包含种子的，比如 "n123sss:q"，这就是一个 new game
     * 2. 不包含种子的，这种就基本是 load a saved game了，所以此时的input一般是l开头的
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        // be able to handle input strings that include movement
        // separate input string into two parts:
        // 1. seed
        // 2. movements (in lower case)
        input = input.toLowerCase(Locale.ROOT);
        TETile[][] world = null;

        if (input.charAt(0) == 'n') {
            int separatePoint = input.toLowerCase(Locale.ROOT).indexOf('s');
            String seed = input.substring(1, separatePoint);
            String movements = input.substring(separatePoint + 1, input.length()).toLowerCase(Locale.ROOT);

            // the input string would be something like "NXXXS", we need to remove the N and the S
            //String seed = input.substring(1, input.length() - 1);
            long s = Long.parseLong(seed);
            MapGenerator mg = new MapGenerator(s);
            world = mg.generateAWorld(s, movements).map;
        }
        // Load a saved game
        if (input.charAt(0) == 'l') {
            String movements = input.substring(1, input.length());
            World loadGame = loadGame();
            MapGenerator mg = new MapGenerator();
            world = mg.loadAWorld(loadGame, movements).map;
        }

        return world;
    }

    /**
     * Solicit keyboard's input.
     * Return strings in lower case.
     * */
    public String solicitKeyboardInput() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
        }
        return ("" + StdDraw.nextKeyTyped()).toLowerCase(Locale.ROOT);
    }

    public void solicitMainMenuOption() throws IOException, ClassNotFoundException {
        String s = "";
        TERenderer ter = new TERenderer();
        while (true) {
            String option = solicitKeyboardInput();
            switch (option) {
                case "n":
                    s = solicitNewGameSeed();
                    MapGenerator mg = new MapGenerator(Long.parseLong(s));
                    World world = mg.generateARandomWorld();

                    ter.initialize(WIDTH, HEIGHT);
                    ter.renderFrame(world.map);
                    playTheGame(world);
                    break;
                case "l":
                    World loadWorld = loadGame();
                    ter.initialize(WIDTH, HEIGHT);
                    ter.renderFrame(loadWorld.map);
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
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 8, "Text a seed consisting of integers");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 4, "Press S to confirm the seed.");

        String s = "";
        StdDraw.setPenColor(StdDraw.WHITE);
        while (s.length() < 10){
            if (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                if (Character.toLowerCase(s.charAt(s.length() - 1)) == 's') {
                    s = s.substring(0, s.length() - 1);
                    break;
                }
                StdDraw.clear(StdDraw.BLACK);
                StdDraw.text(WIDTH / 2, HEIGHT / 2 + 8, "Text a seed consisting of integers");
                StdDraw.text(WIDTH / 2, HEIGHT / 2 + 4, "Press S to confirm the seed.");
                StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, s);
                StdDraw.show();
            }
        }
        // StdDraw.pause(1000); // USELESS
        return s;
    }

    /**
     * With this method and the method below, now we could
     * display the description of the tile under the mouse pointer.
     *
     * @Source https://github.com/Joshmomel/CS61B/blob/master/proj2/byog/Core/Game.java line 149
     * */
    public String waitForControlKey(World world) {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
            mouseTile(world);
        }
        return ("" + StdDraw.nextKeyTyped()).toLowerCase(Locale.ROOT);
    }

    /**
     * Problem to solve:
     * 1. after loading, I cannot move the PLAYER pressing WASD. I don't know why.
     * */








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
        StdDraw.filledRectangle(0, HEIGHT - 1, WIDTH / 8, 1);
        StdDraw.setPenColor(Color.PINK);
        StdDraw.textLeft(1, HEIGHT - 1, tile.description());
        StdDraw.show();
        StdDraw.pause(50); // USELESS
    }


    /**
     * After generating a random world,
     * user can use WASD to move the PLAYER tile
     * until it reach the DOOR tile
     * */
    public void playTheGame(World world) throws IOException, ClassNotFoundException {
        // move the player until it reach the door
        String colonQ  = "";
        while (true) {
            String direction = waitForControlKey(world);
            // move the PLAYER according to user's input
            // W-UP, A-LEFT, S-DOWN, D-RIGHT
            switch (direction) {
                case "w":
                    moveThePlayer(world, up);
                    continue;
                case "a":
                    moveThePlayer(world, left);
                    continue;
                case "s":
                    moveThePlayer(world, down);
                    continue;
                case "d":
                    moveThePlayer(world, right);
                    continue;
            }

            if (direction.equals(":")) {
                if (waitForControlKey(world).equals("q")) {
                    saveGame(world);
                    playWithKeyboard();
                }
            }

            if (world.DOOR.equalsTo(world.PLAYER)) {
                StdDraw.clear(StdDraw.RED);
                StdDraw.setFont(new Font("Arial", Font.BOLD, 60));
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(40, 15, "You win!");
                StdDraw.show();
                StdDraw.disableDoubleBuffering();
                break;
            }
        }
    }

    Position up = new Position(0, 1);     // up
    Position down  = new Position(0, -1); // DOWN
    Position right = new Position(1, 0);  // RIGHT
    Position left = new Position(-1, 0);  // LEFT
    Position[] directions = new Position[]{up, down, right, left};

    public void moveThePlayer(World world, Position direction) {
        int x = world.PLAYER.x;
        int y = world.PLAYER.y;
        // if this movement is valid
        if (world.map[x + direction.x][y + direction.y].description().equals("floor")) {
            world.map[x][y] = Tileset.FLOOR;
            world.map[x + direction.x][y + direction.y] = Tileset.PLAYER;
            world.PLAYER = new Position(x + direction.x, y + direction.y);

            // curX and curY represent current PLAYER's coordinate
            int curX = world.PLAYER.x;
            int curY = world.PLAYER.y;
            world.map[curX][curY].draw(curX, curY);
            world.map[x][y].draw(x, y);
        }
        // if the player reaches the door
        if (world.map[x + direction.x][y + direction.y].description().equals("locked door")) {
            world.PLAYER = new Position(x + direction.x, y + direction.y);
            world.updateTheWorld(world);
            world.map[x][y].draw(x, y);
            int doorX = world.DOOR.x;
            int doorY = world.DOOR.y;
            world.map[doorX][doorY].draw(doorX, doorY);
        }
    }

    /**
     * After pressing L for "load game",
     * */
    public World loadGame() {
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

    public static void saveGame(World world) {
        File f = new File("./world.txt");
        try {
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
