import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Contains the main loop for the animation.
 */
class Animation extends JPanel implements Runnable {

    private Image image; // The image that the animation will be drawn to.
    private ArrayList<Caterpillar> caterpillarList; // Contains all the caterpillars.

    Animation() {
        image = new Image(Properties.WIDTH, Properties.HEIGHT);
        caterpillarList = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Ensures that the animation will run smoothly on Linux.
        if (System.getProperty("os.name").equals("Linux")) {
            Toolkit.getDefaultToolkit().sync();
        }

        setBackground(Color.BLACK);
        image.paint(graphics);
    }

    /**
     * Begin the animation.
     */
    public void run () {

        generateCaterpillars();

        long lastTime = System.nanoTime();
        double elapsedTime = 0.0;
        double FPS = 40.0;

        // Main animation loop.
        while (!caterpillarList.isEmpty()) {

            long now = System.nanoTime();
            elapsedTime += ((now - lastTime) / 1_000_000_000.0) * FPS;
            lastTime = System.nanoTime();

            if (elapsedTime >= 1) {
                incrementCaterpillars();
                elapsedTime--;
                repaint();
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
     * Generates caterpillars according to which parameters are set.
     */
    private void generateCaterpillars () {

        if (Properties.SPAWN_AT_BOTTOM) {
            Spawn.onBottom(image, caterpillarList);
        }
        if (Properties.SPAWN_AT_TOP) {
            Spawn.onTop(image, caterpillarList);
        }
        if (Properties.SPAWN_AT_LEFT) {
            Spawn.onLeft(image, caterpillarList);
        }
        if (Properties.SPAWN_AT_RIGHT) {
            Spawn.onRight(image, caterpillarList);
        }
        if (Properties.SPAWN_RANDOMLY) {
            Spawn.random(image, caterpillarList);
        }
        if (Properties.SPAWN_AT_CENTER) {
            Spawn.inCenter(image, caterpillarList);
        }
        if (Properties.SPAWN_AT_CORNER) {
            Spawn.inCorner(image, caterpillarList);
        }
    }

    /**
     * Allow each caterpillar to crawl for one pixel.
     */
    private void incrementCaterpillars () {

        Iterator<Caterpillar> it;
        Caterpillar caterpillar;

        it = caterpillarList.iterator();

        /*
         * If new caterpillars are spawned, after the initial caterpillar generation,
         * they are pushed to this stack, and then added to the caterpillar list.
         */
        Stack<Caterpillar> stack = new Stack<>();

        while (it.hasNext()) {
            caterpillar = it.next();

            if (caterpillar.canCrawl()) {
                caterpillar.crawl();
            } else {
                it.remove();
            }

            if (Properties.MULTIPLY) {
                multiply(caterpillar, stack);
            }
        }

        while (!stack.isEmpty()) {
            caterpillarList.add(stack.pop());
        }
    }

    /**
     * Random chance that a caterpillar spawns another caterpillar.
     * @param caterpillar   the caterpillar that will do the spawning
     * @param stack         the stack to push the new spawned caterpillar to
     */
    private void multiply (Caterpillar caterpillar, Stack<Caterpillar> stack) {
        if (!caterpillar.isDeadEnd()
                && new Random().nextInt(Properties.MULTIPLICATION_ODDS) == 2) {
            stack.push(new Caterpillar(image, caterpillar.getX(), caterpillar.getY(), caterpillar.getHue()));
        }
    }

}