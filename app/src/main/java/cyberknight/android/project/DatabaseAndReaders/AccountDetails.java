package cyberknight.android.project.DatabaseAndReaders;

import java.sql.Date;

/**
 * Created by umang on 30/6/16.
 */
public class AccountDetails {

    private int id;
    private String category;
    private Date date;
    private String accountType;
    private double amount;
    private String note;

    public AccountDetails() {
        this.category = "";
        this.accountType = "";
        this.note = "";
    }

    public AccountDetails(String category, Date date, String accountType, double amount, String note) {
        this.category = category;
        this.date = date;
        this.accountType = accountType;
        this.amount = amount;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getAmount() {
        return amount;
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
