package nl.twente.bornerbroek4.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fieldhof on 26-9-2015.
 */
public class Player implements Comparable<Player>{

    private String firstName, surName;
    private int hasAttended, couldAttended;

    public Player(JSONObject obj) throws JSONException{
        if(obj.has("FirstName"))
            firstName = obj.getString("Firstname");
        if(obj.has("SurName"))
            surName = obj.getString("SurName");
        if(obj.has("SurNamePrefix")) {
            String surNamePrefix = obj.getString("SurNamePrefix");
            if(surNamePrefix != null)
                surName = surNamePrefix + " " + surName;
        }
        if(obj.has("HasAttended"))
            hasAttended = obj.getInt("HasAttended");
        if(obj.has("CouldAttended"))
            couldAttended = obj.getInt("CouldAttended");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public int getHasAttended() {
        return hasAttended;
    }

    public int getCouldAttended() {
        return couldAttended;
    }

    public int getAttendedPercentage() {
        return (int)(((double)hasAttended/(double)couldAttended)*100);
    }

    @Override
    public int compareTo(Player another) {
        if(this.getAttendedPercentage() > another.getAttendedPercentage()) {
            return 1;
        } else if(this.getAttendedPercentage() == another.getAttendedPercentage()) {
            return (this.firstName + " " + this.surName).compareTo(another.firstName + " " + another.surName);
        }
        return -1;
    }
}
