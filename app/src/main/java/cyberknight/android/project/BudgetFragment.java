package cyberknight.android.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by umang on 1/7/16.
 */
public class BudgetFragment extends Fragment {

    TextView textdaily,textweekly,textyearly;
    EditText edittextmonthly;
    Button okbutton;

    public BudgetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        edittextmonthly = (EditText) view.findViewById(R.id.edit_monthlybudget);
        textweekly = (TextView) view.findViewById(R.id.tvWeeklyBudget);
        textdaily = (TextView) view.findViewById(R.id.tvDailyBudget);
        textyearly = (TextView) view.findViewById(R.id.tvYearlyBudget);
        okbutton = (Button) view.findViewById(R.id.budone);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String budget = edittextmonthly.getText().toString();
                    double dbudget = Double.parseDouble(budget);
                    textweekly.setText(dbudget / 4 + "");
                    textdaily.setText(dbudget / 30 + "");
                    textyearly.setText(dbudget * 12 + "");
                }catch(Exception e){
                    Toast.makeText(getContext(),"Enter some values",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
