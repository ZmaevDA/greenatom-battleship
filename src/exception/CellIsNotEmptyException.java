package exception;

public class CellIsNotEmptyException extends Exception {
    public CellIsNotEmptyException() {
        super("Невозможно поставить корабль в данную ячейку!");
    }
}
