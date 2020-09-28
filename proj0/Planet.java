public class Planet {
    // Its current x position
    public double xxPos;
    // Its current y position
    public double yyPos;
    // Its current velocity in the x direction
    public double xxVel;
    // Its current velocity in the y direction
    public double yyVel;
    // Its mass
    public double mass;
    // The name of the file that corresponds to the image that depicts the planet (for example, jupiter.gif)
    public String imgFileName;
    // the Gravitational Constant G
    private static final double G = 6.67e-11;
    // I made a bug here. I wrote G = Math.pow(6.67, 10**-11);

    // two designated constructor
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /**
     * calculate the distance between planets
     * */
    public double calcDistance(Planet p) {
        double xD = Math.abs(this.xxPos - p.xxPos);
        double yD = Math.abs(this.yyPos - p.yyPos);
        return Math.sqrt(xD * xD + yD * yD);
    }

    /**
     * The calcForceExertedBy method takes in a planet,
     * and returns a double describing the force exerted on this planet by the given planet.
     * The sign (positive and negative) is a little trick here.
     * */
    public double calcForceExertedBy(Planet p) {
        double rSquare = this.calcDistance(p);
        return G * this.mass * p.mass / (rSquare * rSquare);
    }


}
