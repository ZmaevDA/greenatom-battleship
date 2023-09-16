import entity.Game;
import service.ConsoleService;
import service.FieldService;
import service.GameService;
import service.PlayerService;

public class Main {
    public static void main(String[] args) {
        FieldService fieldService = new FieldService();
        PlayerService playerService = new PlayerService();
        ConsoleService consoleService = new ConsoleService();
        GameService gameService = new GameService(fieldService, playerService, consoleService);
        Game game = gameService.createGame();
        gameService.runGame(game);
    }

}