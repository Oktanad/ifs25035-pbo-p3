package domain.exception;

/**
 * Exception yang dilempar saat validasi input domain kontak teman gagal.
 */
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}
