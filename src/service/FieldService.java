package service;

import entity.Cell;
import entity.Field;
import entity.Player;
import entity.Ship;
import exception.InvalidCellSizeException;
import exception.InvalidPointsException;
import state.FieldState;
import state.GameState;
import state.ShipType;
import utils.CellUtils;
import utils.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.ConsoleUtils.readCellFromConsole;

public class FieldService {

    public List<Field> createFields(int playerAmount) {
        return new ArrayList<>() {
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
        for (Map.Entry<Player, List<Ship>> playerShip : playerShipsMap.entrySet()) {
            for (Ship ship : playerShip.getValue()) {
                UiUtils.drawDeck(playerFieldMap.get(playerShip.getKey()), GameState.PREPARE);
                Field field = playerFieldMap.get(playerShip.getKey());
                Cell startCell = null;
                Cell endCell = null;
                boolean isValidForShip = false;
                do {
                    try {
                        startCell = getCellFromUser(playerShip.getKey(), ship.getShipType());
                        endCell = getCellFromUser(playerShip.getKey(), ship.getShipType());
                        isValidForShip = isCellValidForShip(startCell, endCell, ship);
                    } catch (InvalidPointsException | InvalidCellSizeException e) {
                        System.out.println(e.getMessage());
                    }
                } while (startCell == null || endCell == null || !isCellsValid(field, startCell, endCell) || !isValidForShip);
                placeShip(field, startCell, endCell, ship, cellShipMap);
            }
        }
    }

    private void placeShip(
            Field field,
            Cell startCell,
            Cell endCell,
            Ship currShip,
            Map<Cell, Ship> cellShipMap
    ) {
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
    }


    private Cell getCellFromUser(Player player, ShipType ship) throws InvalidPointsException, InvalidCellSizeException {
        String point =
                readCellFromConsole(
                        String.format(
                                "%s выбирите клетку, куда хотите поставить %s\n", player, ship.toString())
                );
        return CellUtils.stringToCell(point);
    }

    private void setBlockedCells(Field field, Cell startCell, Cell endCell) {
        Cell[][] deck = field.getSelfDeck();
        if (isHorizontal(startCell, endCell)) {
            placeHorizontalBlock(deck, startCell, endCell);
        } else {
            placeVerticalBlock(deck, startCell, endCell);
        }
    }

    private boolean isHorizontal(Cell startCell, Cell endCell) {
        return startCell.getX() == endCell.getX();
    }

    private void placeVerticalBlock(Cell[][] deck, Cell startCell, Cell endCell) {
        // Лево
        if (startCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[startCell.getX()][startCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getY() - 1 >= 0) {
            deck[startCell.getX()][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        // Право
        if (endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX()][endCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
        if (endCell.getY() - 1 >= 0) {
            deck[endCell.getX()][endCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        for (int k = startCell.getX(); k < endCell.getX(); k++) {
            if (startCell.getY() + 1 < Field.FIELD_SIZE) {
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
        if (startCell.getX() - 1 > 0 && startCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[startCell.getX() - 1][startCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
        // Низ
        if (endCell.getX() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY()].setFieldType(FieldState.BLOCKED);
        }
        if (endCell.getX() + 1 < Field.FIELD_SIZE && endCell.getY() - 1 > 0) {
            deck[endCell.getX() + 1][endCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        if (endCell.getX() + 1 < Field.FIELD_SIZE && endCell.getY() + 1 < Field.FIELD_SIZE) {
            deck[endCell.getX() + 1][endCell.getY() + 1].setFieldType(FieldState.BLOCKED);
        }
    }

    private void placeHorizontalBlock(Cell[][] deck, Cell startCell, Cell endCell) {
        // Верх низ
        for (int k = startCell.getY(); k < endCell.getY() + 1; k++) {
            if (startCell.getX() + 1 < Field.FIELD_SIZE) {
                deck[startCell.getX() + 1][k].setFieldType(FieldState.BLOCKED);
            }
            if (startCell.getX() - 1 >= 0) {
                deck[startCell.getX() - 1][k].setFieldType(FieldState.BLOCKED);
            }
        }
        // Левые боковушки
        if (startCell.getY() - 1 >= 0) {
            deck[startCell.getX()][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getX() + 1 < Field.FIELD_SIZE && startCell.getY() - 1 >= 0) {
            deck[startCell.getX() + 1][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        if (startCell.getX() - 1 >= 0 && startCell.getY() - 1 >= 0) {
            deck[startCell.getX() - 1][startCell.getY() - 1].setFieldType(FieldState.BLOCKED);
        }
        // Правые боковушки
        if (endCell.getX() - 1 >= 0 && endCell.getY() + 1 < Field.FIELD_SIZE) {
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

    private boolean isCellValidForShip(Cell firstCell, Cell secondCell, Ship ship) throws InvalidCellSizeException {
        int dx = secondCell.getX() - firstCell.getX();
        int dy = secondCell.getY() - firstCell.getY();
        boolean res = dx == ship.getHp() - 1 || dy == ship.getHp() - 1;
        if (!res) {
            throw new InvalidCellSizeException();
        }
        return true;
    }
}