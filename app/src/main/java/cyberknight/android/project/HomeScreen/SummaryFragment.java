package cyberknight.android.project.HomeScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import cyberknight.android.project.AccountManagement.BudgetFragment;
import cyberknight.android.project.DatabaseAndReaders.AccountDetails;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.R;

/**
 * Created by Parth on 30-06-2016.
 * CyberKnight apps
 */
public class SummaryFragment extends Fragment {

    private DbHelper database;
    private ArrayList<AccountDetails> allRecords;
    private String date;

    public SummaryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        TextView budget = (TextView) view.findViewById(R.id.summary_budget);
        TextView expenseB = (TextView) view.findViewById(R.id.summary_expense_b);
        TextView income = (TextView) view.findViewById(R.id.summary_income);
        TextView expenseC = (TextView) view.findViewById(R.id.summary_expense_c);
        TextView balance1 = (TextView) view.findViewById(R.id.summary_balance_1);
        TextView balance2 = (TextView) view.findViewById(R.id.summary_balance_2);
        LinearLayout bal1 = (LinearLayout) view.findViewById(R.id.summary_balance_b);
        LinearLayout bal2 = (LinearLayout) view.findViewById(R.id.summary_balance_c);

        SharedPreferences sharedPreferences = MainActivity.applicationContext.getSharedPreferences(BudgetFragment.BUDGET_PREFRENCES_FILE, Context.MODE_PRIVATE);
        budget.setText(String.valueOf((double)Math.round((sharedPreferences.getFloat("budget",0f)/30)*100d)/100d));

        database = new DbHelper(MainActivity.applicationContext);
        allRecords = database.getAllAccountDetailsByDate(date);
        double totalExpense = 0;
        for(int i=0; i<allRecords.size(); i++){
            totalExpense += allRecords.get(i).getAmount();
        }
        totalExpense = (double) Math.round(totalExpense*100d)/100d;
        expenseB.setText(totalExpense+"");
        expenseC.setText(totalExpense+"");

        double balance_1 = (double)Math.round((sharedPreferences.getFloat("budget",0f)/30)*100d)/100d - totalExpense;
        double balance_2 = 0 - totalExpense;

        if(balance_1>0) bal1.setBackgroundColor(getResources().getColor(R.color.green_400));
        else if(balance_1<0) bal1.setBackgroundColor(getResources().getColor(R.color.red_400));
        else bal1.setBackgroundColor(getResources().getColor(R.color.orange_400));

        if(balance_2>0) bal2.setBackgroundColor(getResources().getColor(R.color.green_400));
        else if(balance_2<0) bal2.setBackgroundColor(getResources().getColor(R.color.red_400));
        else bal2.setBackgroundColor(getResources().getColor(R.color.orange_400));

        balance1.setText((double) Math.round(balance_1*100d)/100d+"");
        balance2.setText((double) Math.round(balance_2*100d)/100d+"");

        return view;
    }

    public void setDate(String date) {
        this.date = date;
    }
}