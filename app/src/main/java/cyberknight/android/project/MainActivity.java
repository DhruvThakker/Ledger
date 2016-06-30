package cyberknight.android.project;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView drawerList;
    RelativeLayout drawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<ListItem> mListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListItems.add(new ListItem("Home",0));
        mListItems.add(new ListItem("Accounts",0));
        mListItems.add(new ListItem("Analysis",0));
        mListItems.add(new ListItem("Budget",0));
        mListItems.add(new ListItem("Settings",0));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navDrawer);

        drawerList = (ListView) findViewById(R.id.navList);
        drawerPane = (RelativeLayout) findViewById(R.id.drawerPane);

        DrawerListAdapter adapter = new DrawerListAdapter(this,mListItems);
        drawerList.setAdapter(adapter);

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

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawers();
                selectItemFromDrawer(position);
            }
        });

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

    private void selectItemFromDrawer(int position){
        if(position==0) {
            Fragment fragment = new MainActivityFragments();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
        setTitle(mListItems.get(position).title);
    }

    class ListItem{
        int icon;
        String title;

        public ListItem(String title, int icon){
            this.icon = icon;
            this.title = title;
        }
    }

    class DrawerListAdapter extends BaseAdapter{

        Context mContext;
        ArrayList<ListItem> mListItems;

        public DrawerListAdapter(Context mContext, ArrayList mListItems){
            this.mContext = mContext;
            this.mListItems = mListItems;
        }

        @Override
        public int getCount() {
            return mListItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mListItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item,null);
            }
            else{
                view = convertView;
            }
            TextView titleView = (TextView) view.findViewById(R.id.title);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mListItems.get(position).title );
            iconView.setImageResource(mListItems.get(position).icon);

            return view;
        }
    }
}
