package cyberknight.android.project.HomeScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

import cyberknight.android.project.DatabaseAndReaders.AccountDetails;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.R;

/**
 * Created by Parth on 29-06-2016.
 * CyberKnight apps
 */
public class HomeFragment extends Fragment implements RecordScreenUpdater{

    private DialogFragment newRecord;
    private TextView pageDate;
    private ListView records;
    private DbHelper database;
    private ArrayList<AccountDetails> allRecords;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pageDate = (TextView) view.findViewById(R.id.pageDate);

        records = (ListView) view.findViewById(R.id.recordList);
        database = new DbHelper(MainActivity.applicationContext);

        allRecords = database.getAllAccountDetailsByDate("2016-07-02");
        allRecords.add(allRecords.size(),new AccountDetails());
        RecordAdapter adapter = new RecordAdapter(getContext(),allRecords);
        records.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabNewRecord);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        // Create and show the dialog.
        newRecord = NewRecord.newInstance(0);
        newRecord.setCancelable(false);
        newRecord.setTargetFragment(this,0);
        newRecord.show(getFragmentManager(),"tag");
    }

    @Override
    public void updateScreenRecords(boolean added) {
        if(added){
            allRecords = database.getAllAccountDetailsByDate("2016-07-02");
            Log.d("Update Records","records"+allRecords+"-------");
            allRecords.add(allRecords.size(),new AccountDetails());
            RecordAdapter adapter = new RecordAdapter(getContext(),allRecords);
            records.setAdapter(adapter);
        }
        else{

        }
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
            text3.setText(fRecordItems.get(position).getCategory().equals("")?"":String.valueOf(fRecordItems.get(position).getAmount()));
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
