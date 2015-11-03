package nl.twente.bornerbroek4.fragment.competition;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.astuetz.PagerSlidingTabStrip;
import nl.twente.bornerbroek4.R;
import nl.twente.bornerbroek4.controller.CompPagerAdapter;

/**
 * Created by Fieldhof on 22-9-2015.
 */
public class CompFragment extends Fragment {

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_competition, null);

        viewPager = (ViewPager) rootView.findViewById(R.id.competition_pager);
        tabs =      (PagerSlidingTabStrip) rootView.findViewById(R.id.competition_tabs);

        viewPager.setAdapter(new CompPagerAdapter(this.getChildFragmentManager()));
        tabs.setViewPager(viewPager);

        return rootView;
    }
}
