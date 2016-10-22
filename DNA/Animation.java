import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Handles the animation loop.
 */
class Animation extends JPanel implements Runnable {

    private static final Color BACK_COLOR = new Color(0x00, 0x31, 0x71);
    private static final Color FORE_COLOR = new Color(255, 255, 255);
    private ArrayList<Point> points; // Holds each node in the DNA structure.

    /**
     * Constructs the animation.
     * @param points    The nodes of the DNA to be drawn.
     */
    Animation(ArrayList<Point> points) {
        this.points = points;
    }

    /**
     * Starts the animation.
     */
    public void run () {

        long start = System.nanoTime();
        long now;
        double timeElapsed = 0.0;
        double FPS = 40.0;

        // The main animation loop.
        while (true) {

            now = System.nanoTime();
            timeElapsed += ((now-start)/1_000_000_000.0) * FPS;
            start = System.nanoTime();

            if (timeElapsed > 1) {
                update();
                repaint();
                timeElapsed--;
            }
            sleepThread();
        }
    }

    /**
     * Spins the DNA.
     */
    private void update () {
        points.forEach((point) -> point.increment());
    }

    private void sleepThread () {
        try {
            sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Ensures that the animation runs smoothly on Linux machines.
        if (System.getProperty("os.name").equals("Linux")) {
            Toolkit.getDefaultToolkit().sync();
        }

        setBackground(BACK_COLOR);
        drawDNA(graphics);
    }

    private void drawDNA (Graphics graphics) {
        drawBridges(graphics);
        drawRidges(graphics);
        drawNodes(graphics);
    }

    /**
     * Draws the vertical lines.
     * @param graphics  the Graphics object that will be used for drawing
     */
    private void drawRidges (Graphics graphics) {

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(FORE_COLOR);

        for (int i = 0; i < points.size()-1; i++) {
            if (Math.abs(points.get(i).getY() - points.get(i + 1).getY()) <= 20) {
                g.drawLine(points.get(i).getX(), points.get(i).getY(),
                        points.get(i + 1).getX(), points.get(i + 1).getY());
            }
        }
    }

    /**
     * Draws lines between nodes on the same horizontal axis.
     * @param graphics  the Graphics object that will be used for drawing
     */
    private void drawBridges (Graphics graphics) {

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Point p1 : points) {
            for (Point p2 : points) {
                if (p1.getY() == p2.getY()) {
                    g.setColor(FORE_COLOR);
                    g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                }
            }
        }

    }

    private void drawNodes (Graphics graphics) {
        points.forEach((point) -> point.draw(graphics, FORE_COLOR));
    }

}