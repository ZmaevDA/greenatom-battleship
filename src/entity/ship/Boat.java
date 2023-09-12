package entity.ship;

public class Boat extends AbstractShip {
    private final static int hp = 1;

    public Boat(int startCell, int endCell) {
        super(hp, startCell, endCell);
    }
}
