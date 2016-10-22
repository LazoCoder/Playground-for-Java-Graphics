import java.util.Random;

/**
 * Represents a floating dot.
 */
class Point {

    // The center point of rotation.
    private int originX;
    private int originY;

    private double angle;    // Ideally between 0 and 2PI.
    private double rotation; // Ideally between 0.1 and 0.5 (or < 0.1 if high radius).

    private int hRadius;
    private int vRadius;

    // The actual location.
    private int x;
    private int y;

    Point () {
        generateRandomPoint();
    }

    private void generateRandomPoint () {
        Random r = new Random();

        int wPadding = Properties.WINDOW_WIDTH / 8;
        int hPadding = Properties.WINDOW_HEIGHT / 8;

        originX = r.nextInt(Properties.WINDOW_WIDTH - wPadding * 2) + wPadding;
        originY = r.nextInt(Properties.WINDOW_HEIGHT - hPadding * 2) + hPadding;

        angle = Math.PI / r.nextInt(360);
        boolean clockwise = r.nextBoolean();

        rotation = (r.nextInt(2) + 2.0) / 100.0;
        if (!clockwise) rotation *= -1;

        hRadius = r.nextInt(100) + 10;
        vRadius = r.nextInt(100) + 10;

        increment();
    }

    void increment () {
        angle += rotation;
        x = (int)(originX + (Math.cos(angle) * hRadius));
        y = (int)(originY + (Math.sin(angle) * vRadius));
    }

    int getX () {
        return x;
    }

    int getY () {
        return y;
    }

    int getDistance (Point p) {

        double xDiff = p.x - x;
        double yDiff = p.y - y;

        double xDiffSquared = Math.pow(xDiff, 2);
        double yDiffSquared = Math.pow(yDiff, 2);

        return (int)Math.sqrt(xDiffSquared + yDiffSquared);

    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("Origin X:\t\t\t" + originX + "\n");
        sb.append("Origin Y:\t\t\t" + originY + "\n");
        sb.append("Start Angle:\t\t" + angle + "\n");
        sb.append("Rotation Step:\t\t" + rotation + "\n");
        sb.append("Horizontal Radius:\t" + hRadius + "\n");
        sb.append("Vertical Radius:\t" + vRadius);
        return new String(sb);
    }
}