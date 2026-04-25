package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;
import ticketmanagement.models.Ticket;

public class AddCommand implements Command {
    private final ApplicationContext ctx;

    public AddCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("\nДОБАВЛЕНИЕ НОВОГО БИЛЕТА:");
        Ticket ticket = ctx.ticketReader().readNewTicket();
        ctx.collectionManager().add(ticket);
        System.out.println("Билет добавлен. ID: " + ticket.getId());
    }
}
