package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;
import ticketmanagement.models.Ticket;

public class MaxByCoordinatesCommand implements Command {
    private final ApplicationContext ctx;

    public MaxByCoordinatesCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "max_by_coordinates";
    }

    @Override
    public String getDescription() {
        return "элемент с максимальной суммой координат";
    }

    @Override
    public void execute(String[] args) {
        Ticket max = ctx.collectionManager().maxByCoordinates();
        if (max == null) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println("\nБИЛЕТ С МАКСИМАЛЬНЫМИ КООРДИНАТАМИ:");
            System.out.println(max);
        }
    }
}
