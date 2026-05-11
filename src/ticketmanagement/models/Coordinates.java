package ticketmanagement.models;

import java.util.Objects;

/**
 * Координаты: x не null (Float), y не null (Double).
 */
public class Coordinates {
    private final Float x;
    private final Double y;

    public Coordinates(Float x, Double y) {
        this.x = Objects.requireNonNull(x, "x");
        this.y = Objects.requireNonNull(y, "y");
    }

    public Float getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        String xStr = x.toString();
        if (xStr.endsWith(".0")) {
            xStr = xStr.substring(0, xStr.length() - 2);
        }
        String yStr = y.toString();
        if (yStr.endsWith(".0")) {
            yStr = yStr.substring(0, yStr.length() - 2);
        }
        return "(" + xStr + ", " + yStr + ")";
    }
}
