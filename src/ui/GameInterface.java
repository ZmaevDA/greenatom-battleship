package ui;

import entity.Field;
import entity.Game;
import entity.Player;
import utils.ConsoleUtils;

public class GameInterface {

//    public void drawSelfFields(Game game, Player player) {
//        Field field = game.getPlayersFields().get(player);
//        for (int i = 0; i < Field.FIELD_SIZE; i++) {
//            for (int j = 0; j < Field.FIELD_SIZE; j++) {
//                System.out.print(field.getFieldCellInSelfDeck(i, j) + " ");
//            }
//            System.out.println();
//        }
//    }
//
//    public void drawSpecFields(Game game, Player player) {
//        Field field = game.getPlayersFields().get(player);
//        for (int i = 0; i < Field.FIELD_SIZE; i++) {
//            for (int j = 0; j < Field.FIELD_SIZE; j++) {
//                System.out.print(field.getFieldCellInSpecDeck(i, j) + " ");
//            }
//            System.out.println();
//        }
//    }

    public String[] readPlayerName(int playerAmount) {
        String[] players = new String[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            players[i] = ConsoleUtils.readLineFromConsole(String.format("Введите имя игрока номер %s", i + 1));
        }
        return players;
    }

}
