import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Creates a particle system where the location of the mouse affects the velocity
 * of the particles.
 */
public class Window extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800/16*9;
    private Engine engine;
    private Thread th;

    private Window() {
        engine = createEngine();
        setWindowProperties();
        th = new Thread(engine);
    }

    private Engine createEngine () {
        engine = new Engine(WIDTH, HEIGHT);
        Container cp = getContentPane();
        engine.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        cp.add(engine);
        cp.addMouseMotionListener(new MyMouseMotionAdapter());
        cp.addMouseListener(new MyMouseAdapter());
        return engine;
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Lazo's Particle System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * The gravity of the particles is affected by the location of the mouse.
     */
    private class MyMouseMotionAdapter extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            super.mouseMoved(mouseEvent);
            engine.mouseLocation(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    /**
     * Clicking the screen begins the simulation.
     */
    private class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            super.mouseClicked(mouseEvent);

            if (mouseEvent.getButton() == MouseEvent.BUTTON1 && !th.isAlive()) {
                th.start();
            }
        }
    }

    public static void main (String[] args) {

        System.out.println("Parameters: ");
        System.out.println("-p\tpreset for different gravity");
        System.out.println("-p2\tpreset for larger particles");

        if (args.length == 1 && args[0].equals("-p")) {
            Engine.limit = 15.0;
            Engine.multiplier = 1;
        } else if (args.length == 1 && args[0].equals("-p2")) {
            Engine.highQuality = true;
            Engine.quantity = 100;
            Engine.size = 10;
            Engine.limit = 15.0;
            Engine.multiplier = 1;
        }
        
        SwingUtilities.invokeLater(() -> new Window());
    }

}
