/**
 * The curves on the flag that make it appear as though it is waving is a series
 * of points moving up and down, represented by this class.
 */
class Point {

    private int originY;
    private int x,y;
    private double angle = 0.0;

    /**
     * The step to increment the location of the point (aka the speed of the point).
     */
    private static final double STEP = 0.01;

    /**
     * Constuct the Point at a particular location.
     * @param x     the x coordinate of the Point
     * @param y     the y coordinate of the Point
     * @param step  the frame number to start the animation at
     */
    Point (int x, int y, int step) {
        this.originY = y;
        this.x = x;
        this.y = y;

        for (int i = 0; i < step; i++)
            increment();
    }

    /**
     * Move the point to its next position or next "frame".
     * @param amount    the amount of frames to increment by
     */
    void increment (int amount) {
        for (int i = 0; i < amount; i++)
            increment();
    }

    /**
     * Move the point to its next position or next "frame".
     */
    void increment () {
        angle += STEP;
        y = (int) (originY + Math.sin(angle) * 40.0);
    }

    int getX () {
        return x;
    }

    int getY () {
        return y;
    }

    public String toString () {
        return "(" + x + ", " + y + ")";
    }
}
