package cyberknight.android.project.AccountManagement;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cyberknight.android.project.R;

public class AccountAnalysisActivity extends AppCompatActivity {

    private DrawerAnalysisFragment drawerAnalysisFragment;
    private PieChartFragment chartReport;
    private Button report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_analysis);

        drawerAnalysisFragment = new DrawerAnalysisFragment();
        chartReport = new PieChartFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.analysis_fragment,drawerAnalysisFragment).commit();

        report = (Button) findViewById(R.id.generateReport);
        Log.d("Out","onCreate");

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("In","onClick");
                String date = drawerAnalysisFragment.getDate();
                if(date.length()==10){

                    chartReport.setCurrent(date);
                    chartReport.setReportOf("Date");
                    Log.d("Date: ",date);
                    FragmentManager fragmentManager = getSupportFragmentManager() ;
                    fragmentManager.beginTransaction().replace(R.id.analysis_fragment,chartReport).commit();
                }
                else{

                    chartReport.setCurrent(date);
                    chartReport.setReportOf("Month");
                    Log.d("Date: ",date);
                    FragmentManager fragmentManager = getSupportFragmentManager() ;
                    fragmentManager.beginTransaction().replace(R.id.analysis_fragment,chartReport).commit();

                }
                report.setVisibility(View.GONE);
            }
        });
    }
}
