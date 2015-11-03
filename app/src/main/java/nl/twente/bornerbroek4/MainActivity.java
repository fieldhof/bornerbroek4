package nl.twente.bornerbroek4;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.astuetz.PagerSlidingTabStrip;
import nl.twente.bornerbroek4.fragment.HomeFragment;
import nl.twente.bornerbroek4.fragment.competition.CompFragment;
import nl.twente.bornerbroek4.fragment.training.TrainingFragment;
import nl.twente.bornerbroek4.httpcalls.LoginAsyncTask;

public class MainActivity extends ActionBarActivity {

    private String[] drawerNames;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LoginAsyncTask().execute();

        initDrawer();

    }

    private void initDrawer() {
        drawerNames = new String[] {"Home", "Competities", "Trainingen"};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.action_settings, R.string.action_settings) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, drawerNames));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            switch(position) {
                case 0:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new HomeFragment())
                            .commit();
                    break;
                case 1:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new CompFragment())
                            .commit();
//                    viewPager.setAdapter(new CompPagerAdapter(getSupportFragmentManager()));
                    Log.d("MainActivity", "CompetitionPagerAdapter set");
                    break;
                case 2:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new TrainingFragment())
                            .commit();
//                    viewPager.setAdapter(new TrainPagerAdapter(getSupportFragmentManager()));
                    Log.d("MainActivity", "TrainingPagerAdapter set");
                    break;
            }

            // Highlight the selected item, update the title, and close the drawer
            drawerList.setItemChecked(position, true);
            setTitle(drawerNames[position]);
            drawerLayout.closeDrawer(drawerList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
