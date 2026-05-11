package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;
import ticketmanagement.interfaces.Command;

public class ReorderCommand implements Command {
    private final ApplicationContext ctx;

    public ReorderCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "reorder";
    }

    @Override
    public String getDescription() {
        return "обратить порядок коллекции";
    }

    @Override
    public void execute(String[] args) {
        ctx.collectionManager().reorder();
        System.out.println("Порядок элементов изменён на обратный");
    }
}
