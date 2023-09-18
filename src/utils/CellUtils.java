package utils;

import entity.Cell;
import state.FieldState;

public class CellUtils {

    public static Cell stringToCell(String string) {
        int x = string.charAt(0) - 'a';
        int y = string.length() == 3 ?
                Integer.parseInt(string.substring(1, 3))  - 1 :
                Integer.parseInt(string.substring(1, 2))  - 1;
        return new Cell(x, y, FieldState.EMPTY);
    }
}
