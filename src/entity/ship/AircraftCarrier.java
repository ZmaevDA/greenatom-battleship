package entity.ship;

public class AircraftCarrier extends AbstractShip {

    private final static int hp = 4;

    public AircraftCarrier(int startCell, int endCell) {
        super(hp, startCell, endCell);
    }
}
