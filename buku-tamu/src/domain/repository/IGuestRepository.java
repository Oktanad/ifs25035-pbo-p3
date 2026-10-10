package domain.repository;

import domain.entity.Guest;

/**
 * Port spesifik Guest yang memperluas generic {@link IRepository}.
 */
public interface IGuestRepository extends IRepository<Guest, Integer> {
    /**
     * Menyimpan tamu baru dengan nama dan tujuan kunjungan.
     *
     * @param name nama tamu
     * @param purpose tujuan kunjungan
     * @return guest yang tersimpan lengkap dengan ID
     */
    Guest save(String name, String purpose);
}
