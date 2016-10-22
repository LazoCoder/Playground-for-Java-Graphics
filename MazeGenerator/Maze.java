import java.awt.*;
import java.util.Random;

/**
 * Generates a random maze.
 */
class Maze {

    /**
     * Cannot be instantiated, as all the methods are static so there is no point.
     */
    private Maze () {}

    static DisjointSet generate (Graphics g, int rows, int columns, int size) {
        DisjointSet disjointSet = new DisjointSet(rows * columns);
        createBorders(g, rows, columns, size, disjointSet); // Enclosing walls need to be closed.
        generateMaze(g, rows, columns, size, disjointSet);
        return disjointSet;
    }

    /**
     * Creates a borders around the maze so that one cannot escape.
     * @param g     the Graphics object used for drawing
     * @param rows  the number of rows of walls
     * @param cols  the number of columns of walls
     * @param size  the size of the maze
     * @param ds    the disjoint set used for the maze
     */
    private static void createBorders (Graphics g, int rows, int cols, int size, DisjointSet ds) {
        createBorderAtTop(g, rows, cols, size, ds);
        createBorderAtBottom(g, rows, cols, size, ds);
        createBorderAtLeft(g, rows, cols, size, ds);
        createBorderAtRight(g, rows, cols, size, ds);
    }

    /**
     * Helper method to create a border on the top of the maze.
     * @param g     the Graphics object used for drawing
     * @param rows  the number of rows of walls
     * @param cols  the number of columns of walls
     * @param size  the size of the maze
     * @param ds    the disjoint set used for the maze
     */
    private static void createBorderAtTop (Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = 1; i < rows; i++) {
            ds.union(i, i - 1);
            drawLine(g, i, i-1, cols, size);
        }
    }

    /**
     * Helper method to create a border on the bottom of the maze.
     * @param g     the Graphics object used for drawing
     * @param rows  the number of rows of walls
     * @param cols  the number of columns of walls
     * @param size  the size of the maze
     * @param ds    the disjoint set used for the maze
     */
    private static void createBorderAtBottom (Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = rows * cols - 1; i > (rows * cols) - rows; i--) {
            ds.union(i, i - 1);
            drawLine(g, i-1, i, cols, size);
        }
    }

    /**
     * Helper method to create a border on the left side of the maze.
     * @param g     the Graphics object used for drawing
     * @param rows  the number of rows of walls
     * @param cols  the number of columns of walls
     * @param size  the size of the maze
     * @param ds    the disjoint set used for the maze
     */
    private static void createBorderAtLeft (Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = 1; i < rows; i++) {
            ds.union((i-1) * cols, i * cols);
            drawLine(g,(i-1) * cols, i * cols, cols, size);
        }
    }

    /**
     * Helper method to create a border on the right side of the maze.
     * @param g     the Graphics object used for drawing
     * @param rows  the number of rows of walls
     * @param cols  the number of columns of walls
     * @param size  the size of the maze
     * @param ds    the disjoint set used for the maze
     */
    private static void createBorderAtRight (Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = 1; i < rows; i++) {
            ds.union((i-1) * cols + rows - 1, i * cols + rows - 1);
            drawLine(g,(i-1) * cols + rows - 1, i * cols + rows - 1, cols, size);
        }
    }

    /**
     * Draws a maze wall.
     * @param g     the Graphics object used for drawing
     * @param idx1  the index of the starting point of the line
     * @param idx2  the index of the ending point of the line
     * @param cols  the number of columns of walls in the maze
     * @param size  the size of the maze
     */
    private static void drawLine (Graphics g, int idx1, int idx2, int cols, int size) {
        int x1, x2, y1, y2;
        x1 = (idx1 % cols) * size + size;
        y1 = (idx1 / cols) * size + size;
        x2 = (idx2 % cols) * size + size;
        y2 = (idx2 / cols) * size + size;
        g.drawLine(x1, y1, x2, y2);
    }


    private static void generateMaze (Graphics g, int rows, int cols, int size, DisjointSet ds) {
        Random random = new Random();
        int randomElement;
        int randomNeighbour;

        while(ds.size() != 1) {

            randomElement = random.nextInt(rows * cols);
            randomNeighbour = randomNeighbour(randomElement, rows, cols);

            if (!ds.isConnected(randomElement, randomNeighbour)) {
                ds.union(randomElement, randomNeighbour);
                drawLine(g, randomElement, randomNeighbour, cols, size);
            }
        }
    }

    /**
     * Randomly generates where the walls will be.
     * @param idx   the index of which wall to create a random neighbor to
     * @param rows  the number of rows of walls
     * @param cols  the number of columns of walls
     * @return      returns the index of the neighbor
     */
    private static int randomNeighbour (int idx, int rows, int cols) {

        Random random = new Random();
        int r;
        int neighbour = -1;

        boolean inBounds = false;

        while (!inBounds) {

            r = random.nextInt(4);

            if (r == 0) {
                neighbour = idx - rows; // Up.
            } else if (r == 1) {
                neighbour = idx + 1;    // Right.
            } else if (r == 2) {
                neighbour = idx + rows; // Down.
            } else if (r == 3) {
                neighbour = idx - 1;    // Left.
            }

            inBounds = inBounds(idx, neighbour, rows, cols);
        }

        return neighbour;
    }

    /**
     * Check to see if the neighbor of a wall is in bounds.
     * @param idx       the index of the wall
     * @param neighbour the neighbor of the index
     * @param rows      the number of rows of walls in the maze
     * @param cols      the number of columns of walls in the maze
     * @return          true if it is in bounds
     */
    private static boolean inBounds (int idx, int neighbour, int rows, int cols) {

        boolean firstRow            = (idx < rows);
        boolean lastRow             = (idx >= (rows * (cols - 1)));
        boolean firstColumn         = (idx % cols == 0);
        boolean lastColumn          = (idx % cols == cols - 1);

        boolean neighbourIsAbove    = (neighbour == idx - rows);
        boolean neighbourIsBelow    = (neighbour == idx + rows);
        boolean neighbourIsLeft     = (neighbour == idx - 1);
        boolean neighbourIsRight    = (neighbour == idx + 1);

        if (firstRow && neighbourIsAbove) {
            return false;
        } else if (lastRow && neighbourIsBelow) {
            return false;
        } else if (firstColumn && neighbourIsLeft) {
            return false;
        } else if (lastColumn && neighbourIsRight) {
            return false;
        } else {
            return true;
        }

    }

}
