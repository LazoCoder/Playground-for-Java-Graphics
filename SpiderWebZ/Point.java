import java.util.Random;

/**
 * Represents a floating dot.
 */
class Point implements Comparable<Point> {

    // The center point of rotation.
    private int originX;
    private int originY;
    private int originZ;

    private double angle;    // Ideally between 0 and 2PI.
    private double rotation; // Ideally between 0.1 and 0.5 (or < 0.1 if high radius).

    private int hRadius;
    private int vRadius;
    private int zRadius;

    // The actual location.
    private int x;
    private int y;
    private int z;

    Point () {
        generateRandomPoint();
    }

    private void generateRandomPoint () {
        Random r = new Random();

        int wPadding = Window.WINDOW_WIDTH / 8;
        int hPadding = Window.WINDOW_HEIGHT / 8;

        originX = r.nextInt(Window.WINDOW_WIDTH - wPadding * 2) + wPadding;
        originY = r.nextInt(Window.WINDOW_HEIGHT - hPadding * 2) + hPadding;
        originZ = r.nextInt(100);

        angle = Math.PI / r.nextInt(360);
        boolean clockwise = r.nextBoolean();

        rotation = (r.nextInt(2) + 2.0) / 100.0;
        if (!clockwise) rotation *= -1;

        hRadius = r.nextInt(100) + 10;
        vRadius = r.nextInt(100) + 10;
        zRadius = r.nextInt(100) + 10;

        increment();
    }

    void increment () {
        angle += rotation;
        x = (int)(originX + (Math.cos(angle) * hRadius));
        y = (int)(originY + (Math.sin(angle) * vRadius));
        z = (int)(originZ + (Math.cos(angle) * zRadius));
        x++;
        if (x == Window.WINDOW_WIDTH) x = 0;
    }

    int getX () {
        return x;
    }

    int getY () {
        return y;
    }

    int getZ () {
        return z;
    }

    /*
    // This gets the distance in 3D space. It doesn't look as cool as 2D space distance.
    public int getDistance (Point p) {

        double xDiff = p.x - x;
        double yDiff = p.y - y;
        double zDiff = p.z - z;

        double xDiffSquared = Math.pow(xDiff, 2);
        double yDiffSquared = Math.pow(yDiff, 2);
        double zDiffSquared = Math.pow(zDiff, 2);

        double xyzSquaredSum = xDiffSquared + yDiffSquared + zDiffSquared;

        return (int)Math.sqrt(xyzSquaredSum);
    }
    */

    // Gets the distance in 2D space.
    int getDistance (Point p) {

        double xDiff = p.x - x;
        double yDiff = p.y - y;

        double xDiffSquared = Math.pow(xDiff, 2);
        double yDiffSquared = Math.pow(yDiff, 2);

        return (int)Math.sqrt(xDiffSquared + yDiffSquared);

    }

    public String toString () {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public int compareTo(Point point) {
        return z - point.z;
    }

}