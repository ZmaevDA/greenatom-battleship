package entity.ship;

public abstract class AbstractShip implements Ship {

    private int hp;
    private int startCell;
    private int endCell;

    public AbstractShip(int hp, int startCell, int endCell) {
        this.hp = hp;
        this.startCell = startCell;
        this.endCell = endCell;
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }
}
