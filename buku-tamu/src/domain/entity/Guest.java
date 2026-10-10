package domain.entity;

/**
 * Entity inti yang merepresentasikan satu tamu pada buku tamu.
 * Berada di layer domain — tidak bergantung pada layer lain.
 */
public class Guest {
    private final int id;
    private String name;
    private String purpose;

    public Guest(int id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePurpose(String purpose) {
        this.purpose = purpose;
    }
}
