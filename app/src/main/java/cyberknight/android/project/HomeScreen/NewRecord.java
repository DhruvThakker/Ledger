package cyberknight.android.project.HomeScreen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.DatabaseAndReaders.JsonReader;
import cyberknight.android.project.R;

/**
 * Created by Parth on 30-06-2016.
 * CyberKnight apps
 */
public class NewRecord extends DialogFragment{

    private DbHelper database;

    static NewRecord newInstance(int num) {
        NewRecord f = new NewRecord();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_record,container,false);
        getDialog().setTitle("Add new record");

        Spinner transaction = (Spinner)v.findViewById(R.id.spinnerTransaction);
        final Spinner category = (Spinner)v.findViewById(R.id.spinnerCategory);
        final Spinner paymentType = (Spinner)v.findViewById(R.id.spinnerPaymentType);
        final EditText amount = (EditText) v.findViewById(R.id.textAmount);
        final EditText note = (EditText) v.findViewById(R.id.textNote);
        final DatePicker date = (DatePicker) v.findViewById(R.id.datePicker);

        JsonReader jsonReader = new JsonReader(v.getContext());
        database = new DbHelper(MainActivity.applicationContext);

        ArrayList<String> trans = new ArrayList<>();
        trans.add("Income");
        trans.add("Expense");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), R.layout.spinner_item ,trans);
        transaction.setAdapter(adapter);
        adapter = new ArrayAdapter<>(v.getContext(), R.layout.spinner_item , jsonReader.getCategories());
        category.setAdapter(adapter);
        adapter = new ArrayAdapter<>(v.getContext(), R.layout.spinner_item , jsonReader.getAccountsNames());
        paymentType.setAdapter(adapter);

        Button button = (Button)v.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().setCancelable(true);
                Log.d("NewRecord",date.getYear()+"-"+date.getMonth()+"-"+date.getDayOfMonth());
                database.addRecord(Double.parseDouble(amount.getText().toString()), new Date(date.getYear(), date.getMonth(), date.getDayOfMonth()),category.getSelectedItem().toString(),paymentType.getSelectedItem().toString(),note.getText().toString());
                RecordScreenUpdater mUpdater = (RecordScreenUpdater) getTargetFragment();
                mUpdater.updateScreenRecords(true);
                getDialog().dismiss();
            }
        });

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode== KeyEvent.KEYCODE_BACK){
                    getDialog().dismiss();
                }
                return false;
            }
        });

        return v;
    }
}
