package service;

import entity.*;
import exception.InvalidCellSizeException;
import exception.InvalidPointsException;
import state.FieldState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static utils.Constants.NO_HIT;
import static utils.Constants.SHIP_DROWNED;

public class GameService {

    private final FieldService fieldService;
    private final PlayerService playerService;
    private final ConsoleService consoleService;

    public Game createGame() {
        Stack<Player> playerList = playerService.createPlayers(
                consoleService.readPlayerName(PlayerService.MAX_PLAYERS_AMOUNT)
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

    public GameService(FieldService fieldService, PlayerService playerService, ConsoleService consoleService) {
        this.fieldService = fieldService;
        this.playerService = playerService;
        this.consoleService = consoleService;
    }

    public void runGame(Game game) {
        boolean isGameOverFlag = false;
        while (!isGameOverFlag) {
            Player curPlayer = game.getCurPlayer();
            Player enemyPlayer = game.getEnemyPlayer();
            Field playerField = game.getFieldByPlayer(curPlayer);
            Field enemyField = game.getFieldByPlayer(enemyPlayer);
            consoleService.printPlayerTurnInfo(game);
            Cell inputCell = null;
            Cell curCell = null;
            do {
                try {
                    inputCell = consoleService.readCell();
                } catch (InvalidCellSizeException | InvalidPointsException e) {
                    consoleService.printExMessage(e);
                    continue;
                }
                curCell = enemyField.getCellFromSelfDeck(inputCell);
            } while (inputCell == null || !isCellValid(inputCell, playerField));
            if (processShoot(curCell, playerField, enemyField, game, enemyPlayer)) {
                game.playerTurnShuffle();
            } else {
                game.enemyTurnShuffle();
            }
            isGameOverFlag = isGameOver(game, enemyPlayer);
            if (isGameOverFlag) {
                consoleService.printEndGameInfo(curPlayer);
            }
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

    private boolean processShoot(Cell curCell, Field playerField, Field enemyField, Game game, Player enemyPlayer) {
        FieldState type = FieldState.MISS;
        if (enemyField.isCellOccupiedByShipInSelfDeck(curCell)) {
            type = FieldState.HIT;
            hitShip(curCell, game);
            consoleService.printHitInfo(curCell);
            if (isKilled(curCell, game)) {
                killShip(game.getCellShipMap().get(curCell), game.getPlayersShipsMap().get(enemyPlayer));
                consoleService.printInfo(SHIP_DROWNED);
            }
            updateFields(curCell, type, playerField, enemyField);
            return true;
        } else {
            consoleService.printInfo(NO_HIT);
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