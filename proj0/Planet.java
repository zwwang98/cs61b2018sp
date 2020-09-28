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

    /**
     *  Describe the force exerted in the X direction.
     * */
    public double calcForceExertedByX(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double r = this.calcDistance(p);
        double f = this.calcForceExertedBy(p);
        return -f * dx / r; // I don't understand why my sign is reverse.
    }

    /**
     *  Describe the force exerted in the Y direction.
     * */
    public double calcForceExertedByY(Planet p) {
        double dy = this.yyPos - p.yyPos;
        double r = this.calcDistance(p);
        double f = this.calcForceExertedBy(p);
        return -f * dy / r; // I don't understand why my sign is reverse.
    }

    /**
     * Take in an array of Planets and calculate the net X force
     * exerted by all planets in that array upon the current Planet.
     * */
    public double calcNetForceExertedByX(Planet[] planets) {
        double netForceX = 0.0;
        for (Planet p : planets) {
            if (p.equals(this)) continue;
            netForceX += p.calcForceExertedByX(this);
        }
        return -netForceX;
        // @??? The sign here still confuses me.
    }

    /**
     * Take in an array of Planets and calculate the net Y force
     * exerted by all planets in that array upon the current Planet.
     * */
    public double calcNetForceExertedByY(Planet[] planets) {
        double netForceY = 0.0;
        for (Planet p : planets) {
            if (p.equals(this)) continue;
            netForceY += p.calcForceExertedByY(this);
        }
        return -netForceY;
        // @??? The sign here still confuses me.
    }

    /**
     *  A method that determines how much the forces exerted on the planet
     *  will cause that planet to accelerate,
     *  and the resulting change in the planet’s velocity and position in a small period of time dt
     * */
    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        this.xxVel += aX * dt;
        this.yyVel += aY * dt;
        this.xxPos += xxVel * dt;
        this.yyPos += yyVel * dt;
    }

}
