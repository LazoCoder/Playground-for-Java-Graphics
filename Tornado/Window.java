import Tornado.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Allows a user to create a graph and rotate it in 3D space.
 */
public class Window extends JFrame {

    private Graph graph;
    private Engine engine;
    private boolean rotateLeft, rotateRight;

    private Window() {
        graph = new Graph();
        engine = createEngine();
        addKeyListener(new MyKeyAdapter());
        setWindowProperties();
        startEngine();
    }

    private Engine createEngine () {
        Engine engine = new Engine();
        Container cp = getContentPane();
        cp.add(engine);
        engine.setPreferredSize(new Dimension(800, 800/16*9));
        engine.addMouseListener(new MyMouseAdapter());
        return engine;
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Lazo's Tornado");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startEngine () {
        Thread th = new Thread(engine);
        th.start();
    }

    /**
     * Deals with the drawing and painting of the graph.
     */
    private class Engine extends JPanel implements Runnable {

        @Override
        public void run() {
            long before = System.nanoTime();
            double elapsedTime = 0.0;
            double FPS = 40.0;

            // The main loop of the animation.
            while (true) {

                long now = System.nanoTime();
                elapsedTime += ((now-before)/1_000_000_000.0) * FPS;
                before = System.nanoTime();

                if (elapsedTime >= 1) {
                    update();
                    repaint();
                    elapsedTime--;
                }

                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }

        private void update () {
            if (rotateLeft) {
                graph.incrementClockwise();
            } else if (rotateRight) {
                graph.incrementCounterClockwise();
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            setBackground(new Color(26, 164, 196));
            graph.paint(graphics);
        }
    }

    /**
     * Clicking the screen creates a new vertex.
     */
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            super.mousePressed(mouseEvent);
            graph.click(mouseEvent.getX(), mouseEvent.getY(), 0, 10);
            engine.repaint();
        }

    }

    /**
     * The left and right arrow keys rotate the graph.
     */
    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            super.keyPressed(keyEvent);

            if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE
                    || keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                graph.removeSelected();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                rotateRight = true;
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                rotateLeft = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            rotateLeft = false;
            rotateRight = false;
        }
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}
