package ticketmanagement;

import ticketmanagement.commands.*;
import ticketmanagement.io.ConsoleInput;
import ticketmanagement.io.CoordinatesReader;
import ticketmanagement.io.PersonReader;
import ticketmanagement.io.TicketReader;
import ticketmanagement.services.CollectionManager;
import ticketmanagement.services.FileManager;
import ticketmanagement.services.IdGenerator;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Точка входа: загрузка коллекции, регистрация команд в {@link CommandInvoker}, цикл ввода.
 */
public class App {
    private final ApplicationContext context;
    private final CommandInvoker invoker;

    public App(String dataFile) {
        IdGenerator idGenerator = new IdGenerator();
        FileManager fileManager = new FileManager();
        CollectionManager collectionManager = new CollectionManager(dataFile, fileManager, idGenerator);

        Scanner systemIn = new Scanner(System.in);
        ConsoleInput consoleInput = new ConsoleInput(systemIn, true);
        CoordinatesReader coordinatesReader = new CoordinatesReader(consoleInput);
        PersonReader personReader = new PersonReader(consoleInput);
        TicketReader ticketReader = new TicketReader(consoleInput, coordinatesReader, personReader, idGenerator);

        AtomicBoolean running = new AtomicBoolean(true);
        this.context = new ApplicationContext(
                collectionManager,
                consoleInput,
                coordinatesReader,
                personReader,
                ticketReader,
                running
        );
        this.invoker = new CommandInvoker(context);
        registerCommands();
        bootstrapLoad(dataFile);
    }

    private void registerCommands() {
        invoker.register(new InfoCommand(context));
        invoker.register(new ShowCommand(context));
        invoker.register(new AddCommand(context));
        invoker.register(new UpdateCommand(context));
        invoker.register(new RemoveByIdCommand(context));
        invoker.register(new ClearCommand(context));
        invoker.register(new SaveCommand(context));
        invoker.register(new ExecuteScriptCommand(context, invoker));
        invoker.register(new ExitCommand(context));
        invoker.register(new RemoveAtCommand(context));
        invoker.register(new AddIfMaxCommand(context));
        invoker.register(new ReorderCommand(context));
        invoker.register(new MaxByCoordinatesCommand(context));
        invoker.register(new PrintUniquePriceCommand(context));
        invoker.register(new PrintFieldDescendingPriceCommand(context));
        invoker.register(new HelpCommand(invoker));
    }

    private void bootstrapLoad(String dataFile) {
        try {
            context.collectionManager().loadFromFile();
            System.out.println("Коллекция загружена: " + dataFile);
            System.out.println("Элементов: " + context.collectionManager().getCollection().size());
        } catch (Exception e) {
            System.out.println("Загрузка: " + e.getMessage());
            System.out.println("Будет пустая коллекция.");
        }
        System.out.println("Введите help для списка команд.");
    }

    public void run() {
        while (context.running().get()) {
            try {
                String line = context.consoleInput().readLine("");
                invoker.executeLine(line);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        System.out.println("До свидания.");
    }

    public static void main(String[] args) {
        try {
            System.setProperty("file.encoding", "UTF-8");
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (Exception ignored) {
        }
        if (args.length == 0) {
            System.out.println("Укажите путь к JSON файлу, например: data/collection.json");
            return;
        }
        try {
            new App(args[0]).run();
        } catch (Exception e) {
            System.out.println("Критическая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
