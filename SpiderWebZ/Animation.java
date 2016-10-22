import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

class Animation {

    private ArrayList<Point> pointList; // Contains the floating dots.
    private static final int DISTANCE = 100;

    Animation() {
        pointList = new ArrayList<>();
        generatePoints();
    }

    private synchronized void generatePoints () {
        for (int i = 0; i < 30; i++) {
            pointList.add(new Point());
        }
    }

    synchronized void update () {
        pointList.forEach((p) -> p.increment());
    }

    void paint (Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintConnections(graphics);
        g.setColor(Color.DARK_GRAY);
        paintPoints(graphics);
    }

    private synchronized void paintPoints (Graphics2D g) {
        pointList.sort(Comparator.naturalOrder());
        for (Point p : pointList) {
            g.setStroke(new BasicStroke(2));
            g.fillOval(p.getX() - (p.getZ()/ 10 + 10) / 2, p.getY() - (p.getZ()/ 10 + 10) / 2, p.getZ()/ 10 + 10, p.getZ()/ 10 + 10);
        }
    }

    private void paintConnections (Graphics2D g) {

        double ratio;
        int color;
        int strokeWidth = 1;
        ArrayList<Connection> connectionList = getConnections();

        for (Connection c: connectionList) {

            ratio = (double)c.getDistance() / (double)DISTANCE;
            color = (int)(ratio * (255.0 - 20));

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

        //setBackground(Color.white);
        for (Point p1 : pointList) {
            for (Point p2 : pointList) {

                distance = p1.getDistance(p2);

                if (p1 != p2 && distance < DISTANCE) {
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

}
