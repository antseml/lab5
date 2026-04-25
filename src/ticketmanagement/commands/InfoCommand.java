package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

public class InfoCommand implements Command {
    private final ApplicationContext ctx;

    public InfoCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "информация о коллекции";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("\nИНФОРМАЦИЯ О КОЛЛЕКЦИИ:");
        System.out.println(ctx.collectionManager().getInfo());
    }
}
