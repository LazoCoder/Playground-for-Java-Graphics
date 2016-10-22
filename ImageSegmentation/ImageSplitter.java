import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 * Splits an image up by clusters of color and stores these
 * segments in a disjoint set object.
 * <br/>
 * It does this by spawning a random walk on each pixel. The
 * random walk then walks everywhere where the pixel is the same
 * color as where it started. As it walks it adds the pixels to
 * a set in the disjoint set data structure.
 */
class ImageSplitter {

    private static DisjointSet disjointSet;
    private static BufferedImage image;

    /**
     * Class cannot be instantiated. All the methods are static.
     */
    private ImageSplitter () {}

    static DisjointSet split (BufferedImage image) {

        ImageSplitter.image = image;
        disjointSet = new DisjointSet(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                randomWalk(new Point(x, y));
            }
        }

        return disjointSet;
    }

    private static void randomWalk (Point p) {
        Stack<Point> stack = new Stack<>();
        stack.push(p);

        Point p1;
        Point p2;

        while (!stack.isEmpty()) {

            p1 = stack.peek();

            if (canMoveRight(p1)) {
                p2 = moveRight(p1);
            } else if (canMoveDown(p1)) {
                p2 = moveDown(p1);
            } else if (canMoveLeft(p1)) {
                p2 = moveLeft(p1);
            } else if (canMoveUp(p1)) {
                p2 = moveUp(p1);
            } else {
                stack.pop();
                continue;
            }

            disjointSet.union(p1, p2);
            stack.push(p2);
        }
    }

    private static Point moveLeft (Point p) {
        return new Point(p.getX() - 1, p.getY());
    }

    private static Point moveRight (Point p) {
        return new Point(p.getX() + 1, p.getY());
    }

    private static Point moveUp (Point p) {
        return new Point(p.getX(), p.getY() - 1);
    }

    private static Point moveDown (Point p) {
        return new Point(p.getX(), p.getY() + 1);
    }

    private static boolean canMoveLeft (Point p) {
        return isValidMove(p, moveLeft(p));
    }

    private static boolean canMoveRight (Point p) {
        return isValidMove(p, moveRight(p));
    }

    private static boolean canMoveUp (Point p) {
        return isValidMove(p, moveUp(p));
    }

    private static boolean canMoveDown (Point p) {
        return isValidMove(p, moveDown(p));
    }

    private static boolean isValidMove (Point p1, Point p2) {
        return isInBounds(p2) && isSameColor(p1, p2) && !disjointSet.connected(p1, p2);
    }

    private static boolean isSameColor (Point p1, Point p2) {
        int color1 = image.getRGB(p1.getX(), p1.getY());
        int color2 = image.getRGB(p2.getX(), p2.getY());
        return color1 == color2;
    }

    private static boolean isInBounds (Point p) {

        boolean xInBoundaries = (p.getX() >= 0) && (p.getX() < image.getWidth());
        boolean yInBoundaries = (p.getY() >= 0) && (p.getY() < image.getHeight());

        return xInBoundaries && yInBoundaries;

    }

}
