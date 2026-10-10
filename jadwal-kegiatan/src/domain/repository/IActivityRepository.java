package domain.repository;

import domain.entity.Activity;

/**
 * Port spesifik Activity yang memperluas generic {@link IRepository}.
 */
public interface IActivityRepository extends IRepository<Activity, Integer> {
    /**
     * Menyimpan kegiatan baru dengan judul, hari, dan waktu.
     *
     * @param title judul kegiatan
     * @param day hari kegiatan
     * @param time waktu kegiatan
     * @return kegiatan yang tersimpan lengkap dengan ID
     */
    Activity save(String title, String day, String time);
}
