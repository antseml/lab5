package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

public class ExitCommand implements Command {
    private final ApplicationContext ctx;

    public ExitCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "выход без сохранения";
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Выйти без сохранения? (y/n): ");
        String c = ctx.consoleInput().readLine("").toLowerCase();
        if (c.equals("y") || c.equals("yes")) {
            ctx.running().set(false);
        } else {
            System.out.println("Отменено");
        }
    }
}
