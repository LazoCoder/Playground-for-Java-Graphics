import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Creates an animation of floating dots that connect to other dots
 * within its vicinity.
 */
public class Window extends JFrame {

    private Window() {
        Engine engine = createEngine();
        setWindowProperties();
        startAnimation(engine);
    }

    private Engine createEngine () {
        Engine engine = new Engine();
        Dimension dim = new Dimension(Properties.WINDOW_WIDTH, Properties.WINDOW_HEIGHT);
        engine.setPreferredSize(dim);
        Container cp = getContentPane();
        cp.add(engine);
        return engine;
    }

    private void setWindowProperties() {
        setBackground(Color.BLACK);
        setResizable(false);
        pack();
        setTitle("Lazo's Spider Web Animation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(new MyKeyListener());
    }

    private void startAnimation (Engine engine) {
        Thread th = new Thread(engine);
        th.start();
    }

    /**
     * Contains the main loop for the animation.
     */
    private class Engine extends JPanel implements Runnable {

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
                    animation.increment();
                    elapsedTime--;
                }

                repaint();

            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            setBackground(new Color(53, 53, 53));
            animation.paint(graphics);
        }

    }

    /**
     * F1, F2, F3, F4 & F5 keys change how the animation is displayed.
     */
    private class MyKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent keyEvent) {

            if (keyEvent.getKeyCode() == KeyEvent.VK_F1) {
                Properties.drawPoints = !Properties.drawPoints;
            }
            else if (keyEvent.getKeyCode() == KeyEvent.VK_F2) {
                Properties.drawConnections = !Properties.drawConnections;
            }
            else if (keyEvent.getKeyCode() == KeyEvent.VK_F3) {
                Properties.drawPointIndex = !Properties.drawPointIndex;
                Properties.drawPointCoordinates = false;
                Properties.drawPoints = true;
            }
            else if (keyEvent.getKeyCode() == KeyEvent.VK_F4) {
                Properties.drawPointCoordinates = !Properties.drawPointCoordinates;
                Properties.drawPointIndex = false;
                Properties.drawPoints = true;
            }
            else if (keyEvent.getKeyCode() == KeyEvent.VK_F5) {
                Properties.highQuality = !Properties.highQuality;
            }

        }

    }

    public static void main(String[] args) {

        // Preset
        if (args.length == 1 && args[0].equals("-p")) {
            Properties.POINTS = 200;
            Properties.CONNECTION_DISTANCE = 40;
            Properties.drawPoints = false;
        }

        System.out.println("Use the parameter -p to see another preset.");

        SwingUtilities.invokeLater(() -> new Window());

    }


}