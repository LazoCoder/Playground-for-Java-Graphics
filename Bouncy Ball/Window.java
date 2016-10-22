import javax.swing.*;
import java.awt.*;

/**
 * Creates a simulation of a bouncy ball that can be moved with the arrow keys.
 */
public class Window extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800/16*9;

    private Window() {
        Engine engine = createEngine();
        setWindowProperties();
        startEngine(engine);
    }

    private void setWindowProperties () {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Lazo's Bouncy Ball");
        setVisible(true);
    }

    private Engine createEngine () {
        Engine engine = new Engine(WIDTH, HEIGHT);
        Container cp = getContentPane();
        cp.add(engine);
        cp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return engine;
    }

    private void startEngine (Engine engine) {
        Thread th = new Thread(engine);
        th.start();
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}
