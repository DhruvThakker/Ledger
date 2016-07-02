package cyberknight.android.project.HomeScreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import cyberknight.android.project.R;

/**
 * Created by Parth on 02-07-2016.
 * CyberKnight apps
 */
public class AnalysisFragment extends Fragment {

    public AnalysisFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);


        PieChart pieChart = (PieChart) view.findViewById(R.id.chart);
        // creating data values
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        PieDataSet dataset = new PieDataSet(entries, "# of Calls");
        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        PieData data = new PieData(labels, dataset); // initialize Piedata
        pieChart.setData(data); //set data into chart

        dataset.setColors(ColorTemplate.COLORFUL_COLORS); // set the color
        pieChart.animateY(5000);
        pieChart.setCenterTextSize(14);

        pieChart.setDrawHoleEnabled(true);
        //pieChart.setHoleRadius(4f);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("Hello World");

        return view;
    }
}
