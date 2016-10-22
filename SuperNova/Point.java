import java.util.Random;

/**
 * Represents a particle in the explosion.
 */
class Point {

    // The center point of rotation.
    private int originX;
    private int originY;

    private double angle;    // Ideally between 0 and 2PI.

    private double radius;
    private double radiusIncrement;

    // The actual location.
    private int x;
    private int y;

    Point () {
        generateRandomPoint();
    }

    private void generateRandomPoint () {
        Random r = new Random();

        originX = Properties.WIDTH / 2;
        originY = Properties.HEIGHT / 2;

        angle = (2 * Math.PI) * r.nextDouble();

        radius = 0;
        radiusIncrement = r.nextDouble() * (double)r.nextInt(5);

        increment();
    }

    void increment () {
        radius += radiusIncrement;
        x = (int)(originX + (Math.cos(angle) * radius));
        y = (int)(originY + (Math.sin(angle) * radius));
    }

    int getX () {
        return x;
    }

    int getY () {
        return y;
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("Origin X:\t\t\t" + originX + "\n");
        sb.append("Origin Y:\t\t\t" + originY + "\n");
        sb.append("Start Angle:\t\t" + angle + "\n");
        sb.append("Radius:\t" + radius + "\n");
        return new String(sb);
    }
}