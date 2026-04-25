package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

public class RemoveAtCommand implements Command {
    private final ApplicationContext ctx;

    public RemoveAtCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "remove_at";
    }

    @Override
    public String getDescription() {
        return "удалить элемент по индексу (аргумент: index)";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: remove_at <index>");
            return;
        }
        try {
            int index = Integer.parseInt(args[0]);
            if (ctx.collectionManager().removeAt(index)) {
                System.out.println("Элемент с индексом " + index + " удалён");
            } else {
                System.out.println("Неверный индекс: " + index + ". Размер: "
                        + ctx.collectionManager().getCollection().size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный индекс");
        }
    }
}
