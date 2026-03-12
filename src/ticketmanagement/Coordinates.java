package ticketmanagement;

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
        return "(" + x + ", " + y + ")";
    }
}