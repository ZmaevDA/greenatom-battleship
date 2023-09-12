package entity.ship;

public class Destroyer extends AbstractShip {

    private final static int hp = 2;

    public Destroyer(int startCell, int endCell) {
        super(hp, startCell, endCell);
    }

}
