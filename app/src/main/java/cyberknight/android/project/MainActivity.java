package cyberknight.android.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout drawerPane, navButtons[] = new LinearLayout[6];
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

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

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.navigation_bottom_button, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.home_item:
                        Log.d("daa","home");
                        Fragment fragment = new MainActivityFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                        break;
                    case R.id.summary_item:
                        Log.d("daa","summary");
                        Fragment fragment1 = new BalanceFragment();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        fragmentManager1.beginTransaction().replace(R.id.content_frame, fragment1).commit();
                        break;
                    case R.id.analysis_item:

                        break;
                }
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        bottomBar.setActiveTabColor("#C2185B");

        Fragment fragment = new MainActivityFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

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
    public boolean onOptionsItemSelected(MenuItem item) {
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
                Fragment fragment = new MainActivityFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                setTitle("Home");
                break;
            case R.id.navAccoout:
                setTitle("Accounts");
                break;
            case R.id.navAnalysis:
                setTitle("Analysis");
                break;
            case R.id.navBudget:
                setTitle("Budget");
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
