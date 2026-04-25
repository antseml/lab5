package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

public class SaveCommand implements Command {
    private final ApplicationContext ctx;

    public SaveCommand(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    @Override
    public void execute(String[] args) {
        try {
            ctx.collectionManager().saveToFile();
            System.out.println("Коллекция сохранена");
        } catch (Exception e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }
}
