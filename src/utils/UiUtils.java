package utils;

import entity.Cell;
import entity.Field;
import entity.FieldType;

public class UiUtils {

    public static void drawDeck(Cell[][] deck) {
        for (char i = 0; i < deck.length; i++) {
            System.out.printf(i + 1 == 1 ? "  %s" : " %s", i + 1);
        }
        System.out.println();
        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            System.out.printf("%c ", (char) i + 97);
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                System.out.printf(deck[i][j].getFieldType() == FieldType.EMPTY ? "~ " : "%s ", deck[i][j]);
            }
            System.out.println();
        }
    }

}
