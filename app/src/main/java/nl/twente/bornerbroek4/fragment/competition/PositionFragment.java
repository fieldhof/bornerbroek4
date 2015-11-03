package nl.twente.bornerbroek4.fragment.competition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import nl.twente.bornerbroek4.R;

/**
 * Created by Fieldhof on 22-9-2015.
 */
public class PositionFragment extends Fragment {

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
        View rootView = inflater.inflate(R.layout.fragment_comp_position, null);
        TextView text = (TextView)rootView.findViewById(R.id.textview1);
        text.setText("Hallo allemaal, hoe gaat het?");
        text.setTextColor(getResources().getColor(android.R.color.black));
        return rootView;
    }
}
