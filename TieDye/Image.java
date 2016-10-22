import java.awt.*;

/**
 * Represents an image where the caterpillars will crawl on and be painted to.
 */
class Image {

    private Color[][] array;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private int width;
    private int height;

    Image (int width, int height) {
        this.width = width;
        this.height = height;

        array = new Color[width][height];

        fillArray(DEFAULT_COLOR);
    }

    /**
     * Fill the entire image with a color. Essentially this is the background color.
     * @param color     the color to fill the image with
     */
    private void fillArray (Color color) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array[x][y] = color;
            }
        }
    }

    int getWidth () {
        return width;
    }

    int getHeight () {
        return height;
    }

    /**
     * Get the color at a particular location on the image.
     * @param x     the x coordinate of the location
     * @param y     the y coordinate of the location
     * @return      the color found at the location
     */
    Color getColor (int x, int y) {

        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException();
        }

        return array[x][y];
    }

    /**
     * Set the color at a particular location on the image
     * @param x     the x coordinate of the location
     * @param y     the y coordinate of the location
     * @param color the color to set to
     */
    void setColor (int x, int y, Color color) {

        if (x < 0 || x > width || y < 0 || y > height) {
            throw new IndexOutOfBoundsException();
        }

        array[x][y] = color;
    }

    /**
     * Paint the image using a Graphics object.
     * @param graphics  the Graphics object used for painting
     */
    void paint (Graphics graphics) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (getColor(x, y) != Color.BLACK) {
                    graphics.setColor(getColor(x,y));

                    if (!Properties.FILL) {
                        graphics.drawRect(x * Properties.SCALE,
                                y * Properties.SCALE,
                                Properties.SCALE-2,
                                Properties.SCALE-2);
                    } else {
                        graphics.fillRect(x * Properties.SCALE,
                                y * Properties.SCALE,
                                Properties.SCALE,
                                Properties.SCALE);
                    }
                }
            }
        }
    }

}
