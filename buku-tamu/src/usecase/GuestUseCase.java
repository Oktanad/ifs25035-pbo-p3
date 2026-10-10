package usecase;

import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IGuestRepository;
import java.util.List;

/**
 * Use case yang menangani logika bisnis aplikasi buku tamu.
 * Bebas dari I/O konsol dan melempar checked exception domain.
 */
public class GuestUseCase {
    private final IGuestRepository guestRepository;

    public GuestUseCase(IGuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Guest registerGuest(String name, String purpose) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nama tamu tidak boleh kosong");
        }
        if (purpose == null || purpose.trim().isEmpty()) {
            throw new ValidationException("Tujuan kunjungan tidak boleh kosong");
        }
        return guestRepository.save(name.trim(), purpose.trim());
    }

    public void removeGuest(int id) throws EntityNotFoundException {
        boolean deleted = guestRepository.deleteById(id);
        if (!deleted) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Guest> searchGuests(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return guestRepository.findBy(g -> g.getName().toLowerCase().contains(lowerKeyword));
    }
}
