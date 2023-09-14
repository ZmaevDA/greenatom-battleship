package utils;

import entity.Cell;
import entity.FieldType;

public class CellUtils {

    public static Cell stringToCell(String string) {
        int x = string.charAt(0) - 'a';
        int y = Integer.parseInt(string.substring(1, 2))  - 1;
        return new Cell(x, y, FieldType.EMPTY);
    }

}
