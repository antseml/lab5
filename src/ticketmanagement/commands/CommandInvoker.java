package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Реестр и диспетчер команд (аналог {@code CommandManager} + {@code Runner.launchCommand} из референса).
 */
public class CommandInvoker {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final ApplicationContext context;

    public CommandInvoker(ApplicationContext context) {
        this.context = context;
    }

    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    public Collection<Command> registeredCommands() {
        return commands.values();
    }

    public ApplicationContext getContext() {
        return context;
    }

    /**
     * Разбор одной строки (интерактив или строка из скрипта).
     */
    public void executeLine(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        String[] parts = line.split("\\s+");
        String name = parts[0].toLowerCase();
        String[] args = parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0];
        Command command = commands.get(name);
        if (command == null) {
            System.out.println("Неизвестная команда: '" + name + "'. Введите help для списка.");
            return;
        }
        command.execute(args);
    }
}
