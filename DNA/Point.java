import java.awt.*;

/**
 * Represents the circular node on the DNA structure.
 */
class Point {

    private int originX;
    private int x,y;
    private double angle = 0.0;
    private int z = 0;

    /**
     * The step to increment the location of the point (aka the speed of the point).
     */
    private static final double STEP = 0.1;

    /**
     * Construct Point at a particular location.
     * @param x     the x coordinate of the Point
     * @param y     the y coordinate of the Point
     * @param step  the frame number to start the animation at
     */
    Point (int x, int y, int step) {
        this.originX = x;
        this.x = x;
        this.y = y;

        for (int i = 0; i < step; i++)
            increment();
    }

    /**
     * Move the point to its next position or next "frame".
     */
    void increment () {
        angle += STEP;
        x = (int) (originX + Math.sin(angle) * 40.0);
        z = (int) (10 + Math.cos(angle) * 10) + 10;
    }

    /**
     * Get the current x coordinate of the point
     * @return  the x coordinate
     */
    int getX () {
        return x;
    }

    /**
     * Get the current y coordinate of the point
     * @return  the t coordinate
     */
    int getY () {
        return y;
    }

    /**
     * Draw the point to the screen.
     * @param graphics  the Graphics object to use for drawing
     * @param foreColor the color of the point`
     */
    void draw (Graphics graphics, Color foreColor) {
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(foreColor);
        g.fillOval(x-(z/4+5)/2, y-(z/4+5)/2, z/4 + 5, z/4 + 5);

    }

    @Override
    public String toString () {
        return "(" + x + ", " + y + ")";
    }
}
