package utils;

import entity.Cell;
import entity.Field;
import state.FieldState;
import state.GameState;

public class UiUtils {

    public static void drawDeck(Field field, GameState state) {
        Cell[][] selfDeck = field.getSelfDeck();
        Cell[][] specDeck = field.getSpecDeck();
        for (char i = 0; i < selfDeck.length; i++) {
            System.out.printf(i + 1 == 1 ? "  %s" : " %s", i + 1);
        }
        System.out.print("\t\t");
        for (char i = 0; i < selfDeck.length; i++) {
            System.out.printf(i + 1 == 1 ? "  %s" : " %s", i + 1);
        }
        System.out.println();
        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            System.out.printf("%c ", (char) i + 97);
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                if (state == GameState.RUN) {
                    System.out.printf(
                            selfDeck[i][j].getFieldType() == FieldState.EMPTY ||
                            selfDeck[i][j].getFieldType() == FieldState.BLOCKED ? "~ " : "%s ", selfDeck[i][j]);
                } else {
                    System.out.printf(selfDeck[i][j].getFieldType() == FieldState.EMPTY ? "~ " : "%s ", selfDeck[i][j]);
                }
            }
            System.out.print("\t\t");
            System.out.printf("%c ", (char) i + 97);
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                System.out.printf(specDeck[i][j].getFieldType() == FieldState.EMPTY ? "~ " : "%s ", specDeck[i][j]);
            }
            System.out.println();
        }
    }
}
