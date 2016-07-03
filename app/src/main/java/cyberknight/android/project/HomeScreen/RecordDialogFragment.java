package cyberknight.android.project.HomeScreen;

import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cyberknight.android.project.DatabaseAndReaders.AccountDetails;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.R;

/**
 * Created by Parth on 03-07-2016.
 * CyberKnight apps
 */
public class RecordDialogFragment extends DialogFragment{

    static RecordDialogFragment newInstance(int num) {
        RecordDialogFragment f = new RecordDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record_dialog, container, false);
        final TextView amount = (TextView) v.findViewById(R.id.recordAmount);
        final TextView category = (TextView) v.findViewById(R.id.recordCategory);
        final TextView paymentType = (TextView) v.findViewById(R.id.recordPaymentType);
        final TextView note = (TextView) v.findViewById(R.id.recordNote);
        final TextView date = (TextView) v.findViewById(R.id.recordDate);

        DbHelper database = new DbHelper(MainActivity.applicationContext);
        ArrayList<AccountDetails> records = database.getAllAccountDetailsByDate(getArguments().getString("date"));
        int position = getArguments().getInt("position");
        amount.setText("Rs. "+records.get(position).getAmount());
        category.setText(records.get(position).getCategory());
        paymentType.setText(records.get(position).getAccountType());
        note.setText(records.get(position).getNote());
        date.setText(records.get(position).getDate());

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
