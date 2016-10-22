import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates an animation of DNA, rotating in place.
 */
public class Window extends JFrame {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 800/16*9;

    private Window() {
        ArrayList<Point> points = createPoints();
        Animation animation = createAnimation(points);
        setWindowProperties();
        startAnimation(animation);
    }

    /**
     * Creates the nodes that form the basic skeletal structure of the DNA animation.
     * @return  the nodes
     */
    private ArrayList<Point> createPoints () {
        ArrayList<Point> points = new ArrayList<>();

        for (int i = 0; i < 18; i++) {
            points.add(new Point(WIDTH / 2, 40 + (i * 20), i * 5));
        }

        for (int i = 0; i < 18; i++) {
            points.add(new Point(WIDTH / 2, 40 + (i * 20), 30 + (i * 5)));
        }

        return points;
    }

    private Animation createAnimation (ArrayList<Point> points) {
        Animation animation = new Animation(points);
        Container cp = getContentPane();
        cp.add(animation);
        animation.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return animation;
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("DNA");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startAnimation (Animation animation) {
        Thread th = new Thread(animation);
        th.start();
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}
