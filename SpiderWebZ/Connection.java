/**
 * Represents the line connecting the dots.
 */
class Connection implements Comparable<Connection> {

    private int x1, y1, x2, y2;
    private int distance;

    Connection (int x1, int y1, int x2, int y2, int distance) {

        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;

        this.distance = distance;
    }

    int getX1 () {
        return x1;
    }

    int getY1 () {
        return y1;
    }

    int getX2 () {
        return x2;
    }

    int getY2 () {
        return y2;
    }

    int getDistance () {
        return distance;
    }

    @Override
    public int compareTo(Connection t) {
        if (distance < t.distance) return 1;
        else if (distance > t.distance) return -1;
        return 0;
    }
}