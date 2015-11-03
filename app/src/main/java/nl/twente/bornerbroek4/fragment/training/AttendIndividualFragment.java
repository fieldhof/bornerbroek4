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
import nl.twente.bornerbroek4.model.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class AttendIndividualFragment extends Fragment implements Observer{

    private LayoutInflater inflater;
    private ArrayAdapter<Player> adapter;

    private List<Player> players = new ArrayList<Player>();

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

        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_attend_indiv, null);

        TextView team =     (TextView) rootView.findViewById(R.id.attend_indiv_team_name);
        ListView listView = (ListView) rootView.findViewById(R.id.attend_indiv_listview);

        adapter = new TeamAdapter(this.getActivity(), R.layout.attend_indiv_list_item, players);

        listView.setAdapter(adapter);

        getData();

        team.setText("Bornerbroek 4");
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void update(Observable observable, Object data) {
        adapter.notifyDataSetChanged();
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
                    JSONArray jsonPlayers = overview.getJSONArray("TrainingOverview");
                    players.clear();
                    for(int i = 0; i < jsonPlayers.length(); i++) {
                        players.add(new Player(jsonPlayers.getJSONObject(i)));
                    }
                    adapter.notifyDataSetChanged();
                    adapter.sort(new Comparator<Player>() {
                        @Override
                        public int compare(Player lhs, Player rhs) {
                            return rhs.compareTo(lhs);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute("/trainings/training-overview/seasons/3/teams/27");
    }

    public class Player implements Comparable<Player>{
        private int id, teamId, percentage;
        private String firstName, surName, hasAttended, couldAttend;

        public Player(JSONObject obj) throws JSONException{
            this.id = obj.getInt("PlayerID");
            this.teamId = obj.getInt("TeamID");
            this.percentage = obj.getInt("AttendedPercentage");

            this.firstName = obj.getString("FirstName");
            this.surName = obj.getString("SurName");
            this.hasAttended = obj.getString("HasAttended");
            this.couldAttend = obj.getString("CouldAttend");
        }

        public int getId() {
            return id;
        }

        public int getTeamId() {
            return teamId;
        }

        public int getPercentage() {
            return percentage;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getSurName() {
            return surName;
        }

        public String getHasAttended() {
            return hasAttended;
        }

        public String getCouldAttend() {
            return couldAttend;
        }


        @Override
        public int compareTo(Player another) {
            if(this.percentage > another.percentage) {
                return 1;
            } else if (this.percentage < another.percentage) {
                return -1;
            } else {
                return -(this.surName + " " + this.firstName).compareTo((another.surName + " " + another.firstName));
            }
        }
    }

    private class TeamAdapter extends ArrayAdapter<Player> {

        private int resource;

        public TeamAdapter(Context context, int resource, List<Player> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return super.getCount() + 1;
        }

        @Override
        public Player getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = inflater.inflate(resource, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.attend_indiv_name);
            TextView nr = (TextView) convertView.findViewById(R.id.attend_indiv_nr);
            TextView outOf = (TextView) convertView.findViewById(R.id.attend_indiv_outof);
            TextView percent = (TextView) convertView.findViewById(R.id.attend_indiv_perc);

            if(position == 0) {
                name.setText("Speler");
                name.setTypeface(null, Typeface.BOLD);

                nr.setText("#");
                nr.setTypeface(null, Typeface.BOLD);

                outOf.setText("Opkomst");
                outOf.setTypeface(null, Typeface.BOLD);

                percent.setText("%");
                percent.setTypeface(null, Typeface.BOLD);
            } else {
                Player person = getItem(position - 1);
                name.setText(person.getFirstName() + " " + person.getSurName());
                name.setTypeface(null, Typeface.NORMAL);

                nr.setText("" + position);
                nr.setTypeface(null, Typeface.NORMAL);

                outOf.setText(person.hasAttended + "/" + person.couldAttend);
                outOf.setTypeface(null, Typeface.NORMAL);

                percent.setText(person.getPercentage() + "%");
                percent.setTypeface(null, Typeface.BOLD);

            }
            return convertView;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Model.getInstance().deleteObserver(this);
    }
}
