/**
 * Represents the location of a pixel on an image.
 */
class Point {

    private int x;
    private int y;

    /**
     * Constructs the point.
     * @param x     the x coordinate of the pixel
     * @param y     the y coordinate of the pixel
     */
    Point (int x, int y) {

        if (x >= 65535 || y >= 65535) {
            throw new RuntimeException("Exceeded max value of 65535 for x or y.");
        }

        this.x = x;
        this.y = y;
    }

    int getX () {
        return x;
    }

    int getY () {
        return y;
    }

    @Override
    public int hashCode() {
        return ((getX() << 16) | getY());
    }

    @Override
    public boolean equals(Object o) {

        boolean result = false;

        if (o instanceof Point) {
            Point other = (Point) o;
            result = (this.getX() == other.getX() && this.getY() == other.getY());
        }

        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
