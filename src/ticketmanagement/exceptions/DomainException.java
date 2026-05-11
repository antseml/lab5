package ticketmanagement.exceptions;

/**
 * Ошибка предметной области.
 */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
