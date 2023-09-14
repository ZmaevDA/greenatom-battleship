package exception;

public class InvalidPointsException extends Exception {
    public InvalidPointsException() {
        super("Невозможно расположить корабль в заданный промежуток!");
    }

}
