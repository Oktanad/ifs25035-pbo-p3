package domain.repository;

import domain.entity.Contact;

/**
 * Port spesifik Contact yang memperluas generic {@link IRepository}.
 */
public interface IContactRepository extends IRepository<Contact, Integer> {
    /**
     * Menyimpan kontak baru dengan nama, telepon, dan email.
     *
     * @param name nama kontak
     * @param phone nomor telepon
     * @param email alamat email
     * @return kontak yang tersimpan lengkap dengan ID
     */
    Contact save(String name, String phone, String email);
}
