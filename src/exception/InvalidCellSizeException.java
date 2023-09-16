package exception;

public class InvalidCellSizeException extends Exception {
    public InvalidCellSizeException() {
        super("Неверная длинна строки");
    }
}
