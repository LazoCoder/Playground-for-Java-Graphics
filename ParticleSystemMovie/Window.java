import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Renders a particle system to a Movie and then plays the Movie.
 */
public class Window extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600/16*9;

    private Movie movie;
    private ArrayList<BufferedImage> frames; // The frames in the Movie.

    // One list keeps track of each particle, the other keeps track of the velocity.
    private ArrayList<Vector> vectorList, velocityList;

    private Vector acceleration, mouse;
    private int last = 0;

    // The parameters and details of the simulation.
    private static final int SIZE = 2;
    private static final double LIMIT = 5.0;
    private static final double MULTIPLIER = 0.1;
    private static int quantity = 100_000;
    private static int totalFrames = 300; // The total frames in the movie.

    private Window() {
        frames = new ArrayList<>();
        movie = createMovie();
        initialize();
        setWindowProperties();
        startMovie();
    }

    /**
     * Creates a Movie object of a particular size relative to the Window size.
     * @return  the Movie object
     */
    private Movie createMovie () {
        movie = new Movie(frames);
        Container cp = getContentPane();
        cp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        movie.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        cp.add(movie);
        return movie;
    }

    /**
     * Generates and renders the particles to the Movie.
     */
    private void initialize () {
        long before = System.nanoTime();
        generateParticles();
        render();
        long now = System.nanoTime();
        long elapsedTime = (now-before)/1_000_000_000L;
        System.out.println("Rendering completed in " + elapsedTime + " seconds.");
    }

    /**
     * Creates a set of particles in random locations.
     */
    private void generateParticles () {
        System.out.println("Generating particles...");
        vectorList = new ArrayList<>();
        velocityList = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < quantity; i++) {
            vectorList.add(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT)));
            velocityList.add(new Vector());
        }
        acceleration = new Vector();
        mouse = new Vector(WIDTH/3, HEIGHT/2);
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Lazo's Particle System Movie");
        setVisible(true);
    }

    private void startMovie () {
        Thread th = new Thread(movie);
        th.start();
    }

    /**
     * Moves the particles, draws them to a frame, then puts the frame into the Movie,
     * while printing the progress.
     */
    private void render () {

        System.out.println("Positioning particles...");
        update(300); // Move the particles around a bit before rendering them to the Movie.

        System.out.println("Rendering...");
        for (int i = 0; i < totalFrames; i++) {
            update(3);
            BufferedImage bImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            draw(g);
            frames.add(bImage);
            g.dispose();

            printProgress(i, totalFrames);
        }

        System.out.println("100%\t" +
                "Max Mem: " + Runtime.getRuntime().maxMemory()/100000 + "MB\t" +
                "Used Mem: " + (Runtime.getRuntime().totalMemory()/100000 -
                Runtime.getRuntime().freeMemory()/1000000) + "MB\t"
                + "\r");
    }

    private void printProgress(int progress, int total) {
        int step = total / 100;
        if (progress - step >= last) {
            System.out.print((int)Math.round(((double)(progress))/((double)(total))*100.0) + "%\t" +
                    "Max Mem: " +
                    Runtime.getRuntime().maxMemory()/100000 + "MB\t" +
                    "Used Mem: " +
                    (Runtime.getRuntime().totalMemory()/100000
                            - Runtime.getRuntime().freeMemory()/1000000)
                    + "MB\t" + "\r");
            last = progress;
            System.gc();
        }
    }

    private void update (int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            update();
        }
    }

    private void update () {
        int i = 0;
        for (Vector v : vectorList) {

            if (frames.size() % 20 == 0) {
                Random r = new Random();
                mouse = new Vector(200 + r.nextInt(200), 113 + r.nextInt(113));
            }

            acceleration = mouse.clone();
            acceleration.sub(v);
            acceleration.normalize();
            acceleration.mul(MULTIPLIER);

            velocityList.get(i).add(acceleration);
            velocityList.get(i).limit(LIMIT);
            v.add(velocityList.get(i));
            i++;
        }
    }

    private void draw (Graphics2D g) {
        g.setColor(new Color(53, 53, 53));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (quantity > 20_000) g.setColor(new Color(255, 255, 255, 20));
        else if (quantity > 10_0000) g.setColor(new Color(255, 255, 255, 40));
        else g.setColor(new Color(255, 255, 255, 80));

        for (Vector v : vectorList) {
            g.fillOval((int) v.getX() - SIZE / 2, (int) v.getY() - SIZE / 2, SIZE, SIZE);
        }

        // For debugging center of gravity.
        // drawMouse(g);
    }

    private void drawMouse (Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillOval((int) mouse.getX() - 5, (int) mouse.getY() - 5, 10, 10);
    }

    public static void main (String[] args) {

        System.out.println("Format: \tjava Window [quantity] [frames]");
        System.out.println("Default:\tjava Window 100000 300");

        if (args.length == 2) {
            quantity = Integer.parseInt(args[0]);
            totalFrames = Integer.parseInt(args[1]);
            System.out.println("Creating system of " + quantity +
                    " particles with " + totalFrames + " frames.");
        }

        SwingUtilities.invokeLater(() -> new Window());
    }

}
