import java.awt.*;

/**
 * Represents the orb that hovers around Bart's head.
 */
class Orb {

    private int originX;
    private int x,y;
    private double angle = 0.0;
    private int z = 0;

    /**
     * The step to increment the location of the orb (aka the speed of the orb).
     */
    private static final double STEP = 0.1;

    /**
     * Construct Orb a particular location.
     * @param x     the x coordinate of the orb
     * @param y     the y coordinate of the orb
     * @param step  the frame number to start the animation at
     */
    Orb (int x, int y, int step) {
        this.originX = x;
        this.x = x;
        this.y = y;

        for (int i = 0; i < step; i++) {
            increment();
        }
    }

    /**
     * Move the orb to its next position or the next "frame".
     */
    void increment () {
        angle += STEP;
        x = (int) (originX + Math.sin(angle) * 100.0);
        z = (int) (10 + Math.cos(angle) * 10) + 10;
    }

    /**
     * Get the position in the Z-plane.
     * @return  the z coordinate
     */
    int getZ () {
        return z;
    }

    /**
     * Draw the orb to the screen.
     * @param graphics  the Graphics object to use for drawing
     */
    void draw (Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillOval(x-z/2, y-z/2, z, z);
    }

    @Override
    public String toString () {
        return "(" + x + ", " + y + ")";
    }
}
