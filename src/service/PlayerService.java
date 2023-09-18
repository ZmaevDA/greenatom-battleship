package service;

import entity.Player;
import entity.Ship;
import state.ShipType;

import java.util.*;

public class PlayerService {

    public static final int MAX_PLAYERS_AMOUNT = 2;

    private static final int AIRCRAFT_CARRIER_HP = 4;
    private static final int CRUISER_HP = 3;
    private static final int DESTROYER_HP = 2;
    private static final int MOTOR_BOAT_HP = 1;

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
                add(new Ship(AIRCRAFT_CARRIER_HP, ShipType.AIRCRAFT_CARRIER));
                add(new Ship(CRUISER_HP, ShipType.CRUISER));
//                add(new Ship(CRUISER_HP, ShipType.CRUISER));
//                add(new Ship(DESTROYER_HP, ShipType.DESTROYER));
//                add(new Ship(DESTROYER_HP, ShipType.DESTROYER));
//                add(new Ship(DESTROYER_HP, ShipType.DESTROYER));
//                add(new Ship(MOTOR_BOAT_HP, ShipType.MOTOR_BOAT));
//                add(new Ship(MOTOR_BOAT_HP, ShipType.MOTOR_BOAT));
//                add(new Ship(MOTOR_BOAT_HP, ShipType.MOTOR_BOAT));
//                add(new Ship(MOTOR_BOAT_HP, ShipType.MOTOR_BOAT));
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
