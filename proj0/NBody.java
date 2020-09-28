public class NBody {

    /**
     * Given a file name, it should return a double
     * corresponding to the radius of the universe in that file
     * */
    public static double readRadius(String file) {
        /* Start reading in the "file" file. */
        In in = new In(file);
        // Ignore the first value which represents the number of planets in the universe.
        in.readInt();
        return in.readDouble();
    }

    /**
     * Given a file name, it should return
     * an array of Planets corresponding to the planets in the file
     * */
    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        // read the number of planets which decides the size of the Planet[]
        int n = in.readInt();
        Planet[] planets = new Planet[n];
        // ignore the second value which is the radius of the universe
        in.readDouble();
        int i = 0;
        while (!in.isEmpty() && i < n) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i++] = new Planet(xPos, yPos, xVel, yVel, m, img);
        }
        return planets;
    }

    public static void main(String[] args) {
        /* First Step: Collecting All Needed Input. */
        // Store the 0th and 1st command line arguments as doubles named T and dt.
        // @source: The way to convert a String type value into a double value:
        // https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        // Store the 2nd command line argument as a String named filename.
        String filename = args[2];
        // Read in the planets and the universe radius from the file described
        // by filename using your methods from earlier in this assignment.
        Planet[] planets = readPlanets(filename);
        double r = readRadius(filename);

        /* Drawing the background. */
        // set the scale so that it matches the radius of the universe
        StdDraw.setScale(-r, r);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        /* Drawing all the planets */
        for (Planet p : planets) {
            p.draw();
        }
    }
}
