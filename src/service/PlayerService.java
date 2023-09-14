package service;

import entity.Player;

import java.util.Stack;

public class PlayerService {

    public static final int MAX_PLAYERS_AMOUNT = 2;

    public Stack<Player> createPlayers(String[] nickname) {
        return new Stack<Player>() {
            {
                for (int i = 0; i < MAX_PLAYERS_AMOUNT; i++) {
                    push(new Player(nickname[i]));
                }
            }
        };
    }
}
