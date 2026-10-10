package domain.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Kriteria pengurutan kegiatan.
 * Setiap opsi membawa comparator-nya sendiri sehingga logika pengurutan terpusat di domain.
 */
public enum SortOption {
    /** Urutkan berdasarkan hari (Senin sampai Minggu), lalu waktu. */
    DAY(Comparator.comparing((Activity activity) -> getDayIndex(activity.getDay()))
            .thenComparing(Activity::getTime, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan berdasarkan waktu (paling awal lebih dulu). */
    TIME(Comparator.comparing(Activity::getTime, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan judul dari A ke Z (case-insensitive). */
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan judul dari Z ke A (case-insensitive). */
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    /** Urutan hari dalam sepekan. */
    private static final List<String> DAYS =
            Arrays.asList("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu");

    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Activity> comparator() {
        return comparator;
    }

    private static int getDayIndex(String day) {
        for (int i = 0; i < DAYS.size(); i++) {
            if (DAYS.get(i).equalsIgnoreCase(day)) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }
}
