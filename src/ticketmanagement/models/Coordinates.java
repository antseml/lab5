package ticketmanagement.models;

/**
 * Координаты: x не null, y — long.
 */
public class Coordinates {
    private final Double x;
    private final long y;

    public Coordinates(Double x, long y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        String xStr = x.toString();
        if (xStr.endsWith(".0")) {
            xStr = xStr.substring(0, xStr.length() - 2);
        }
        return "(" + xStr + ", " + y + ")";
    }
}
