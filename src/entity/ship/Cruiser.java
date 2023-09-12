package entity.ship;

public class Cruiser extends AbstractShip {
    private final static int hp = 3;

    public Cruiser(int startCell, int endCell) {
        super(hp, startCell, endCell);
    }
}
