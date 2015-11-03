package nl.twente.bornerbroek4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Fieldhof on 26-9-2015.
 */
public class Model extends Observable {

    public enum ObserverStatus {
        TOKEN_CHANGED,
        PERSONS_CHANGED,
    }

    private String token;
    private static Model model;
    private List<Player> traininOverview = new ArrayList<Player>();

    private Model() {

    }

    public static Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
