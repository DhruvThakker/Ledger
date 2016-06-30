package cyberknight.android.project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException npe){
            Log.d("MainActivity","support action bar failed");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                Fragment fragment = new MainActivityFragments();
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
