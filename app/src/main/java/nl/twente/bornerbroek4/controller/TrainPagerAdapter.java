package nl.twente.bornerbroek4.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import nl.twente.bornerbroek4.fragment.training.AttendIndividualFragment;
import nl.twente.bornerbroek4.fragment.training.AttendTeamFragment;
import nl.twente.bornerbroek4.httpcalls.RestAsyncTask;
import org.json.JSONException;
import org.json.JSONObject;


public class TrainPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private final Fragment[] fragments;

    public TrainPagerAdapter(FragmentManager fm) {
        super(fm);
        TITLES = new String[] {"Opkomst","Trainingen"};
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(fragments[position] == null) {
                    fragments[position] = new AttendIndividualFragment();
                }
                return fragments[position];
            case 1:
                if(fragments[position] == null) {
                    fragments[position] = new AttendTeamFragment();
                }
                return fragments[position];
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    private void getData() {

        new RestAsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    return;
                }

                try {
                    JSONObject overview = new JSONObject(s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute("/trainings/training-overview/seasons/3/teams/27");

        new RestAsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                //TODO add data to Model
            }
        }.execute("/trainings/seasons/3/teams/27");
    }
}