package cyberknight.android.project;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Parth on 30-06-2016.
 * CyberKnight apps
 */
public class NewRecord extends DialogFragment {

    DbHelper database;

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
        super.onCreate(savedInstanceState);;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_record,container,false);
        final TextView date = (TextView) v.findViewById(R.id.date);
        date.setText("22-12-1997");
        getDialog().setTitle("Add new record");

        Spinner transaction = (Spinner)v.findViewById(R.id.spinnerTransaction);
        final Spinner category = (Spinner)v.findViewById(R.id.spinnerCategory);
        final Spinner paymentType = (Spinner)v.findViewById(R.id.spinnerPaymentType);
        final EditText amount = (EditText) v.findViewById(R.id.textAmount);
        final EditText note = (EditText) v.findViewById(R.id.textNote);

        JsonReader jsonReader = new JsonReader(v.getContext());
        database = new DbHelper(MainActivity.applicationContext);

        ArrayList<String> trans = new ArrayList<>();
        trans.add("Income");
        trans.add("Expense");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_item ,trans);
        transaction.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_item , jsonReader.getCategories());
        category.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_item , jsonReader.getAccountsNames());
        paymentType.setAdapter(adapter);

        Button button = (Button)v.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivityFragment.setCancelable(true);
                database.addRecord(Double.parseDouble(amount.getText().toString()), new Date(2016,07,01),category.getSelectedItem().toString(),paymentType.getSelectedItem().toString(),note.getText().toString());
                getDialog().dismiss();
            }
        });

        return v;
    }
}
