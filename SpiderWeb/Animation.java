import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Handles the animation loop.
 */
class Animation {

    private ArrayList<Point> pointList; // Contains the floating dots.

    Animation() {
        generatePoints();
    }

    /**
     * Generates random points.
     */
    private synchronized void generatePoints () {
        pointList = new ArrayList<>();
        for (int i = 0; i < Properties.POINTS; i++) {
            Point p = new Point();
            pointList.add(p);
        }
    }

    void paint (Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;

        if (Properties.highQuality) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        if (Properties.drawConnections) {
            paintConnections(g);
        }
        if (Properties.drawPoints) {
            paintPoints(g);
        }
    }

    private synchronized void paintPoints (Graphics2D g) {

        int xOffset;
        int yOffset;
        int size = Properties.POINT_SIZE;
        int index = 0;

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1));

        for (Point p : pointList) {

            // Corrects the coordinates so that they represent the center of the point.
            xOffset = p.getX() - size / 2;
            yOffset = p.getY() - size / 2;
            g.fillOval(xOffset, yOffset, size, size);

            if (Properties.drawPointCoordinates) {
                g.drawString("(" + p.getX() + ", " + p.getY() + ")", xOffset + size, yOffset + size);
            } else if (Properties.drawPointIndex) {
                g.drawString("" + index++, xOffset + size, yOffset + size);
            }

        }
    }

    private synchronized void paintConnections (Graphics2D g) {

        double ratio;
        int color;
        int strokeWidth = 1;
        ArrayList<Connection> connectionList = getConnections();

        for (Connection c: connectionList) {

            ratio = (double)c.getDistance() / (double)Properties.CONNECTION_DISTANCE;
            color = 255 - (int)(ratio * (255.0 - 53));

            if (ratio < 0.05)     strokeWidth = 5;
            else if (ratio < 0.1) strokeWidth = 4;
            else if (ratio < 0.2) strokeWidth = 3;
            else if (ratio < 0.4) strokeWidth = 2;
            else if (ratio < 1.0) strokeWidth = 1;

            g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g.setColor(new Color(color, color, color));
            g.drawLine(c.getX1(), c.getY1(), c.getX2(), c.getY2());
        }
    }

    private synchronized ArrayList<Connection> getConnections () {

        int distance;
        ArrayList<Connection> connectionList = new ArrayList<>();
        Connection connection;

        for (Point p1 : pointList) {
            for (Point p2 : pointList) {

                distance = p1.getDistance(p2);

                if (p1 != p2 && distance < Properties.CONNECTION_DISTANCE) {
                    connection = new Connection(p1.getX(), p1.getY(), p2.getX(), p2.getY(), distance);
                    connectionList.add(connection);
                }
            }
        }

        // Connections need to be ordered by their size.
        // This way, brighter connections will be drawn in front of darker connections.
        // This looks more natural.
        connectionList.sort(Comparator.naturalOrder());

        return connectionList;
    }

    synchronized void increment () {
        pointList.forEach((p) -> p.increment());
    }

}