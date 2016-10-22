import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates an animation of the Serbian flag.
 */
public class Window extends JFrame {

    private static int WIDTH = 800;
    private static int HEIGHT = 800/16*9;

    // Colors of the flag.
    private static final Color COLOR_TOP = new Color(12, 64, 118);
    private static final Color COLOR_MIDDLE = new Color(189, 51, 57);
    private static final Color COLOR_BOTTOM = new Color(255, 255, 255);

    private ArrayList<Point> upperFlag = new ArrayList<>(); // Upper section of flag.
    private ArrayList<Point> lowerFlag = new ArrayList<>(); // Lower section of flag.

    private Window() {
        createFlag();
        Animation animation = createEngine();
        setWindowProperties();
        start(animation);
    }

    /**
     * Creates the points that make up the basic skeletal structure of the flag.
     */
    private void createFlag () {
        for (int i = 0; i < 103; i++) {
            upperFlag.add(new Point(i * 8, HEIGHT / 2 - 50, i * 5));
        }

        for (int i = 0; i < 103; i++) {
            lowerFlag.add(new Point(i * 8, HEIGHT / 2 + 50, i * 5));
        }
    }

    private Animation createEngine () {
        Animation animation = new Animation();
        Container cp = getContentPane();
        cp.add(animation);
        animation.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return animation;
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Flag");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void start (Animation animation) {
        Thread th = new Thread(animation);
        th.start();
    }

    /**
     * Handles the animation loop.
     */
    private class Animation extends JPanel implements Runnable {

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

                sleep();
            }
        }

        /**
         * Sleep for 10 milliseconds.
         */
        private void sleep () {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * Go to the next frame.
         */
        private void update () {
            upperFlag.forEach((i) -> i.increment(20));
            lowerFlag.forEach((i) -> i.increment(20));
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            // Ensures that the animation runs smoothly on Linux.
            if (System.getProperty("os.name").equals("Linux")) {
                Toolkit.getDefaultToolkit().sync();
            }

            setBackground(COLOR_BOTTOM);

            drawWaves(graphics, lowerFlag, COLOR_TOP);
            drawWaves(graphics, upperFlag, COLOR_MIDDLE);
        }

        private void drawWaves (Graphics graphics, ArrayList<Point> list, Color c) {

            Graphics2D g = (Graphics2D) graphics;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(c);

            int[] x = new int[list.size()+2];
            int[] y = new int[list.size()+2];

            for (int i = 0; i < list.size()-1; i++) {
                x[i] = list.get(i).getX();
                y[i] = list.get(i).getY();
                if (list.get(i).getY() == 0) System.out.println(list.get(i));
            }

            x[list.size()-2] = Window.WIDTH;
            y[list.size()-2] = 0;

            g.fillPolygon(x, y, list.size()+2);
        }

    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}
