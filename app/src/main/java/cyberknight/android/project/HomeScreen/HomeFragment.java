package cyberknight.android.project.HomeScreen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cyberknight.android.project.DatabaseAndReaders.RecordDetails;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.R;

/**
 * Created by Parth on 29-06-2016.
 * CyberKnight apps
 */
public class HomeFragment extends Fragment implements RecordScreenUpdater{

    private ListView records;
    private DbHelper database;
    private ArrayList<RecordDetails> allRecords;
    private String currentDate;
    private Activity parentActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
    }

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        records = (ListView) view.findViewById(R.id.recordList);
        database = new DbHelper(MainActivity.applicationContext);

        Log.d("current Date","------------------"+currentDate);
        allRecords = database.getAllAccountDetailsByDate(currentDate);
        if(!(allRecords.size()==0)) allRecords.add(allRecords.size(),new RecordDetails());
        else allRecords.add(new RecordDetails("","No Entries today..","","",-1,""));

        RecordAdapter adapter = new RecordAdapter(getContext(),allRecords);
        records.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabNewRecord);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.d("HOME FRAGMENT GESTURES", "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 10;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 0;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                currentDate = changeDate(currentDate, 1);
                                ((RecordScreenUpdater)parentActivity).setDateTo(currentDate);
                                updateScreenRecords();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                currentDate = changeDate(currentDate, -1);
                                ((RecordScreenUpdater)parentActivity).setDateTo(currentDate);
                                updateScreenRecords();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        records.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(position!=allRecords.size()-1) {
                                    DialogFragment showRecord = RecordDialogFragment.newInstance(0);
                                    Bundle temp = new Bundle();
                                    temp.putInt("position", position);
                                    temp.putString("date", currentDate);
                                    showRecord.setTargetFragment(getThisFragment(),0);
                                    showRecord.setArguments(temp);
                                    showRecord.setCancelable(false);
                                    showRecord.show(getFragmentManager(), "RecordFromHome");
                                }
                            }
                        });
                        return super.onSingleTapUp(e);
                    }
                });

        view.bringToFront();
        view.invalidate();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });



        records.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        return view;
    }

    void showDialog() {
        DialogFragment newRecord = NewRecord.newInstance(0);
        Bundle temp = new Bundle();
        temp.putString("CurrentDate",currentDate);
        temp.putString("Mode","new");
        newRecord.setArguments(temp);
        newRecord.setCancelable(false);
        newRecord.setTargetFragment(this,0);
        newRecord.show(getFragmentManager(),"tag");
    }

    @Override
    public void updateScreenRecords() {
        allRecords = database.getAllAccountDetailsByDate(currentDate);
        Log.d("Update Records","records"+allRecords+"-------");
        if(!(allRecords.size()==0)) allRecords.add(allRecords.size(),new RecordDetails());
        else allRecords.add(new RecordDetails("","No Entries today..","","",-1,""));
        RecordAdapter adapter = new RecordAdapter(getContext(),allRecords);
        records.setAdapter(adapter);
    }

    @Override
    public void setDateTo(String date) {
        currentDate = date;
    }

    public Fragment getThisFragment(){ return this; }

    public String changeDate(String date, int numToAdd){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, numToAdd);
        date = sdf.format(c.getTime());
        return date;
    }

    class RecordAdapter extends BaseAdapter {

        Context fContext;
        ArrayList<RecordDetails> fRecordItems;

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
            text3.setText((fRecordItems.get(position).getCategory().equals("No Entries today..")||fRecordItems.get(position).getCategory().equals(""))?
                    "":String.valueOf(fRecordItems.get(position).getAmount()));
            String temp = fRecordItems.get(position).getTransaction();
            if(temp!=null && temp.equals("Income"))    text3.setTextColor(getResources().getColor(R.color.green_400));
            else text3.setTextColor(getResources().getColor(R.color.red_400));
            text4.setText(fRecordItems.get(position).getAccountType());
            iconView.setImageResource(getIconFor(fRecordItems.get(position).getCategory()));

            return view;
        }
    }

    public static int getIconFor(String s){
        int icon;
        switch (s){
            case "Food":
                icon = R.drawable.item_food;
                break;
            case "Entertainment":
                icon = R.drawable.item_entertainment;
                break;
            case "Travel":
                icon = R.drawable.item_travel;
                break;
            case "Education":
                icon = R.drawable.item_education;
                break;
            case "Clothing/Beauty":
                icon = R.drawable.item_clothing;
                break;
            case "Social":
                icon = R.drawable.item_social;
                break;
            case "Medical":
                icon = R.drawable.item_medical;
                break;
            case "Salary":
                icon = R.drawable.item_salary;
                break;
            case "Pocket Money":
                icon = R.drawable.item_pockey_money;
                break;
            case "Part-Time job":
                icon = R.drawable.item_part_time_job;
                break;
            case "Other Expense":
                icon = R.drawable.item_other_expense;
                break;
            case "Other Income":
                icon = R.drawable.item_other_income;
                break;
            default:
                icon = 0;
        }
        return icon;
    }
}
