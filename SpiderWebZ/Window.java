import javax.swing.*;
import java.awt.*;

/**
 * Creates an animation of floating dots that connect to other dots
 * within its vicinity. The dots change size over time.
 */
public class Window extends JFrame {

    private Engine engine; // The engine that will run the animation.
    static int WINDOW_WIDTH = 800;
    static int WINDOW_HEIGHT = WINDOW_WIDTH/16*9;

    private Window() {
        initializeEngine();
        setWindowProperties();
        startAnimation();
    }

    private void initializeEngine () {
        engine = new Engine();
        engine.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        Container cp = getContentPane();
        cp.add(engine);
    }

    private void setWindowProperties() {
        setResizable(false);
        pack();
        setTitle("Lazo's Waves Graphics");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startAnimation () {
        Thread th = new Thread(engine);
        th.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}
