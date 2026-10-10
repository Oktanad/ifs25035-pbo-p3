package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan transaksi.
 * Setiap opsi membawa comparator-nya sendiri sehingga logika pengurutan
 * terpusat di domain.
 */
public enum SortOption {
    /** Urutkan jumlah dari terkecil ke terbesar. */
    AMOUNT_ASC(Comparator.comparingLong(Transaction::getAmount)),

    /** Urutkan jumlah dari terbesar ke terkecil. */
    AMOUNT_DESC(Comparator.comparingLong(Transaction::getAmount).reversed()),

    /** Tampilkan pemasukan terlebih dahulu. */
    INCOME_FIRST(Comparator.comparing((Transaction t) -> t.getType() != TransactionType.INCOME)),

    /** Tampilkan pengeluaran terlebih dahulu. */
    EXPENSE_FIRST(Comparator.comparing((Transaction t) -> t.getType() != TransactionType.EXPENSE));

    private final Comparator<Transaction> comparator;

    SortOption(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Transaction> comparator() {
        return comparator;
    }
}
