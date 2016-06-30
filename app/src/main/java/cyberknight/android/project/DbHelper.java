package cyberknight.android.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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

    public static long autoId;

    public static final String CREATE_TABLE_DAILY_INFO = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + KEY_ID + " INTEGER (20) PRIMARY KEY, "
            + KEY_CATEGORY + " VARCHAR (20) , "
            + KEY_DATE + " VARCHAR (15) "
            + KEY_ACCOUNT_TYPE + " VARCHAR (10) "
            + KEY_AMOUNT + " INTEGER (10) "
            + KEY_NOTE + " VARCHAR (10) "
            + ")";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DAILY_INFO);
        autoId = 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public void addRecord(int amount,String date,String category,String accountType,String note){

        Log.d(TAG,"Adding Amount...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,autoId++);
        values.put(KEY_AMOUNT,amount);
        values.put(KEY_DATE,date);
        values.put(KEY_CATEGORY,category);
        values.put(KEY_ACCOUNT_TYPE,accountType);
        values.put(KEY_NOTE,note);

        db.insert(TABLE_NAME,null,values);

        Log.d(TAG,"Created Table "+TABLE_NAME);

    }

    public void changeRecord(int id,int amount,String date,String category,String accountType,String note){

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG,"Updated message status");

        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_AMOUNT,amount);
        values.put(KEY_DATE,date);
        values.put(KEY_CATEGORY,category);
        values.put(KEY_ACCOUNT_TYPE,accountType);
        values.put(KEY_NOTE,note);

        db.update(TABLE_NAME, values, KEY_ID + " = " + id,null);

    }

    public boolean deleteRecord(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_ID + "=" + id, null) > 0;
    }

}
