package ticketmanagement;

import java.util.*;

public class App {
    private CollectionManager collectionManager;
    private InputReader inputReader;
    private boolean isRunning = true;

    public App() {
        this.collectionManager = new CollectionManager();
        this.inputReader = new InputReader(new Scanner(System.in), true);
    }

    public void start() {
        while (isRunning) {
            String input = inputReader.readString("Введите команду: ");
            processCommand(input);
        }
    }

    private void processCommand(String input) {
        switch (input) {
            case "help":
                System.out.println("help, info, show, add, exit");
                break;
            case "info":
                System.out.println(collectionManager.getInfo());
                break;
            case "show":
                System.out.println(collectionManager.show());
                break;
            case "add":
                Ticket t = inputReader.readTicket();
                collectionManager.add(t);
                break;
            case "exit":
                isRunning = false;
                break;
            default:
                System.out.println("Неизвестная команда");
        }
    }

    public static void main(String[] args) {
        new App().start();
    }
}