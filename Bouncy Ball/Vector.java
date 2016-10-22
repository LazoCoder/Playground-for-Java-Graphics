/**
 * Used for the location and movement of the ball as well as the force of gravity.
 */
class Vector {

    private double x, y;
    private double limit = Double.MAX_VALUE;

    Vector () {
        this(0, 0);
    }

    Vector (double x, double y) {
        this.x = x;
        this.y = y;
    }

    void newLocation (double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX () {
        return x;
    }

    double getY () {
        return y;
    }

    void add (Vector v) {
        x += v.x;
        y += v.y;
        limit();
    }

    private double magnitude () {
        return Math.sqrt(x*x + y*y);
    }

    void setLimit (double limit) {
        this.limit = limit;
        limit();
    }

    private void limit () {
        double mag = magnitude();
        if (mag > limit) {
            double divisor = mag / limit;
            x /= divisor;
            y /= divisor;
        }
    }

    @Override
    public Vector clone () {
        return new Vector(x, y);
    }

    @Override
    public String toString () {
        return "( " + x + ", " + y + " )";
    }

}
