/**
 * Used to represent the location, direction and speed of each particle.
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

    double getX () {
        return x;
    }

    double getY () {
        return y;
    }

    void setXY (double x, double y) {
        setX(x);
        setY(y);
    }

    private void setX (double x) {
        this.x = x;
    }

    private void setY (double y) {
        this.y = y;
    }

    void add (Vector v) {
        x += v.x;
        y += v.y;
        limit();
    }

    void sub (Vector v) {
        x -= v.x;
        y -= v.y;
        limit();
    }

    void mul (double s) {
        x *= s;
        y *= s;
        limit();
    }

    void div (double s) {
        x /= s;
        x /= s;
        limit();
    }

    void normalize () {
        double m = mag();
        if (m > 0) {
            x /= m;
            y /= m;
        }
    }

    private double mag () {
        return Math.sqrt(x*x + y*y);
    }

    void limit (double l) {
        limit = l;
        limit();
    }

    private void limit () {
        double m = mag();
        if (m > limit) {
            double ratio = m / limit;
            x /= ratio;
            y /= ratio;
        }
    }

    public Vector clone () {
        return new Vector(x, y);
    }

    public String toString () {
        return "(" + x + ", " + y + ")";
    }

}
