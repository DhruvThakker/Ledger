package cyberknight.android.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Parth on 29-06-2016.
 * CyberKnight apps
 */
public class MainActivityFragment extends Fragment {

    private static DialogFragment newFragment;
    private DbHelper database;
    private ArrayList<AccountDetails> allRecords;
    private ListView records;

    public MainActivityFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        records = (ListView) view.findViewById(R.id.recordList);
        database = new DbHelper(MainActivity.applicationContext);

        allRecords = database.getAllAccountDetailsByDate(new Date(2016,07,01));;
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabNewRecord);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        updateRecords();
        return view;
    }

    void showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        // Create and show the dialog.
        newFragment = NewRecord.newInstance(0);
        newFragment.setCancelable(false);
        newFragment.show(getFragmentManager(),"tag");
    }

    static void setCancelable(boolean var){
        newFragment.setCancelable(var);
    }

    public void updateRecords(){
        RecordAdapter adapter = new RecordAdapter(getContext(),allRecords);
        records.setAdapter(adapter);
    }

    class RecordAdapter extends BaseAdapter {

        Context fContext;
        ArrayList<AccountDetails> fRecordItems;

        public RecordAdapter(Context fContext, ArrayList fRecordItems){
            this.fContext = fContext;
            this.fRecordItems = fRecordItems;
        }

        @Override
        public int getCount() {
            return fRecordItems.size();
        }

        @Override
        public Object getItem(int position) {
            return fRecordItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) fContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.record_bar,null);
            }
            else{
                view = convertView;
            }
            TextView text = (TextView) view.findViewById(R.id.category);
            TextView text2 = (TextView) view.findViewById(R.id.note);
            TextView text3 = (TextView) view.findViewById(R.id.amount);
            TextView text4 = (TextView) view.findViewById(R.id.type);
            ImageView iconView = (ImageView) view.findViewById(R.id.recIcon);
            text.setText(fRecordItems.get(position).getCategory());
            text2.setText(fRecordItems.get(position).getNote());
            text3.setText(String.valueOf(fRecordItems.get(position).getAmount()));
            text4.setText(fRecordItems.get(position).getAccountType());
            iconView.setImageResource(getIconFor(fRecordItems.get(position).getCategory()));

            return view;
        }

        private int getIconFor(String s){
            int icon;
            switch (s){
                case "Food":
                    icon = R.drawable.new_record;
                    break;
                default:
                    icon = 0;
            }
            return icon;
        }
    }
}
