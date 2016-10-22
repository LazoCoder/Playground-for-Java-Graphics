import javax.swing.*;
import java.awt.*;

/**
 * Creates an explosion of color to the screen.
 */
public class Window extends JFrame {

    private Window() {
        Animation animation = createAnimation();
        setWindowProperties();
        startAnimation(animation);
    }

    private Animation createAnimation () {
        Animation animation = new Animation();
        Container cp = getContentPane();
        cp.add(animation);
        animation.setPreferredSize(new Dimension(Properties.WIDTH * Properties.SCALE, Properties.HEIGHT * Properties.SCALE));
        return animation;
    }

    private void setWindowProperties () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Lazo's TieDye Animation");
        pack();
        setVisible(true);
    }

    private void startAnimation (Animation animation) {
        Thread th = new Thread(animation);
        th.start();
    }

    public static void main (String[] args) {

        System.out.println("Parameters:");
        System.out.println("-p     " + "explosion preset");
        System.out.println("-p2    " + "crunch preset");
        System.out.println("-p3    " + "fireworks preset");

        if      (args.length == 1 && args[0].equals("-p"))  Properties.explosion();
        else if (args.length == 1 && args[0].equals("-p2")) Properties.crunch();
        else if (args.length == 1 && args[0].equals("-p3")) Properties.largeFireworks();

        SwingUtilities.invokeLater(() -> new Window());
    }

}
