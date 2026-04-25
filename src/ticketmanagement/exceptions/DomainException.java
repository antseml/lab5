package ticketmanagement.exceptions;

/**
 * Ошибка предметной области (расширяйте по необходимости).
 */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
