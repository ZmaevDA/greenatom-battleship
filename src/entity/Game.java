package entity;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Game {
    private Stack<Player> players;
    private Map<Player, Field> playersFieldsMap;

    private Map<Player, List<Ship>> playersShipsMap;
    private Map<Cell, Ship> cellShipMap;

    public Game(Stack<Player> players, Map<Player, Field> playersFieldsMap, Map<Player, List<Ship>> playersShipsMap, Map<Cell, Ship> cellShipMap) {
        this.players = players;
        this.playersFieldsMap = playersFieldsMap;
        this.playersShipsMap = playersShipsMap;
        this.cellShipMap = cellShipMap;
    }

    public Stack<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Stack<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.push(player);
    }

    public Field getFieldByPlayer(Player player) {
        return playersFieldsMap.get(player);
    }

    public Map<Player, List<Ship>> getPlayersShipsMap() {
        return playersShipsMap;
    }

    public Map<Cell, Ship> getCellShipMap() {
        return cellShipMap;
    }

}
