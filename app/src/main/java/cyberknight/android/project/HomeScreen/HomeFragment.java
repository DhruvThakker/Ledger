package cyberknight.android.project.HomeScreen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

import cyberknight.android.project.R;

/**
 * Created by Parth on 29-06-2016.
 * CyberKnight apps
 */
public class HomeFragment extends Fragment{

    private DialogFragment newRecord;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<SubHomeFragment> list = new ArrayList<>();
    private TextView pageDate;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPagerList);
        pageDate = (TextView) view.findViewById(R.id.pageDate);

        for(int i=0; i<5; i++){
            list.add(new SubHomeFragment());
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),mViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(2);
        pageDate.setText(list.get(mViewPager.getCurrentItem()).getPageDate().toString());
        modifyList(2);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabNewRecord);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mViewPager.getCurrentItem());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),mViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    void showDialog(int currentPage) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        // Create and show the dialog.
        newRecord = NewRecord.newInstance(0);
        newRecord.setCancelable(false);
        newRecord.setTargetFragment(list.get(currentPage),0);
        newRecord.show(getFragmentManager(),"tag");
    }

    public void modifyList(int currFragment){
        Date pageDate = list.get(currFragment).getPageDate();
        if(currFragment==1){
            list.get(2).setPageDate(new Date(pageDate.getTime()+24*60*60*1000));
            list.get(3).setPageDate(new Date(pageDate.getTime()-24*60*60*1000));
        }
        else if(currFragment==2){
            list.get(3).setPageDate(new Date(pageDate.getTime()+24*60*60*1000));
            list.get(1).setPageDate(new Date(pageDate.getTime()-24*60*60*1000));
        }
        else if(currFragment==3){
            list.get(1).setPageDate(new Date(pageDate.getTime()+24*60*60*1000));
            list.get(2).setPageDate(new Date(pageDate.getTime()-24*60*60*1000));
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm,final ViewPager mViewPager) {
            super(fm);

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            mViewPager.setCurrentItem(3,false);
                            break;
                        case 1:
                            modifyList(1);
                            pageDate.setText(list.get(1).getPageDate().toString());
                            break;
                        case 2:
                            modifyList(2);
                            pageDate.setText(list.get(2).getPageDate().toString());
                            break;
                        case 3:
                            modifyList(3);
                            pageDate.setText(list.get(3).getPageDate().toString());
                            break;
                        case 4:
                            mViewPager.setCurrentItem(1,false);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return list.get(position);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
