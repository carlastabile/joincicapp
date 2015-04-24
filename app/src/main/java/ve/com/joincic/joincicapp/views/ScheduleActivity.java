package ve.com.joincic.joincicapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import java.util.List;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.adapters.DemoCollectionPagerAdapter;
import ve.com.joincic.joincicapp.application.JoincicApp;
import ve.com.joincic.joincicapp.requesters.ScheduleRequester;

/**
 * This class manages everything related to the schedule of the event
 * such as the hour and place of presentations and practical works.
 *
 * Created by Carla Urrea Stabile on 02/04/15.
 */
public class ScheduleActivity extends ParentActivity {
    final static String TAG = "ScheduleActivity";
    public final static int TABS_NUMBER = 4; //the number of days
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNavigationTabs();

        ScheduleRequester requester = new ScheduleRequester(this);
        requester.execute();
    }

    public void getInformation(){

    }


    public void setNavigationTabs(){
        final ActionBar actionBar = getSupportActionBar();
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        // Add tabs, specifying the tab's text and TabListener
        String[] days = getResources().getStringArray(R.array.schedule_days);
        for (int i = 0; i < TABS_NUMBER; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(days[i])
                            .setTabListener(tabListener));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
    }

    @Override
    protected String getActivityName() {
        return getResources().getString(R.string.calendar);
    }
}
