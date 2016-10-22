import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Splits an image based on clusters of color, then allows the user
 * to select those clusters to highlight them.
 */
public class Window extends JFrame {

    private Canvas canvas; // The panel to draw to.
    private BufferedImage image; // The image that will be segmented.
    private DisjointSet disjointSet; // Segmentation container.
    private ArrayList<Point> selectedPixels = new ArrayList<>();

    private Window(BufferedImage image, DisjointSet disjointSet) {
        this.image = image;
        this.disjointSet = disjointSet;
        canvas = createCanvas();
        setWindowProperties();
    }

    private void setWindowProperties () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setTitle("Lazo's Image Segmentation Program");
        setVisible(true);
    }

    private Canvas createCanvas () {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        Container cp = getContentPane();
        cp.add(canvas);
        canvas.addMouseListener(new MyMouseAdapter());
        return canvas;
    }

    private static BufferedImage getImage (String path) {

        BufferedImage image;

        try {
            image = ImageIO.read(Window.class.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException("Image could not be loaded.");
        }

        return image;
    }

    private class Canvas extends JPanel {

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(image, 0, 0, null);
            drawSet(graphics, selectedPixels, Color.GREEN);
        }

        private void drawSet (Graphics graphics, ArrayList<Point> list, Color color) {
            graphics.setColor(color);

            for (Point p : list) {
                graphics.drawRect(p.getX(), p.getY(), 0, 0);
            }
        }
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            super.mousePressed(mouseEvent);

            selectedPixels = disjointSet.getSet(new Point(mouseEvent.getX(), mouseEvent.getY()));
            canvas.repaint();
        }
    }

    public static void main(String[] args) {
        BufferedImage image = getImage("marilyn.png");
        Quantization.quantize(image, 2);
        DisjointSet djs = ImageSplitter.split(image);

        SwingUtilities.invokeLater(() -> new Window(image, djs));
    }

}
