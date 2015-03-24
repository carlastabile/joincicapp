package ve.com.joincic.joincicapp.views;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.app.ToolbarActionBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        setNavigationDrawer();

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

        int size = mPlanetTitles.length;
        for (int i = 0; i < size; i++) {
            items.add(new DrawerItem(mPlanetTitles[i], 0));
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
     * Swaps fragments in the main content view
     */

    private void selectItem(int position) {
        // Odd positions will be lighter
        // Even positions will be darker

        if (mPlanetTitles[position] == null) {
            return;

        } else if (mPlanetTitles[position].equals("Sun")){ // Profile
            Toast.makeText(this, "HOLA!!!", Toast.LENGTH_SHORT).show();
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
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
}
