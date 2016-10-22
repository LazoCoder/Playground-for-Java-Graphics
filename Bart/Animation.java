import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Handles the animation loop.
 */
class Animation extends JPanel implements Runnable {

    /**
     * Holds the orbs that are orbiting around Bart's head.
     */
    private ArrayList<Orb> orbs = new ArrayList<>();
    private BufferedImage imageOfBart;

    /**
     * Construct Animation.
     * @param orbs          the orbs that will rotate around Bart's head
     * @param imageOfBart   the .png image of Bart
     */
    Animation(ArrayList<Orb> orbs, BufferedImage imageOfBart) {
        this.orbs = orbs;
        this.imageOfBart = imageOfBart;
    }

    /**
     * Begin the animation.
     */
    public void run () {

        long start = System.nanoTime();
        long now;
        double timeElapsed = 0.0;
        double FPS = 40.0;

        // Main animation loop.
        while (true) {

            now = System.nanoTime();
            timeElapsed += ((now-start)/1_000_000_000.0) * FPS;
            start = System.nanoTime();

            if (timeElapsed > 1) {
                update();
                repaint();
                timeElapsed--;
            }

            threadSleep();
        }

    }

    /**
     * Sleep 10 milliseconds.
     */
    private void threadSleep () {
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

        setBackground(new Color(26, 164, 196));
        drawIn3D(graphics);
    }

    /**
     * Draws the graphics in order of their z value.
     * The orbs that are further away are drawn first.
     * This gives it the illusion of being 3D.
     * @param graphics      the graphics object used for drawing
     */
    private void drawIn3D (Graphics graphics) {

        for (Orb orb : orbs) {
            if (orb.getZ() < 15) {
                orb.draw(graphics);
            }
        }

        graphics.drawImage(imageOfBart, 0, 0, null);

        for (Orb orb : orbs) {
            if (orb.getZ() >= 15) {
                orb.draw(graphics);
            }
        }

    }

    /**
     * Moves each orb to its next location.
     */
    private void update() {
        orbs.forEach((orb) -> orb.increment());
    }

}