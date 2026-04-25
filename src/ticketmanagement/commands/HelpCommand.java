package ticketmanagement.commands;

/**
 * Справка по зарегистрированным командам.
 */
public class HelpCommand implements Command {
    private final CommandInvoker invoker;

    public HelpCommand(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "показать справку по командам";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("\nДОСТУПНЫЕ КОМАНДЫ:");
        for (Command c : invoker.registeredCommands()) {
            System.out.printf("%-35s — %s%n", c.getName(), c.getDescription());
        }
    }
}
