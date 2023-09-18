package entity;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Game {
    private final Stack<Player> players;
    private final Map<Player, Field> playersFieldsMap;

    private final Map<Player, List<Ship>> playersShipsMap;
    private final Map<Cell, Ship> cellShipMap;

    private Player curPlayer;
    private Player enemyPlayer;

    public Game(Stack<Player> players,
                Map<Player, Field> playersFieldsMap,
                Map<Player, List<Ship>> playersShipsMap,
                Map<Cell, Ship> cellShipMap
    ) {
        this.players = players;
        this.curPlayer = players.pop();
        this.enemyPlayer = players.pop();
        this.playersFieldsMap = playersFieldsMap;
        this.playersShipsMap = playersShipsMap;
        this.cellShipMap = cellShipMap;
    }

    public void enemyTurnShuffle() {
        players.push(enemyPlayer);
        players.push(curPlayer);
        enemyPlayer = players.pop();
        curPlayer = players.pop();
    }

    public void playerTurnShuffle() {
        players.push(curPlayer);
        players.push(enemyPlayer);
        curPlayer = players.pop();
        enemyPlayer = players.pop();
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

    public Player getCurPlayer() {
        return curPlayer;
    }

    public Player getEnemyPlayer() {
        return enemyPlayer;
    }
}
