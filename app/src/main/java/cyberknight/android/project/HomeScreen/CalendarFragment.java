package cyberknight.android.project.HomeScreen;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.Toast;

import cyberknight.android.project.R;

/**
 * Created by umang on 1/7/16.
 */
public class CalendarFragment extends DialogFragment {

    CalendarView calendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar,container,false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        calendarView = (CalendarView) v.findViewById(R.id.calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                RecordScreenUpdater recordScreenUpdater = (RecordScreenUpdater) getTargetFragment();
                recordScreenUpdater.setDateTo(NewRecord.getSringFormatForDate(year,month+1,dayOfMonth));
                getDialog().dismiss();
            }
        });

        return v;
    }
}
