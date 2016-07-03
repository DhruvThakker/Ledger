package cyberknight.android.project.AccountManagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import cyberknight.android.project.DatabaseAndReaders.AccountDetails;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.DatabaseAndReaders.JsonReader;
import cyberknight.android.project.DatabaseAndReaders.MyColorTemplate;
import cyberknight.android.project.HomeScreen.MainActivity;
import cyberknight.android.project.R;

/**
 * Created by umang on 3/7/16.
 */
public class PieChartFragment extends Fragment {


    private ArrayList<String> categories = new ArrayList<>();
    private JsonReader jsonReader = new JsonReader(MainActivity.applicationContext);
    private PieChart pieChart;
    private ArrayList<AccountDetails> mfoodRecords=new ArrayList<>();
    private String current;
    private String reportOf;
    private double income = 0;
    private double expence = 0;
    private LinearLayout layoutincome,layoutexpense;
    private TextView textViewincome,textViewexpense;

    public void setReportOf(String reportOf) {
        this.reportOf = reportOf;
    }

    private ArrayList<Entry> entries = new ArrayList<>();
    DbHelper dbHelper;

    public PieChartFragment() {
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        layoutincome = (LinearLayout) view.findViewById(R.id.chart_income);
        layoutexpense = (LinearLayout) view.findViewById(R.id.chart_expense);
        textViewincome = (TextView) view.findViewById(R.id.chart_income_text);
        textViewexpense = (TextView) view.findViewById(R.id.chart_expense_text);

        categories = jsonReader.getExpenseCategories();
        dbHelper=new DbHelper(MainActivity.applicationContext);

        if(reportOf.equals("Date")){
            mfoodRecords = dbHelper.getAllAccountDetailsByDate(current);
        }
        else if(reportOf.equals("Month")){
            mfoodRecords = dbHelper.getAllAccountDetailsByMonth(current.substring(0,4),current.substring(5,7));
        }

        for (int i=0;i<categories.size();i++){
            double temp = getAmountOfCategory(categories.get(i));
            if(temp==0){
                categories.remove(i);
                i--;
            }
            else{
                entries.add(new Entry((float)temp,i));
            }
            expence+=temp;
        }

        for(int i=0; i<mfoodRecords.size(); i++){
            if(mfoodRecords.get(i).getBalenceType().equals("Income"))   income += mfoodRecords.get(i).getAmount();
        }

        Log.d("No Of Category",categories.size()+"");

        PieDataSet dataset = new PieDataSet(entries, "Categories");
        PieData data = new PieData(categories, dataset); // initialize Piedata
        pieChart.setData(data); //set data into chart
        pieChart.setDescription("");  // set the description
        dataset.setColors(MyColorTemplate.COLORFUL_COLORS); // set the color
        pieChart.animateY(1000);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText(current);

        textViewincome.setText(income+"");
        textViewexpense.setText(expence+"");

        if (income>0) layoutincome.setBackgroundColor(getResources().getColor(R.color.green_400));
        else layoutincome.setBackgroundColor(getResources().getColor(R.color.orange_400));

        if (expence>0) layoutexpense.setBackgroundColor(getResources().getColor(R.color.red_400));
        else layoutexpense.setBackgroundColor(getResources().getColor(R.color.orange_400));

        return view;
    }

    public double getAmountOfCategory(String category){
        double amount = 0;

        for(int i=0;i<mfoodRecords.size();i++){
            if(mfoodRecords.get(i).getCategory().equals(category) && mfoodRecords.get(i).getBalenceType().equals("Expense")){
                amount+=mfoodRecords.get(i).getAmount();
            }
        }
        return  amount;
    }

}
