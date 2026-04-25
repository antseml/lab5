package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

import java.util.List;

public class PrintFieldDescendingPriceCommand implements Command {
    private final ApplicationContext ctx;

    public PrintFieldDescendingPriceCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "print_field_descending_price";
    }

    @Override
    public String getDescription() {
        return "цены в порядке убывания";
    }

    @Override
    public void execute(String[] args) {
        List<Integer> prices = ctx.collectionManager().getPricesDescending();
        if (prices.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println("ЦЕНЫ ПО УБЫВАНИЮ:");
            for (int i = 0; i < prices.size(); i++) {
                System.out.printf("%3d. %d%n", i + 1, prices.get(i));
            }
        }
    }
}
