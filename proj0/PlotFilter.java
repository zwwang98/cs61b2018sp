public class PlotFilter {

    public static void main(String[] args) {

        StdDraw.setXscale(600000, 1300000);
        StdDraw.setYscale(120000, 270000);

        In in = new In("USA.txt");
        // read in bounding box and rescale
        double x0 = in.readDouble();
        double y0 = in.readDouble();
        double x1 = in.readDouble();
        double y1 = in.readDouble();
        StdDraw.setXscale(x0, x1);
        StdDraw.setYscale(y0, y1);

        // for bigger points
        StdDraw.setPenRadius(0.005);

        // to speed up performance, defer displaying points
        StdDraw.enableDoubleBuffering();

        // plot points, one at a time
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            StdDraw.point(x, y);
        }

        // display all of the points now
        StdDraw.show();

    }
}