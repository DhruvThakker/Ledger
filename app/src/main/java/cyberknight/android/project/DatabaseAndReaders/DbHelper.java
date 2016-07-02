package cyberknight.android.project.DatabaseAndReaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by umang on 30/6/16.
 */
public class DbHelper extends SQLiteOpenHelper {


    private static final String TAG = DbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Account";

    public static final String TABLE_NAME = "DailyAccount";
    public static final String KEY_ID = "auto_id";
    public static final String KEY_CATEGORY = "category_type";
    public static final String KEY_DATE = "date";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_ACCOUNT_TYPE = "account_type";
    public static final String KEY_NOTE = "note";

    public static final String CREATE_TABLE_DAILY_INFO = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + KEY_CATEGORY + " VARCHAR (20) , "
            + KEY_DATE + " VARCHAR(10) , "
            + KEY_ACCOUNT_TYPE + " VARCHAR (10) , "
            + KEY_AMOUNT + " REAL , "
            + KEY_NOTE + " VARCHAR (10) "
            + ")";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DAILY_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public void addRecord(double amount, String date,String category,String accountType,String note){

        Log.d(TAG,"Adding Amount...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT,amount);
        values.put(KEY_DATE,date);
        values.put(KEY_CATEGORY,category);
        values.put(KEY_ACCOUNT_TYPE,accountType);
        values.put(KEY_NOTE,note);

        db.insert(TABLE_NAME,null,values);

        Log.d(TAG,"RECORD ADDED++++++++++++++++++++++++++++++++++++++++ "+TABLE_NAME);

    }

    public void changeRecord(int id,int amount, String date,String category,String accountType,String note){

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG,"Updated message status");

        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT,amount);
        values.put(KEY_DATE,date);
        values.put(KEY_CATEGORY,category);
        values.put(KEY_ACCOUNT_TYPE,accountType);
        values.put(KEY_NOTE,note);

        db.update(TABLE_NAME, values, KEY_ID + " = " + id,null);

    }

    public ArrayList<AccountDetails> getAllAccountDetails() {

        ArrayList<AccountDetails> accountModels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " tq ";

        Log.e(TAG, "Get All Details " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Log.d(TAG, "Account Cursor "+c.toString());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                AccountDetails account = new AccountDetails();
                account.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                account.setAmount(c.getDouble(c.getColumnIndex(KEY_AMOUNT)));
                account.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                account.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
                account.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                account.setAccountType(c.getString(c.getColumnIndex(KEY_ACCOUNT_TYPE)));
                accountModels.add(account);

            } while (c.moveToNext());
        }
        c.close();
        return accountModels;
    }

    public ArrayList<AccountDetails> getAllAccountDetailsByDate(String date) {

        ArrayList<AccountDetails> accountModels = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " tm WHERE tm." + KEY_DATE + " = '" + date +"' ORDER BY tm." +KEY_ID + " DESC";

        Log.d(TAG, "Get All Date Query "+selectQuery);


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Date Cursor "+c.toString());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                AccountDetails account = new AccountDetails();
                account.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                account.setAmount(c.getDouble(c.getColumnIndex(KEY_AMOUNT)));
                account.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                account.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
                account.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                account.setAccountType(c.getString(c.getColumnIndex(KEY_ACCOUNT_TYPE)));
                accountModels.add(account);

            } while (c.moveToNext());
        }
        return accountModels;
    }

    public ArrayList<AccountDetails> getAllAccountDetailsByCategory(String category) {

        ArrayList<AccountDetails> accountModels = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " tm WHERE tm." + KEY_CATEGORY + " = '" + category +"' ORDER BY tm." +KEY_DATE + " DESC";

        Log.d(TAG, "Get All Date Query "+selectQuery);


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Date Cursor "+c.toString());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                AccountDetails account = new AccountDetails();
                account.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                account.setAmount(c.getDouble(c.getColumnIndex(KEY_AMOUNT)));
                account.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                account.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
                account.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                account.setAccountType(c.getString(c.getColumnIndex(KEY_ACCOUNT_TYPE)));
                accountModels.add(account);

            } while (c.moveToNext());
        }
        return accountModels;
    }

    public boolean deleteRecord(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_ID + "=" + id, null) > 0;
    }

}
