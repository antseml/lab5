package ticketmanagement;

/**
 * Класс, представляющий координаты на плоскости.
 * Содержит координаты x (Double, не null) и y (long).
 * 
 * @author AS
 * @version 1.1
 */

public class Coordinates {
    private Double x;
    private long y;

    public Coordinates(Double x, long y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() { return x; }
    public long getY() { return y; }

    @Override
    public String toString() {
        String xStr = x.toString();
        if (xStr.endsWith(".0")) {
            xStr = xStr.substring(0, xStr.length() - 2);
        }
        return "(" + xStr + ", " + y + ")";
    }
}