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
        String[] parts = input.split(" ");
        String command = parts[0];

        switch (command) {
            case "help":
                System.out.println("help, info, show, add, update, remove_by_id, clear, remove_at, exit");
                break;
            case "info":
                System.out.println(collectionManager.getInfo());
                break;
            case "show":
                System.out.println(collectionManager.show());
                break;
            case "add":
                collectionManager.add(inputReader.readTicket());
                break;
            case "update":
                Long id = Long.parseLong(parts[1]);
                collectionManager.updateById(id, inputReader.readTicket());
                break;
            case "remove_by_id":
                collectionManager.removeById(Long.parseLong(parts[1]));
                break;
            case "clear":
                collectionManager.clear();
                break;
            case "remove_at":
                collectionManager.removeAt(Integer.parseInt(parts[1]));
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