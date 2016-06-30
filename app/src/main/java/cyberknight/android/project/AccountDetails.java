package cyberknight.android.project;

/**
 * Created by umang on 30/6/16.
 */
public class AccountDetails {

    private int id;
    private String category;
    private String date;
    private String accountType;
    private int amount;
    private String note;

    public AccountDetails(String category, String date, String accountType, int amount, String note) {
        this.category = category;
        this.date = date;
        this.accountType = accountType;
        this.amount = amount;
        this.note = note;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
