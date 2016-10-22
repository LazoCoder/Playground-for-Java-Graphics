import javax.swing.*;
import java.awt.*;

/**
 * Contains the main loop that will be running the animation.
 */
class Engine extends JPanel implements Runnable {

    private Animation animation;

    Engine () {
        animation = new Animation();
    }

    public void run () {
        long lastTime = System.nanoTime();
        double elapsedTime = 0.0;
        double FPS = 80.0;

        while(true) {

            long now = System.nanoTime();
            elapsedTime += ((now - lastTime) / 1_000_000_000.0) * FPS;
            lastTime = now;

            if (elapsedTime >= 1) {
                update();
                elapsedTime--;
            }
            repaint();
        }
    }

    private void update () {
        animation.update();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        animation.paint(graphics);
    }

}