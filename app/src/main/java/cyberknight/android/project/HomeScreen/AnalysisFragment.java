package cyberknight.android.project.HomeScreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;


import cyberknight.android.project.DatabaseAndReaders.MyColorTemplate;
import cyberknight.android.project.DatabaseAndReaders.AccountDetails;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.DatabaseAndReaders.JsonReader;
import cyberknight.android.project.R;

/**
 * Created by Parth on 02-07-2016.
 * CyberKnight apps
 */
public class AnalysisFragment extends Fragment {

    private ArrayList<String> categories = new ArrayList<>();
    private JsonReader jsonReader = new JsonReader(MainActivity.applicationContext);
//    private ArrayList<String> accountTypes = new ArrayList<>();

    private PieChart pieChart;
    private ArrayList<AccountDetails> foodRecords=new ArrayList<>();
    private TextView page_date;
    private ArrayList<Entry> entries = new ArrayList<>();
    private double income = 0;
    private double expence = 0;

    DbHelper dbHelper;
    private String date;

    public AnalysisFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        categories = jsonReader.getExpenseCategories();
        dbHelper=new DbHelper(MainActivity.applicationContext);
        page_date = (TextView) view.findViewById(R.id.analysis_pagedate);
        pieChart = (PieChart) view.findViewById(R.id.chart);

        page_date.setText(date);

        foodRecords = dbHelper.getAllAccountDetailsByDate(date);

        for (int i=0;i<categories.size();i++){
            double temp = getAmountOfCategory(categories.get(i));
            if(temp==0){
                categories.remove(i);
                i--;
            }
            else {
                entries.add(new Entry((float)temp,i));
            }
            expence+=temp;
        }


        for(int i=0; i<foodRecords.size(); i++){
            if(foodRecords.get(i).getTransaction().equals("Income"))   income += foodRecords.get(i).getAmount();
        }

        PieDataSet dataset = new PieDataSet(entries, "Categories");
        // creating labels

        Legend l=pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(12f);
        l.setYEntrySpace(12f);
        l.setYOffset(0f);
        PieData data = new PieData(categories, dataset); // initialize Piedata
        pieChart.setData(data); //set data into chart
        //data.setValueFormatter(new PercentFormatter());
        //data.setValueTextSize(11f);
        pieChart.setDescription("");  // set the description

        dataset.setColors(MyColorTemplate.COLORFUL_COLORS); // set the color
        pieChart.animateY(600);

        pieChart.setDrawHoleEnabled(true);
        //pieChart.setHoleRadius(4f);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("Income: "+income+"\n"+"Expense: "+expence);
        return view;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public double getAmountOfCategory(String category){
        double amount = 0;

        for(int i=0;i<foodRecords.size();i++){
            if(foodRecords.get(i).getCategory().equals(category) && foodRecords.get(i).getTransaction().equals("Expense")){
                amount+=foodRecords.get(i).getAmount();
            }
        }
        return  amount;
    }

}
