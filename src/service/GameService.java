package service;

import entity.Field;
import entity.Game;
import entity.Player;
import exception.CellIsNotEmptyException;
import exception.InvalidPointsException;
import ui.GameInterface;
import utils.UiUtils;

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
        prepareFieldsForPlayers(playersFields);
        return new Game(playerList, playersFields);
    }

    private void prepareFieldsForPlayers(Map<Player, Field> playersFields) {
        for (Player player : playersFields.keySet()) {
            Field curField = playersFields.get(player);
            UiUtils.drawDeck(curField.getPlayerDeck());
            fieldService.placeShips(playersFields.get(player));
        }
    }


    public GameService(FieldService fieldService, PlayerService playerService, GameInterface gameInterface) {
        this.fieldService = fieldService;
        this.playerService = playerService;
        this.gameInterface = gameInterface;
    }

    public void runGame(Game game) {
        gameInterface.drawFields(game);
    }

    public void isGameOver() {

    }


}
