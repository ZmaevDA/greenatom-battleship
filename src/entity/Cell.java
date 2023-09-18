package entity;

import state.FieldState;

public class Cell {
    private final int x;
    private final int y;

    private FieldState fieldState;

    public Cell(int x, int y, FieldState fieldState) {
        this.x = x;
        this.y = y;
        this.fieldState = fieldState;
    }

    public FieldState getFieldType() {
        return fieldState;
    }

    public void setFieldType(FieldState fieldState) {
        this.fieldState = fieldState;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return fieldState.toString().substring(0, 1);
    }
}
