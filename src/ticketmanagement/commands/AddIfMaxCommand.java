package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;
import ticketmanagement.models.Ticket;

public class AddIfMaxCommand implements Command {
    private final ApplicationContext ctx;

    public AddIfMaxCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "добавить элемент, если он больше максимального (по имени)";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("\nДОБАВЛЕНИЕ (add_if_max):");
        Ticket ticket = ctx.ticketReader().readNewTicket();
        if (ctx.collectionManager().addIfMax(ticket)) {
            System.out.println("Билет добавлен. ID: " + ticket.getId());
        } else {
            System.out.println("Билет не добавлен (не больше текущего максимума по compareTo)");
        }
    }
}
