package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.ITransactionRepository;
import java.util.List;

/**
 * Use case yang menangani logika bisnis aplikasi catatan keuangan.
 * Tidak melakukan I/O - hanya memproses data dan melempar checked exception
 * domain jika operasi tidak valid atau data tidak ditemukan.
 */
public class FinanceUseCase {
    private final ITransactionRepository transactionRepository;

    public FinanceUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction addTransaction(String description, long amount, TransactionType type)
            throws ValidationException {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("Keterangan transaksi tidak boleh kosong");
        }
        if (description.trim().length() < 2) {
            throw new ValidationException("Keterangan transaksi minimal 2 karakter");
        }
        if (amount <= 0) {
            throw new ValidationException("Jumlah tidak valid");
        }
        if (type == null) {
            throw new ValidationException("Tipe transaksi tidak boleh kosong");
        }
        return transactionRepository.save(description.trim(), amount, type);
    }

    public void removeTransaction(int id) throws EntityNotFoundException, ValidationException {
        if (id <= 0) {
            throw new ValidationException("ID tidak valid");
        }
        boolean deleted = transactionRepository.deleteById(id);
        if (!deleted) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Transaction> searchTransactions(String keyword) {
        if (keyword == null) {
            return List.of();
        }
        String lowerKeyword = keyword.toLowerCase();
        return transactionRepository.findBy(
                t -> t.getDescription().toLowerCase().contains(lowerKeyword));
    }

    public List<Transaction> sortTransactions(SortOption option) {
        if (option == null) {
            return List.of();
        }
        return transactionRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    /**
     * Menghitung saldo = total pemasukan - total pengeluaran.
     */
    public long getBalance() {
        long income = 0;
        long expense = 0;
        for (Transaction t : transactionRepository.findAll()) {
            if (t.getType() == TransactionType.INCOME) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }
        return income - expense;
    }
}
