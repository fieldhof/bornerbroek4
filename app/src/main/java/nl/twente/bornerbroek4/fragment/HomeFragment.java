package nl.twente.bornerbroek4.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.twente.bornerbroek4.R;

/**
 * Created by Fieldhof on 26-9-2015.
 */
public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PositionFragment", "Loaded...");
        View rootView = inflater.inflate(R.layout.fragment_home, null);
        TextView text = (TextView)rootView.findViewById(R.id.home_textview);
        text.setText("Dit is de app van Bornerbroek 4");
        text.setTextColor(getResources().getColor(android.R.color.black));
        return rootView;
    }

}
