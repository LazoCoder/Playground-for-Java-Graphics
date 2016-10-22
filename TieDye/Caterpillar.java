import java.awt.*;
import java.util.Random;
import java.util.Stack;

/**
 * Represents a random walk.
 * <br/>
 * The caterpillar is to be spawned on an image. When the method crawl() is executed, it randomly
 * selects a direction and moves one pixel in that direction. If the caterpillar runs into another
 * caterpillar or a wall, it backtracks to a previous location and continues crawling until there
 * is absolutely no space left.
 */
class Caterpillar {

    private Image image;
    private int y;
    private int x;
    private Stack<Point> stack;
    private Color empty = Color.BLACK;

    // Could have used an enum here.
    private static final int MOVE_LEFT = 0;
    private static final int MOVE_UP = 1;
    private static final int MOVE_RIGHT = 2;
    private static final int MOVE_DOWN = 3;

    private float hue = 170;

    Caterpillar (Image image, int startX, int startY, float startHue) {

        stack = new Stack<>();

        this.hue    = startHue;
        this.image  = image;
        this.x      = startX;
        this.y      = startY;

        if (!isInBounds(x, y)) {
            throw new IndexOutOfBoundsException();
        }

        pushToStack();
        updateImage();
    }

    /**
     * Checks to see if there is no where else for the caterpillar to go and it has backtracked
     * all the way to the beginning.
     * @return  true if the caterpillar has no where to crawl to
     */
    boolean canCrawl() {
        return !(isDeadEnd() && stack.isEmpty());
    }

    /**
     * Makes the caterpillar crawl one pixel in a random direction.
     * If the caterpillar encounters another caterpillar or a boundary, it backtracks
     * and continues crawling.
     */
    void crawl () {
        popFromStack();

        if (!canCrawl()) {
            return;
        }

        moveCaterpillar();
        updateImage();
    }

    /**
     * Helper method to move the caterpillar one pixel in a random direction.
     */
    private void moveCaterpillar () {
        int randomDirection;

        randomDirection = getRandomDirection();

        if (randomDirection == MOVE_LEFT)       moveLeft();
        else if (randomDirection == MOVE_RIGHT) moveRight();
        else if (randomDirection == MOVE_UP)    moveUp();
        else if (randomDirection == MOVE_DOWN)  moveDown();
    }

    private void popFromStack () {
        Point point;

        while (isDeadEnd() && !stack.isEmpty()) {
            point = stack.pop();
            x = point.x;
            y = point.y;
            hue = point.hue;
        }

    }

    private void updateImage () {
        image.setColor(x, y, Color.getHSBColor(hue += Properties.HUE_INCREMENT, 1, 1));
    }

    int getX () {
        return x;
    }

    int getY () {
        return y;
    }

    float getHue () {
        return hue;
    }

    private int getRandomDirection () {

        Random r = new Random();
        int randomDirection;

        while (!isDeadEnd()) {

            randomDirection = r.nextInt(5);

            if (randomDirection == MOVE_LEFT && canMoveLeft())          return randomDirection;
            else if (randomDirection == MOVE_RIGHT && canMoveRight())   return randomDirection;
            else if (randomDirection == MOVE_UP && canMoveUp())         return randomDirection;
            else if (randomDirection == MOVE_DOWN && canMoveDown())     return randomDirection;

        }

        throw new AssertionError("Unable to find a random direction even though one is available.");
    }

    private boolean canMoveLeft () {
        return isValidSpot(x-1, y);
    }

    private boolean canMoveRight () {
        return isValidSpot(x+1, y);
    }

    private boolean canMoveUp () {
        return isValidSpot(x, y-1);
    }

    private boolean canMoveDown () {
        return isValidSpot(x, y+1);
    }

    boolean isDeadEnd () {
        return (!canMoveLeft() && !canMoveRight()
                && !canMoveUp() && !canMoveDown());
    }

    private void moveLeft () {
        x--;
        pushToStack();
    }

    private void moveRight () {
        x++;
        pushToStack();
    }

    private void moveUp () {
        y--;
        pushToStack();
    }

    private void moveDown () {
        y++;
        pushToStack();
    }

    private void pushToStack () {
        stack.push(new Point(x,y,hue));
    }

    /**
     * A spot is valid if it is in bounds and empty.
     * @param x     the x coordinate of the location
     * @param y     the y coordinate of the location
     * @return      true if it is valid
     */
    private boolean isValidSpot (int x, int y) {
        return isInBounds(x, y) && isEmptySpot(x, y);
    }

    private boolean isInBounds (int x, int y) {
        boolean xOutOfBounds = x < 0 || x >= image.getWidth();
        boolean yOutOfBounds = y < 0 || y >= image.getHeight();
        return (!xOutOfBounds && !yOutOfBounds);
    }

    private boolean isEmptySpot (int x, int y) {
        return image.getColor(x,y) == empty;
    }

    /**
     * Helper class that represents a point on the image.
     */
    private class Point {
        int x;
        int y;
        float hue;
        Point (int x, int y, float hue) {
            this.x = x;
            this.y = y;
            this.hue = hue;
        }
    }

}
