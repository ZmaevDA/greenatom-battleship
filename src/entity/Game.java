package entity;

import java.util.Map;
import java.util.Stack;

public class Game {
    private Stack<Player> players;

    private Map<Player, Field> playersFields;

    public Game(Stack<Player> players, Map<Player, Field> playersFields) {
        this.players = players;
        this.playersFields = playersFields;
    }

    public Stack<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Stack<Player> players) {
        this.players = players;
    }

    public Map<Player, Field> getPlayersFields() {
        return playersFields;
    }

    public void setPlayersFields(Map<Player, Field> playersFields) {
        this.playersFields = playersFields;
    }
}
