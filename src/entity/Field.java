package entity;

public class Field {
    public final static int FIELD_SIZE = 10;

    private Cell[][] playerDeck;
    private Cell[][] enemyDeck;

    public Field() {
        this.playerDeck = makeEmptyField();
        this.enemyDeck = makeEmptyField();
    }

    private Cell[][] makeEmptyField() {
        Cell[][] deck = new Cell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                deck[i][j] = new Cell(i, j, FieldType.EMPTY);
            }
        }
        return deck;
    }

    public void changeCellState(FieldType fieldType, int startPoint, int endPoint) {
        this.playerDeck[startPoint][endPoint].setFieldType(fieldType);
    }

    public Cell getFieldCell(Cell cell) {
        return playerDeck[cell.getX()][cell.getY()];
    }

    public Cell getFieldCell(int startPoint, int endPoint) {
        return playerDeck[startPoint][endPoint];
    }

    public boolean isCellOccupiedByShip(int x, int y) {
        return playerDeck[x][y].getFieldType() != FieldType.BLOCKED &&
                playerDeck[x][y].getFieldType() != FieldType.EMPTY;
    }

    public boolean isCellEmptyInPlayerDeck(Cell cell) {
        return playerDeck[cell.getX()][cell.getY()].getFieldType() == FieldType.EMPTY;
    }

    public Cell[][] getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(Cell[][] playerDeck) {
        this.playerDeck = playerDeck;
    }

    public Cell[][] getEnemyDeck() {
        return enemyDeck;
    }

    public void setEnemyDeck(Cell[][] enemyDeck) {
        this.enemyDeck = enemyDeck;
    }
}
