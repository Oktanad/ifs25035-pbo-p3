package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

/**
 * Tampilan konsol aplikasi catatan keuangan.
 * Menerima input user, memanggil use case, dan menangani exception domain secara terstruktur.
 */
public class FinanceView {
    private final FinanceUseCase financeUseCase;
    private final FinancePresenter presenter;

    public FinanceView(FinanceUseCase financeUseCase, FinancePresenter presenter) {
        this.financeUseCase = financeUseCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showTransactions(
                    financeUseCase.getAllTransactions(),
                    financeUseCase.getBalance());
            printMenu();

            String input = InputUtil.input("Pilih");
            System.out.println();
            switch (input) {
                case "1" -> addIncome();
                case "2" -> addExpense();
                case "3" -> searchTransaction();
                case "4" -> sortTransaction();
                case "5" -> showBalance();
                case "6" -> removeTransaction();
                case "x" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Pemasukan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Lihat Saldo");
        System.out.println("6. Hapus");
        System.out.println("x. Keluar");
    }

    private void addIncome() {
        addTransaction(TransactionType.INCOME, "Pemasukan");
    }

    private void addExpense() {
        addTransaction(TransactionType.EXPENSE, "Pengeluaran");
    }

    private void addTransaction(TransactionType type, String label) {
        System.out.println("[Tambah " + label + "]");
        String description = InputUtil.input("Keterangan (x Jika Batal)");

        if (description.equals("x")) {
            return;
        }
        if (description.trim().isEmpty()) {
            presenter.showInvalidDescription();
            return;
        }

        String strAmount = InputUtil.input("Jumlah");
        long amount;
        try {
            amount = parseAmount(strAmount);
        } catch (ValidationException e) {
            presenter.showInvalidAmount();
            return;
        }

        try {
            var t = financeUseCase.addTransaction(description, amount, type);
            presenter.showAddSuccess(t, financeUseCase.getBalance());
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void removeTransaction() {
        System.out.println("[Hapus Transaksi]");
        String strId = InputUtil.input("ID Transaksi (x Jika Batal)");

        if (strId.equals("x")) {
            return;
        }

        try {
            int id = parseId(strId);
            financeUseCase.removeTransaction(id);
            presenter.showRemoveSuccess();
        } catch (ValidationException e) {
            presenter.showInvalidId();
        } catch (EntityNotFoundException e) {
            presenter.showRemoveFailed(e.getId());
        }
    }

    private void searchTransaction() {
        System.out.println("[Cari Transaksi]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (!keyword.equals("x")) {
            presenter.showSearchResults(financeUseCase.searchTransactions(keyword), keyword);
        }
    }

    private void sortTransaction() {
        System.out.println("[Urutkan Transaksi]");
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        System.out.println();
        if (input.equals("x")) {
            return;
        }

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedTransactions(
                financeUseCase.sortTransactions(option),
                financeUseCase.getBalance());
    }

    private void showBalance() {
        presenter.showBalance(financeUseCase.getBalance());
    }

    private int parseId(String value) throws ValidationException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("ID harus berupa angka numerik");
        }
    }

    private long parseAmount(String value) throws ValidationException {
        try {
            long amount = Long.parseLong(value.trim());
            if (amount <= 0) {
                throw new ValidationException("Jumlah tidak valid");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new ValidationException("Jumlah tidak valid");
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.AMOUNT_ASC;
            case "2" -> SortOption.AMOUNT_DESC;
            case "3" -> SortOption.INCOME_FIRST;
            case "4" -> SortOption.EXPENSE_FIRST;
            default -> null;
        };
    }
}