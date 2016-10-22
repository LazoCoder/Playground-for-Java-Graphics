import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Creates an animation of orbs circling around Bart Simpson's head.
 */
public class Window extends JFrame {

    private Window() {
        Animation animation = createAnimation();
        setWindowProperties();
        startAnimation(animation);
    }

    private Animation createAnimation () {
        BufferedImage imageOfBart = getImageOfBart("bart");
        ArrayList<Orb> orbs = createOrbs(imageOfBart);

        Animation animation = new Animation(orbs, imageOfBart);
        Container cp = getContentPane();
        cp.add(animation);
        animation.setPreferredSize(new Dimension(imageOfBart.getWidth(), imageOfBart.getHeight()));

        return animation;
    }

    private ArrayList<Orb> createOrbs (BufferedImage imageOfBart) {

        ArrayList<Orb> list = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            list.add(new Orb(imageOfBart.getWidth() / 2, 40 + (i * 20), i * 5));
        }

        return list;
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Bart");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startAnimation (Animation animation) {
        Thread th = new Thread(animation);
        th.start();
    }

    private static BufferedImage getImageOfBart (String path) {

        BufferedImage image;

        try {
            image = ImageIO.read(Window.class.getResource(path + ".png"));
        } catch (IOException ex) {
            throw new RuntimeException("Image could not be loaded.");
        }

        return image;
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

}
