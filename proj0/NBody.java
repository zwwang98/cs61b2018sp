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

        /* Animation */
        // This part is completed following the instructions on the website below:
        // https://sp18.datastructur.es/materials/proj/proj0/proj0#creating-an-animation
        StdDraw.enableDoubleBuffering();
        double t = 0;
        int n = planets.length; // the number of planets in this universe
        while (t < T) {
            // Create an xForces array and yForces array.
            double[] xForce = new double[n];
            double[] yForce = new double[n];
            // Calculate the net x and y forces for each planet,
            // storing these in the xForces and yForces arrays respectively.
            for (int i = 0; i < n; i++) {
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
                // Call update on each of the planets.
                // This will update each planet’s position, velocity, and acceleration.
                planets[i].update(dt, xForce[i], yForce[i]);
            }
            // Draw the background image.
            StdDraw.setScale(-r, r);
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            // Draw all of the planets.
            for (Planet p : planets) {
                p.draw();
            }
            // Show the offscreen buffer (see the show method of StdDraw).
            StdDraw.show();
            // Pause the animation for 10 milliseconds (see the pause method of StdDraw).
            // You may need to tweak this on your computer.
            StdDraw.pause(10);
            // Increase your time variable by dt.
            t += dt;
        }
    }
}
