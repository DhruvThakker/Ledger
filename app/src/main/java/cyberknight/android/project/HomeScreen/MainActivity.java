package cyberknight.android.project.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.AccountManagement.AccountManagementActivity;
import cyberknight.android.project.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Context applicationContext;
    private LinearLayout drawerPane, navButtons[] = new LinearLayout[6];
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navDrawer);

        drawerPane = (LinearLayout) findViewById(R.id.drawerPane);

        navButtons[0] = (LinearLayout) findViewById(R.id.navHome);
        navButtons[1] = (LinearLayout) findViewById(R.id.navAccoout);
        navButtons[2] = (LinearLayout) findViewById(R.id.navAnalysis);
        navButtons[3] = (LinearLayout) findViewById(R.id.navBudget);
        navButtons[4] = (LinearLayout) findViewById(R.id.navSettings);
        navButtons[5] = (LinearLayout) findViewById(R.id.navAboutUs);

        applicationContext = getApplicationContext();
        DbHelper database = new DbHelper(getApplicationContext());

        home = new HomeFragment();
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.navigation_bottom_button, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.home_item:
                        Log.d("daa","home");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, home).commit();
                        break;
                    case R.id.summary_item:
                        Log.d("daa","summary");
                        Fragment fragment = new SummaryFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                        break;
                    case R.id.analysis_item:
                        fragment = new AnalysisFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                        break;
                }
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        bottomBar.setActiveTabColor("#C2185B");

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

        try {
            getSupportActionBar().setElevation(3);
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
            calendarFragment.setTargetFragment(home,0);
            calendarFragment.show(fragmentManager,"Calendar Fragment");

            return true;
        }

        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.navHome:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, home).commit();
                setTitle("Home");
                break;
            case R.id.navAccoout:
                Intent i = new Intent(MainActivity.this,AccountManagementActivity.class);
                i.putExtra("Fragment","Accounts");
                startActivity(i);
                break;
            case R.id.navAnalysis:
                i = new Intent(MainActivity.this,AccountManagementActivity.class);
                i.putExtra("Fragment","Analysis");
                startActivity(i);
                break;
            case R.id.navBudget:
                i = new Intent(MainActivity.this,AccountManagementActivity.class);
                i.putExtra("Fragment","Budget");
                startActivity(i);
                break;
            case R.id.navSettings:
                setTitle("Settings");
                break;
            case R.id.navAboutUs:
                setTitle("About Us");
                break;
            default:
        }
        mDrawerLayout.closeDrawers();
    }
}