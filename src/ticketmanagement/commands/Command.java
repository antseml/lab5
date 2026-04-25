package ticketmanagement.commands;

/**
 * Команда консоли (паттерн Command). Новые команды добавляются отдельными классами и регистрацией в {@link CommandInvoker}.
 */
public interface Command {

    /** Имя команды в нижнем регистре (как вводит пользователь). */
    String getName();

    String getDescription();

    /**
     * Выполнение команды; ошибки обрабатываются внутри или пробрасываются на верхний уровень цикла.
     *
     * @param args аргументы без имени команды
     */
    void execute(String[] args);
}
