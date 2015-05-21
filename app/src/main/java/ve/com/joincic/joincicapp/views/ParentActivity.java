package ve.com.joincic.joincicapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.adapters.DrawerItem;
import ve.com.joincic.joincicapp.adapters.DrawerItemAdapter;
import ve.com.joincic.joincicapp.application.JoincicApp;
import ve.com.joincic.joincicapp.requesters.ScheduleRequester;

/**
 * This class is the parent of all the classes of the JOINCIC App.
 * <p/>
 * Created by Carla Urrea Stabile on 3/23/15.
 */
public abstract class ParentActivity extends ActionBarActivity {
    public final static String TAG = "ParentActivity";
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    protected abstract int getLayoutId();
    protected abstract String getActivityName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        setActivityName(getActivityName());

        setNavigationDrawer();

    }

    public void setActivityName(String name){
        getSupportActionBar().setTitle(name);
    }

    public void setNavigationDrawer() {

        getSupportActionBar().setIcon(
                new ColorDrawable(getResources().getColor(
                        android.R.color.transparent)));

        int actionBarTitleId = Resources.getSystem().getIdentifier(
                "action_bar_title", "id", "android");
        if (actionBarTitleId > 0) {
            TextView title = (TextView) findViewById(actionBarTitleId);
            if (title != null) {
                title.setTextColor(Color.WHITE);
            }
        }

        mPlanetTitles = getResources().getStringArray(
                R.array.menu_items);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        ArrayList<Integer> iconIds = getIconIds();

//        items.add(new DrawerItem(getResources().getString(R.string.app_name),
//                R.drawable.ic_joincic_logo));

        int size = mPlanetTitles.length;
        for (int i = 0; i < size; i++) {
            items.add(new DrawerItem(mPlanetTitles[i], iconIds.get(i)));
        }

        DrawerItemAdapter adapter = new DrawerItemAdapter(this, items);

        mDrawerList.setAdapter(adapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */

            public void onDrawerClosed(View view) {
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */

            public void onDrawerOpened(View drawerView) {
            }
        };

        mDrawerToggle.syncState();

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Takes the user to the corresponding activity depending on the option they selected
     * in the menu
     * */
    private void selectItem(int position) {

        Resources res = getResources();
        SharedPreferences prefs = getSharedPreferences(JoincicApp.REQUEST_PREFS, MODE_PRIVATE);

        if (mPlanetTitles[position] == null) {
            return;
        } else if (mPlanetTitles[position].equals(res.getString(R.string.about_us))){ //About us
            Intent i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (mPlanetTitles[position].equals(res.getString(R.string.calendar))){ //Calendar

            boolean savedData = prefs.getBoolean(JoincicApp.SCHEDULE_REQUEST, false);

            if (!isNetworkAvailable()) {
                if (!savedData) {
                    Toast.makeText(this,
                            getResources().getString(R.string.no_connection),
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(this, ScheduleActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }
            } else {
                ScheduleRequester requester = new ScheduleRequester(this);
                requester.execute();
            }

        } else if (mPlanetTitles[position].equals(res.getString(R.string.map))){ //Map

            if (!isNetworkAvailable()) {
                Toast.makeText(this,
                            getResources().getString(R.string.no_connection),
                            Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(this, MapActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }

        } else if (mPlanetTitles[position].equals(res.getString(R.string.pratical_work))){ //Practical Work

        } else if (mPlanetTitles[position].equals(res.getString(R.string.questions))){ //Questions

        } else {
            return;
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    /**
     * Gets the id of the icon for the menu
     *
     * @return an array of integer with the ids
     * */
    public ArrayList<Integer> getIconIds(){
        ArrayList<Integer> iconIds = new ArrayList<Integer>();

        iconIds.add(R.drawable.ic_question);
        iconIds.add(R.drawable.ic_calendar);
        iconIds.add(R.drawable.ic_map);
        iconIds.add(R.drawable.ic_practical_work);
        iconIds.add(R.drawable.ic_question);

        return iconIds;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.

        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
