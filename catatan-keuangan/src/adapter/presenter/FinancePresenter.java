package adapter.presenter;

import domain.entity.Transaction;
import java.util.List;

/**
 * Presenter yang memformat data dari use case menjadi output layar.
 */
public class FinancePresenter {

    private String format(Transaction t) {
        return String.format("[%d] %s | Rp %d | [%s]",
                t.getId(),
                t.getDescription(),
                t.getAmount(),
                t.getType().getLabel());
    }

    private void printList(List<Transaction> transactions, String header, String emptyMessage) {
        System.out.println(header);

        if (transactions.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        for (Transaction t : transactions) {
            System.out.println(format(t));
        }
    }

    /** Menampilkan daftar semua transaksi beserta saldo. */
    public void showTransactions(List<Transaction> transactions, long balance) {
        printList(transactions, "Daftar Transaksi:", "- Belum ada transaksi!");
        System.out.println("Saldo: Rp " + balance);
    }

    /** Menampilkan hasil pencarian berdasarkan kata kunci. */
    public void showSearchResults(List<Transaction> transactions, String keyword) {
        printList(transactions, "Hasil Pencarian: \"" + keyword + "\"", "- Transaksi tidak ditemukan!");
    }

    /** Menampilkan daftar transaksi yang sudah diurutkan. */
    public void showSortedTransactions(List<Transaction> transactions, long balance) {
        printList(transactions, "Daftar Transaksi (Terurut):", "- Belum ada transaksi!");
    }

    /** Menampilkan saldo saat ini. */
    public void showBalance(long balance) {
        System.out.println("Saldo saat ini: Rp " + balance);
    }

    public void showAddSuccess(Transaction t, long balance) {
        System.out.printf("Berhasil menambah transaksi: %s%n", format(t));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus transaksi.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus transaksi dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidDescription() {
        System.out.println("[!] Keterangan tidak boleh kosong!");
    }

    public void showInvalidAmount() {
        System.out.println("[!] Jumlah tidak valid!");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}