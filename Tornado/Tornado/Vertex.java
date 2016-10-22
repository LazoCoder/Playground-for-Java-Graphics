package Tornado;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a node in a graph. It can point to other nodes, but when it does,
 * these edges are represented by the Point object.
 */
class Vertex extends Point implements Iterable<Point> {

    private ArrayList<Point> points;

    Vertex(int x, int y, int z) {
        super(x, y, z);
        points = new ArrayList<>();
    }

    /**
     * Check if two vertices are connected.
     * @param point     the point to check against this vertex
     * @return          true if they are connected
     */
    boolean isConnectedTo (Point point) {
        return points.contains(point);
    }

    /**
     * Connect this vertex to another vertex (represented as a point)
     * @param point     the point to connect this vertex to
     */
    void connectTo (Point point) {
        if (isConnectedTo(point)) {
            return;
        }
        points.add(point);
    }

    /**
     * Removes a connection between a vertex and a point.
     * @param point     the point to remove from the vertex
     */
    void removeConnection (Point point) {
        points.remove(point);
    }

    public Iterator<Point> iterator () {
        return points.iterator();
    }

    @Override
    public String toString () {

        StringBuilder sb = new StringBuilder("Vertex at " + super.toString() + " connects to ");
        sb.append(points.size());
        sb.append(" point(s): ");

        for (Point p : points) {
            sb.append(" ");
            sb.append(p);
        }

        return new String(sb);
    }


}