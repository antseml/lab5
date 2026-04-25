package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

public class ShowCommand implements Command {
    private final ApplicationContext ctx;

    public ShowCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "показать все элементы";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("\nЭЛЕМЕНТЫ КОЛЛЕКЦИИ:");
        System.out.println(ctx.collectionManager().show());
    }
}
