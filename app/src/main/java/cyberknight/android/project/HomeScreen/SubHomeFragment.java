package cyberknight.android.project.HomeScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cyberknight.android.project.DatabaseAndReaders.AccountDetails;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.R;

/**
 * Created by Parth on 02-07-2016.
 * CyberKnight apps
 */
public class SubHomeFragment extends Fragment  implements RecordScreenUpdater {

    private ListView records;
    private DbHelper database;
    private ArrayList<AccountDetails> allRecords;
    private Date pageDate;

    public SubHomeFragment(){
        Calendar currentTime = Calendar.getInstance();
        pageDate = new Date((currentTime.getTime()).getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sub_home, container, false);
        records = (ListView) view.findViewById(R.id.recordList);
        database = new DbHelper(MainActivity.applicationContext);

        allRecords = database.getAllAccountDetailsByDate(getPageDate());
        allRecords.add(allRecords.size(),new AccountDetails());
        RecordAdapter adapter = new RecordAdapter(getContext(),allRecords);
        records.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateScreenRecords(true);
    }

    @Override
    public void updateScreenRecords(boolean added) {
        if(added){
            allRecords = database.getAllAccountDetailsByDate(new Date(getPageDate().getYear(),getPageDate().getMonth(),getPageDate().getDate()));
            Log.d("Update Records","records"+allRecords+"-------"+getPageDate());
            allRecords.add(allRecords.size(),new AccountDetails());
            RecordAdapter adapter = new RecordAdapter(getContext(),allRecords);
            records.setAdapter(adapter);
        }
        else{

        }
    }

    public Date getPageDate() {
        return pageDate;
    }

    public void setPageDate(Date pageDate) {
        this.pageDate = pageDate;
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
