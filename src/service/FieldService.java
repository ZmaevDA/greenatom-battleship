package service;

import entity.Cell;
import entity.Field;
import entity.FieldType;
import entity.Player;
import exception.CellIsNotEmptyException;
import exception.InvalidPointsException;
import utils.CellUtils;
import utils.UiUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.ConsoleUtils.readLineFromConsole;

public class FieldService {

    List<FieldType> ships = new ArrayList<>() {
        {
            add(FieldType.AIRCRAFT_CARRIER);
            add(FieldType.CRUISER);
            add(FieldType.CRUISER);
            add(FieldType.DESTROYER);
            add(FieldType.DESTROYER);
            add(FieldType.DESTROYER);
            add(FieldType.MOTOR_BOAT);
            add(FieldType.MOTOR_BOAT);
            add(FieldType.MOTOR_BOAT);
            add(FieldType.MOTOR_BOAT);
        }
    };

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

    public void placeShips(Field field) {
        for (FieldType ship : ships) {
            Cell startCell, endCell;
            do {
                startCell = getCellFromUser(ship);
                endCell = getCellFromUser(ship);
                try {
                    placeShip(field, startCell, endCell, ship);
                } catch (CellIsNotEmptyException | InvalidPointsException e) {
                    e.printStackTrace();
                }
                UiUtils.drawDeck(field.getPlayerDeck());
            } while (isCellsValid(field, startCell, endCell));
        }
    }

    private void placeShip(
            Field field,
            Cell startCell,
            Cell endCell,
            FieldType currShip
    ) throws CellIsNotEmptyException, InvalidPointsException {
        if (!isCellValidForShip(startCell, endCell, currShip)) {
            throw new InvalidPointsException();
        }
        if (isCellsValid(field, startCell, endCell)) {
            if (isHorizontal(startCell, endCell)) {
                for (int i = 0; i < currShip.getLength(); i++) {
                    field.changeCellState(currShip, startCell.getX(), startCell.getY() + i);
                }
            } else {
                for (int i = 0; i < currShip.getLength(); i++) {
                    field.changeCellState(currShip, startCell.getX() + i, startCell.getY());
                }
            }
            setBlockedCells(field, startCell, endCell);
        } else {
            throw new CellIsNotEmptyException();
        }
    }

    private Cell getCellFromUser(FieldType ship) {
        String point;
        Pattern pattern = Pattern.compile("[a-j][0-9]");
        Matcher matcher;
        do {
            point =
                    readLineFromConsole(
                            String.format(
                                    "Выбирите клетку, куда хотите поставить корабль: %s", ship.toString())
                    );
            matcher = pattern.matcher(point);
        }
        while (matcher.find());
        return CellUtils.stringToCell(point);
    }

    private void setBlockedCells(Field field, Cell startCell, Cell endCell) {
        Cell[][] deck = field.getPlayerDeck();
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
        // Лево право
        if (deck[i][j].getFieldType() != FieldType.EMPTY && deck[i][j].getFieldType() != FieldType.BLOCKED) {
            if (j + 1 < Field.FIELD_SIZE) {
                deck[i][j + 1].setFieldType(FieldType.BLOCKED);
            }
            if (j - 1 > 0) {
                deck[i][j - 1].setFieldType(FieldType.BLOCKED);
            }
        }
        // Верх
        if (startCell.getX() - 1 > 0) {
            deck[startCell.getX() - 1][startCell.getY()].setFieldType(FieldType.BLOCKED);
        }
        if (startCell.getX() - 1 > 0 && startCell.getY() - 1 > 0) {
            deck[startCell.getX() - 1][startCell.getY() - 1].setFieldType(FieldType.BLOCKED);
        }
        if (startCell.getX() - 1 > 0 && startCell.getY() + 1 > 0) {
            deck[startCell.getX() - 1][startCell.getY() + 1].setFieldType(FieldType.BLOCKED);
        }
        // Низ
        if (startCell.getX() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY()].setFieldType(FieldType.BLOCKED);
        }
        if (startCell.getX() + 1 < Field.FIELD_SIZE && startCell.getY() - 1 > 0) {
            deck[endCell.getX() + 1][endCell.getY() - 1].setFieldType(FieldType.BLOCKED);
        }
        if (startCell.getX() + 1 < Field.FIELD_SIZE && startCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY() + 1].setFieldType(FieldType.BLOCKED);
        }
    }

    private void placeHorizontalBlock(Cell[][] deck, Cell startCell, Cell endCell, int i, int j) {
        // Верх низ
        if (deck[i][j].getFieldType() != FieldType.EMPTY &&
                deck[i][j].getFieldType() != FieldType.BLOCKED) {
            if (i + 1 < Field.FIELD_SIZE) {
                deck[i + 1][j].setFieldType(FieldType.BLOCKED);
            }
            if (i - 1 > 0) {
                deck[i - 1][j].setFieldType(FieldType.BLOCKED);
            }
        }
        // Левые боковушки
        if (startCell.getY() - 1 > 0) {
            deck[startCell.getX()][startCell.getY() - 1].setFieldType(FieldType.BLOCKED);
        }
        if (startCell.getX() + 1 < Field.FIELD_SIZE && startCell.getY() - 1 > 0) {
            deck[startCell.getX() + 1][startCell.getY() - 1].setFieldType(FieldType.BLOCKED);
        }
        if (startCell.getX() - 1 > 0 && startCell.getY() - 1 > 0) {
            deck[startCell.getX() - 1][startCell.getY() - 1].setFieldType(FieldType.BLOCKED);
        }
        // Правые боковушки
        if (endCell.getX() - 1 > 0 && endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() - 1][endCell.getY() + 1].setFieldType(FieldType.BLOCKED);
        }
        if (endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX()][endCell.getY() + 1].setFieldType(FieldType.BLOCKED);

        }
        if (endCell.getX() + 1 < Field.FIELD_SIZE && endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY() + 1].setFieldType(FieldType.BLOCKED);
        }
    }

    private boolean isCellsValid(Field field, Cell firstCell, Cell secondCell) {
        return field.isCellEmptyInPlayerDeck(firstCell) && field.isCellEmptyInPlayerDeck(secondCell);
    }

    private boolean isCellValidForShip(Cell firstCell, Cell secondCell, FieldType fieldType) {
        int dx = secondCell.getX() - firstCell.getX();
        int dy = secondCell.getY() - firstCell.getY();
        return dx == fieldType.getLength() - 1 || dy == fieldType.getLength() - 1;
    }
}