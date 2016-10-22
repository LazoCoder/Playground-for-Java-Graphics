package Tornado;

/**
 * Represents an edge in a graph.
 */
class Point {

    private int x, y, z;
    private int originX = 800 / 2;
    private double angleX = 0.0;
    private double angleY = 0.0;
    private double increment = 0.1;
    private int distanceX;
    private int distanceY;

    Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

        distanceX = (x - originX);

        if (distanceX != 0) {
            angleX = Math.asin((x - originX) / distanceX);
        }
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

    /**
     * Rotate the point counter-clockwise in the z-axis.
     */
    void incrementCounterClockwise () {
        angleX += increment;
        x = (int) (originX + Math.sin(angleX) * distanceX);
        z = (int) ((Math.cos(angleX) * distanceX) * (2.0/5.0));
    }

    /**
     * Rotate the point clockwise in the z-axis.
     */
    void incrementClockwise () {
        angleX -= increment;
        x = (int) (originX + Math.sin(angleX) * distanceX);
        z = (int) ((Math.cos(angleX) * distanceX) * (2.0/5.0));
    }

    public Point clone () {
        Point newPoint = new Point(this.getX(), this.getY(), this.getZ());
        newPoint.distanceX = this.distanceX;
        newPoint.distanceY = this.distanceY;
        newPoint.angleX = this.angleX;
        newPoint.angleY = this.angleY;
        return newPoint;
    }

    @Override
    public boolean equals (Object o) {

        if (o instanceof Point) {

            Point p = (Point) o;
            boolean equivalentX = (getX() == p.getX());
            boolean equivalentY = (getY() == p.getY());
            boolean equivalentZ = (getZ() == p.getZ());

            return (equivalentX && equivalentY && equivalentZ);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (getX() << 20) & (getY() << 10) & (getZ() << 10);
    }

    @Override
    public String toString () {
        return "("+x+", "+y+", "+z+")";
    }

}
