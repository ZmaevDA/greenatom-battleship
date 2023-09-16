package service;

import entity.*;
import state.FieldState;
import state.GameState;
import ui.GameInterface;
import utils.CellUtils;
import utils.ConsoleUtils;
import utils.UiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GameService {

    private FieldService fieldService;
    private PlayerService playerService;
    private GameInterface gameInterface;

    public Game createGame() {
        Stack<Player> playerList = playerService.createPlayers(
                gameInterface.readPlayerName(PlayerService.MAX_PLAYERS_AMOUNT)
        );
        List<Field> fieldList = fieldService.createFields(playerList.size());
        Map<Player, Field> playersFields = fieldService.assignFieldToPlayer(playerList, fieldList);
        Map<Player, List<Ship>> playerShipsMap = playerService.giveShipsToPlayer(playerList);
        Map<Cell, Ship> cellShipMap = new HashMap<>();
        prepareFieldsForPlayers(playersFields, playerShipsMap, cellShipMap);
        return new Game(playerList, playersFields, playerShipsMap, cellShipMap);
    }

    private void prepareFieldsForPlayers(
            Map<Player, Field> playersFieldsMap,
            Map<Player, List<Ship>> playerShipsMap,
            Map<Cell, Ship> cellShipMap
    ) {
            fieldService.placeShips(playersFieldsMap, playerShipsMap, cellShipMap);
    }

    public GameService(FieldService fieldService, PlayerService playerService, GameInterface gameInterface) {
        this.fieldService = fieldService;
        this.playerService = playerService;
        this.gameInterface = gameInterface;
    }

    public void runGame(Game game) {
        boolean isGameOverFlag = false;
        while (!isGameOverFlag) {
            Player curPlayer = game.getPlayers().pop();
            Player enemyPlayer = game.getPlayers().pop();
            Field playerField = game.getFieldByPlayer(curPlayer);
            Field enemyField = game.getFieldByPlayer(enemyPlayer);
            Cell inputCell, curCell;
            System.out.printf("Ход игрока %s\n", curPlayer);
            System.out.printf("Доска игрока %s\n", curPlayer.toString());
            UiUtils.drawDeck(playerField, GameState.RUN);
            do {
                inputCell = CellUtils.stringToCell(
                            ConsoleUtils.readLineFromConsole("Выберите клетку, в которую хотите стрелять"
                        )
                );
                curCell = enemyField.getCellFromSelfDeck(inputCell);
            } while (!isCellValid(inputCell, playerField));
            if (makeShoot(curCell, playerField, enemyField, game, enemyPlayer)) {
                System.out.printf("Есть попадание по клетке %s\n", curCell);
                game.addPlayer(enemyPlayer);
                game.addPlayer(curPlayer);
            } else {
                System.out.println("Мимо!");
                game.addPlayer(curPlayer);
                game.addPlayer(enemyPlayer);
            }
            isGameOverFlag = isGameOver(game, enemyPlayer);
            System.out.printf(isGameOverFlag ? "Победил игрок: %s" : "", curPlayer);
        }
    }

    private void updateFields(Cell curCell, FieldState type, Field playerField, Field enemyField) {
        updatePlayerFiled(curCell, type, playerField);
        updateEnemyFiled(curCell, type, enemyField);
    }

    private void updatePlayerFiled(Cell curCell, FieldState type, Field playerField) {
        playerField.changeCellStateInSpecDeck(type, curCell);
    }

    private void updateEnemyFiled(Cell curCell, FieldState type, Field enemyField) {
        enemyField.changeCellStateInSelfDeck(type, curCell);
    }

    private boolean makeShoot(Cell curCell, Field playerField, Field enemyField, Game game, Player enemyPlayer) {
        FieldState type = FieldState.MISS;
        if (enemyField.isCellOccupiedByShipInSelfDeck(curCell)) {
            type = FieldState.HIT;
            hitShip(curCell, game);
            if (isKilled(curCell, game)) {
                killShip(game.getCellShipMap().get(curCell), game.getPlayersShipsMap().get(enemyPlayer));
                type = FieldState.KILLED;
            }
            updateFields(curCell, type, playerField, enemyField);
            return true;
        } else {
            updatePlayerFiled(curCell, type, playerField);
            return false;
        }
    }

    private void hitShip(Cell curCell, Game game) {
        game.getCellShipMap().get(curCell).decreaseHp();
    }

    private void killShip(Ship ship, List<Ship> ships) {
        ships.remove(ship);
    }

    private boolean isKilled(Cell curCell, Game game) {
        return game.getCellShipMap().get(curCell).getHp() == 0;
    }

    private boolean isCellValid(Cell curCell, Field playerField) {
        return playerField.isCellEmptyInSpecDeck(curCell);
    }

    public boolean isGameOver(Game game, Player currPlayer) {
        return game.getPlayersShipsMap().get(currPlayer).isEmpty();
    }
}
