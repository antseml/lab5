package ticketmanagement.interfaces;

import ticketmanagement.util.CommandInvoker;

/**
 * Команда консоли (паттерн Command). Новые команды добавляются отдельными классами и регистрацией в {@link CommandInvoker}.
 */
public interface Command {

    String getName();

    String getDescription();

    void execute(String[] args);
}
