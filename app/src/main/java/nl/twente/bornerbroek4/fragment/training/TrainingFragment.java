package nl.twente.bornerbroek4.fragment.training;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.astuetz.PagerSlidingTabStrip;
import nl.twente.bornerbroek4.R;
import nl.twente.bornerbroek4.controller.TrainPagerAdapter;
import nl.twente.bornerbroek4.model.Model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Fieldhof on 22-9-2015.
 */
public class TrainingFragment extends Fragment implements Observer{

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        Model.getInstance().addObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_training, null);

        viewPager = (ViewPager) rootView.findViewById(R.id.training_pager);
        tabs =      (PagerSlidingTabStrip) rootView.findViewById(R.id.training_tabs);

        viewPager.setAdapter(new TrainPagerAdapter(getChildFragmentManager()));
        tabs.setViewPager(viewPager);

        return rootView;
    }

    @Override
    public void update(Observable observable, Object data) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Model.getInstance().deleteObserver(this);

    }
}
