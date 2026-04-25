package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

import java.util.Set;

public class PrintUniquePriceCommand implements Command {
    private final ApplicationContext ctx;

    public PrintUniquePriceCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "print_unique_price";
    }

    @Override
    public String getDescription() {
        return "уникальные значения цены";
    }

    @Override
    public void execute(String[] args) {
        Set<Integer> unique = ctx.collectionManager().getUniquePrices();
        if (unique.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println("\nУНИКАЛЬНЫЕ ЦЕНЫ:");
            unique.stream().sorted().forEach(p -> System.out.println("  • " + p));
            System.out.println("Всего: " + unique.size());
        }
    }
}
