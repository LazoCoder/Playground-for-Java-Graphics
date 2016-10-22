import javax.swing.*;
import java.awt.*;

/**
 * Generates a maze and draws it to the screen.
 */
class Window extends JFrame {

    private static int CANVAS_WIDTH = 640;
    private static int CANVAS_HEIGHT = 640;

    private static int sizeOfWindow;
    private static int sizeOfBlock;
    private static int sizeOfMaze;

    private DrawCanvas canvas;

    private Window() {
		CANVAS_WIDTH = sizeOfWindow;
		CANVAS_HEIGHT = sizeOfWindow;
        canvas = createCanvas();
        setWindowProperties();
    }

    private DrawCanvas createCanvas () {
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        Container cp = getContentPane();
        cp.add(canvas);
        return canvas;
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Maze");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);

            g.setColor(Color.MAGENTA);

            int rows = sizeOfMaze;
            int columns = sizeOfMaze;
            int size = sizeOfBlock;

            Maze.generate(g, rows, columns, size);
        }
    }

    private static void set (int w, int b, int m) {
        sizeOfWindow = w;
        sizeOfBlock = b;
        sizeOfMaze = m;
    }

    public static void main(String[] args) {

        set(600, 20, 29);

        System.out.println("Parameters:");
        System.out.println("-tiny     " + "draws a tiny maze");
        System.out.println("-small    " + "draws a small maze");
        System.out.println("-medium   " + "draws a medium maze");
        System.out.println("-large    " + "draws a large maze");

        if (args.length == 1 && args[0].compareTo("-tiny") == 0) {
            set (600, 20, 29);
        } else if (args.length == 1 && args[0].compareTo("-small") == 0) {
            set(600, 10, 59);
        } else if (args.length == 1 && args[0].compareTo("-medium") == 0) {
            set(600, 5, 119);
        } else if (args.length == 1 && args[0].compareTo("-large") == 0) {
            set(600, 2, 299);
        } else if (args.length == 3){
            set(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }

        SwingUtilities.invokeLater(() -> new Window());
    }
}