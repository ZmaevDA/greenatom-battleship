package service;

import entity.Cell;
import entity.Game;
import entity.Player;
import exception.InvalidCellSizeException;
import exception.InvalidPointsException;
import state.GameState;
import state.ShipType;
import utils.CellUtils;
import utils.ConsoleUtils;
import utils.Constants;
import utils.UiUtils;

import static utils.Colors.ANSI_RESET;
import static utils.Colors.RED;
import static utils.ConsoleUtils.readCellFromConsole;
import static utils.Constants.*;

public class ConsoleService {

    public String[] readPlayerName(int playerAmount) {
        String[] players = new String[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            players[i] = ConsoleUtils.readNameFromConsole(String.format("%s %s", ENTER_PLAYER_NAME, i + 1));
        }
        return players;
    }

    public void printPlayerTurnInfo(Game game) {
        Player curPlayer = game.getCurPlayer();
        System.out.printf("%s %s\n%s %s\n", PLAYER_TURN, curPlayer, PLAYER_DECK, curPlayer);
        UiUtils.drawDeck(game.getFieldByPlayer(curPlayer), GameState.RUN);
    }

    public Cell readCell() throws InvalidPointsException, InvalidCellSizeException {
        return CellUtils.stringToCell(ConsoleUtils.readCellFromConsole(CHOOSE_CELL));
    }

    public Cell readCellForShip(Player player, ShipType shipType) throws InvalidPointsException, InvalidCellSizeException {
        String shipTypeToString;
        switch (shipType) {
            case AIRCRAFT_CARRIER:
                shipTypeToString = AIRCRAFT_CARIER_NAME;
                break;
            case CRUISER:
                shipTypeToString = CRUISER_NAME;
                break;
            case DESTROYER:
                shipTypeToString = DESTROYER_NAME;
                break;
            case MOTOR_BOAT:
                shipTypeToString = MOTOR_BOAT_NAME;
                break;
            default:
                shipTypeToString = UNTITLED;
                break;
        }
        String point = readCellFromConsole(
                String.format(
                        "%s %s %s\n", player,ENTER_CELL_FOR_SHIP, shipTypeToString)
        );
        return CellUtils.stringToCell(point);
    }

   public void printExMessage(Exception e) {
       System.out.println(RED + e.getMessage() + ANSI_RESET);
   }

   public void printInfo(String string) {
       System.out.println(string);
   }

   public void printHitInfo(Cell cell) {
       System.out.printf("%s %s\n", Constants.HIT, cell);
   }

   public void printEndGameInfo(Player player) {
       System.out.printf("%s %s", Constants.PLAYER_WON, player);
   }
}
