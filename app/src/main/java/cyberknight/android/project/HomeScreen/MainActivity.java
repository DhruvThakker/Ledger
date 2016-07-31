package cyberknight.android.project.HomeScreen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cyberknight.android.project.AccountManagement.AboutUsActivity;
import cyberknight.android.project.AccountManagement.AnalysisActivity;
import cyberknight.android.project.AccountManagement.SettingsActivity;
import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.AccountManagement.AccountActivity;
import cyberknight.android.project.DatabaseAndReaders.JsonReader;
import cyberknight.android.project.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecordScreenUpdater{

    public static Context applicationContext;
    private LinearLayout drawerPane, navButtons[] = new LinearLayout[6], bottomBarHome, bottomBarSummary, bottomBarAnalysis;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment home;
    private SummaryFragment summary;
    private AnalysisFragment analysis;
    private TextView pageDate;
    private String currentDate;
    private Fragment currentLoadedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navDrawer);
        drawerPane = (LinearLayout) findViewById(R.id.drawerPane);
        pageDate = (TextView) findViewById(R.id.pageDate);

        navButtons[0] = (LinearLayout) findViewById(R.id.navHome);
        navButtons[1] = (LinearLayout) findViewById(R.id.navAccoout);
        navButtons[2] = (LinearLayout) findViewById(R.id.navAnalysis);
        navButtons[3] = (LinearLayout) findViewById(R.id.navBudget);
        navButtons[4] = (LinearLayout) findViewById(R.id.navSettings);
        navButtons[5] = (LinearLayout) findViewById(R.id.navAboutUs);

        ImageView prevDate = (ImageView) findViewById(R.id.previousDate);
        ImageView nextDate = (ImageView) findViewById(R.id.nextDate);

        bottomBarHome = (LinearLayout) findViewById(R.id.bottom_bar_home);
        bottomBarSummary = (LinearLayout) findViewById(R.id.bottom_bar_summary);
        bottomBarAnalysis = (LinearLayout) findViewById(R.id.bottom_bar_analysis);

        applicationContext = getApplicationContext();
        DbHelper database = new DbHelper(getApplicationContext());

        try {
            ArrayList<String> accountNames;
            JsonReader tmp = new JsonReader(applicationContext);
            accountNames = tmp.getAccountsNames();

            for (int i = 0; i < accountNames.size(); i++) {
                database.addInitialAmount(i, accountNames.get(i), 0);
            }
        }catch (Error e){
            Log.d("MainActivity","Records Not Added");
        }

        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        pageDate.setText(currentDate);
        home = new HomeFragment();
        summary = new SummaryFragment();
        analysis = new AnalysisFragment();

        home.setDateTo(currentDate);
        currentLoadedFragment = home;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, home).commit();

        for(int i=0; i<6; i++) navButtons[i].setOnClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        bottomBarHome.setOnClickListener(this);
        bottomBarSummary.setOnClickListener(this);
        bottomBarAnalysis.setOnClickListener(this);
        prevDate.setOnClickListener(this);
        nextDate.setOnClickListener(this);
        pageDate.setOnClickListener(this);

        /*drawerPane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        try {
            getSupportActionBar().setElevation(2);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException npe){
            Log.d("MainActivity","support action bar failed");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calendar) {

            DialogFragment calendarFragment = new CalendarFragment();
            calendarFragment.setTargetFragment(currentLoadedFragment,0);
            calendarFragment.show(fragmentManager,"Calendar Fragment");

            return true;
        }else if (id == R.id.action_aboutUs) {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            return true;
        }
        else if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

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

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(v.getId()){
            case R.id.bottom_bar_home:
                bottomBarHome.setAlpha(1f);
                bottomBarSummary.setAlpha(0.5f);
                bottomBarAnalysis.setAlpha(0.5f);
                home.setDateTo(currentDate);
                currentLoadedFragment = home;
                fragmentManager.beginTransaction().replace(R.id.content_frame, home).commit();
                break;
            case R.id.bottom_bar_summary:
                bottomBarHome.setAlpha(0.5f);
                bottomBarSummary.setAlpha(1f);
                bottomBarAnalysis.setAlpha(0.5f);
                summary.setDateTo(currentDate);
                currentLoadedFragment = summary;
                fragmentManager.beginTransaction().replace(R.id.content_frame, summary).commit();
                break;
            case R.id.bottom_bar_analysis:
                bottomBarHome.setAlpha(0.5f);
                bottomBarSummary.setAlpha(0.5f);
                bottomBarAnalysis.setAlpha(1f);
                analysis.setDateTo(currentDate);
                currentLoadedFragment = analysis;
                fragmentManager.beginTransaction().replace(R.id.content_frame, analysis).commit();
                break;
            case R.id.navHome:
                home.setDateTo(currentDate);
                currentLoadedFragment = home;
                fragmentManager.beginTransaction().replace(R.id.content_frame, home).commit();
                setTitle("Home");
                break;
            case R.id.navAccoout:
                Intent i = new Intent(MainActivity.this,AccountActivity.class);
                i.putExtra("Fragment","Accounts");
                startActivity(i);
                break;
            case R.id.navAnalysis:
                i = new Intent(MainActivity.this,AnalysisActivity.class);
                startActivity(i);
                break;
            case R.id.navBudget:
                i = new Intent(MainActivity.this,AccountActivity.class);
                i.putExtra("Fragment","Budget");
                startActivity(i);
                break;
            case R.id.navSettings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.navAboutUs:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                break;
            case R.id.pageDate:
                currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                pageDate.setText(currentDate);
                ((RecordScreenUpdater)currentLoadedFragment).setDateTo(currentDate);
                ((RecordScreenUpdater)currentLoadedFragment).updateScreenRecords();
                break;
            case R.id.nextDate:
                currentDate = changeDate(currentDate, 1);
                pageDate.setText(currentDate);
                ((RecordScreenUpdater)currentLoadedFragment).setDateTo(currentDate);
                ((RecordScreenUpdater)currentLoadedFragment).updateScreenRecords();
                break;
            case R.id.previousDate:
                currentDate = changeDate(currentDate, -1);
                pageDate.setText(currentDate);
                ((RecordScreenUpdater)currentLoadedFragment).setDateTo(currentDate);
                ((RecordScreenUpdater)currentLoadedFragment).updateScreenRecords();
                break;
            default:
        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void updateScreenRecords() {
    }

    @Override
    public void setDateTo(String date) {
        currentDate = date;
        pageDate.setText(currentDate);
    }

    @Override
    public void finish() {
        getSharedPreferences(SettingsActivity.SETTINGS_FILE,Context.MODE_PRIVATE).edit().putBoolean("firstCheck",true).commit();
        super.finish();
    }

    @Override
    public void onDestroy() {
        getSharedPreferences(SettingsActivity.SETTINGS_FILE,Context.MODE_PRIVATE).edit().putBoolean("firstCheck",true).commit();
        super.onDestroy();
    }
}