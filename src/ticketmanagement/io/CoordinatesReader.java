package ticketmanagement.io;

import ticketmanagement.models.Coordinates;

/**
 * Пошаговый ввод координат с именами полей.
 */
public class CoordinatesReader {
    private final ConsoleInput input;

    public CoordinatesReader(ConsoleInput input) {
        this.input = input;
    }

    public Coordinates read() {
        Float x = input.readPositiveFloat("координата X (Float, > 0)");
        Double y = input.readDoubleNonNull("координата Y (Double, не null)");
        return new Coordinates(x, y);
    }
}
