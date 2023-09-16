package entity;

import state.FieldState;

public class Field {
    public final static int FIELD_SIZE = 10;

    private Cell[][] selfDeck;
    private Cell[][] specDeck;

    public Field() {
        this.selfDeck = makeEmptyField();
        this.specDeck = makeEmptyField();
    }

    private Cell[][] makeEmptyField() {
        Cell[][] deck = new Cell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                deck[i][j] = new Cell(i, j, FieldState.EMPTY);
            }
        }
        return deck;
    }

    public Cell getCellFromSelfDeck(Cell cell) {
        return selfDeck[cell.getX()][cell.getY()];
    }

    public Cell changeCellStateInSelfDeck(FieldState fieldState, int startPoint, int endPoint) {
        this.selfDeck[startPoint][endPoint].setFieldType(fieldState);
        return this.selfDeck[startPoint][endPoint];
    }

    public void changeCellStateInSelfDeck(FieldState fieldState, Cell cell) {
        this.selfDeck[cell.getX()][cell.getY()].setFieldType(fieldState);
    }

    public void changeCellStateInSpecDeck(FieldState fieldState, Cell cell) {
        this.specDeck[cell.getX()][cell.getY()].setFieldType(fieldState);
    }

    public Cell getFieldCellInSelfDeck(Cell cell) {
        return selfDeck[cell.getX()][cell.getY()];
    }

    public Cell getFieldCellInSelfDeck(int startPoint, int endPoint) {
        return selfDeck[startPoint][endPoint];
    }

    public Cell getFieldCellInSpecDeck(int startPoint, int endPoint) {
        return specDeck[startPoint][endPoint];
    }

    public boolean isCellOccupiedByShipInSelfDeck(int x, int y) {
        return selfDeck[x][y].getFieldType() != FieldState.BLOCKED &&
                selfDeck[x][y].getFieldType() != FieldState.EMPTY;
    }

    public boolean isCellOccupiedByShipInSelfDeck(Cell cell) {
        return selfDeck[cell.getX()][cell.getY()].getFieldType() != FieldState.BLOCKED &&
                selfDeck[cell.getX()][cell.getY()].getFieldType() != FieldState.EMPTY;
    }

    public boolean isCellEmptyInSelfDeck(Cell cell) {
        return selfDeck[cell.getX()][cell.getY()].getFieldType() == FieldState.EMPTY;
    }

    public boolean isCellEmptyInSpecDeck(Cell cell) {
        return specDeck[cell.getX()][cell.getY()].getFieldType() == FieldState.EMPTY;
    }

    public boolean canShootInSpecDeck(Cell cell) {
        return specDeck[cell.getX()][cell.getY()].getFieldType() == FieldState.EMPTY;
    }

    public Cell[][] getSelfDeck() {
        return selfDeck;
    }

    public void setSelfDeck(Cell[][] selfDeck) {
        this.selfDeck = selfDeck;
    }

    public Cell[][] getSpecDeck() {
        return specDeck;
    }

    public void setSpecDeck(Cell[][] specDeck) {
        this.specDeck = specDeck;
    }
}
