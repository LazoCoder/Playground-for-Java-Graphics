import java.awt.*;
import java.util.ArrayList;

/**
 * Contains and executes the animation.
 */
class Animation {

    private ArrayList<Point> listOfPoints;

    Animation() {
        listOfPoints = new ArrayList<>();
        generatePoints();
    }

    /**
     * Generate points with random velocities.
     */
    private void generatePoints () {
        for (int i = 0; i < Properties.POINTS; i++) {
            listOfPoints.add(new Point());
        }
    }

    /**
     * Move each point to its next location in the blast.
     */
    void incrementPoints () {
        listOfPoints.forEach((p) -> p.increment());
    }

    void paint (Graphics g) {

        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.clipRect(0, 0, Properties.WIDTH, Properties.HEIGHT);

        graphics.setColor(Properties.COLOR);

        for (Point p : listOfPoints) {
            graphics.fillOval(p.getX() - Properties.SIZE / 2, p.getY() - Properties.SIZE / 2, Properties.SIZE, Properties.SIZE);
        }
    }

}
