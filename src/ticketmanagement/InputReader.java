package ticketmanagement;

import java.util.*;

public class InputReader {
    private Scanner scanner;
    private boolean isInteractive;

    public InputReader(Scanner scanner, boolean isInteractive) {
        this.scanner = scanner;
        this.isInteractive = isInteractive;
    }

    public String readString(String prompt) {
        if (isInteractive) System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                if (isInteractive) System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                if (isInteractive) System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }
    }

    public Coordinates readCoordinates() {
        double x = readDouble("X: ");
        long y = readInt("Y: ");
        return new Coordinates(x, y);
    }

    public Ticket readTicket() {
        String name = readString("Название: ");
        Coordinates coordinates = readCoordinates();
        int price = readInt("Цена: ");
        String comment = readString("Комментарий: ");
        TicketType type = TicketType.valueOf(readString("Тип: ").toUpperCase());

        return new Ticket(name, coordinates, price, comment, type);
    }
}