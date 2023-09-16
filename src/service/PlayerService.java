package service;

import entity.Player;
import entity.Ship;
import state.ShipType;

import java.util.*;

public class PlayerService {

    public static final int MAX_PLAYERS_AMOUNT = 2;

    public Stack<Player> createPlayers(String[] nickname) {
        return new Stack<>() {
            {
                for (int i = 0; i < MAX_PLAYERS_AMOUNT; i++) {
                    push(new Player(nickname[i]));
                }
            }
        };
    }

    private List<Ship> createPlayerShips() {
        return new ArrayList<>() {
            {
                add(new Ship(4, ShipType.AIRCRAFT_CARRIER));
                add(new Ship(3, ShipType.CRUISER));
                add(new Ship(3, ShipType.CRUISER));
                add(new Ship(2, ShipType.DESTROYER));
                add(new Ship(2, ShipType.DESTROYER));
                add(new Ship(2, ShipType.DESTROYER));
                add(new Ship(1, ShipType.MOTOR_BOAT));
                add(new Ship(1, ShipType.MOTOR_BOAT));
                add(new Ship(1, ShipType.MOTOR_BOAT));
                add(new Ship(1, ShipType.MOTOR_BOAT));
            }
        };
    }

    public Map<Player, List<Ship>> giveShipsToPlayer(Stack<Player> players) {
        Map<Player, List<Ship>> playerShipsMap = new HashMap<>();
        for (Player player: players) {
            playerShipsMap.put(player, createPlayerShips());
        }
        return playerShipsMap;
    }
}
