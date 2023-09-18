import entity.Game;
import service.ConsoleService;
import service.FieldService;
import service.GameService;
import service.PlayerService;

public class Main {
    public static void main(String[] args) {
        PlayerService playerService = new PlayerService();
        ConsoleService consoleService = new ConsoleService();
        FieldService fieldService = new FieldService(consoleService);
        GameService gameService = new GameService(fieldService, playerService, consoleService);
        Game game = gameService.createGame();
        gameService.runGame(game);
    }
}