/**
 * Used to represent the location, direction and speed of each particle during
 * the rendering phase.
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

    void normalize () {
        double m = mag();
        if (m > 0) {
            x /= m;
            y /= m;
        }
    }

    double mag () {
        return Math.sqrt(x*x + y*y);
    }

    public double dist (Vector v) {

        double xDiff = v.x - x;
        double yDiff = v.y - y;

        double xDiffSquared = xDiff*xDiff;
        double yDiffSquared = yDiff*yDiff;

        return Math.sqrt(xDiffSquared + yDiffSquared);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (Double.compare(vector.x, x) != 0) return false;
        return Double.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;

        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString () {
        return "(" + x + ", " + y + ")";
    }

}
