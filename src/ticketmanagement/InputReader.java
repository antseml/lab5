package ticketmanagement;

import java.util.Scanner;

public class InputReader {
    private Scanner scanner;
    private boolean isInteractive;

    public InputReader(Scanner scanner, boolean isInteractive) {
        this.scanner = scanner;
        this.isInteractive = isInteractive;
    }

    public String readString(String prompt) {
        if (isInteractive) System.out.print(prompt);
        return scanner.nextLine();
    }

    public Ticket readTicket() {
        System.out.print("Название: ");
        String name = scanner.nextLine();

        System.out.print("Цена: ");
        int price = Integer.parseInt(scanner.nextLine());

        return new Ticket(name, price);
    }
}