package domain.exception;

/**
 * Exception yang dilempar saat validasi input domain jadwal kegiatan gagal.
 */
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}
