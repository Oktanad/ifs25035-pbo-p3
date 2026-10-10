package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IContactRepository;
import java.util.List;
import java.util.Optional;

public class ContactUseCase {
    private final IContactRepository contactRepository;

    public ContactUseCase(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact addContact(String name, String phone, String email) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nama kontak tidak boleh kosong");
        }
        if (name.trim().length() < 2) {
            throw new ValidationException("Nama kontak minimal 2 karakter");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Nomor telepon tidak boleh kosong");
        }
        if (!isValidPhone(phone)) {
            throw new ValidationException("Nomor telepon tidak valid (harus 8-15 digit angka)");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email tidak boleh kosong");
        }
        if (!isValidEmail(email)) {
            throw new ValidationException("Format email tidak valid");
        }
        return contactRepository.save(name.trim(), phone.trim(), email.trim());
    }

    public void updateContact(int id, String name, String phone, String email)
            throws EntityNotFoundException, ValidationException {
        if (id <= 0) {
            throw new ValidationException("ID tidak valid");
        }
        Optional<Contact> found = contactRepository.findById(id);
        if (found.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        boolean hasName = name != null && !name.trim().isEmpty();
        boolean hasPhone = phone != null && !phone.trim().isEmpty();
        boolean hasEmail = email != null && !email.trim().isEmpty();

        if (!hasName && !hasPhone && !hasEmail) {
            throw new ValidationException("Minimal satu data harus diisi untuk perubahan");
        }

        Contact contact = found.get();
        if (hasName) {
            if (name.trim().length() < 2) {
                throw new ValidationException("Nama kontak minimal 2 karakter");
            }
            contact.changeName(name.trim());
        }
        if (hasPhone) {
            if (!isValidPhone(phone)) {
                throw new ValidationException("Nomor telepon tidak valid (harus 8-15 digit angka)");
            }
            contact.changePhone(phone.trim());
        }
        if (hasEmail) {
            if (!isValidEmail(email)) {
                throw new ValidationException("Format email tidak valid");
            }
            contact.changeEmail(email.trim());
        }
        contactRepository.update(contact);
    }

    public void removeContact(int id) throws EntityNotFoundException, ValidationException {
        if (id <= 0) {
            throw new ValidationException("ID tidak valid");
        }
        boolean deleted = contactRepository.deleteById(id);
        if (!deleted) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Contact> searchContacts(String keyword) {
        if (keyword == null) {
            return List.of();
        }
        String lowerKeyword = keyword.toLowerCase();
        return contactRepository.findBy(c -> c.getName().toLowerCase().contains(lowerKeyword));
    }

    public List<Contact> sortContacts(SortOption option) {
        if (option == null) {
            return List.of();
        }
        return contactRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private boolean isValidPhone(String phone) {
        return phone.trim().matches("^\\+?[0-9]{8,15}$");
    }

    private boolean isValidEmail(String email) {
        return email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
