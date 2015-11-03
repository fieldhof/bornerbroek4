package nl.twente.bornerbroek4.model;

import java.util.List;

/**
 * Created by Fieldhof on 3-11-2015.
 */
public class Team {

    private int id;
    private List<Player> players;

    public Team(int id) {
        this.id = id;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getId() {
        return id;
    }

}
