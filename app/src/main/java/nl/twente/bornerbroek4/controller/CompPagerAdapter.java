package nl.twente.bornerbroek4.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import nl.twente.bornerbroek4.fragment.competition.PositionFragment;
import nl.twente.bornerbroek4.fragment.competition.ProgrammFragment;
import nl.twente.bornerbroek4.fragment.competition.ResultFragment;
import nl.twente.bornerbroek4.fragment.competition.StatsFragment;

/**
 * Created by mjansen on 17-9-15.
 */
public class CompPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private final Fragment[] fragments;

    public CompPagerAdapter(FragmentManager fm) {
        super(fm);
        TITLES = new String[] {"Uitslagen","Programma", "Stand", "Statistieken"};
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
                    fragments[position] = new ResultFragment();
                }
                return fragments[position];
            case 1:
                if(fragments[position] == null) {
                    fragments[position] = new ProgrammFragment();
                }
                return fragments[position];
            case 2:
                if(fragments[position] == null) {
                    fragments[position] = new PositionFragment();
                }
                return fragments[position];
            case 3:
                if(fragments[position] == null) {
                    fragments[position] = new StatsFragment();
                }
                return fragments[position];
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}