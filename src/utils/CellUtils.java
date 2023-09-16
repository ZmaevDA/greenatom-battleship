package utils;

import entity.Cell;
import state.FieldState;

public class CellUtils {

    public static Cell stringToCell(String string) {
        int x = string.charAt(0) - 'a';
        int y = Integer.parseInt(string.substring(1, 2))  - 1;
        return new Cell(x, y, FieldState.EMPTY);
    }
}
