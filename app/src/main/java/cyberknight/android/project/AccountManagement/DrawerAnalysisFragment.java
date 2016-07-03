package cyberknight.android.project.AccountManagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cyberknight.android.project.HomeScreen.NewRecord;
import cyberknight.android.project.R;

/**
 * Created by umang on 3/7/16.
 */
public class DrawerAnalysisFragment extends Fragment {

    private RadioButton optionDate,optionMonth;
    private RadioGroup radioOptions;
    private int checked;
    private DatePicker datePicker;
    private String date;
    private Button report;

    public String getDate() {
        return date;
    }

    static DrawerAnalysisFragment newInstance(int num) {
        DrawerAnalysisFragment f = new DrawerAnalysisFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    public DrawerAnalysisFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_drawer_analysis, container, false);

        radioOptions = (RadioGroup) view.findViewById(R.id.rgOptions);
        optionDate = (RadioButton) view.findViewById(R.id.rbByDate);
        optionMonth = (RadioButton) view.findViewById(R.id.rbByMonth);
        datePicker = (DatePicker) view.findViewById(R.id.anlysisDatePicker);
        report = (Button) view.findViewById(R.id.generateReport);

        optionDate.setChecked(true);
        date = NewRecord.getSringFormatForDate(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        radioOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId==R.id.rbByDate){
                    date = NewRecord.getSringFormatForDate(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
                    Log.d("Date: ",date);
                }
                else if (checkedId==R.id.rbByMonth) {
                    date = NewRecord.getSringFormatForDate(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()).substring(0, 7);
                    Log.d("Date: ", date);
                }
            }
        });


        return view;
    }


}
