package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

public class ClearCommand implements Command {
    private final ApplicationContext ctx;

    public ClearCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Очистить коллекцию? (y/n): ");
        String c = ctx.consoleInput().readLine("").toLowerCase();
        if (c.equals("y") || c.equals("yes")) {
            ctx.collectionManager().clear();
            System.out.println("Коллекция очищена");
        } else {
            System.out.println("Отменено");
        }
    }
}
