import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Creates bubbles when the screen is clicked.
 */
public class Window extends JFrame {

    private static int WIDTH = 800;
    private static int HEIGHT = WIDTH/16*9;

    private Engine engine;
    private Bubbles bubbles;

    private Window() {
        engine = createEngine();
        bubbles = new Bubbles();
        setWindowProperties();
        startEngine(engine);
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Lazo's Bubbles");
        setVisible(true);
    }

    private Engine createEngine () {
        engine = new Engine();
        Container cp = getContentPane();
        cp.add(engine);
        engine.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        engine.addMouseListener(new MyMouseAdapter());
        return engine;
    }

    private void startEngine (Engine engine) {
        Thread th = new Thread(engine);
        th.start();
    }

    /**
     * Contains the main loop for the simulation.
     */
    private class Engine extends JPanel implements Runnable {

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            setBackground(Color.BLACK);
            bubbles.paint(graphics);
        }

        public void run () {

            long before = System.nanoTime();
            double FPS = 40.0;
            long now;
            double delta = 0.0;

            while (true) {

                now = System.nanoTime();
                delta  += ((now - before) / 1_000_000_000.0) * FPS;
                before = System.nanoTime();

                if (delta >= 1) {
                    bubbles.increment();
                    repaint();
                    delta -= 1;
                }

            }

        }

    }

    /**
     * Clicking on the Window creates bubbles.
     */
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            super.mousePressed(mouseEvent);
            bubbles.addBubble(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}
