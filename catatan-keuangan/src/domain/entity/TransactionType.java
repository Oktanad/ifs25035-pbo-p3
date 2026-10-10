package domain.entity;

/**
 * Tipe transaksi keuangan: pemasukan atau pengeluaran.
 */
public enum TransactionType {
    INCOME("Pemasukan"),
    EXPENSE("Pengeluaran");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

    /** Label tampilan untuk tipe transaksi. */
    public String getLabel() {
        return label;
    }
}
