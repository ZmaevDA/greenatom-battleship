package service;

import entity.*;
import exception.CellIsNotEmptyException;
import exception.InvalidPointsException;
import state.FieldState;
import state.GameState;
import state.ShipType;
import utils.CellUtils;
import utils.UiUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.ConsoleUtils.readLineFromConsole;

public class FieldService {

    public List<Field> createFields(int playerAmount) {
        return new ArrayList<Field>() {
            {
                for (int i = 0; i < playerAmount; i++) {
                    add(new Field());
                }
            }
        };
    }

    public Map<Player, Field> assignFieldToPlayer(List<Player> players, List<Field> fields) {
        return new HashMap<>() {
            {
                for (int i = 0; i < players.size(); i++) {
                    put(players.get(i), fields.get(i));
                }
            }
        };
    }

    public void placeShips(
            Map<Player, Field> playerFieldMap,
            Map<Player, List<Ship>> playerShipsMap,
            Map<Cell, Ship> cellShipMap
    ) {
        for (Map.Entry<Player, List<Ship>> playerShip: playerShipsMap.entrySet()) {
            for (Ship ship: playerShip.getValue()) {
                Field field = playerFieldMap.get(playerShip.getKey());
                Cell startCell, endCell;
                do {
                    startCell = getCellFromUser(ship.getShipType());
                    endCell = getCellFromUser(ship.getShipType());
                    try {
                        placeShip(field, startCell, endCell, ship, cellShipMap);
                    } catch (CellIsNotEmptyException | InvalidPointsException e) {
                        System.out.println(e.getMessage());
                    }
                    UiUtils.drawDeck(field, GameState.PREPARE);
                } while (isCellsValid(field, startCell, endCell));
            }
        }
    }

    private void placeShip(
            Field field,
            Cell startCell,
            Cell endCell,
            Ship currShip,
            Map<Cell, Ship> cellShipMap
    ) throws CellIsNotEmptyException, InvalidPointsException {
        if (!isCellValidForShip(startCell, endCell, currShip)) {
            throw new InvalidPointsException();
        }
        if (isCellsValid(field, startCell, endCell)) {
            if (isHorizontal(startCell, endCell)) {
                for (int i = 0; i < currShip.getHp(); i++) {
                    Cell cell = field.changeCellStateInSelfDeck(
                            FieldState.FILLED,
                            startCell.getX(),
                            startCell.getY() + i
                    );
                    cellShipMap.put(cell, currShip);
                }
            } else {
                for (int i = 0; i < currShip.getHp(); i++) {
                    Cell cell = field.changeCellStateInSelfDeck(
                            FieldState.FILLED,
                            startCell.getX() + i,
                            startCell.getY()
                    );
                    cellShipMap.put(cell, currShip);
                }
            }
            setBlockedCells(field, startCell, endCell);
        } else {
            throw new CellIsNotEmptyException();
        }
    }

    private Cell getCellFromUser(ShipType ship) {
        String point;
        Pattern pattern = Pattern.compile("[a-j][0-9]|10");
        Matcher matcher;
        do {
            point =
                    readLineFromConsole(
                            String.format(
                                    "Выбирите клетку, куда хотите поставить корабль: %s", ship.toString())
                    );
            matcher = pattern.matcher(point);
        }
        while (!matcher.matches());
        return CellUtils.stringToCell(point);
    }

    private void setBlockedCells(Field field, Cell startCell, Cell endCell) {
        Cell[][] deck = field.getSelfDeck();
        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                if (isHorizontal(startCell, endCell)) {
                    placeHorizontalBlock(deck, startCell, endCell, i, j);
                } else {
                    placeVerticalBlock(deck, startCell, endCell, i, j);
                }
            }
        }
    }

    private boolean isHorizontal(Cell startCell, Cell endCell) {
        return startCell.getX() == endCell.getX();
    }

    private void placeVerticalBlock(Cell[][] deck, Cell startCell, Cell endCell, int i, int j) {
        // Лево
        if (startCell.getY() + 1 <= Field.FIELD_SIZE) {
            deck[startCell.getX()][startCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getY() - 1 >= 0) {
            deck[startCell.getX()][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        // Право
        if (endCell.getY() + 1 <= Field.FIELD_SIZE) {
            deck[endCell.getX()][endCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
        if (endCell.getY() - 1 >= 0) {
            deck[endCell.getX()][endCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        for (int k = startCell.getX(); k < endCell.getX(); k++) {
            if (startCell.getY() + 1 <= Field.FIELD_SIZE) {
                deck[k][startCell.getY() + 1].setFieldType(FieldState.BLOCKED);
            }
            if (startCell.getY() - 1 >= 0) {
                deck[k][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
            }
        }
        // Верх
        if (startCell.getX() - 1 >= 0) {
            deck[startCell.getX() - 1][startCell.getY()].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getX() - 1 >= 0 && startCell.getY() - 1 >= 0) {
            deck[startCell.getX() - 1][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getX() - 1 >= 0 && startCell.getY() + 1 >= 0) {
            deck[startCell.getX() - 1][startCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
        // Низ
        if (endCell.getX() + 1 <= Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY()].setFieldType(FieldState.BLOCKED);
        }
        if (endCell.getX() + 1 <= Field.FIELD_SIZE && endCell.getY() - 1 >= 0) {
            deck[endCell.getX() + 1][endCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        if (endCell.getX() + 1 <= Field.FIELD_SIZE && endCell.getY() + 1 <= Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
    }

    private void placeHorizontalBlock(Cell[][] deck, Cell startCell, Cell endCell, int i, int j) {
        // Верх низ
        if (deck[i][j].getFieldType() != FieldState.EMPTY &&
                deck[i][j].getFieldType() != FieldState.BLOCKED) {
            if (i + 1 < Field.FIELD_SIZE) {
                deck[i + 1][j].setFieldType(FieldState.BLOCKED);
            }
            if (i - 1 > 0) {
                deck[i - 1][j].setFieldType(FieldState.BLOCKED);
            }
        }
        // Левые боковушки
        if (startCell.getY() - 1 > 0) {
            deck[startCell.getX()][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getX() + 1 < Field.FIELD_SIZE && startCell.getY() - 1 >= 0) {
            deck[startCell.getX() + 1][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getX() - 1 > 0 && startCell.getY() - 1 > 0) {
            deck[startCell.getX() - 1][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        // Правые боковушки
        if (endCell.getX() - 1 > 0 && endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() - 1][endCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
        if (endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX()][endCell.getY() + 1].setFieldType(FieldState.BLOCKED);

        }
        if (endCell.getX() + 1 < Field.FIELD_SIZE && endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
    }

    private boolean isCellsValid(Field field, Cell firstCell, Cell secondCell) {
        return field.isCellEmptyInSelfDeck(firstCell) && field.isCellEmptyInSelfDeck(secondCell);
    }

    private boolean isCellValidForShip(Cell firstCell, Cell secondCell, Ship ship) {
        int dx = secondCell.getX() - firstCell.getX();
        int dy = secondCell.getY() - firstCell.getY();
        return dx == ship.getHp() - 1 || dy == ship.getHp() - 1;
    }
}