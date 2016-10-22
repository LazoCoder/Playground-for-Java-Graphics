package Tornado;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Representation of a graph, containing vertexes and edges.
 * <br/>
 * A vertex can be selected if the user clicks on it. The selected
 * vertex can then be connected to other vertices.
 */
public class Graph {

    private ArrayList<Vertex> vertices;
    private Vertex selected; // The user can select a vertex.

    public Graph () {
        vertices = new ArrayList<>();
        selected = null;
    }

    /**
     * Adds a new vertex to the graph.
     * @param v     the vertex to add
     */
    private void addVertex (Vertex v) {
        vertices.add(v);
    }

    /**
     * Selects the closest vertex to where to user clicked.
     * @param x         the x coordinate of where the user clicked
     * @param y         the y coordinate of where the user clicked
     * @param distance  the radius to check within
     * @return          true if there is a vertex within the radius
     */
    private boolean select (int x, int y, double distance) {
        Vertex v = findVertex(x, y, distance);

        if (v == null) {
            return false;
        }

        selected = v;
        return true;
    }

    /**
     * Unselect the selected vertex.
     */
    private void unSelect () {
        selected = null;
    }

    private boolean isAVertexSelected () {
        return (selected != null);
    }

    /**
     * Connect the selected vertex to another vertex.
     * @param vertex    the vertex to connect the selected vertex to
     * @return          true if the two were connected successfully
     */
    private boolean connectSelectedTo (Vertex vertex) {

        if (!isAVertexSelected()) {
            return false;
        }

        Point p = vertex.clone();
        selected.connectTo(p);
        Point p2 = selected.clone();

        for (Vertex v : vertices) {
            if (v.equals(p)) {
                v.connectTo(p2);
                break;
            }
        }

        return true;
    }

    /**
     * Deletes the selected vertex.
     * @return  true if deleted successfully
     */
    public boolean removeSelected () {
        if (!isAVertexSelected()) {
            return false;
        }

        removeConnection(selected);
        vertices.remove(selected);
        selected = null;
        return true;
    }

    private void removeConnection (Vertex vertex) {

        selected.removeConnection(vertex);

        for (Vertex v : vertices) {
            Iterator<Point> it = v.iterator();
            while (it.hasNext()) {
                if (it.next().equals(selected)) {
                    it.remove();
                }
            }
        }
    }

    public void click (int x, int y, int z, double distance) {

        Vertex newV = new Vertex(x, y, z);
        Vertex v = findVertex(x, y, distance);

        if (isAVertexSelected() && selected.equals(v)) {
            unSelect();
        } else if (isAVertexSelected() && v != null && !selected.isConnectedTo(v)) {
            connectSelectedTo(v);
        } else if (isAVertexSelected() && v != null) {
            removeConnection(v);
        } else if (isAVertexSelected()) {
            unSelect();
        } else if (!select(x, y, distance)) {
            addVertex(newV);
        }
    }

    // Finds the Vertex with highest z within distance.
    private Vertex findVertex (int x, int y, double distance) {

        Iterator<Vertex> it = vertices.iterator();
        Vertex result = null;

        while (it.hasNext()) {

            Vertex v = it.next();

            int xDiff = v.getX() - x;
            int yDiff = v.getY() - y;

            double xDiffSquared = Math.pow(xDiff, 2);
            double yDiffSquared = Math.pow(yDiff, 2);

            double distanceBetweenPoints = Math.sqrt(xDiffSquared + yDiffSquared);

            if (distanceBetweenPoints <= distance) {
                if (result == null) result = v;
                else if (result.getZ() < v.getZ()) result = v;
            }

        }

        return result;
    }

    public void paint (Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);

        paintLinks(g);
        paintPoints(g);
        paintSelected(g);
    }

    private void paintPoints (Graphics2D graphics) {

        Collections.sort(vertices, new Comparator<Vertex>() {
            @Override
            public int compare (Vertex p1, Vertex p2) {
                return p1.getZ() - p2.getZ();
            }
        });

        for (Vertex v : vertices) {
            int size = 20 + v.getZ()/20;
            graphics.setColor(new Color(26, 164, 196));
            graphics.fillOval(v.getX() - size/2 - 2, v.getY() - size/2 - 2, size+4, size+4);
            graphics.setColor(Color.WHITE);
            graphics.fillOval(v.getX() - size/2, v.getY() - size/2, size, size);
        }
        graphics.setColor(Color.WHITE);
    }

    private void paintLinks (Graphics2D graphics) {
        for (Vertex v : vertices) {
            v.forEach((p) -> graphics.drawLine(v.getX(), v.getY(), p.getX(), p.getY()));
        }
    }

    /**
     * Paints the point that was selected by the user.
     * @param graphics  The Graphics object to be used for painting
     */
    private void paintSelected (Graphics2D graphics) {

        graphics.setColor(Color.CYAN);
        if (isAVertexSelected()) {
            int size = 15 + selected.getZ()/20;
            graphics.fillOval(selected.getX() - size/2, selected.getY() - size/2, size, size);
        }
        graphics.setColor(Color.WHITE);
    }

    /**
     * Rotates the graph counter-clockwise on the z-axis.
     */
    public void incrementCounterClockwise () {
        for (Vertex v : vertices) {
            v.incrementCounterClockwise();
            v.forEach((point) -> point.incrementCounterClockwise());
        }
    }

    /**
     * Rotates the graph clockwise on the z-axis.
     */
    public void incrementClockwise () {
        for (Vertex v : vertices) {
            v.incrementClockwise();
            v.forEach((point) -> point.incrementClockwise());
        }
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("-------Graph-------\n");
        vertices.forEach((v) -> sb.append(v + "\n"));
        return new String(sb);
    }

}
