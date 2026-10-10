package domain.exception;

/**
 * Base checked exception untuk hierarki domain buku tamu.
 */
public class DomainException extends Exception {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
