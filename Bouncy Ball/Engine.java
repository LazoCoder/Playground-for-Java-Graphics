import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles the physics and collision detection.
 */
class Engine extends JPanel implements Runnable {

    // Panel properties.
    private static final Color COLOR_BACK = new Color(52, 152, 219);
    private static final Color COLOR_FRONT = new Color(44, 62, 80);
    private int width, height;

    // Ball properties and forces acting on the ball.
    private Vector ballPosition, ballVelocity, ballAcceleration;
    private static final int SIZE = 40;
    private static final double INPUT_FORCE = 0.1;

    /**
     * Helper variables for the key listener. If the left, right, or down arrow keys
     * are pressed, the key listener sets the boolean value to true. The main loop
     * checks to see if any of these boolean values are true, and then applies the
     * appropriate forces to the ball.
     */
    private boolean kLeft, kRight, kDown;

    /**
     * Constructs the engine (inside of a panel).
     * @param width     the width of the panel
     * @param height    the height of the panel
     */
    Engine (int width, int height) {
        setPanelProperties(width, height);
        loadBall();
    }

    /**
     * Sets the SIZE of the panel and background color and adds the key listener.
     * @param width     the width of the panel
     * @param height    the height of the panel
     */
    private void setPanelProperties (int width, int height) {
        this.width = width;
        this.height = height;
        setFocusable(true); // Needs to be set to true for key strokes to be detected.
        addKeyListener(new MyKeyAdapter());
        setBackground(COLOR_BACK);
    }

    /**
     * Creates a ball in the center of the screen with gravity pushing down on it.
     */
    private void loadBall () {
        ballPosition = new Vector(width/2, height/2);
        ballVelocity = new Vector(0, 0);
        ballVelocity.setLimit(40);
        ballAcceleration = new Vector(0, 0.1);
    }

    /**
     * Start the simulation.
     */
    @Override
    public void run() {

        long before = System.nanoTime();
        double elapsedTime = 0.0;
        double FPS = 60.0;

        // The Engine main loop.
        while (true) {

            long now = System.nanoTime();
            elapsedTime += ((now-before)/1_000_000_000.0) * FPS;
            before = System.nanoTime();

            if (elapsedTime >= 1) {
                update(2);
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
     * Updates the forces applied to the ball.
     * @param times     the amount of times to update.
     */
    private void update (int times) {
        for (int i = 0; i < times; i++) {
            update();
        }
    }

    /**
     * Updates the forces applied to the ball.
     */
    private void update () {
        ballVelocity.add(ballAcceleration);
        ballPosition.add(ballVelocity);

        addKeyInputForce();
        checkBounds();
    }

    /**
     * Ensures the ball does not escape the bounds of the screen.
     */
    private void checkBounds () {
        if (ballPosition.getY() > height - SIZE /2) {
            ballPosition.newLocation(ballPosition.getX(), height- SIZE /2);
            ballVelocity.add(new Vector(0, -ballVelocity.getY()*2));
        } else if (ballPosition.getY() < SIZE /2) {
            ballPosition.newLocation(ballPosition.getX(), SIZE /2);
            ballVelocity.add(new Vector(0, -ballVelocity.getY()*2));
        } else if (ballPosition.getX() < SIZE /2) {
            ballPosition.newLocation(SIZE /2, ballPosition.getY());
            ballVelocity.add(new Vector(-ballVelocity.getX()*2, 0));
        } else if (ballPosition.getX() > width - SIZE /2) {
            ballPosition.newLocation(width- SIZE /2, ballPosition.getY());
            ballVelocity.add(new Vector(-ballVelocity.getX()*2, 0));
        }
    }

    /**
     * If an arrow key is held down, apply the appropriate force to the ball.
     */
    private void addKeyInputForce () {

        Vector input = new Vector();

        if (kLeft) {
            input.add(new Vector(-INPUT_FORCE, 0));
        }
        if (kRight) {
            input.add(new Vector(INPUT_FORCE, 0));
        }
        if (kDown) {
            input.add(new Vector(0, INPUT_FORCE));
        }

        ballVelocity.add(input);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ensures that the graphics runs smoothly on Linux.
        if (System.getProperty("os.name").equals("Linux")) {
            Toolkit.getDefaultToolkit().sync();
        }

        drawBall(g);
    }

    private void drawBall (Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(3));
        graphics.setColor(COLOR_FRONT);
        graphics.setColor(new Color(236, 240, 241));
        graphics.fillOval((int) ballPosition.getX()- SIZE /2, (int) ballPosition.getY()- SIZE /2, SIZE, SIZE);
        graphics.setColor(COLOR_FRONT);
        graphics.drawOval((int) ballPosition.getX()- SIZE /2, (int) ballPosition.getY()- SIZE /2, SIZE, SIZE);
    }


    /**
     * Detects the left, right and down arrow keys.
     */
    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                kLeft = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                kRight = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN){
                kDown = true;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            kLeft = false;
            kRight = false;
            kDown = false;
        }
    }

}
