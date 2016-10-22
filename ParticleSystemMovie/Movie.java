import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Contains frames that are to be drawn to the screen.
 */
class Movie extends JPanel implements Runnable {

    private ArrayList<BufferedImage> frames;
    private int currentFrame = 0;
    private int increment = 1;

    /**
     * Construct the Movie.
     * @param frames    the images to play sequentially as a movie
     */
    Movie (ArrayList<BufferedImage> frames) {
        this.frames = frames;
        setBackground(Color.BLACK);
    }

    public void run () {

        long before = System.nanoTime();
        double elapsedTime = 0.0;
        double FPS = 30.0;

        // The movie loop which goes through each frame and draws it.
        while (true) {

            long now = System.nanoTime();
            elapsedTime += ((now - before)/1_000_000_000.0) * FPS;
            before = System.nanoTime();

            if (elapsedTime >= 1) {
                update();
                repaint();
                elapsedTime--;
            }

            sleep();

        }

    }

    /**
     * Sleep for 10 milliseconds.
     */
    private void sleep () {
        try {
            Thread.sleep(10);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Go to the next frame. If the last frame is reached, play in reverse.
     */
    private void update () {
        currentFrame += increment;

        // When the last frame is reached, play the movie in reverse.
        // This looks smoother than playing it from the beginning.
        if (currentFrame == frames.size() - 1 || currentFrame == 0) {
            increment *= -1;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ensure it runs smoothlu on Linux.
        if (System.getProperty("os.name").equals("Linux")) {
            Toolkit.getDefaultToolkit().sync();
        }

        g.drawImage(frames.get(currentFrame), 0, 0, null);
    }
}
