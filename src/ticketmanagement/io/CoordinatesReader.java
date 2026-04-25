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
        if (input.isInteractive()) {
            System.out.println("Ввод координат:");
        }
        Double x = input.readDoubleNonNull("координата X (Double, не null)");
        long y = input.readLong("координата Y (long)", false);
        return new Coordinates(x, y);
    }
}
