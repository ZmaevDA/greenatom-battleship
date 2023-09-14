import entity.Game;
import service.FieldService;
import service.GameService;
import service.PlayerService;
import ui.GameInterface;

public class Main {
    public static void main(String[] args) {
        FieldService fieldService = new FieldService();
        GameInterface gameInterface = new GameInterface();
        PlayerService playerService = new PlayerService();
        GameService gameService = new GameService(fieldService, playerService, gameInterface);
        Game game = gameService.createGame();
        gameService.runGame(game);
    }

}