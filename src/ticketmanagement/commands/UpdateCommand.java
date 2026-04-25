package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;
import ticketmanagement.models.Coordinates;
import ticketmanagement.models.Person;
import ticketmanagement.models.Ticket;
import ticketmanagement.models.TicketType;

public class UpdateCommand implements Command {
    private final ApplicationContext ctx;

    public UpdateCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить элемент по id (аргумент: id)";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: update <id>");
            return;
        }
        try {
            Long id = Long.parseLong(args[0]);
            Ticket existing = ctx.collectionManager().getCollection().stream()
                    .filter(t -> t.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (existing == null) {
                System.out.println("Билет с id " + id + " не найден");
                return;
            }
            System.out.println("\nОБНОВЛЕНИЕ БИЛЕТА ID: " + id);
            System.out.println(existing);
            System.out.println("Выберите поле: 1-name 2-coordinates 3-price 4-comment 5-type 6-person 0-отмена");
            int choice = ctx.consoleInput().readInt("номер пункта меню", false);
            switch (choice) {
                case 1:
                    String name = ctx.consoleInput().readNonEmptyLine("Введите новое название (name): ");
                    ctx.collectionManager().updateFieldById(id, "name", name);
                    System.out.println("Название обновлено");
                    break;
                case 2:
                    Coordinates c = ctx.coordinatesReader().read();
                    ctx.collectionManager().updateFieldById(id, "coordinates", c);
                    System.out.println("Координаты обновлены");
                    break;
                case 3:
                    int price = ctx.consoleInput().readInt("новую цену (price > 0)", true);
                    ctx.collectionManager().updateFieldById(id, "price", price);
                    System.out.println("Цена обновлена");
                    break;
                case 4:
                    String comment = ctx.consoleInput().readNonEmptyLine("Введите новый комментарий (comment): ");
                    ctx.collectionManager().updateFieldById(id, "comment", comment);
                    System.out.println("Комментарий обновлён");
                    break;
                case 5:
                    TicketType type = ctx.consoleInput().readEnum("тип билета (type)", TicketType.class);
                    ctx.collectionManager().updateFieldById(id, "type", type);
                    System.out.println("Тип обновлён");
                    break;
                case 6:
                    Person p = ctx.personReader().readNullable();
                    ctx.collectionManager().updateFieldById(id, "person", p);
                    System.out.println("Человек обновлён");
                    break;
                case 0:
                    System.out.println("Отмена");
                    break;
                default:
                    System.out.println("Неверный выбор");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный id");
        }
    }
}
