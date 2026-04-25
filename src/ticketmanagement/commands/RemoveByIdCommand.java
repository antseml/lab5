package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

public class RemoveByIdCommand implements Command {
    private final ApplicationContext ctx;

    public RemoveByIdCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент по id (аргумент: id)";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: remove_by_id <id>");
            return;
        }
        try {
            Long id = Long.parseLong(args[0]);
            if (ctx.collectionManager().removeById(id)) {
                System.out.println("Билет с id " + id + " удалён");
            } else {
                System.out.println("Билет с id " + id + " не найден");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный id");
        }
    }
}
