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

}
