package ticketmanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class App {
    private CollectionManager collectionManager;
    private InputReader inputReader;
    private boolean isRunning = true;

    public App(String fileName) {
        this.collectionManager = new CollectionManager(fileName);
        this.inputReader = new InputReader  (new Scanner(System.in), true);
        
        initializeApplication(fileName);
    }

    private void initializeApplication(String fileName) {
        
        try {
            collectionManager.loadFromFile();
            System.out.println("Коллекция успешно загружена из файла: " + fileName);
            System.out.println("Загружено элементов: " + collectionManager.getCollection().size());
        } catch (Exception e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
            System.out.println("Будет создана новая пустая коллекция");
        }
        
        System.out.println("\nВведите 'help' для просмотра списка доступных команд");
    }

    public void start() {
        while (isRunning) {
            try {
                String userInput = inputReader.readString("");
                
                if (userInput.isEmpty()) {
                    continue;
                }
                
                processCommand(userInput);
                
            } catch (Exception e) {
                System.out.println("Непредвиденная ошибка: " + e.getMessage());
                System.out.println("Приложение продолжает работу...");
            }
        }
        
        shutdown();
    }

    private void processCommand(String input) {
        String[] commandParts = input.trim().split("\\s+");
        String command = commandParts[0].toLowerCase();
        String[] args = Arrays.copyOfRange(commandParts, 1, commandParts.length);

        try {
            switch (command) {
                case "help":
                    executeHelp();
                    break;
                case "info":
                    executeInfo();
                    break;
                case "show":
                    executeShow();
                    break;
                case "add":
                    executeAdd();
                    break;
                case "update":
                    executeUpdate(args);
                    break;
                case "remove_by_id":
                    executeRemoveById(args);
                    break;
                case "clear":
                    executeClear();
                    break;
                case "save":
                    executeSave();
                    break;
                case "execute_script":
                    executeScript(args);
                    break;
                case "exit":
                    executeExit();
                    break;
                case "remove_at":
                    executeRemoveAt(args);
                    break;
                case "add_if_max":
                    executeAddIfMax();
                    break;
                case "reorder":
                    executeReorder();
                    break;
                case "max_by_coordinates":
                    executeMaxByCoordinates();
                    break;
                case "print_unique_price":
                    executePrintUniquePrice();
                    break;
                case "print_field_descending_price":
                    executePrintFieldDescendingPrice();
                    break;
                default:
                    System.out.println("Неизвестная команда: '" + command + "'");
                    System.out.println("Введите 'help' для просмотра доступных команд");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в аргументах команды: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Недостаточно аргументов для команды");
        } catch (Exception e) {
            System.out.println("Ошибка выполнения команды: " + e.getMessage());
        }
    }

    private void executeHelp() {
        System.out.println("\nДОСТУПНЫЕ КОМАНДЫ:");
        System.out.printf("%-25s - %s\n", "help", "показать справку");
        System.out.printf("%-25s - %s\n", "info", "информация о коллекции");
        System.out.printf("%-25s - %s\n", "show", "показать все элементы");
        System.out.printf("%-25s - %s\n", "add", "добавить новый элемент");
        System.out.printf("%-25s - %s\n", "update {id}", "обновить элемент по id");
        System.out.printf("%-25s - %s\n", "remove_by_id {id}", "удалить элемент по id");
        System.out.printf("%-25s - %s\n", "clear", "очистить коллекцию");
        System.out.printf("%-25s - %s\n", "save", "сохранить коллекцию в файл");
        System.out.printf("%-25s - %s\n", "execute_script {file}", "выполнить скрипт из файла");
        System.out.printf("%-25s - %s\n", "exit", "выход без сохранения");
        System.out.printf("%-25s - %s\n", "remove_at {index}", "удалить элемент по индексу");
        System.out.printf("%-25s - %s\n", "add_if_max", "добавить если больше максимального");
        System.out.printf("%-25s - %s\n", "reorder", "обратная сортировка коллекции");
        System.out.printf("%-25s - %s\n", "max_by_coordinates", "элемент с макс. координатами");
        System.out.printf("%-25s - %s\n", "print_unique_price", "уникальные значения цены");
        System.out.printf("%-25s - %s\n", "print_field_descending_price", "цены в порядке убывания");
    }

    private void executeInfo() {
        System.out.println("\nИНФОРМАЦИЯ О КОЛЛЕКЦИИ:");
        System.out.println(collectionManager.getInfo());
    }

    private void executeShow() {
        System.out.println("\nЭЛЕМЕНТЫ КОЛЛЕКЦИИ:");
        String result = collectionManager.show();
        System.out.println(result);
    }

    private void executeAdd() {
        System.out.println("\nДОБАВЛЕНИЕ НОВОГО БИЛЕТА:");
        Ticket newTicket = inputReader.readTicket();
        collectionManager.add(newTicket);
        System.out.println("Билет успешно добавлен! ID: " + newTicket.getId());
    }

    private void executeUpdate(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указан id билета");
        }
        
        Long id = Long.parseLong(args[0]);
        Ticket existingTicket = collectionManager.getCollection().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (existingTicket == null) {
            System.out.println("Билет с id " + id + " не найден");
            return;
        }

        System.out.println("\n ОБНОВЛЕНИЕ БИЛЕТА ID: " + id);
        System.out.println("Текущие данные:");
        System.out.println(existingTicket);
        System.out.println("\nВведите новые данные:");
        
        Ticket updatedTicket = inputReader.readTicket();
        
        if (collectionManager.updateById(id, updatedTicket)) {
            System.out.println("Билет успешно обновлен!");
        } else {
            System.out.println("Не удалось обновить билет");
        }
    }

    private void executeRemoveById(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указан id билета");
        }
        
        Long id = Long.parseLong(args[0]);
        
        if (collectionManager.removeById(id)) {
            System.out.println("Билет с id " + id + " успешно удален");
        } else {
            System.out.println("Билет с id " + id + " не найден");
        }
    }

    private void executeClear() {
        System.out.print("\n Вы уверены, что хотите очистить коллекцию? (y/n): ");
        String confirmation = inputReader.readString("").toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            collectionManager.clear();
            System.out.println("Коллекция очищена");
        } else {
            System.out.println("Операция отменена");
        }
    }

    private void executeSave() {
        try {
            collectionManager.saveToFile();
            System.out.println("Коллекция успешно сохранена в файл");
        } catch (Exception e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    private void executeScript(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указано имя файла скрипта");
        }
        
        String scriptFileName = args[0];
        File scriptFile = new File(scriptFileName);
        
        if (!scriptFile.exists()) {
            System.out.println("Файл скрипта не найден: " + scriptFileName);
            return;
        }
        
        System.out.println("\n ВЫПОЛНЕНИЕ СКРИПТА: " + scriptFileName);
        
        try (Scanner fileScanner = new Scanner(scriptFile)) {
            InputReader scriptReader = new InputReader(fileScanner, false);
            InputReader originalReader = inputReader;
            inputReader = scriptReader;
            
            int lineNumber = 0;
            int commandCount = 0;
            
            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine().trim();
                
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                commandCount++;
                System.out.printf("[%d] Выполнение: %s\n", lineNumber, line);
                processCommand(line);
            }
            
            System.out.printf("\nСкрипт выполнен. Обработано команд: %d\n", commandCount);
            inputReader = originalReader;
            
        } catch (FileNotFoundException e) {
            System.out.println("Файл скрипта не найден: " + scriptFileName);
        }
    }

    private void executeExit() {
        System.out.print("\n Выйти без сохранения? (y/n): ");
        String confirmation = inputReader.readString("").toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            isRunning = false;
        } else {
            System.out.println("Выход отменен");
        }
    }

    private void executeRemoveAt(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указан индекс");
        }
        
        int index = Integer.parseInt(args[0]);
        
        if (collectionManager.removeAt(index)) {
            System.out.println("Элемент с индексом " + index + " удален");
        } else {
            System.out.println("Неверный индекс: " + index);
            System.out.println("Текущий размер коллекции: " + collectionManager.getCollection().size());
        }
    }

    private void executeAddIfMax() {
        System.out.println("\n ДОБАВЛЕНИЕ БИЛЕТА (ADD IF MAX):");
        
        Ticket newTicket = inputReader.readTicket();
        
        if (collectionManager.addIfMax(newTicket)) {
            System.out.println("Билет добавлен! ID: " + newTicket.getId());
            System.out.println("Билет является максимальным в коллекции");
        } else {
            System.out.println("Билет не добавлен (не является максимальным)");
        }
    }

    private void executeReorder() {
        collectionManager.reorder();
        System.out.println("Порядок элементов изменен на обратный");
    }

    private void executeMaxByCoordinates() {
        Ticket maxTicket = collectionManager.maxByCoordinates();
        
        if (maxTicket == null) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println("\nБИЛЕТ С МАКСИМАЛЬНЫМИ КООРДИНАТАМИ:");
            System.out.println(maxTicket);
        }
    }

    private void executePrintUniquePrice() {
        Set<Integer> uniquePrices = collectionManager.getUniquePrices();
        
        if (uniquePrices.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println("\nУНИКАЛЬНЫЕ ЗНАЧЕНИЯ ЦЕН:");
            uniquePrices.stream()
                .sorted()
                .forEach(price -> System.out.println("  • " + price));
            System.out.println("Всего уникальных цен: " + uniquePrices.size());
        }
    }

    private void executePrintFieldDescendingPrice() {
        List<Integer> descendingPrices = collectionManager.getPricesDescending();
        
        if (descendingPrices.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println("ЦЕНЫ В ПОРЯДКЕ УБЫВАНИЯ:");
            for (int i = 0; i < descendingPrices.size(); i++) {
                System.out.printf("%3d. %d\n", i + 1, descendingPrices.get(i));
            }
        }
    }

    private void shutdown() {
        System.out.println("До свидания!");
    }

    public static void main(String[] args) {
        try {
            System.setProperty("file.encoding", "UTF-8");
            
            PrintStream utf8Out = new PrintStream(System.out, true, "UTF-8");
            PrintStream utf8Err = new PrintStream(System.err, true, "UTF-8");
            System.setOut(utf8Out);
            System.setErr(utf8Err);
            
        } catch (Exception e) {
            
        }
        
        if (args.length == 0) {
            System.out.println("Ошибка: не указан путь к файлу с данными");
            System.out.println("Пример использования: java ticketmanagement.App data/collection.json");
            System.out.println("Создайте директорию 'data' и файл 'collection.json' в ней");
            return;
        }

        String dataFile = args[0];
        
        try {
            App application = new App(dataFile);
            application.start();
        } catch (Exception e) {
            System.out.println("Критическая ошибка при запуске приложения:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}