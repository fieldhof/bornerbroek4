package nl.twente.bornerbroek4.fragment.training;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import nl.twente.bornerbroek4.R;
import nl.twente.bornerbroek4.httpcalls.RestAsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public class AttendTeamFragment extends Fragment {

    private LayoutInflater inflater;
    private ArrayAdapter adapter;
    private ArrayList<Training> trainings = new ArrayList<Training>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_attend_team, null);

        TextView team =     (TextView) rootView.findViewById(R.id.attend_team_team_name);
        ListView listView = (ListView) rootView.findViewById(R.id.attend_team_listview);

        adapter = new TeamAdapter(this.getActivity(), R.layout.attend_team_list_item, trainings);

        listView.setAdapter(adapter);

        getData();

        team.setText("Bornerbroek 4");
        // Inflate the layout for this fragment
        return rootView;
    }

    private void getData() {
        new RestAsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    return;
                }

                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray jsonTrainings = obj.getJSONArray("Trainings");
                    trainings.clear();
                    for(int i = 0; i < jsonTrainings.length(); i++) {
                        trainings.add(new Training(jsonTrainings.getJSONObject(i)));
                    }
                    adapter.notifyDataSetChanged();
                    adapter.sort(new Comparator<Training>() {
                        @Override
                        public int compare(Training lhs, Training rhs) {
                            if(lhs.getId() >= rhs.getId())
                                return -1;
                            return 1;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute("/trainings/seasons/3/teams/27");
    }

    private class Training {
        private int id, totalAttended, totalPlayers;
        private String date;

        public Training (JSONObject obj) throws JSONException{
            this.id = obj.getInt("TrainingID");
            this.totalAttended = obj.getInt("TotalAttended");
            this.totalPlayers = obj.getInt("TotalPlayers");
            this.date = obj.getString("TrainingDate");
        }

        public int getId() {
            return id;
        }

        public int getTotalAttended() {
            return totalAttended;
        }

        public int getTotalPlayers() {
            return totalPlayers;
        }

        public String getDate() {
            return date;
        }
    }

    private class TeamAdapter extends ArrayAdapter<Training> {

        private int resource;

        public TeamAdapter(Context context, int resource, ArrayList<Training> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return super.getCount() + 1;
        }

        @Override
        public Training getItem(int position) {
            if(position == 0) {
                return null;
            }
            return trainings.get(position -1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = inflater.inflate(resource, parent, false);
            }

            TextView date = (TextView) convertView.findViewById(R.id.attend_team_date);
            TextView nr = (TextView) convertView.findViewById(R.id.attend_team_nr);

            if(position == 0) {
                date.setText("Trainingsdatum");
                date.setTypeface(null, Typeface.BOLD);

                nr.setText("Opkomst");
                nr.setTypeface(null, Typeface.BOLD);
            } else {
                Training item = getItem(position);
                date.setText(item.getDate());
                date.setTypeface(null, Typeface.NORMAL);

                nr.setText("" + item.getTotalAttended());
                nr.setTypeface(null, Typeface.BOLD);
            }


            return convertView;
        }
    }

}
