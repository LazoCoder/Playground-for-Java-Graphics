import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the physics.
 */
class Engine extends JPanel implements Runnable {

    private int myWidth, myHeight;
    private static final Color COLOR_BACK = new Color(53,53,53);
    private static final Color COLOR_FRONT = new Color(0xEC,0xF0,0xF1);

    private Vector acceleration, mouse;
    private ArrayList<Vector> vectorList, velocityList;

    // Parameters and details of the simulation.
    static int quantity = 10000;
    static int size = 2;
    static boolean highQuality = false;
    static double limit = 5.0;
    static double multiplier = 0.1;

    Engine (int width, int height) {
        this.myWidth = width;
        this.myHeight = height;
        createParticles();
        acceleration = new Vector();
        mouse = new Vector(myWidth /2, myHeight /2);
    }

    private void createParticles () {
        vectorList = new ArrayList<>();
        velocityList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            vectorList.add(new Vector(new Random().nextInt(myWidth), new Random().nextInt(myHeight)));
            velocityList.add(new Vector());
        }
    }

    /**
     * Start the simulation.
     */
    @Override
    public void run() {

        long before = System.nanoTime();
        double elapsedTime = 0.0;
        double FPS = 60.0;

        // The main loop.
        while (true) {

            long now = System.nanoTime();
            elapsedTime += ((now-before) / 1_000_000_000.0) * FPS;
            before = System.nanoTime();

            if (elapsedTime >= 1) {
                update();
                repaint();
                elapsedTime--;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {}
        }
    }

    private void update () {

        int i = 0;
        for (Vector v : vectorList) {
            acceleration = mouse.clone();
            acceleration.sub(v);
            acceleration.normalize();
            acceleration.mul(multiplier);

            velocityList.get(i).add(acceleration);
            velocityList.get(i).limit(limit);
            v.add(velocityList.get(i));
            // checkBorders(v);
            i++;
        }
    }

    /**
     * If a particle leaves the window, it re-appears on the other opposite side.
     * Example: if it goes too far left, it re-appears on the right.
     * @param v     the vector to check
     */
    private void checkBorders (Vector v) {
        if (v.getX() < 0) {
            v.setXY(myWidth, myHeight - v.getY());
        } else if (v.getX() > myWidth) {
            v.setXY(0, myHeight - v.getY());
        } else if (v.getY() < 0) {
            v.setXY(myWidth - v.getX(), myHeight);
        } else if (v.getY() > myHeight) {
            v.setXY(myWidth - v.getX(), 0);
        }
    }

    void mouseLocation (int x, int y) {
        mouse = new Vector(x, y);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Ensures that the simulation runs smoothly on Linux.
        if (System.getProperty("os.name").equals("Linux")) {
            Toolkit.getDefaultToolkit().sync();
        }

        Graphics2D g = (Graphics2D) graphics;

        if (highQuality) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        setBackground(COLOR_BACK);
        g.setColor(COLOR_FRONT);

        for (Vector v : vectorList) {
            g.fillOval((int) v.getX() - size / 2, (int) v.getY() - size / 2, size, size);
        }
    }

}
