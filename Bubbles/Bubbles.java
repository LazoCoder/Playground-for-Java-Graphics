import java.awt.*;
import java.util.ArrayList;

/**
 * Creates, contains and handles all the bubble objects. This includes changing their hue,
 * increasing their size, and deleting them when they reach the limit.
 */
class Bubbles {

    private ArrayList<Bubble> bubbles;
    private static final int MAX_BUBBLES = 20;
    private float currentHue = 0f;

    /**
     * Constructs bubbles.
     */
    Bubbles() {
        bubbles = new ArrayList<>();
    }

    /**
     * Adds a new bubble at the specified coordinate.
     * @param x     the x coordinate of the bubble
     * @param y     the y coordinate of the bubble
     */
    void addBubble (int x, int y) {
        bubbles.add(new Bubble(x,y,4, Color.getHSBColor(currentHue+=0.01f,1.0f,1.0f)));
        if (bubbles.size() > MAX_BUBBLES) {
            bubbles.remove(0);
        }
    }

    /**
     * Increases the size of each bubble.
     */
    void increment () {
        bubbles.forEach((bubble) -> bubble.size += 8);
    }

    void paint (Graphics g) {

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.clipRect(0,0,800,800/16*9);

        paintBubbles(graphics);
    }

    private void paintBubbles (Graphics graphics) {
        int xOffset, yOffset, size;

        for (Bubble bubble : bubbles) {

            xOffset = bubble.x - (bubble.size / 2);
            yOffset = bubble.y - (bubble.size / 2);
            size    = bubble.size;

            graphics.setColor(bubble.color);
            graphics.fillOval(xOffset, yOffset, size, size);
        }
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (Bubble ripple : bubbles) {
            sb.append("(");
            sb.append(ripple.x);
            sb.append(", ");
            sb.append(ripple.y);
            sb.append(")");
            sb.append(" Size: ");
            sb.append(ripple.size);
            sb.append("\n");
        }

        return new String(sb);
    }

    /**
     * A representation of a single bubble.
     */
    private class Bubble {

        int x;
        int y;
        int size;
        Color color;

        Bubble(int x, int y, int size, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }

    }

}