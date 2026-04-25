package ticketmanagement;

import ticketmanagement.io.ConsoleInput;
import ticketmanagement.io.CoordinatesReader;
import ticketmanagement.io.PersonReader;
import ticketmanagement.io.TicketReader;
import ticketmanagement.services.CollectionManager;
import ticketmanagement.services.IdGenerator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Общие зависимости приложения (упрощённый DI без фреймворка).
 */
public final class ApplicationContext {
    private final CollectionManager collectionManager;
    private final ConsoleInput consoleInput;
    private final CoordinatesReader coordinatesReader;
    private final PersonReader personReader;
    private final TicketReader ticketReader;
    private final AtomicBoolean running;
    private final Set<String> activeScriptPaths = new HashSet<>();
    private int scriptDepth;
    private static final int MAX_SCRIPT_DEPTH = 10;
    private final Deque<InputLevel> inputStack = new ArrayDeque<>();

    private static final class InputLevel {
        final Scanner scanner;
        final boolean interactive;

        InputLevel(Scanner scanner, boolean interactive) {
            this.scanner = scanner;
            this.interactive = interactive;
        }
    }

    public ApplicationContext(CollectionManager collectionManager,
                              ConsoleInput consoleInput,
                              CoordinatesReader coordinatesReader,
                              PersonReader personReader,
                              TicketReader ticketReader,
                              AtomicBoolean running) {
        this.collectionManager = collectionManager;
        this.consoleInput = consoleInput;
        this.coordinatesReader = coordinatesReader;
        this.personReader = personReader;
        this.ticketReader = ticketReader;
        this.running = running;
    }

    public CollectionManager collectionManager() {
        return collectionManager;
    }

    public ConsoleInput consoleInput() {
        return consoleInput;
    }

    public CoordinatesReader coordinatesReader() {
        return coordinatesReader;
    }

    public PersonReader personReader() {
        return personReader;
    }

    public TicketReader ticketReader() {
        return ticketReader;
    }

    public IdGenerator idGenerator() {
        return collectionManager.getIdGenerator();
    }

    public AtomicBoolean running() {
        return running;
    }

    public boolean isMaxScriptDepth() {
        return scriptDepth >= MAX_SCRIPT_DEPTH;
    }

    public boolean isScriptActive(String canonicalPath) {
        return activeScriptPaths.contains(canonicalPath);
    }

    public void pushScript(String canonicalPath) {
        activeScriptPaths.add(canonicalPath);
        scriptDepth++;
    }

    public void popScript(String canonicalPath) {
        activeScriptPaths.remove(canonicalPath);
        scriptDepth = Math.max(0, scriptDepth - 1);
    }

    public int scriptDepth() {
        return scriptDepth;
    }

    public int maxScriptDepth() {
        return MAX_SCRIPT_DEPTH;
    }

    /**
     * Вложенные скрипты: сохраняем текущий {@link Scanner} и переключаемся на файл.
     */
    public void pushScriptInput(Scanner fileScanner) {
        inputStack.push(new InputLevel(consoleInput.getScanner(), consoleInput.isInteractive()));
        consoleInput.setScanner(fileScanner, false);
    }

    public void popScriptInput() {
        if (inputStack.isEmpty()) {
            return;
        }
        InputLevel prev = inputStack.pop();
        consoleInput.setScanner(prev.scanner, prev.interactive);
    }
}
