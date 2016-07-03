package cyberknight.android.project.DatabaseAndReaders;

/**
 * Created by umang on 30/6/16.
 */
public class AccountDetails {

    private int id;
    private String category;
    private String date;
    private String accountType;
    private double amount;
    private String note;

    public AccountDetails() {
        this.category = "";
        this.accountType = "";
        this.note = "";
    }

    public AccountDetails(String category, String date, String accountType, double amount, String note) {
        this.category = category;
        this.date = date;
        this.accountType = accountType;
        this.amount = (double) Math.round(amount*100d)/100d;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getAmount() {
        return (double) Math.round(amount*100d)/100d;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
